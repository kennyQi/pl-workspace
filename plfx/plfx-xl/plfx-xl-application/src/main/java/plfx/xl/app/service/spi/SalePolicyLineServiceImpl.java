package plfx.xl.app.service.spi;

import hg.common.util.EntityConvertUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.xl.app.component.base.BaseXlSpiServiceImpl;
import plfx.xl.app.service.local.LineLocalService;
import plfx.xl.app.service.local.SalePolicyLineLocalService;
import plfx.xl.domain.model.line.Line;
import plfx.xl.domain.model.salepolicy.SalePolicy;
import plfx.xl.pojo.command.salepolicy.CancelSalePolicyCommand;
import plfx.xl.pojo.command.salepolicy.CreateSalePolicyCommand;
import plfx.xl.pojo.command.salepolicy.IssueSalePolicyCommand;
import plfx.xl.pojo.dto.line.LineDTO;
import plfx.xl.pojo.dto.salepolicy.SalePolicyLineDTO;
import plfx.xl.pojo.qo.SalePolicyLineQO;
import plfx.xl.spi.inter.SalePolicyLineService;

/**
 * @类功能说明：价格政策SERVICE实现
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年7月31日下午4:47:08
 * @版本：V1.0
 */
@Service("salePolicyLineService")
public class SalePolicyLineServiceImpl extends BaseXlSpiServiceImpl<SalePolicyLineDTO, SalePolicyLineQO, SalePolicyLineLocalService> 
	implements SalePolicyLineService {
	
	@Autowired
	private  SalePolicyLineLocalService  service;
	@Autowired
	private  LineLocalService  lineLocalService;
	
	@Override
	protected SalePolicyLineLocalService getService() {
		return service;
	}
	@Override
	protected Class<SalePolicyLineDTO> getDTOClass() {
		return SalePolicyLineDTO.class;
	}

	/**
	 * @方法功能说明：创建价格政策
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 */
	@Override
	public SalePolicyLineDTO create(CreateSalePolicyCommand command) {
		return service.create(command);
	}
	
	/**
	 * @方法功能说明：发布价格政策
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 */
	@Override
	public boolean isssue(IssueSalePolicyCommand command){
		return service.isssue(command);
	}
	
	/**
	 * @方法功能说明：取消价格政策
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 */
	@Override
	public boolean cancel(CancelSalePolicyCommand command){
		return service.cancel(command);
	}
	
	/**
	 * @方法功能说明：更新价格政策状态
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param policyQo
	 */
	@Override
	public boolean updateStatus(SalePolicyLineQO policyQo){
		return service.updateStatus(policyQo);
	}
	
	@Override
	public void startSalePolicy() {
		service.startSalePolicy();
	}
	
	@Override
	public void downSalePolicy() {
		service.downSalePolicy();
	}
	@Override
	public SalePolicyLineDTO querySalePolicyDetail(SalePolicyLineQO salePolicyLineQO) {
		SalePolicy salePolicy = service.queryUnique(salePolicyLineQO);
		SalePolicyLineDTO salePolicyLineDTO = null;
		if(salePolicy != null){
			salePolicyLineDTO = EntityConvertUtils.convertEntityToDto(salePolicy, SalePolicyLineDTO.class);
			List<Line> lineList = lineLocalService.findLineBySalePolicyID(salePolicyLineQO.getId());
			if(lineList != null && lineList.size() != 0){
				List<LineDTO> lineDTOList = EntityConvertUtils.convertEntityToDtoList(lineList, LineDTO.class);
				salePolicyLineDTO.setLineList(lineDTOList);
			}
		}
		
		return salePolicyLineDTO;
	}
}