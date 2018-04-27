package slfx.xl.app.service.spi;

import hg.common.page.Pagination;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import slfx.xl.app.common.util.EntityConvertUtils;
import slfx.xl.app.component.base.BaseXlSpiServiceImpl;
import slfx.xl.app.service.local.LineFinanceLocalService;
import slfx.xl.app.service.local.LineOrderTravelerLocalService;
import slfx.xl.domain.model.order.LineOrder;
import slfx.xl.pojo.dto.finance.LineFinanceDTO;
import slfx.xl.pojo.qo.LineFinanceQO;
import slfx.xl.spi.inter.LineFinanceService;

/**
 * 
 * @类功能说明：线路订单service实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月16日下午2:55:10
 * @版本：V1.0
 *
 */
@Service("lineFinanceService")
public class LineFinanceServiceImpl extends BaseXlSpiServiceImpl<LineFinanceDTO, LineFinanceQO, LineFinanceLocalService> implements LineFinanceService {

	@Resource
	private LineFinanceLocalService lineFinanceLocalService;
	
	@Resource
	private LineOrderTravelerLocalService lineOrderTravelerLocalService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		
		Pagination pagination2 = getService().queryPagination(pagination);
		List<LineOrder> lineOrderList = (List<LineOrder>) pagination2.getList();
		List<LineFinanceDTO> list = null;
		try{
			list = EntityConvertUtils.convertEntityToDtoList(lineOrderList, getDTOClass());			
		}catch(Exception e){
			list = new ArrayList<LineFinanceDTO>();
			e.printStackTrace();
		}
		pagination2.setList(list);
		return pagination2;
	}

	@Override
	protected LineFinanceLocalService getService() {
		return lineFinanceLocalService;
	}

	@Override
	protected Class<LineFinanceDTO> getDTOClass() {
		return LineFinanceDTO.class;
	}
	
}
