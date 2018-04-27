package lxs.app.service.line;

import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lxs.app.dao.line.LxsDateSalePriceDAO;
import lxs.app.dao.line.LxsLineDAO;
import lxs.app.dao.line.LxsLineImageDao;
import lxs.app.dao.line.LxsLineSelectiveDAO;
import lxs.app.dao.line.LxsLineSnapshotDAO;
import lxs.app.util.line.ClientKeyUtil;
import lxs.domain.model.line.DateSalePrice;
import lxs.domain.model.line.DayRoute;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineImage;
import lxs.domain.model.line.LineRouteInfo;
import lxs.domain.model.line.LineSelective;
import lxs.domain.model.line.LineSnapshot;
import lxs.pojo.command.line.InitLineCommand;
import lxs.pojo.exception.line.LineException;
import lxs.pojo.qo.line.LineQO;
import lxs.pojo.qo.line.LineSelectiveQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.request.qo.xl.XLLineApiQO;
import slfx.api.v1.response.xl.XLQueryLineResponse;
import slfx.xl.pojo.dto.line.LineDTO;

import com.alibaba.fastjson.JSON;
@Service
@Transactional
public class LineService extends BaseServiceImpl<Line, LineQO, LxsLineDAO> {
	
	/**
	 * 初始化资源锁（防止重复点击）
	 */
	private static Object INITLOCK = new Object();
	/**
	 * 并发初始化数量(只有一个，用boolean代替)
	 */
	private static boolean INITCUNCURRENTNUM = true;
	/**
	 * 用与终止初始化的线程
	 */
	private static boolean STOP = false;
	/**
	 * 拉去数据的进度
	 */
	private static double RATE = 0;

	/**
	 * 线路DAO
	 */
	@Autowired
	private LxsLineDAO lineDAO;
	/**
	 * 线路快照dao
	 */
	@Autowired
	private LxsLineSnapshotDAO lineSnapshotDAO;
	/**
	 * 商旅分销客户端
	 */
	@Autowired
	private SlfxApiClient slfxApiClient;
	
	@Autowired
	private LxsLineImageDao lxslineImageDao;
	
	@Autowired
	private DayRouteService dayRouteService;
	
	@Autowired
	
	
	
	private DateSalePriceService dateSalePriceService;
	
	@Autowired
	private LxsDateSalePriceDAO lxsDateSalePriceDAO;
	
	@Autowired
	private LxsLineSelectiveDAO lxsLineSelectiveDAO;
	
	/**
	 * 拉去进度监听器
	 */
	private LinkedList<LineService.ProgressListener> listeners = new LinkedList<LineService.ProgressListener>();
	
	@Override
	protected LxsLineDAO getDao() {
		return lineDAO;
	}

	/**
	 * 初始化同步数据
	 * 该方法保证一次只有一个线程在执行初始化，其他请求线程自动返回异常，不再等待
	 * @throws Exception
	 */
	public void initLineData(InitLineCommand command) throws Exception{
		boolean flag = false;//多线程变量标签
		synchronized (INITLOCK) {
			flag = INITCUNCURRENTNUM;//获取同步权限
			if(flag){
				INITCUNCURRENTNUM = false;//取走同步权限
				STOP = false;//关掉终止标识
				RATE = 0;//进度值重置
			}
		}
		if(flag){
			try{
				getAndSetLineData(command);
			}catch(Exception e){
				HgLogger.getInstance().info("lxs_dev", "【initLineData】"+"异常，"+HgLogger.getStackTrace(e));
			}finally{
				INITCUNCURRENTNUM = true;
				STOP = false;
			}
		}else{
			throw new Exception("init is running");
		}
		
	}
	
	/**
	 * 同步线路数据
	 * @param command 
	 * @throws Exception 
	 */
	private void getAndSetLineData(InitLineCommand command) throws Exception {
		int pageSize = 10;
		int pageNo = 1;
		XLQueryLineResponse response = getLineFromRemote(pageSize,pageNo,command);
		try{
			long totalCount = response.getTotalCount();
			if(totalCount <=0){//没有数据
				return;
			}
			//保存数据
			saveLines(response.getLineList());
			while (!STOP) {
				if(totalCount>pageSize*pageNo){//还有数据
					RATE = (pageSize*pageNo)/totalCount*100;
					fireProgressListeners(RATE);//触发监听器
					pageNo ++;
					XLQueryLineResponse resp = getLineFromRemote(pageSize, pageNo,command);
					saveLines(resp.getLineList());
				}else{
					return;
				}
			}
			
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}catch(LineException e){
			if(e.getCode()==LineException.NODATA){
				return;
			}
		}
		
	}
	
	
	/**
	 * 分页查询线路
	 * @param pageSize
	 * @param pageNo
	 * @param command 
	 * @return
	 */
	private XLQueryLineResponse getLineFromRemote(int pageSize,int pageNo, InitLineCommand command) {
		XLLineApiQO qo = new XLLineApiQO();
		qo.setPageNo(pageNo);
		qo.setPageSize(pageSize);
		//看看是否有时间范围
		if(command!=null){
			if(command.getStartDate()!=null){
				qo.setLineSnapshotBeginDate(command.getStartDate());
			}
			if(command.getEndDate()!=null){
				qo.setLineSnapshotEndDate(command.getEndDate());
			}
		}
		ApiRequest apiRequest=new ApiRequest("XLQueryLine",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), qo);
		XLQueryLineResponse response = slfxApiClient.send(apiRequest, XLQueryLineResponse.class);
		HgLogger.getInstance().info("lxs_dev","【XLQueryLineResponse】"+"同步线路结果"+JSON.toJSONString(response));
		return response;
	}
	
	/**
	 * 保存线路对象
	 * @throws LineException 
	 */
	private void saveLines( List<LineDTO> lines) throws LineException {
		if(lines==null||lines.size()<=0){
			throw new LineException(LineException.NODATA,"结果为空");
		}
		for(LineDTO line : lines){
			SaveOrUpdateLine(line);
		}
	}
	
	/**
	 * 创建或者更新线路
	 * @param line
	 */
	public void SaveOrUpdateLine(LineDTO line) {
		try{
			HgLogger.getInstance().info("lxs_dev","【SaveOrUpdateLine】"+"更新或保存线路分销线路数据"+JSON.toJSONString(line));
			HgLogger.getInstance().info("lxs_dev","【SaveOrUpdateLine】"+"更新或保存线路分销线路数据线路名称:"+line.getBaseInfo().getName());
			Line linel = BeanMapperUtils.getMapper().map(line,Line.class);
			 	
			LineQO qo = new LineQO();
			qo.setId(linel.getId());
			qo.setForSale(0);
			qo.setGetDateSalePrice(true);
			qo.setGetPictures(true);
			Line one = lineDAO.queryUnique(qo);
			if(one!=null){
				
				HgLogger.getInstance().info("lxs_dev", "【SaveOrUpdateLine】"+"开始更新本地线路数据。。。。。。。。。"+JSON.toJSONString(one) );
				
				//是否更新快照
				//此处要比较一下快照的id是否相同，如果相同则不更新本地数据，并不再创建新的快照
				boolean snap = false;
				if(one.getLineSnapshotId()==null||!one.getLineSnapshotId().equals(linel.getLineSnapshotId())){
					HgLogger.getInstance().info("lxs_dev", "【SaveOrUpdateLine】"+"更新本地线路数据：快照有更新，本地开始更新数据"+JSON.toJSONString(one.getLineSnapshotId()) );
					snap = true;
					one.setLineSnapshotId(linel.getLineSnapshotId());
				}else{
					HgLogger.getInstance().info("lxs_dev","【SaveOrUpdateLine】"+"更新本地线路数据：快照无更新，本地无更新"+JSON.toJSONString(one.getLineSnapshotId()) );
				}
				
				//有新快照，更新
				if(snap){
					HgLogger.getInstance().info("lxs_dev","【SaveOrUpdateLine】"+"有快照更新"+JSON.toJSONString(one));
					HgLogger.getInstance().info("lxs_dev","【SaveOrUpdateLine】"+"有快照开始更新");
					one.setBaseInfo(linel.getBaseInfo());//基本信息
					if(one.getBaseInfo()!=null){
						one.getBaseInfo().setCreateDate(new Date());
					}
					one.setMinPrice(linel.getMinPrice());//最低价格
					
					
					
					//清空线路首图集
					List<LineImage> lineHeadImageList=one.getLineImageList();
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"开始清空线路首图"+JSON.toJSONString(lineHeadImageList));
					if(lineHeadImageList!=null){
						for(LineImage lineImage: lineHeadImageList){
							lxslineImageDao.deleteById(lineImage.getId());
						}
						lineHeadImageList.clear();
					}
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"清空首图成功");
					//清空线路价格日历列表
					List<DateSalePrice> dateSalePrices = one.getDateSalePriceList();
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"开始清空价格日历"+JSON.toJSONString(dateSalePrices));
					if(dateSalePrices!=null){
						for(DateSalePrice dateSalePrice:dateSalePrices){
							lxsDateSalePriceDAO.delete(dateSalePrice);
						}
						dateSalePrices.clear();
					}
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"清空价格日历成功");
					//清空数据库里每日行程缓存： 级联清空每日行程相关图片集	
					List<DayRoute> dayRoutes=one.getRouteInfo().getDayRouteList();
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"开始清空日程"+JSON.toJSONString(dayRoutes));
					if(dayRoutes!=null){
						for(DayRoute dayRoute:dayRoutes){
							for(LineImage lineImage:dayRoute.getLineImageList()){
								lxslineImageDao.deleteById(lineImage.getId());
							}
							dayRouteService.deleteById(dayRoute.getId());
						}
						dayRoutes.clear();
					}	
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"清空日程成功");
					
					
					
					
					
					
					
					
					
					
					
					
					
					List<LineImage> lineImages = new ArrayList<LineImage>();
					for(LineImage lineImage:linel.getLineImageList()){
						lineImage.setId(UUIDGenerator.getUUID());
						lineImages.add(lineImage);
					}
					one.setLineImageList(lineImages);//线路首图
					List<DateSalePrice> dateSalePrices1 = new ArrayList<DateSalePrice>();
					for(DateSalePrice dateSalePrice:linel.getDateSalePriceList()){
						dateSalePrice.setId(UUIDGenerator.getUUID());
						dateSalePrices1.add(dateSalePrice);
					}
					one.setDateSalePriceList(dateSalePrices1);//价格日历
					/**
					 * 【开始】为线路计算所出行的日期
					 */
					List<String> dates = new ArrayList<String>();
					for (DateSalePrice dateSalePrice : dateSalePrices1) {
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
						String date = simpleDateFormat.format(dateSalePrice.getSaleDate());
						if(!dates.contains(date)){
							dates.add(date);
						}
					}
					String saleDates = "";
					for (String string : dates) {
						saleDates+=string+":";
					}
					/**
					 * 【结束】为线路计算所出行的日期
					 */
					one.setSaleDates(saleDates);
					List<DayRoute> dayRoutes1= new ArrayList<DayRoute>();
					for(DayRoute dayRoute:linel.getRouteInfo().getDayRouteList()){
						lineImages = new ArrayList<LineImage>();
						for(LineImage lineImage:dayRoute.getLineImageList()){
							lineImage.setId(UUIDGenerator.getUUID());
							lineImages.add(lineImage);
						}
						dayRoute.setId(UUIDGenerator.getUUID());
						dayRoute.setLineImageList(lineImages);
						dayRoutes1.add(dayRoute);
					}
					LineRouteInfo lineRouteInfo = linel.getRouteInfo();
					lineRouteInfo.setDayRouteList(dayRoutes1);
					one.setRouteInfo(lineRouteInfo);//行程信息
					one.setPayInfo(linel.getPayInfo());//支付信息
					one.setForSale(Line.SALE);//上架
					HgLogger.getInstance().info("lxs_dev","【SaveOrUpdateLine】"+"有快照更新，开始线路更新"+JSON.toJSONString(one));
					lineDAO.update(one);
					HgLogger.getInstance().info("lxs_dev","【SaveOrUpdateLine】"+"有快照更新，线路更新成功");
					LineSnapshot lineSnapshot = new LineSnapshot();
					lineSnapshot.create(one);
					HgLogger.getInstance().info("lxs_dev","【SaveOrUpdateLine】"+"有快照更新，开始快照更新"+JSON.toJSONString(lineSnapshot));
					lineSnapshotDAO.save(lineSnapshot);
					HgLogger.getInstance().info("lxs_dev","【SaveOrUpdateLine】"+"有快照更新，快照更新成功");
					
				}
							
			}else{
				
				HgLogger.getInstance().info("lxs_dev", "【SaveOrUpdateLine】"+"创建线路"+JSON.toJSONString(one));
				HgLogger.getInstance().info("lxs_dev","【SaveOrUpdateLine】"+"创建线路，线路名："+line.getBaseInfo().getName());
				//创建新线路
				one = linel;
				List<DateSalePrice> dateSalePrices = linel.getDateSalePriceList();
				/**
				 * 【开始】为线路计算所出行的日期
				 */
				List<String> dates = new ArrayList<String>();
				for (DateSalePrice dateSalePrice : dateSalePrices) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
					String date = simpleDateFormat.format(dateSalePrice.getSaleDate());
					if(!dates.contains(date)){
						dates.add(date);
					}
				}
				String saleDates = "";
				for (String string : dates) {
					saleDates+=string+":";
				}
				/**
				 * 【结束】为线路计算所出行的日期
				 */
				one.setSaleDates(saleDates);
				
				one.setForSale(Line.SALE);
				lineDAO.save(one);
				HgLogger.getInstance().info("lxs_dev","【SaveOrUpdateLine】"+"创建线路，创建线路成功");
				//创建新线路快照
				LineSnapshot lineSnapshot = new LineSnapshot();
				lineSnapshot.create(one);
				lineSnapshotDAO.save(lineSnapshot);
				HgLogger.getInstance().info("lxs_dev","【SaveOrUpdateLine】"+"创建线路，创建线路快照成功");
			}

		}catch(Exception e){
			HgLogger.getInstance().info("lxs_dev", "【SaveOrUpdateLine】"+"异常，"+HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
	}

	
	
	/**
	 * 出发进度监听器
	 */
	public void fireProgressListeners(double rate){
		for(LineService.ProgressListener listener : listeners){
			try{
				listener.execute(rate);
			}catch(Exception e){
				HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"执行进度监听器出错错误的监听器名字 ："+"异常，"+HgLogger.getStackTrace(e));
			}
		}
	}
	
	
	static abstract class ProgressListener{
		private String name;
		
		public String getName() {
			return name;
		}

		public ProgressListener(String name){
			this.name = name;
		}

		/**
		 * 进度回调函数（第一个参数为百分比）
		 * @param rate
		 */
		public abstract void execute(double rate);
	}

	/**
	 * 下架
	 */
	public void notForSale(String id){
		Line line = lineDAO.get(id);
		if(line!=null){
			line.setForSale(Line.NOT_SALE);
			line.setSelectiveLine(null);
			getDao().update(line);
			
			LineSelectiveQO lineSelectiveQO = new LineSelectiveQO();
			lineSelectiveQO.setLineID(id);
			LineSelective lineSelective = lxsLineSelectiveDAO.queryUnique(lineSelectiveQO);
			if(lineSelective!=null){
				lxsLineSelectiveDAO.delete(lineSelective);
			}
		}
	}
	
	/**
	 * 上架
	 * @param id
	 */
	public void forSale(String id){
		Line line = lineDAO.get(id);
		if(line!=null){
			line.setForSale(Line.SALE);
			getDao().update(line);
		}
	}

	public boolean delLineCascadeEntity(LineDTO lineDTO){
		String lineId=lineDTO.getId();
		try{
			LineQO qo = new LineQO();
			qo.setId(lineId);
			qo.setForSale(0);
			qo.setGetDateSalePrice(true);
			Line line = lineDAO.queryUnique(qo);
			if(line==null){
				HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"同步线路时，清空本地级联对象，线路为null"+JSON.toJSONString(lineDTO));
				
			}else{
				
				if(line.getLineSnapshotId()==null||!line.getLineSnapshotId().equals(lineDTO.getLineSnapshotId())){
					
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"同步线路时，快照没有变化，开始清空本地级联对象操作。。。。。。。。。"+JSON.toJSONString(line));
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"同步线路时，快照没有变化，开始清空本地级联对象操作，线路名称"+line.getBaseInfo().getName());
					//清空线路首图集
					List<LineImage> lineHeadImageList=line.getLineImageList();
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"开始清空线路首图"+JSON.toJSONString(lineHeadImageList));
					if(lineHeadImageList!=null){
						for(LineImage lineImage: lineHeadImageList){
							lxslineImageDao.deleteById(lineImage.getId());
						}
						lineHeadImageList.clear();
					}
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"清空首图成功");
					//清空线路价格日历列表
					List<DateSalePrice> dateSalePrices = line.getDateSalePriceList();
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"开始清空价格日历"+JSON.toJSONString(dateSalePrices));
					if(dateSalePrices!=null){
						for(DateSalePrice dateSalePrice:dateSalePrices){
							lxsDateSalePriceDAO.delete(dateSalePrice);
						}
						dateSalePrices.clear();
					}
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"清空价格日历成功");
					//清空数据库里每日行程缓存： 级联清空每日行程相关图片集	
					List<DayRoute> dayRoutes=line.getRouteInfo().getDayRouteList();
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"开始清空日程"+JSON.toJSONString(dayRoutes));
					if(dayRoutes!=null){
						for(DayRoute dayRoute:dayRoutes){
							for(LineImage lineImage:dayRoute.getLineImageList()){
								lxslineImageDao.deleteById(lineImage.getId());
							}
							dayRouteService.deleteById(dayRoute.getId());
						}
						dayRoutes.clear();
					}	
					HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"清空日程成功");
				}
			}
		}catch(Exception e){
			HgLogger.getInstance().info("lxs_dev", "【delLineCascadeEntity】"+"清空线路相关数据失败"+HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 
	 * @方法功能说明：获取最大排序
	 * @修改者名字：cangs
	 * @修改时间：2015年10月12日下午1:53:10
	 * @修改内容：
	 * @参数：@return
	 * @return:int
	 * @throws
	 */
	public int getMaxSort(){
		LineQO lineQO = new LineQO();
		return lineDAO.maxProperty("sort", lineQO);
	}
}
