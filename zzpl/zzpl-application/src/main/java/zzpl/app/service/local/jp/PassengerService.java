package zzpl.app.service.local.jp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.api.client.dto.jp.GNPassengerDTO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.domain.model.order.Passenger;
import zzpl.pojo.qo.jp.PassengerQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class PassengerService extends
		BaseServiceImpl<Passenger, PassengerQO, PassengerDAO> {

	@Autowired
	private PassengerDAO passengerDAO;
	
	/**
	 * @Title: getList 
	 * @author guok
	 * @Description: 乘机人列表
	 * @Time 2015年10月19日上午11:19:23
	 * @param passengerQO
	 * @return List<Passenger> 设定文件
	 * @throws
	 */
	public List<GNPassengerDTO> getList(PassengerQO passengerQO) {
		List<Passenger> passengers = passengerDAO.queryList(passengerQO);
		List<GNPassengerDTO> gnPassengerDTOs = new ArrayList<GNPassengerDTO>();
		for (Passenger passenger : passengers) {
			Hibernate.initialize(passenger.getFlightOrder());
			GNPassengerDTO gnPassengerDTO = new GNPassengerDTO();
			gnPassengerDTO.setIdNO(passenger.getIdNO());
			gnPassengerDTO.setIdType(passenger.getIdType());
			gnPassengerDTO.setPassengerName(passenger.getPassengerName());
			gnPassengerDTO.setTelephone(passenger.getTelephone());
			gnPassengerDTO.setPassengerType(passenger.getPassengerType());
			gnPassengerDTOs.add(gnPassengerDTO);
		}
		
		return gnPassengerDTOs;
	}

	@Override
	protected PassengerDAO getDao() {
		return passengerDAO;
	}

}
