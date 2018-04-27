package zzpl.app.service.local.user;

import hg.common.component.BaseServiceImpl;
import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.CostCenterDAO;
import zzpl.domain.model.user.CostCenter;
import zzpl.pojo.command.user.AddCostCenterCommand;
import zzpl.pojo.command.user.DeleteCostCenterCommand;
import zzpl.pojo.command.user.ModifyCostCenterCommand;
import zzpl.pojo.dto.user.CostCenterDTO;
import zzpl.pojo.exception.user.CostCenterException;
import zzpl.pojo.qo.user.CostCenterQO;

@Service
@Transactional
public class CostCenterService extends
		BaseServiceImpl<CostCenter, CostCenterQO, CostCenterDAO> {

	@Autowired
	private CostCenterDAO costCenterDAO;
	
	/**
	 * 
	 * @方法功能说明：添加
	 * @修改者名字：cangs
	 * @修改时间：2015年8月7日下午5:04:43
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws CostCenterException
	 * @return:void
	 * @throws
	 */
	public void addCostCenter(AddCostCenterCommand command) throws CostCenterException{
		try {
			CostCenter costCenter = BeanMapperUtils.getMapper().map(command, CostCenter.class);
			costCenter.setId(UUIDGenerator.getUUID());
			costCenter.setStatus(0);
			costCenterDAO.save(costCenter);
		} catch (Exception e) {
			throw new CostCenterException(CostCenterException.ADD_COST_CENTER_ERROR);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：删除
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:13:08
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void deleteCostCenter(DeleteCostCenterCommand command) {
		CostCenter costCenter = costCenterDAO.get(command.getCostCenterID());
		costCenter.setStatus(command.getStauts());
		costCenterDAO.update(costCenter);
	}

	/**
	 * 
	 * @方法功能说明：修改
	 * @修改者名字：cangs
	 * @修改时间：2015年6月24日下午5:13:22
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modfiyCostCenter(ModifyCostCenterCommand command) {
		CostCenter costCenter = costCenterDAO.get(command.getId());
		costCenter.setCostCenterName(command.getCostCenterName());
		costCenterDAO.update(costCenter);
	}
	
	/**
	 * @Title: queryCostCentList 
	 * @author guok
	 * @Description: 查询多条成本中心
	 * @Time 2015年10月12日下午3:30:18
	 * @param costCenterQO
	 * @return List<CostCenterDTO> 设定文件
	 * @throws
	 */
	public List<CostCenterDTO> queryCostCentList(CostCenterQO costCenterQO) {
		List<CostCenter> costCenters = costCenterDAO.queryList(costCenterQO);
		List<CostCenterDTO> costCenterDTOs = new ArrayList<CostCenterDTO>();
		for (CostCenter costCenter : costCenters) {
			CostCenterDTO costCenterDTO = BeanMapperUtils.getMapper().map(costCenter,
					CostCenterDTO.class);
			costCenterDTOs.add(costCenterDTO);
		}
		return costCenterDTOs;
	}
	
	@Override
	protected CostCenterDAO getDao() {
		return costCenterDAO;
	}

}
