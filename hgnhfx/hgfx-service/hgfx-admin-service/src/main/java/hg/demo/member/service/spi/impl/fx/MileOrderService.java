package hg.demo.member.service.spi.impl.fx;


import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.service.cache.FixedPriceSetManager;
import hg.demo.member.service.dao.hibernate.AuthUserDAO;
import hg.demo.member.service.dao.hibernate.fx.MileOrderDao;
import hg.demo.member.service.qo.hibernate.fx.MileOrderQo;
import hg.demo.member.service.qo.hibernate.fx.OldDistributorQo;
import hg.framework.common.util.UUIDGenerator;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.abnormalRule.CheckAbnormalRuleCommand;
import hg.fx.command.mileOrder.CheckMileOrderCommand;
import hg.fx.command.mileOrder.ConfirmMileOrderCommand;
import hg.fx.command.mileOrder.CreateMileOrderCommand;
import hg.fx.command.mileOrder.ImportMileOrderCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.MileOrder;
import hg.fx.spi.AbnormalRuleSPI;
import hg.fx.spi.ProductInUseSPI;
import hg.fx.spi.ReserveInfoSPI;
import hg.fx.util.CodeUtil;
import hg.fx.util.DateUtil;
import hg.hjf.nh.NHFile;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
@Transactional
@Service
public class MileOrderService extends
		BaseService /*<MileOrder, MileOrderQo, MileOrderDao>*/ {
	@Autowired
	private MileOrderDao mileOrderDao;
	@Autowired
	private OldDistributorService distributorService;
	@Autowired
	private ReserveInfoSPI reserveInfoSPI;
	@Autowired
	JedisPool jedisPool;
	@Autowired
	AuthUserDAO authUserDAO;
	@Autowired
	AbnormalRuleSPI abnormalRuleSPI;
	@Autowired
	ProductInUseSPI productInUseSPI;
	
	@Autowired
	private FixedPriceSetManager fixedManager;

	private  Logger logger = LoggerFactory.getLogger(MileOrderService.class);
	/**
	 * 是否要检查 风险控制
	 */
	boolean checkAbnormal=true;
	
	
	protected MileOrderDao getDao() {
		return mileOrderDao;
	}

	
	public static boolean hasNumer(String str){
		  for (int i = 0; i < str.length(); i++){
		   if (Character.isDigit(str.charAt(i))){
		    return true;
		   }
		  }
		  return false;
	}
	
	/**
	 * 批量导入订单，检查卡号.
	 * @param importMileOrderCommand
	 * @return 错误在前的list
	 */
	public ImportMileOrderCommand importExcel(ImportMileOrderCommand importMileOrderCommand) {
		ImportMileOrderCommand okList = new ImportMileOrderCommand();
		ImportMileOrderCommand errorList = new ImportMileOrderCommand();
		
		int ok=0;
	    Long okMiles=0l;
		List<CreateMileOrderCommand> l = importMileOrderCommand.getList();
		for (CreateMileOrderCommand createMileOrderCommand : l) {
			if( StringUtils.isEmpty(createMileOrderCommand.getCsairCard())
				){
				errorList.getList().add(createMileOrderCommand);
				createMileOrderCommand.setErrorTip("卡号不能为空");
				continue;
			}
			if( StringUtils.isEmpty(createMileOrderCommand.getCsairName())
				|| hasNumer(createMileOrderCommand.getCsairName())	){
					errorList.getList().add(createMileOrderCommand);
					createMileOrderCommand.setErrorTip("姓名不能为空或数字");
					continue;
				}
			try {
				if( StringUtils.isNotEmpty( createMileOrderCommand.getCsairName())){
					
//					
						if(createMileOrderCommand.getCsairName().getBytes("utf-8").length>40){
							errorList.getList().add(createMileOrderCommand);
							createMileOrderCommand.setErrorTip("姓名异常，超过40个字符");
							continue;
						}
						if(!Pattern.matches("^([\u4e00-\u9fa5]{1,13}|[a-zA-Z]{1,40})$", createMileOrderCommand.getCsairName())){
							errorList.getList().add(createMileOrderCommand);
							createMileOrderCommand.setErrorTip("姓名异常，只能为字符或汉字");
							continue;
							
						}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}			
			if(createMileOrderCommand.getNum()==null ||createMileOrderCommand.getNum()<=0
					){
					errorList.getList().add(createMileOrderCommand);
					createMileOrderCommand.setErrorTip("积分不能为空或负");
					continue;
				}
			if(  createMileOrderCommand.getNum()%100 !=0 ){
					errorList.getList().add(createMileOrderCommand);
					createMileOrderCommand.setErrorTip("积分只能为100的倍数");
					continue;
				}
			if(  createMileOrderCommand.getNum() > 99999999 ){
				errorList.getList().add(createMileOrderCommand);
				createMileOrderCommand.setErrorTip("积分顶多为8位");
				continue;
			}			
			//卡号格式
			final boolean checkCardValid = NHFile.checkCardValid(createMileOrderCommand.getCsairCard());
			
			
			if(checkCardValid){
				okList.getList().add(createMileOrderCommand);
				ok++;
				okMiles += createMileOrderCommand.getNum()*createMileOrderCommand.getUnitPrice();
			}
			else{
				errorList.getList().add(createMileOrderCommand);
				createMileOrderCommand.setErrorTip("卡号格式错误");
			}
		}

		ImportMileOrderCommand command = new ImportMileOrderCommand();
		command.getList().addAll(errorList.getList());
		command.getList().addAll(okList.getList());
		command.setOkRows(ok);
		command.setOkMiles(okMiles);
		return command;
	}

	
	/**
	 * 提交导入的订单。逐笔处理：预付金够，冻结预付金，生成订单和预付金变化明细，否则生成状态为取消的订单。
	 * @param importMileOrderCommand
	 * @return 
	 */
	public ImportMileOrderCommand submitBatch(ImportMileOrderCommand importMileOrderCommand) {
		final String distributorId = importMileOrderCommand.getDistributorId();
		Distributor distributor = distributorService.get(distributorId);

		List<CreateMileOrderCommand> l = importMileOrderCommand.getList();
		int i=0;
		for (CreateMileOrderCommand create : l) {
			
			if(l.size()>=500 && i++ % 500==0){
//				System.out.println(DateUtil.formatDateTime4(new Date())+ " processed "+i);
			}
			
			if( create.getErrorTip()!=null)
				throw new RuntimeException("至少一个错误，不能提交，错误为"+create.getErrorTip());
			
			//check 商品
			final String productId = create.getProduct().getId();
			boolean check=productInUseSPI.checkInUse(distributorId, productId);
			if(!check){
				throw new RuntimeException("提交的商品不是使用中的商品。productId:"+productId+",distributorId:"+distributorId);
			}
			
			//check ordercode
			if(StringUtils.isNotBlank(create.getOrderCode())){
				MileOrderQo qo=new MileOrderQo();
				qo.setDistributorId(distributorId);
				qo.setOrderCode(create.getOrderCode());
				if(mileOrderDao.queryUnique(qo) !=null){
					throw new RuntimeException("商户订单号重复！商户，订单号分别为"+distributorId+","+create.getOrderCode());
				}
			}
			
			// 获取本月一号日期 2016-01
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, 1);
			String fistday = DateUtil.formatDate2(c.getTime());
			// 每月1号 00:31之前系统维护定价折扣 禁止下单
			if ( DateUtil.parseDateTime1(fistday+" 00:30:59").after(new Date()) ){
				throw new RuntimeException("系统维护中，请稍后重试！");
			}
			
			
			MileOrder mileOrder = new MileOrder();
			mileOrder.create(create, importMileOrderCommand,distributor.getCode());
			
			// 根据经销商折扣模式为定价时    计算折扣后的订单金额
			if (distributor.getDiscountType() == Distributor.DISCOUNT_TYPE_FIXED_PRICE){
				
				String percent = fixedManager.getCurrentFixedPriceSet(create.getProduct().getId(), distributorId);
				// 没对应折扣时用原价
				if (StringUtils.isNotBlank(percent)){
					// 折扣 *订单价格
					Long newCount = BigDecimal.valueOf(Double.valueOf(percent)).multiply(BigDecimal.valueOf(mileOrder.getCount())).longValue();
					mileOrder.setCount(newCount);
				}
			}
			
			// 冻结预付金
			final String freeze = reserveInfoSPI.freeze(mileOrder);
			//freeze ok
			if(freeze==null){
				CheckAbnormalRuleCommand command = new CheckAbnormalRuleCommand();
				command.setCardNo(mileOrder.getCsairCard());
				command.setMileageNum(mileOrder.getCount());
				command.setFromPlatform(mileOrder.getDistributor().getId());
				command.setProdId(mileOrder.getProduct().getId());
				
				String result = checkAbnormal && !mileOrder.getStatus().equals(	MileOrder.STATUS_CANCEL) ? 
						abnormalRuleSPI.conformOrderToTheRule(command) : 
							null;
				//  风险ok
				if(StringUtils.isNotBlank(result)){
					mileOrder.setErrorReason(result);
					mileOrder.setStatus(MileOrder.STATUS_NO_CHECK);
				}else{
					mileOrder.setStatus(MileOrder.STATUS_CHECK_PASS);
					mileOrder.setCheckDate(new Date());
				}
			}else{
				mileOrder.setStatus(MileOrder.STATUS_CANCEL);
				mileOrder.setErrorReason("备付金冻结失败，订单取消。"+freeze);
				
			}
			create.setSavedOrder(mileOrder);
			
			//补充冗余字段
			mileOrder.setProductName(mileOrder.getProduct().getProdName());
			mileOrder.setMerName(distributor.getName());
			mileOrder.setChannelName(mileOrder.getProduct().getChannel().getChannelName());
			
			getDao().save(mileOrder);
		}
		return importMileOrderCommand;
	}

	/**
	 * 订单批量通过、拒绝， 输入ids记录id数组，flag=true通过 false拒绝
	 * @param ids
	 * @param flag
	 * @throws Exception
	 */
	public void  batchCheck(CheckMileOrderCommand cmd, Boolean flag)throws Exception{
		 
		if(flag)
			check(cmd);
		else
			checkNOPASS(cmd);
	}
	
	/**
	 * 订单批量确认、拒绝， 输入ids记录id数组，flag=true通过 false拒绝
	 * @param ids
	 * @param flag
	 * @throws Exception
	 */
	public void  batchConfirm(ConfirmMileOrderCommand cmd, Boolean flag) {
		 
		{
			List<String> idList = cmd.getIds();
			int i=0;
			for (String id : idList) {
				MileOrder mileOrder =getDao(). get(id);
				if (mileOrder.getStatus().intValue() == MileOrder.STATUS_CHECK_PASS) {
					i++;
					if (flag) {
						mileOrder.setStatus(MileOrder.STATUS_CONFIRM_PASS);
					} else {
						mileOrder.setStatus(MileOrder.STATUS_CHECK_NO_PASS);
						mileOrder.setRefuseReason(cmd.getRefuseReason());
						
						//冻结取消
						reserveInfoSPI.cancelFreeze(mileOrder);

					}
					 
					mileOrder.setConfirmPerson(cmd.getConfirmPerson());
					mileOrder.setConfirmDate( new Date());
					getDao().update(mileOrder);
				}
			}
			logger.info(String.format("人工确认订单%d个，操作 %s",i,flag?"通过":"拒绝"));
		}
	}
	
	/**
	 * 批量通过审核
	 * @param command
	 */
	public void check(CheckMileOrderCommand command) {
		List<String> idList = command.getIds();
		for (String id : idList) {
			MileOrder mileOrder =getDao(). get(id);
			if (mileOrder.getStatus().intValue() == MileOrder.STATUS_NO_CHECK) {
				
				//reserveInfoSPI.finishFreeze(mileOrder);
				
				mileOrder.setStatus(MileOrder.STATUS_CHECK_PASS);
				mileOrder.setReason(command.getReason());
				AuthUser checkPerson =authUserDAO.get(command.getCheckPersonId());
				mileOrder.setCheckPerson(checkPerson);
				mileOrder.setAduitPerson(command.getAduitPerson());
				mileOrder.setCheckDate(new Date());
				getDao().update(mileOrder);
			}
		}

	}

	/**
	 * 批量拒绝
	 * @param command
	 */
	public void checkNOPASS(CheckMileOrderCommand command) {
		List<String> idList = command.getIds();
		for (String id : idList) {
			MileOrder mileOrder =getDao(). get(id);
			if (mileOrder.getStatus().intValue() == MileOrder.STATUS_NO_CHECK) {
				
				//冻结取消
				reserveInfoSPI.cancelFreeze(mileOrder);
				
				//订单更新
				mileOrder.setStatus(MileOrder.STATUS_CHECK_NO_PASS);
				AuthUser checkPerson =authUserDAO.get(command.getCheckPersonId());
				mileOrder.setCheckPerson(checkPerson);
				mileOrder.setAduitPerson(command.getAduitPerson());
				mileOrder.setCheckDate(new Date());
				mileOrder.setErrorReason(command.getReason());
				getDao().update(mileOrder);
			}
		}

	}

	 

	/**
	 * 提交订单给外部处理
	 * @param sendMileOrderList
	 */
	public void sendOrder(List<MileOrder> sendMileOrderList) {
		if (sendMileOrderList == null) {
			return;
		}
		Date d = new Date();
		for (MileOrder mileOrder : sendMileOrderList) {
			mileOrder.setSendStatus(MileOrder.SEND_STATUS_YES);
			mileOrder.setToCsairDate(d);
			getDao().	update(mileOrder);
		}

	}
	/**
	 * 提交订单给外部处理
	 * @param id
	 */
	public void sendOrderByOrderCode(String id) {
		MileOrder mileOrder=get(id);
		if(mileOrder==null){
			return;
		}
		Date d = new Date();
		mileOrder.setSendStatus(MileOrder.SEND_STATUS_YES);
		mileOrder.setToCsairDate(d);
		update(mileOrder);

	}

	/**
	 * @deprecated 仅限批处理任务调用
	 * @param mileOrder
	 */
	public void update(MileOrder mileOrder) {
		getDao().update(mileOrder);
	}



	public MileOrder get(String id) {
		return getDao().get(id);
	}


	/**
	 * 标记 ：提交 汇积分 这个 特定商户的订单给外部处理
	 * @param mileOrder
	 * @return
	 */
	public String saveHjfMileOrder(MileOrder mileOrder) {
		OldDistributorQo qo = new OldDistributorQo();
		qo.setName("汇积分");
		qo.setNameLike(false);
		Distributor d = distributorService.queryUnique(qo);
		mileOrder.setFlowCode(CodeUtil.getMileOrderFlowCode(d.getCode()));
		mileOrder.setId(UUIDGenerator.getUUID());
		//mileOrder.setStatusRemoved(false);
		mileOrder.setDistributor(d);
		mileOrder.setSendStatus(MileOrder.SEND_STATUS_YES);
		mileOrder.setImportDate(new Date());

		getDao().save(mileOrder);
		return "1";
	}

	/**
	 * 
	 * @方法功能说明：更新返回订单状态
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2016-2-18下午4:59:09
	 * @version：
	 * @修改内容：
	 * @参数：@param orderCode
	 * @参数：@param csairInfo
	 * @参数：@param Status
	 * @参数：@return
	 * @return:String
	 * @throws
	 * 
	 */
	public String updateReturnMileOrder(String orderCode, String csairInfo,
			int Status) {
		MileOrderQo qo = new MileOrderQo();
		qo.setOrderCode(orderCode);
		MileOrder mileOrder =getDao(). queryUnique(qo);
		mileOrder.setStatus(Status);
		mileOrder.setCsairInfo(csairInfo);
		mileOrder.setCsairReturnDate(new Date());
		update(mileOrder);
		return "1";
	}


	public List<MileOrder> queryList(MileOrderQo qo) {
		return mileOrderDao.queryList(qo);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
//		System.out.println(hasNumer("邢1"));
//		System.out.println(hasNumer("邢xinglj"));
//		System.out.println(200%100);
//		System.out.println(201%100);
//		System.out.println("这个是汉字，邢立军十这个是汉字，邢立军十".getBytes("utf-8").length);
	}


	public boolean isCheckAbnormal() {
		return checkAbnormal;
	}


	public void setCheckAbnormal(boolean checkAbnormal) {
		this.checkAbnormal = checkAbnormal;
	}
	 
}	