package jxc.app.service.supplier;

import java.util.ArrayList;
import java.util.List;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.DeleteSupplierLinkManCommand;
import hg.pojo.command.ModifySupplierLinkManCommand;
import hg.pojo.dto.supplier.SupplierLinkManDTO;
import hg.pojo.qo.LinkManQO;
import jxc.app.dao.supplier.LinkManDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.supplier.SupplierLinkMan;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class SupplierLinkManService extends BaseServiceImpl<SupplierLinkMan, LinkManQO, LinkManDao>{
	
	@Autowired
	private LinkManDao linkManDao; 
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private JxcLogger logger;
	
	@Override
	protected LinkManDao getDao() {
		return linkManDao;
	}
	
	/**
	 * 创建供应商联系人
	 * @param supplierId
	 * @param linkManDTO
	 * @return
	 */
	public SupplierLinkMan createSupplierLinkMan(String supplierId,SupplierLinkManDTO linkManDTO){
		
		Supplier supplier=supplierService.get(supplierId);
		
		SupplierLinkMan linkMan = new SupplierLinkMan();
		linkMan.createLinkMan(linkManDTO,supplier);
		this.save(linkMan);
		
		return linkMan;
	}
	
	/**
	 * 更新供应商联系人
	 * @param supplierId
	 * @param linkManDTO
	 * @return
	 */
	public List<SupplierLinkMan> updateSupplierLinkMan(ModifySupplierLinkManCommand command){

		//获取供应商联系人DTO
		List<SupplierLinkManDTO> linkManDTOList = command.getSupplierLinkManList();
		
		//获取供应商
		Supplier supplier=supplierService.get(command.getSupplierId());
		
		List<SupplierLinkMan> linkManList=new ArrayList<SupplierLinkMan>();
		
		for (SupplierLinkManDTO supplierLinkManDTO : linkManDTOList) {

			SupplierLinkMan linkMan=new SupplierLinkMan();
			
			//判断供应商联系人是否已存在
			if(StringUtils.isNotBlank(supplierLinkManDTO.getSupplierLinkManId())){
				//供应商联系人已存在，更新供应商联系人信息
				linkMan=this.get(supplierLinkManDTO.getSupplierLinkManId());
				linkMan.modifyLinkMan(supplierLinkManDTO);
				this.update(linkMan);
				
				 logger.debug(this.getClass(), "czh", command.getOperatorName()+"修改供应商"+supplier.getBaseInfo().getName()+"联系人 " +JSON.toJSONString(supplierLinkManDTO), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
			}else{
				//供应商联系人不存在，新建供应商联系人
				linkMan  = this.createSupplierLinkMan(command.getSupplierId(),supplierLinkManDTO);
				
				logger.debug(this.getClass(), "czh", command.getOperatorName()+"新增供应商"+supplier.getBaseInfo().getName()+"联系人 " + JSON.toJSONString(supplierLinkManDTO), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
			}
			linkManList.add(linkMan);
		}

		return linkManList;
	}
	
	/**
	 * 删除供应商联系人
	 * @param command
	 */
	public void deleteSupplierLinkMan(DeleteSupplierLinkManCommand command){

		SupplierLinkMan linkMan = this.get(command.getSupplierLinkManId());
		this.deleteById(command.getSupplierLinkManId());
		
		logger.debug(this.getClass(), "czh", command.getOperatorName()+"删除供应商联系人 " + linkMan.getName(), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}
	
	/**
	 * 设置常用联系人
	 * @param linkMan
	 * @return
	 */
	public SupplierLinkMan setUsuallySupplierLinkMan(SupplierLinkMan linkMan){
		linkMan.setDefaultLinkMan(true);
		this.update(linkMan);
		return linkMan;
	}
}
