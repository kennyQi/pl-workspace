package hsl.app.service.local.line;

import com.alibaba.fastjson.JSON;
import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hg.common.util.EntityConvertUtils;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.dao.line.*;
import hsl.domain.model.xl.DateSalePrice;
import hsl.domain.model.xl.DayRoute;
import hsl.domain.model.xl.Line;
import hsl.domain.model.xl.LineImage;
import hsl.domain.model.xl.order.LineSnapshot;
import hsl.pojo.command.line.InitLineCommand;
import hsl.pojo.exception.LineException;
import hsl.pojo.qo.line.HslLineQO;
import hsl.pojo.util.LogFormat;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plfx.api.client.api.v1.xl.request.qo.XLLineApiQO;
import plfx.api.client.api.v1.xl.response.XLQueryLineResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.SlfxApiClient;
import plfx.xl.pojo.dto.line.LineDTO;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
@Service
@Transactional
public class LineLocalService extends BaseServiceImpl<Line, HslLineQO, LineDAO>{
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
	 * 拉去进度监听器
	 */
	private LinkedList<LineLocalService.ProgressListener> listeners = new LinkedList<LineLocalService.ProgressListener>();
	
	/**
	 * 商旅分销客户端
	 */
	@Autowired
	private SlfxApiClient slfxApiClient;
	/**
	 * 线路dao
	 */
	@Autowired
	private LineDAO lineDAO;
	/**
	 * 线路快照dao
	 */
	@Autowired
	private LineSnapshotDAO lineSnapshotDAO;
	/**
	 * 每日路线dao
	 */
	@Autowired
	private DayRouteDAO dayRouteDAO;
	/**
	 * 价格日历dao
	 */
	@Autowired
	private DateSalePriceDAO dateSalePriceDAO;
		
	@Autowired
	private LineImageDAO lineImageDAO;
	
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
				e.printStackTrace();
				HgLogger.getInstance().error("zhuxy", LogFormat.log("初始化线路数据异常", HgLogger.getStackTrace(e)));
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
		
		int pageSize = 50;
		int pageNo = 1;
		XLQueryLineResponse response = getLineFromRemote(pageSize,pageNo,command);
		HgLogger.getInstance().info("renfeng", LogFormat.log("直销admin同步线路数据，分销返回结果集：",JSON.toJSONString(response.getTotalCount())+"查询的页数："+pageNo));
		try{
			if(null == response.getTotalCount() || response.getTotalCount() <=0){//没有数据
				return;
			}
			long totalCount = response.getTotalCount();
			//保存数据
			saveLines(response.getLineList());
			while (!STOP) {
				HgLogger.getInstance().info("renfeng", LogFormat.log("直销admin同步线路数据，pageNo：",pageNo+""));
				if(totalCount>pageSize*pageNo){//还有数据
					
					RATE = (pageSize*pageNo)/totalCount*100;
					fireProgressListeners(RATE);//触发监听器
					pageNo ++;
					HgLogger.getInstance().info("renfeng", LogFormat.log("直销admin同步线路数据，pageNo：", pageNo + ",RATE:"+RATE+""));
					XLQueryLineResponse resp = getLineFromRemote(pageSize, pageNo,command);
					saveLines(resp.getLineList());
				}else{
					
					HgLogger.getInstance().info("renfeng", LogFormat.log("直销admin同步线路数据，pageNo：",pageNo+",RATE:"+RATE+""));
					return;
				}
			}
			
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}catch(LineException e){
			if(e.getCode().equals(LineException.NODATA)){
				return;
			}
		}
		
	}

	/**
	 * 保存线路对象
	 * @throws LineException 
	 */
	private void saveLines( List<LineDTO> lines) throws LineException {
		HgLogger.getInstance().info("renfeng", LogFormat.log("直销admin同步线路数据",JSON.toJSONString(lines) ));
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
			HgLogger.getInstance().info("renfeng", LogFormat.log("更新或保存线路分销线路数据",JSON.toJSONString(line) ));
			Line linel = BeanMapperUtils.getMapper().map(line,Line.class);
			 	
			HslLineQO qo = new HslLineQO();
			qo.setId(linel.getId());
			qo.setForSale(0);
			qo.setGetDateSalePrice(true);
			qo.setGetPictures(true);
			Line one = lineDAO.queryUnique(qo);
			if(one!=null){
				getDao().evict(one);
				HgLogger.getInstance().info("renfeng", LogFormat.log("开始更新本地线路数据。。。。。。。。。",JSON.toJSONString(one) ));
				
				//是否更新快照
				//此处要比较一下快照的id是否相同，如果相同则不更新本地数据，并不再创建新的快照
				boolean snap = false;
				if(one.getLineSnapshotId()==null||!one.getLineSnapshotId().equals(linel.getLineSnapshotId())){
					HgLogger.getInstance().info("renfeng", LogFormat.log("更新本地线路数据：快照有更新，本地开始更新数据",JSON.toJSONString(one.getLineSnapshotId()) ));
					snap = true;
					one.setLineSnapshotId(linel.getLineSnapshotId());
				}else{
					HgLogger.getInstance().info("renfeng", LogFormat.log("更新本地线路数据：快照无更新，本地无更新",JSON.toJSONString(one.getLineSnapshotId()) ));
				}
				//有新快照，更新
				if(snap){
					one.setBaseInfo(linel.getBaseInfo());//基本信息
					if(one.getBaseInfo()!=null){
						one.getBaseInfo().setCreateDate(new Date());
					}
					one.setMinPrice(linel.getMinPrice());//最低价格
					one.setLineImageList(linel.getLineImageList());//线路首图
					one.setDateSalePriceList(linel.getDateSalePriceList());//价格日历
					
					one.setRouteInfo(linel.getRouteInfo());//行程信息
					one.setPayInfo(linel.getPayInfo());//支付信息
					one.setForSale(Line.SALE);//上架
					lineDAO.update(one);
					
					LineSnapshot lineSnapshot = new LineSnapshot();
					lineSnapshot.create(one);
					lineSnapshotDAO.save(lineSnapshot);
				}
			}else{
				HgLogger.getInstance().info("renfeng", LogFormat.log("创建线路",JSON.toJSONString(linel)));
				//创建新线路
				one = linel;
				one.setForSale(Line.SALE);
				lineDAO.save(one);
				HgLogger.getInstance().info("renfeng", LogFormat.log("创建线路成功。。。。。。。。",JSON.toJSONString(linel)));
				//创建新线路快照
				LineSnapshot lineSnapshot = new LineSnapshot();
				lineSnapshot.create(one);
				lineSnapshotDAO.save(lineSnapshot);
				HgLogger.getInstance().info("renfeng", LogFormat.log("创建线路快照成功。。。。。。。。",JSON.toJSONString(linel)));
			}

		}catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("renfeng", "同步线路失败："+HgLogger.getStackTrace(e)+JSON.toJSONString(line));
		}
	}
	/**
	 * @方法功能说明：同步线路时，清空本地级联对象
	 * @修改者名字：renfeng
	 * @修改时间：2015年5月12日下午4:13:59
	 * @修改内容：删除线路下的首图集、价格日历列表、行程列表以及每日行程下的图片集
	 * @参数：@param lineId
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean delLineCascadeEntity(LineDTO lineDTO){
		String lineId=lineDTO.getId();
		try{
			
			HslLineQO qo = new HslLineQO();
			qo.setId(lineId);
			qo.setForSale(0);
			qo.setGetDateSalePrice(true);
			Line line = lineDAO.queryUnique(qo);
			if(line==null){
				HgLogger.getInstance().info("renfeng", LogFormat.log("同步线路时，清空本地级联对象，线路为null",JSON.toJSONString(lineDTO)));
				
			}else{
				
				if(line.getLineSnapshotId()==null||!line.getLineSnapshotId().equals(lineDTO.getLineSnapshotId())){
					
					HgLogger.getInstance().info("renfeng", LogFormat.log("同步线路时，快照没有变化，开始清空本地级联对象操作。。。。。。。。。",JSON.toJSONString(line)));
					
					//清空线路首图集
					List<LineImage> lineHeadImageList=line.getLineImageList();
					if(lineHeadImageList!=null){
						for(LineImage lineImage: lineHeadImageList){
							this.lineImageDAO.delete(lineImage);
						}
						lineHeadImageList.clear();
					}
					
					//清空线路价格日历列表
					List<DateSalePrice> nlist = line.getDateSalePriceList();
					if(nlist!=null){
						for(DateSalePrice price:nlist){
							this.dateSalePriceDAO.delete(price);
						}
						nlist.clear();
					}
					
					//清空数据库里每日行程缓存： 级联清空每日行程相关图片集	
					List<DayRoute> routelist_old=line.getRouteInfo().getDayRouteList();
					if(routelist_old!=null){
						
						for(DayRoute dayRoute:routelist_old){
							
							this.dayRouteDAO.delete(dayRoute);
						}
						
						line.getRouteInfo().getDayRouteList().clear();
						
					}	
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
			
		return true;
	
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
		HgLogger.getInstance().info("renfeng", LogFormat.log("直销admin同步线路数据，分销返回结果集：",JSON.toJSONString(response.getTotalCount())+"查询的页数："+pageNo+pageSize));
		return response;
	}

	/**
	 * 获取初始化的进度
	 * @return
	 */
	public double getProgress(){
		if(!INITCUNCURRENTNUM){
			return RATE;
		}else{
			return 100;
		}
	}
	
	/**
	 * 手动终止初始化(用于终止初始化的过程)
	 */
	public void stopInit(){
		STOP = true;
	}

	@Override
	protected LineDAO getDao() {
		return lineDAO;
	}
	
	/**
	 * 添加进程监听器
	 * @throws LineException 
	 */
	public void addListener(LineLocalService.ProgressListener listener) throws LineException{
		synchronized (listeners) {
			if(listeners.size()>100){
				throw new LineException(LineException.FULL_LISTENERS, "监听器数量已达上限");
			}
			listeners.add(listener);
		}
	}
	
	/**
	 * 移除进程监听器
	 */
	public void removeListrener(LineLocalService.ProgressListener listener){
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}
	
	/**
	 * 出发进度监听器
	 */
	public void fireProgressListeners(double rate){
		for(LineLocalService.ProgressListener listener : listeners){
			try{
				listener.execute(rate);
			}catch(Exception e){
				HgLogger.getInstance().error("zhuxy",
						LogFormat.log("执行进度监听器出错", 
								"错误的监听器名字 ："+listener.getName()+"\n"+HgLogger.getStackTrace(e)));
			}
		}
	}
	
	/**
	 * 下架
	 */
	public void notForSale(String id){
		HslLineQO qo = new HslLineQO();
		qo.setId(id);
		qo.setForSale(0);
		Line line = lineDAO.queryUnique(qo);
		if(line!=null){
			line.setForSale(Line.NOT_SALE);
			getDao().update(line);
		}
	}
	
	/**
	 * 上架
	 * @param id
	 */
	public void forSale(String id){
		HslLineQO qo = new HslLineQO();
		qo.setId(id);
		qo.setForSale(0);
		Line line = getDao().queryUnique(qo);
		if(line!=null){
			line.setForSale(Line.SALE);
			getDao().update(line);
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
	 * @方法功能说明：从分销查询最新线路
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月3日上午11:26:07
	 * @修改内容：
	 * @参数：@param hslLineQo
	 * @参数：@return
	 * @return:XLQueryLineResponse
	 * @throws
	 */
	public hsl.pojo.dto.line.LineDTO getLineFromRemote(	HslLineQO hslLineQo) {
		
		hsl.pojo.dto.line.LineDTO lineDto=null;
		
		XLLineApiQO qo = new XLLineApiQO();
		if(StringUtils.isNotBlank(hslLineQo.getId())){
			qo.setId(hslLineQo.getId());
		}
				
		ApiRequest apiRequest=new ApiRequest("XLQueryLine",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), qo);
		XLQueryLineResponse response = slfxApiClient.send(apiRequest, XLQueryLineResponse.class);
		if(response!=null&&response.getLineList()!=null&&response.getLineList().size()>0){
			List<LineDTO> lineList=response.getLineList();
			lineDto=EntityConvertUtils.convertDtoToEntity(lineList.get(0), hsl.pojo.dto.line.LineDTO.class);
		}
				
		return lineDto;
	}
		

}
