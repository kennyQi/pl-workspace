package jxc.app.service.supplier;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateSupplierCommand;
import hg.pojo.command.DeleteSupplierCommand;
import hg.pojo.command.DeleteSupplierPriorityCommand;
import hg.pojo.command.ImportBatchSupplierCommand;
import hg.pojo.command.ModifySupplierCommand;
import hg.pojo.command.ModifySupplierPriorityPolicyCommand;
import hg.pojo.dto.supplier.SupplierDTO;
import hg.pojo.dto.supplier.SupplierLinkManDTO;
import hg.pojo.exception.SupplierException;
import hg.pojo.qo.SupplierPriorityPolicyQO;
import hg.pojo.qo.SupplierPriorityQO;
import hg.pojo.qo.SupplierQO;
import hg.pojo.qo.WarehouseQO;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxc.app.dao.supplier.SupplierDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.Constants;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.supplier.SupplierBaseInfo;
import jxc.domain.model.supplier.SupplierContact;
import jxc.domain.model.supplier.SupplierLinkMan;
import jxc.domain.model.supplier.SupplierPriority;
import jxc.domain.model.supplier.SupplierPriorityPolicy;
import jxc.domain.model.warehouse.Warehouse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class SupplierService extends BaseServiceImpl<Supplier, SupplierQO, SupplierDao> {
	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private SupplierLinkManService linkManService;

	@Autowired
	private SupplierPriorityService supplierPriorityService;

	@Autowired
	private JxcLogger logger;

	@Autowired
	private SupplierPriorityPolicyService supplierPriorityPolicyService;

	@Override
	protected SupplierDao getDao() {
		return supplierDao;
	}

	/**
	 * 添加供应商
	 * 
	 * @param command
	 * @return
	 * @throws SupplierException
	 */
	public String createSupplier(CreateSupplierCommand command) throws SupplierException {

		// 判断供应商的名称是否重复
		if (supplierNameIsExisted(command.getName(), null, true)) {

			// 供应商名称存在不允许创建
			throw new SupplierException(SupplierException.RESULT_SUPPLIER_NAME_REPEAT, "该供应商已存在");
		}

		// 供应商不存在允许创建
		Supplier supplier = new Supplier();
		supplier.createSupplier(command);
		this.save(supplier);
		logger.debug(this.getClass(), "czh", command.getOperatorName() + "新增供应商 " + JSON.toJSONString(command), command.getOperatorName(),
				command.getOperatorType(), command.getOperatorAccount(), "");

		return supplier.getId();
	}

	/**
	 * 修改供应商
	 * 
	 * @param command
	 * @return
	 * @throws SupplierException
	 */
	public void updateSupplier(ModifySupplierCommand command) throws SupplierException {

		// 判断供应商是否重名
		if (supplierNameIsExisted(command.getName(), command.getSupplierId(), false)) {

			// 供应商名称存在不允许修改
			throw new SupplierException(SupplierException.RESULT_SUPPLIER_NAME_REPEAT, "该供应商已存在");
		}

		// 获取原供应商
		Supplier supplier = this.get(command.getSupplierId());
		String oldSupplierName = supplier.getBaseInfo().getName();

		// 查询包含原供应商名称的供应商优先级策略
		SupplierPriorityPolicyQO supplierPriorityPolicyQO = new SupplierPriorityPolicyQO();
		supplierPriorityPolicyQO.setProjectName(oldSupplierName);
		SupplierPriorityPolicy policy = supplierPriorityPolicyService.queryUnique(supplierPriorityPolicyQO);

		// 判断供应商是否已使用
		if (checkSupplierIsUse(command.getSupplierId())) {

			// 判断供应商类型是否修改
			if (supplier.getBaseInfo().getType().intValue() != command.getType().intValue()) {

				// 供应商已使用不能修改供应商类型
				throw new SupplierException(SupplierException.RESULT_SUPPLIER_USE, "该供应商已在供应商优先级或者采购单中使用，不能修改供应商类型");
			}
		}

		// 供应商既不重名又没有修改供应商类型，更新供应商
		supplier.modifySupplier(command);
		this.update(supplier);

		logger.debug(this.getClass(), "czh", command.getOperatorName() + "修改供应商 " + JSON.toJSONString(command), command.getOperatorName(),
				command.getOperatorType(), command.getOperatorAccount(), "");

		if (policy != null && (!oldSupplierName.equals(supplier.getBaseInfo().getName()))) {
			// 更新供应商策略
			ModifySupplierPriorityPolicyCommand modifySupplierPriorityPolicyCommand = new ModifySupplierPriorityPolicyCommand();
			modifySupplierPriorityPolicyCommand.setPolicyId(policy.getId());
			modifySupplierPriorityPolicyCommand.setSupplierName(policy.getSupplierName().replaceAll(oldSupplierName, supplier.getBaseInfo().getName()));

			supplierPriorityPolicyService.updateSupplierPriorityPolicy(modifySupplierPriorityPolicyCommand);

			logger.debug(this.getClass(), "czh", command.getOperatorName() + "修改" + policy.getProjectName() + "项目供应商优先级策略为"
					+ modifySupplierPriorityPolicyCommand.getSupplierName(), command.getOperatorName(), command.getOperatorType(),
					command.getOperatorAccount(), "");
		}
	}

	/**
	 * 删除供应商
	 * 
	 * @param command
	 * @throws SupplierException
	 */
	public void deleteSupplier(DeleteSupplierCommand command) throws SupplierException {

		List<String> ids = command.getSupplierListId();

		// 判断选择的供应商中是否有已使用
		for (String id : ids) {
			// 判断供应商是否使用(二期)
			if (checkSupplierIsUse(id)) {
				// 供应商已使用，获取对象
				Supplier supplier = this.get(id);
				// 供应商已使用，不能删除
				throw new SupplierException(SupplierException.RESULT_SUPPLIER_USE, "供应商" + supplier.getBaseInfo().getName() + "该供应商已在供应商优先级或者采购单中使用,不能删除");
			}
		}

		// 所选择的供应商均未使用，首先删除供应商的优先级及修改策略
		DeleteSupplierPriorityCommand deleteSupplierPriorityCommand = new DeleteSupplierPriorityCommand();
		deleteSupplierPriorityCommand.setSupplierIds(ids);
		deleteSupplierPriorityCommand.setOperatorAccount(command.getOperatorAccount());
		deleteSupplierPriorityCommand.setOperatorName(command.getOperatorName());
		deleteSupplierPriorityCommand.setOperatorType(command.getOperatorType());
		supplierPriorityService.deleteSupplierPriority(deleteSupplierPriorityCommand);

		// 删除供应商
		for (String id : ids) {

			Supplier supplier = this.get(id);
			supplier.setStatusRemoved(true);
			update(supplier);

			logger.debug(this.getClass(), "czh", command.getOperatorName() + "删除供应商 " + supplier.getBaseInfo().getName(), command.getOperatorName(),
					command.getOperatorType(), command.getOperatorAccount(), "");
		}
	}

	/**
	 * 批量导入供应商
	 * 
	 * @param commands
	 * @throws SupplierException
	 */
	private void importBatchSupplier(Map<CreateSupplierCommand, SupplierLinkManDTO> commands) throws SupplierException {
		for (Map.Entry<CreateSupplierCommand, SupplierLinkManDTO> command : commands.entrySet()) {
			String supplierId = createSupplier(command.getKey());
			SupplierLinkManDTO supplierLinkManDTO = command.getValue();
			linkManService.createSupplierLinkMan(supplierId, supplierLinkManDTO);
		}
	}

	/**
	 * 判断供应商是否使用
	 * 
	 * @param command
	 * @return
	 */
	public boolean checkSupplierIsUse(String supplierId) {

		SupplierPriorityQO qo = new SupplierPriorityQO();
		qo.setSupplierId(supplierId);
		SupplierPriority s = supplierPriorityService.queryUnique(qo);
		if (s != null) {
			return true;
		}
		/**
		 * 判断供应商是否在采购单中使用(二期)
		 */
		return false;
	}

	/**
	 * 判断供应商名称是否重复
	 * 
	 * @param supplierName
	 *            ,type,isCreate
	 * @return
	 */
	public boolean supplierNameIsExisted(String name, String id, boolean isCreate) {
		SupplierQO qo = new SupplierQO();
		qo.setSupplierName(name);
		Supplier s = queryUnique(qo);

		if (isCreate) {
			if (s != null) {
				return true;
			}
		} else {
			if (s != null && !id.equals(s.getId())) {
				return true;
			}
		}
		return false;

	}

	public HSSFWorkbook exportSupplier(SupplierQO qo) {
		List<Supplier> supplierList = supplierDao.queryList(qo);
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet("供应商");
		// 表格的字段
		String[] headers = "供应商名称,供应商类型,开户银行,账号,联系电话,联系地址,邮编号码,联系邮箱,传真号码,公司网址,法人姓名,税务号,注册资金,成立时间,备注,姓名,职务,手机号码,联系电话,联系QQ,联系邮箱,供应商编号".split(",");

		String[] columns = "name,type,bank,account,phone,address,postCode,email,fax,URL,legalPerson,tax,registeredCapital,establishDate,remark,name,post,mobile,phone,QQ,email,supplierCode"
				.split(",");
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		Iterator<Supplier> it = supplierList.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			Supplier t = (Supplier) it.next();
			SupplierBaseInfo baseInfo = t.getBaseInfo();
			SupplierContact contact = t.getContact();
			List<SupplierLinkMan> linkMans = t.getLinkManList();
			SupplierLinkMan linkMan = null;
			if (linkMans != null && linkMans.size() > 0) {
				linkMan = linkMans.get(0);
			}
			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				String getMethodName = "get" + columns[i].substring(0, 1).toUpperCase() + columns[i].substring(1);
				Class tCls = null;
				if (i > 3 && i <= 8) {
					if (contact != null) {
						tCls = contact.getClass();
					}
				} else if (i > 14 && i < 21) {
					if (linkMan != null) {
						tCls = linkMan.getClass();
					}
				} else if (i == 14 || i == 21) {
					tCls = t.getClass();
				} else {
					if (baseInfo != null) {
						tCls = baseInfo.getClass();
					}
				}

				Method getMethod = null;
				try {
					if (tCls != null) {
						getMethod = tCls.getMethod(getMethodName, new Class[] {});
					}
					Object value = null;
					if (getMethod != null) {
						if (i > 3 && i <= 8) {
							if (contact != null) {
								value = getMethod.invoke(contact, new Object[] {});
							}
						} else if (i > 14 && i < 21) {
							if (linkMan != null) {
								value = getMethod.invoke(linkMan, new Object[] {});
							}
						} else if (i == 14 || i == 21) {
							value = getMethod.invoke(t, new Object[] {});
						} else {
							if (baseInfo != null) {
								value = getMethod.invoke(baseInfo, new Object[] {});
							}
						}
					}
					if (value != null) {
						String textValue = value.toString();
						if (i == 1) {
							if (Integer.parseInt(textValue) == Constants.SUPPLIER_TYPE_COMMISSION_SALE) {
								cell.setCellValue("经销");
							} else {
								cell.setCellValue("代销");
							}
						} else {
							cell.setCellValue(textValue);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return workbook;
	}

	@Transactional(rollbackFor = Exception.class)
	public void importSupplier(ImportBatchSupplierCommand command, StringBuffer sb) throws SupplierException {

		List<SupplierDTO> supplierDtoList = command.getSupplierList();
		List<SupplierLinkManDTO> linkMans = command.getSupplierLinkManList();
		Map<CreateSupplierCommand, SupplierLinkManDTO> commands = new HashMap<CreateSupplierCommand, SupplierLinkManDTO>();
		if (supplierDtoList != null && supplierDtoList.size() > 0) {
			for (int j = 0; j < supplierDtoList.size(); j++) {
				SupplierDTO supplierDTO = supplierDtoList.get(j);
				SupplierLinkManDTO linkManDTO = linkMans.get(j);
				CreateSupplierCommand supplierCommand = new CreateSupplierCommand();
				SupplierQO qo = new SupplierQO();
				qo.setSupplierName(supplierDTO.getName());
				Supplier supplier = queryUnique(qo);
				if (supplier == null) {
					supplierCommand.setName(supplierDTO.getName());
					if ("经销".equals(supplierDTO.getTypeName())) {
						supplierCommand.setType(Constants.SUPPLIER_TYPE_COMMISSION_SALE);
					} else if ("代销".equals(supplierDTO.getTypeName())) {
						supplierCommand.setType(Constants.SUPPLIER_TYPE_CONSIGNMENT_SALE);
					} else {
						sb.append("第").append(j + 2).append("行，供应商类型不正确&ltbr/&gt");

					}
					supplierCommand.setBank(supplierDTO.getBank());
					supplierCommand.setAccount(supplierDTO.getAccount());
					supplierCommand.setPhone(supplierDTO.getPhone());
					supplierCommand.setAddress(supplierDTO.getAddress());
					supplierCommand.setPostCode(supplierDTO.getPostCode());
					supplierCommand.setEmail(supplierDTO.getEmail());
					supplierCommand.setFax(supplierDTO.getFax());
					supplierCommand.setURL(supplierDTO.getURL());
					supplierCommand.setLegalPerson(supplierDTO.getLegalPerson());
					supplierCommand.setTax(supplierDTO.getTax());
					supplierCommand.setRegisteredCapital(supplierDTO.getRegisteredCapital());
					supplierCommand.setEstablishDate(supplierDTO.getEstablishDate());
					supplierCommand.setRemark(supplierDTO.getRemark());
					supplierCommand.setSupplierCode(supplierDTO.getSupplierCode());
					supplierCommand.setOperatorName(command.getOperatorName());
					supplierCommand.setOperatorType(command.getOperatorType());
					supplierCommand.setOperatorAccount(command.getOperatorAccount());
					
					commands.put(supplierCommand, linkManDTO);

				} else {
					sb.append("第").append(j + 2).append("行，供应商已存在&ltbr/&gt");
				}
			}

		}
		if (sb.length() > 0) {
			throw new SupplierException(null, "导入失败");
		}

		importBatchSupplier(commands);
		logger.debug(this.getClass(), "czh", "导入供应商 " + commands.size(), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}
}
