package slfx.jp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.app.dao.AskOrderTicketDAO;
import slfx.jp.app.dao.JPOrderDAO;
import slfx.jp.app.dao.PassengerDAO;
import slfx.jp.command.client.YGAskOrderTicketCommand;
import slfx.jp.domain.model.order.FlightTicket;
import slfx.jp.domain.model.order.JPOrder;
import slfx.jp.domain.model.order.Passenger;
import slfx.jp.pojo.dto.flight.FlightPassengerDTO;
import slfx.jp.pojo.dto.flight.TicketDTO;
import slfx.jp.pojo.dto.supplier.yg.YGAskOrderTicketDTO;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.yg.open.inter.YGFlightService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：LOCAL请求出票SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:43:40
 * @版本：V1.0
 *
 */
@Service
@Transactional
public class PlatformAskOrderTicketLocalService extends BaseServiceImpl<JPOrder, PlatformOrderQO, JPOrderDAO> {

	@Autowired
	private YGFlightService ygFlightService;
	
	@Autowired
	private JPOrderDAO jpOrderDAO;
	
	@Autowired
	private AskOrderTicketDAO askOrderTicketDAO;
	
	@Autowired
	private PassengerDAO passengerDAO;
	
	@Autowired
	private DomainEventRepository domainEventRepository;
	
	@Override
	protected JPOrderDAO getDao() {
		return null;
	}
	
	public boolean askOrderTicket(YGAskOrderTicketCommand command) {
		HgLogger.getInstance().info("tandeng","LocalPlatformAskOrderTicketService->shopCreateOrder->YGAskOrderTicketCommand[平台请求易购出票]:"+ JSON.toJSONString(command));
		boolean bool =false;
		
		if (command == null) {
			return bool;
		}
		//请求出票
		YGAskOrderTicketDTO ygAskOrderTicketDTO = ygFlightService.askOrderTicket(command);	
		
		if(ygAskOrderTicketDTO != null && "0".equals(ygAskOrderTicketDTO.getErrorCode().trim())){
			bool = true;
		}

		HgLogger.getInstance().info("tandeng","LocalPlatformAskOrderTicketService->shopCreateOrder->YGAskOrderTicketCommand[平台请求易购出票结束],bool="+bool);
		return bool;
	}
	
	public  List<FlightPassengerDTO> queryFlightPassengerDTO(String dealerOrderId){
		
		List<FlightPassengerDTO> flightPassengerDTOList = new ArrayList<FlightPassengerDTO>();
		
		List<Passenger> passenerList=this.passengerDAO.findPassengerListByDealerOrderId(dealerOrderId);
		this.convertPassengerToFlightPassengerDTO(passenerList, flightPassengerDTOList);
		return flightPassengerDTOList; 
	}
	
	
	private void convertPassengerToFlightPassengerDTO(List<Passenger> passenerList,List<FlightPassengerDTO> flightPassengerDTOList){
		if(passenerList!=null&&passenerList.size()>0){
			for(Passenger psg:passenerList){
				FlightPassengerDTO psgDto=new FlightPassengerDTO();
				psgDto.setPsgNo(psg.getPsgId()!=null?psg.getPsgId():0);
				psgDto.setPassangerType(psg.getPsgType());
				psgDto.setName(psg.getName());
				//psgDto.setCountry(psg.getCountry());
				psgDto.setIdentityType(psg.getIdentityType());
				psgDto.setCardType(psg.getCardType());
				psgDto.setCardNo(psg.getCardNo());
				psgDto.setInsueSum(psg.getInsueSum()!=null?psg.getInsueSum():0);
				psgDto.setInsueFee(psg.getInsueFee());
				
				TicketDTO ticketDto=new TicketDTO();
				FlightTicket ticket=psg.getTicket();
				if(ticket!=null){
//					ticketDto.setOrderId(psg.getJpOrderId());
					ticketDto.setTicketNo(ticket.getTicketNo());
					ticketDto.setIdCardNo(psg.getCardNo());
					ticketDto.setStatus(TicketDTO.STATUS_PROCESS);
				}
				
				psgDto.setTicketDto(ticketDto);
				flightPassengerDTOList.add(psgDto);
				
				
			}
		}
		
	}
	

}
