package hg.dzpw.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.dao.UseRecordDao;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.qo.SingleTicketQo;
import hg.dzpw.app.pojo.qo.UseRecordQo;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.SingleTicketStatus;
import hg.dzpw.domain.model.ticket.UseRecord;
import hg.dzpw.pojo.api.appv1.exception.DZPWAppApiException;
import hg.dzpw.pojo.api.appv1.response.UseTicketResponse;
import hg.dzpw.pojo.command.platform.useticket.PlatformUseTicketCommand;
import hg.log.util.HgLogger;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UseRecordLocalService extends BaseServiceImpl<UseRecord, UseRecordQo, UseRecordDao>{

	@Autowired
	private GroupTicketLocalService groupTicketLocalService;
	
	
	@Autowired
	private SingleTicketLocalService singleTicketLocalService;
	
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	
	@Autowired
	private UseRecordDao dao;
	
	@Override
	protected UseRecordDao getDao() {
		return this.dao;
	}
	
	public void useTicket(PlatformUseTicketCommand command) throws DZPWAppApiException, ParseException{
		
			SingleTicket sTicket = null;
			GroupTicketQo gt = new GroupTicketQo();
			Date now = new Date();
			
			ScenicSpotQo ssQo =  new ScenicSpotQo();
			ScenicSpot scenicSpot = null;
			
			ssQo.setId(command.getScenicSpotId());
			scenicSpot = scenicSpotLocalService.queryUnique(ssQo);
			if(scenicSpot == null){
				HgLogger.getInstance().debug("guotx", "景区验票-核销【查询不到景区，景区不存在】");
				throw new DZPWAppApiException(UseTicketResponse.RESULT_ERROR, "景区不存在", UseTicketResponse.class);
			}
			
			//查询套票
			gt.setTicketNo(command.getTicketNo());
			GroupTicket gTicket = this.groupTicketLocalService.queryUnique(gt);
			
			//查询套票下的单票
			SingleTicketQo stq = new SingleTicketQo();
			stq.setScenicSpotQo(ssQo);
			stq.setGroupTicketQo(gt);
			List<SingleTicket> singleTicketList = this.singleTicketLocalService.queryList(stq);
			
			//套票为空时
			if(gTicket==null || singleTicketList==null){
				HgLogger.getInstance().debug("guotx", String.format("景区验票-核销【查询不到购票记录，%s为空】", gTicket==null?"groupTicket":"singleTickets"));
				throw new DZPWAppApiException(UseTicketResponse.RESULT_NOT_FOUND, "查不到购票记录", UseTicketResponse.class);
			}
			//联票未到使用期 或 过期
//			if(gTicket.getUseDateStart().getTime()>now.getTime() || gTicket.getUseDateEnd().getTime()<now.getTime())
//				throw new DZPWAppApiException(
//						UseTicketResponse.RESULT_GROUP_TICKET_OUTOFDATE,
//						"对不起，您的联票有效期为" + DateUtil.formatDateTime(	gTicket.getUseDateStart(), "yyyy-MM-dd")+"至"+DateUtil.formatDateTime(gTicket.getUseDateEnd(), "yyyy-MM-dd"),
//						UseTicketResponse.class);
			
			//获取当前景区单票记录
			for(SingleTicket st :singleTicketList){
				if(st.getScenicSpot().getId().equals(scenicSpot.getId())){
					sTicket = st;
					break;
				}
			}
			
			if(sTicket==null){
				HgLogger.getInstance().debug("guotx", "景区验票-核销【未找到当前景区的票】");
				throw new DZPWAppApiException(UseTicketResponse.RESULT_ERROR, "未找到当前景区的票", UseTicketResponse.class);
			}
			//单票未入园
			if(sTicket.getStatus().getCurrent()==SingleTicketStatus.SINGLE_TICKET_CURRENT_UNUSE){
				//验证联票是否过有效期
				if(gTicket.getUseDateStart().getTime()>now.getTime() || gTicket.getUseDateEnd().getTime()<now.getTime()){
					HgLogger.getInstance().debug("guotx", String.format("景区验票-核销【首次入园，联票已过有效期，singleTicketId=%s】", gTicket.getId()));
					throw new DZPWAppApiException(
							UseTicketResponse.RESULT_TICKET_OUTOFDATE,
							"对不起，您的联票有效期为" + DateUtil.formatDateTime(	gTicket.getUseDateStart(), "yyyy-MM-dd")+"至"+DateUtil.formatDateTime(gTicket.getUseDateEnd(), "yyyy-MM-dd"),
							UseTicketResponse.class);
				}
				//初次使用
				sTicket.setFirstTimeUseDate(now);//设置初次使用时间
				
				//入园开始、结束时间以自然日为准备
				String start = DateUtil.DATE_FORMAT2().format(now)+" 00:00:00";
				sTicket.setUseDateStart(DateUtil.DATE_TIME_FORMAT().parse(start));//设置入园开始时间
				
				//计算入园结束时间
				//门票默认有效天数
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, scenicSpot.getBaseInfo().getTicketDefaultValidDays()-1);
				
				String end = DateUtil.DATE_FORMAT2().format(cal.getTime())+" 23:59:59";
				sTicket.setUseDateEnd(DateUtil.DATE_TIME_FORMAT().parse(end));
				
			}else {//非第一次入园
				//超过有效期
				if (sTicket.getUseDateStart().getTime()>now.getTime()||sTicket.getUseDateEnd().getTime()<now.getTime()) {
					HgLogger.getInstance().debug("guotx", String.format("景区验票-核销【非首次入园，单票已过有效期，singleTicketId=%s】", sTicket.getId()));
					throw new DZPWAppApiException(UseTicketResponse.RESULT_TICKET_OUTOFDATE, 
						"对不起，当前门票已超过使用有效期",UseTicketResponse.class);
				}
				UseRecordQo recordQo=new UseRecordQo();
				recordQo.setSingleTicketId(sTicket.getId());
				
				List<UseRecord> recoredList=this.queryList(recordQo);
				//当天入园次数
				int todayUseTime=0;
				Date currentDayBegin=DateUtil.dateStr2BeginDate(DateUtil.formatDate(now));
				Date currentDayEnd=DateUtil.dateStr2EndDate(DateUtil.formatDate(now));
				for (UseRecord useRecord : recoredList) {
					if (useRecord.getUseDate().getTime()>currentDayBegin.getTime() &&
							useRecord.getUseDate().getTime()<currentDayEnd.getTime()) {
						todayUseTime++;
					}
				}
				if (sTicket.getTicketPolicy().getUseInfo().getValidTimesPerDay()<=todayUseTime) {
					HgLogger.getInstance().debug("guotx", String.format("景区验票-核销【超出当日可游玩次数限制，singleTicketId=%s】", sTicket.getId()));
					throw new DZPWAppApiException(UseTicketResponse.RESULT_TICKET_DAYTIME_LIMIT,
							"超过景区当天入园次数限制",UseTicketResponse.class);
				}
			}
			//添加入园记录
			UseRecord ur = new UseRecord();
			ur.setId(UUIDGenerator.getUUID());
			ur.setGroupTicketId(gTicket==null?null:gTicket.getId());
			ur.setSingleTicketId(sTicket==null?null:sTicket.getId());
			ur.setScenicSpotId(scenicSpot.getId());
			ur.setCheckType(Integer.valueOf(command.getCheckWay()));
			ur.setUseDate(now);
			this.dao.save(ur);
			
			int num = 0;
			for(SingleTicket st :singleTicketList){
				if(st.getFirstTimeUseDate()!=null)
					num = 1+num;
			}
			
			//TODO 修改联票和订单状态
//			if(num>0 && num<singleTicketList.size()){
//				gTicket.getStatus().setCurrent(hg.dzpw.pojo.common.DZPWConstants.GROUP_TICKET_STATUS_USE_SOME);
//			}else if(num == singleTicketList.size()){
//				gTicket.getStatus().setCurrent(hg.dzpw.pojo.common.DZPWConstants.GROUP_TICKET_STATUS_USE_ALL);
//			}
			
			this.groupTicketLocalService.update(gTicket);
			
			//设置入园手续费
			this.groupTicketLocalService.update(gTicket);
			Double settlementFee=sTicket.getSettlementInfo().getSettlementFee();
			settlementFee=(settlementFee==null?0:settlementFee);
			settlementFee+=scenicSpot.getSettleInfo().getSettlementFee();
			sTicket.getSettlementInfo().setSettlementFee(settlementFee);
			
			//TODO 结算给景区
			//修改单票状态
			sTicket.getStatus().setCurrent(SingleTicketStatus.SINGLE_TICKET_CURRENT_USED);
			this.singleTicketLocalService.update(sTicket);
	}
}