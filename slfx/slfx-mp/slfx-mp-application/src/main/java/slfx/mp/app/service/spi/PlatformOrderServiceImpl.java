package slfx.mp.app.service.spi;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.api.v1.request.command.mp.MPOrderCancelCommand;
import slfx.api.v1.request.command.mp.MPOrderCreateCommand;
import slfx.mp.app.common.util.EntityConvertUtils;
import slfx.mp.app.pojo.qo.MPOrderQO;
import slfx.mp.app.service.local.MPOrderLocalService;
import slfx.mp.command.admin.AdminCancelOrderCommand;
import slfx.mp.domain.model.order.MPOrder;
import slfx.mp.domain.model.order.TCOrder;
import slfx.mp.pojo.dto.order.MPOrderDTO;
import slfx.mp.pojo.dto.order.TCOrderDTO;
import slfx.mp.qo.PlatformOrderQO;
import slfx.mp.spi.exception.SlfxMpException;
import slfx.mp.spi.inter.PlatformOrderService;

@Service
public class PlatformOrderServiceImpl implements PlatformOrderService {
	
	@Autowired
	private MPOrderLocalService mpOrderLocalService;
	
	@Override
	public Boolean adminCancelOrder(AdminCancelOrderCommand command) throws SlfxMpException {
		return mpOrderLocalService.adminCancelOrder(command);
	}

	@Override
	public String apiOrderTicket(MPOrderCreateCommand command) throws SlfxMpException {
		return mpOrderLocalService.apiOrderTicket(command);
	}

	@Override
	public Boolean apiCancelOrder(MPOrderCancelCommand command) throws SlfxMpException {
		return mpOrderLocalService.apiCancelOrder(command);
	}
	
	public void syncOrder(String id) throws SlfxMpException{
		mpOrderLocalService.syncOrder(id);
	}
	
	protected MPOrderDTO convertDTO(Object[] list) {
		if (list == null || list.length < 5) return null;
		MPOrder mpOrder = (MPOrder) list[0];
		TCOrder tcOrder = (TCOrder) list[1];
		MPOrderDTO mpOrderDTO = EntityConvertUtils.convertEntityToDto(mpOrder,
				MPOrderDTO.class);
		TCOrderDTO tcOrderDTO = EntityConvertUtils.convertEntityToDto(tcOrder,
				TCOrderDTO.class);
		mpOrderDTO.setTcOrder(tcOrderDTO);
		
		return mpOrderDTO;
	}
	
	protected List<MPOrderDTO> convertDTOList(List<Object[]> list) {
		List<MPOrderDTO> resultList = new ArrayList<MPOrderDTO>();
		if (list != null) {
			for (Object[] e : list) {
				MPOrderDTO dto = convertDTO(e);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	@Override
	public MPOrderDTO queryUnique(PlatformOrderQO qo) {
		MPOrderQO mpOrderQO = parseQo(qo);
		Object[] list = mpOrderLocalService.queryOrderUnique(mpOrderQO);
		return convertDTO(list);
	}

	@Override
	public List<MPOrderDTO> queryList(PlatformOrderQO qo) {
		MPOrderQO mpOrderQO = parseQo(qo);
		List<Object[]> list = mpOrderLocalService.queryOrderList(mpOrderQO);
		return convertDTOList(list);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Pagination queryPagination(Pagination pagination) {
		Pagination conditionPagination = parsePaginationQo(pagination);
		Pagination pagination2 = mpOrderLocalService.queryOrderPagination(conditionPagination);
		List<Object[]> list = (List<Object[]>) pagination2.getList();
		pagination2.setList(convertDTOList(list));
		pagination2.setCondition(pagination.getCondition());
		return pagination2;
	}

	protected MPOrderQO parseQo(PlatformOrderQO qo) {
		return BeanMapperUtils.map(qo, MPOrderQO.class);
	}
	
	protected Pagination parsePaginationQo(Pagination pagination) {
		Pagination pagination2 = BeanMapperUtils.map(pagination, Pagination.class);
		pagination2.setPageNo(pagination.getPageNo());
		PlatformOrderQO qo = (PlatformOrderQO) pagination.getCondition();
		MPOrderQO mpOrderQO = parseQo(qo);
		pagination2.setCondition(mpOrderQO);
		return pagination2;
	}

}
