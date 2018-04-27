package jxc.app.service.product;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateSpecificationCommand;
import hg.pojo.command.DeleteSpecificationCommand;
import hg.pojo.command.ImportBatchSpecificationCommand;
import hg.pojo.command.ModifySpecificationCommand;
import hg.pojo.command.ModifySpecificationStatusCommand;
import hg.pojo.dto.product.SpecValueDTO;
import hg.pojo.dto.product.SpecificationDTO;
import hg.pojo.exception.JxcException;
import hg.pojo.qo.CategoryQO;
import hg.pojo.qo.SpecDetailQO;
import hg.pojo.qo.SpecValueQO;
import hg.pojo.qo.SpecificationQO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxc.app.dao.product.SpecificationDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.product.ProductCategory;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.product.SpecValue;
import jxc.domain.model.product.Specification;
import jxc.domain.model.product.SpecificationStatus;

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
import com.rabbitmq.client.AMQP.Tx.Rollback;

@Service
public class SpecificationService extends BaseServiceImpl<Specification, SpecificationQO, SpecificationDao> {
	@Autowired
	private SpecificationDao specificationDao;

	@Autowired
	private SpecValueService specValueService;

	@Autowired
	private SpecDetailService specDetailService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private JxcLogger logger;

	@Override
	protected SpecificationDao getDao() {
		return specificationDao;
	}

	@Transactional(rollbackFor = Exception.class)
	public Specification createSpecification(CreateSpecificationCommand command) throws JxcException {
		Specification specification = new Specification();
		specification.createSpecificationCommand(command);
		List<String> values = command.getSpecValueList();
		SpecValueDTO specValueDTO = null;
		for (String value : values) {
			if (StringUtils.isNotBlank(value)) {
				if (checkSpecValIsExistedAtCategory(value, command.getCategoryId())) {
					throw new JxcException(null, "该分类下已存在该规格值：" + value);
				}
			}
		}
		specification = save(specification);
		for (String value : values) {
			if (StringUtils.isNotBlank(value)) {
				specValueDTO = new SpecValueDTO();
				specValueDTO.setSpecificationId(specification.getId());
				specValueDTO.setName(value);
				specValueService.createSpecValue(specValueDTO);
			}
		}
		logger.debug(this.getClass(), "czh", "新增商品规格 " + specification.getSpecName() + ":" + JSON.toJSONString(values), command.getOperatorName(),
				command.getOperatorType(), command.getOperatorAccount(), "");
		return specification;
	}

	@Transactional(rollbackFor = Exception.class)
	public Specification updateSpecification(ModifySpecificationCommand command) throws JxcException {
		SpecificationQO specificationQO = new SpecificationQO();
		specificationQO.setId(command.getSpecificationId());
		Specification specification = queryUnique(specificationQO);
		CategoryQO categoryQo = new CategoryQO();
		categoryQo.setId(command.getCategoryId());
		ProductCategory category = productCategoryService.queryUnique(categoryQo);
		if (category != null) {
			specification.modifySpecificationCommand(command, category);
			specification = update(specification);
			List<String> values = command.getSpecValueList();
			SpecValueQO qo = new SpecValueQO();
			qo.setSpecificationId(command.getSpecificationId());
			List<SpecValue> specValues = specValueService.queryList(qo);
			// 判断原规格值是否包含在新规格值中是从新增规格值集合中移除该规格，否则删除该规格值
			for (SpecValue specValue : specValues) {
				if (values.contains(specValue.getSpecValue())) {
					values.remove(specValue.getSpecValue());
				} else {
					specValueService.deleteById(specValue.getId());
				}
			}
			for (String value : values) {
				if (StringUtils.isNotBlank(value)) {
					if (checkSpecValIsExistedAtCategory(value, command.getCategoryId())) {
						throw new JxcException(null, "该分类下已存在该规格值：" + value);
					}
				}
			}
			SpecValueDTO specValueDTO = null;
			for (String value : values) {
				if (StringUtils.isNotBlank(value)) {
					specValueDTO = new SpecValueDTO();
					specValueDTO.setSpecificationId(specification.getId());
					specValueDTO.setName(value);
					specValueService.createSpecValue(specValueDTO);
				}
			}
			logger.debug(this.getClass(), "czh", "修改商品规格 " + specification.getSpecName() + ":" + JSON.toJSONString(values), command.getOperatorName(),
					command.getOperatorType(), command.getOperatorAccount(), "");
		}
		return specification;
	}

	public void deleteSpecification(DeleteSpecificationCommand command) {
		List<String> ids = command.getSpecificationListId();
		for (String id : ids) {
			Specification specification = get(id);
			SpecValueQO specValueQo = new SpecValueQO();
			specValueQo.setSpecificationId(id);
			List<SpecValue> values = specValueService.queryList(specValueQo);
			if (values != null && values.size() > 0) {
				for (SpecValue specValue : values) {
					specValueService.removeSpecValue(specValue.getId());
				}
				logger.debug(this.getClass(), "czh", "删除商品规格 " + values.get(0).getSpecification().getSpecName(), command.getOperatorName(),
						command.getOperatorType(), command.getOperatorAccount(), "");
			}

			specification.setStatusRemoved(true);
			update(specification);

		}

	}

	public HSSFWorkbook exportSpecification(SpecificationQO qo) {
		List<Specification> specifications = specificationDao.queryList(qo);
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet("商品规格");
		// 表格的字段
		String[] headers = "规格名称,所属商品分类名称,状态,规格值".split(",");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		Iterator<Specification> it = specifications.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			Specification t = (Specification) it.next();

			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue(t.getSpecName());

			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue(t.getProductCategory().getName());

			HSSFCell cell2 = row.createCell(2);
			if (t.getStatus().getUsing()) {
				cell2.setCellValue("启用");
			} else {
				cell2.setCellValue("禁用");
			}

			StringBuffer value = new StringBuffer();
			SpecValueQO specValueQo = new SpecValueQO();
			specValueQo.setSpecificationId(t.getId());
			List<SpecValue> values = specValueService.queryList(specValueQo);

			for (SpecValue specValue : values) {
				value.append(specValue.getSpecValue()).append(" ");
			}
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue(value.toString());
		}
		return workbook;
	}

	public void importSpecification(ImportBatchSpecificationCommand command) throws JxcException {
		StringBuffer sb = new StringBuffer();

		List<SpecificationDTO> data = command.getSpecificationList();
		Map<CreateSpecificationCommand, ModifySpecificationStatusCommand> commands = new HashMap<CreateSpecificationCommand, ModifySpecificationStatusCommand>();
		if (data != null && data.size() > 0) {
			CreateSpecificationCommand specificationCommand = null;
			ModifySpecificationStatusCommand specificationStatusCommand = null;
			for (int i = 0; i < data.size(); i++) {
				SpecificationDTO specificationDTO = data.get(i);
				specificationCommand = new CreateSpecificationCommand();
				specificationStatusCommand = new ModifySpecificationStatusCommand();
				CategoryQO categoryQo = new CategoryQO();
				categoryQo.setName(specificationDTO.getCategoryName());
				ProductCategory category = productCategoryService.queryUnique(categoryQo);
				if (category == null) {
					sb.append("第").append(i + 2).append("行：分类不存在&ltbr/&gt");
					continue;
				} else if (!productCategoryService.isEndCategory(category.getId())) {
					sb.append("第").append(i + 2).append("行：该规格不是底级节点&ltbr/&gt");
					continue;
				}
				if (category != null && checkUniqueSpecification(specificationDTO.getSpecName(), category.getId())) {
					if (!checkSpecificationDTO(specificationDTO, sb, i + 2)) {
						continue;
					}
					specificationCommand.setCategoryId(category.getId());
					specificationCommand.setSpecificationName(specificationDTO.getSpecName());
					List<String> specValueList = new ArrayList<String>();
					String[] specValues = specificationDTO.getSpecValueName().split(" ");
					for (String specValue : specValues) {
						specValueList.add(specValue);
						if (checkSpecValIsExistedAtCategory(specValue, category.getId())) {
							sb.append("第").append(i + 2).append("行：规格值：").append(specValue).append(" 在该分类下已存在&ltbr/&gt");
						}
					}
					specificationCommand.setSpecValueList(specValueList);
					if ("启用".equals(specificationDTO.getUsing())) {
						specificationStatusCommand.setUsing(true);
					} else if ("禁用".equals(specificationDTO.getUsing())) {
						specificationStatusCommand.setUsing(false);
					} else {
						sb.append("第").append(i + 2).append("行：状态填写错误&ltbr/&gt");
					}
					if (sb.length() == 0) {
						specificationCommand.setOperatorAccount(command.getOperatorAccount());
						specificationCommand.setOperatorName(command.getOperatorName());
						specificationCommand.setOperatorType(command.getOperatorType());
						specificationStatusCommand.setOperatorAccount(command.getOperatorAccount());
						specificationStatusCommand.setOperatorName(command.getOperatorName());
						specificationStatusCommand.setOperatorType(command.getOperatorType());
						commands.put(specificationCommand, specificationStatusCommand);
					}

				}
			}
			if (sb.length() > 0) {
				throw new JxcException(null, sb.toString());
			}
		}
		
		
		importBatchSpecification(commands);
		logger.debug(this.getClass(), "czh", "导入商品规格 " + commands.size(), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(),
				"");
	}

	private boolean checkSpecificationDTO(SpecificationDTO specificationDTO, StringBuffer sb, int lineCount) {
		boolean flag = true;
		if (StringUtils.isBlank(specificationDTO.getCategoryName())) {
			sb.append("第").append(lineCount).append("行：分类名未填&ltbr/&gt");
			flag = false;
		}
		if (StringUtils.isBlank(specificationDTO.getSpecName())) {
			sb.append("第").append(lineCount).append("行：规格名称为空&ltbr/&gt");
			flag = false;
		}
		if (StringUtils.isBlank(specificationDTO.getSpecValueName())) {
			sb.append("第").append(lineCount).append("行：规格值为空&ltbr/&gt");
			flag = false;
		}
		if (StringUtils.isBlank(specificationDTO.getUsing())) {
			sb.append("第").append(lineCount).append("行：规格状态为空&ltbr/&gt");
			flag = false;
		}
		return flag;
	}

	
	@Transactional(rollbackFor = Exception.class)
	public void importBatchSpecification(Map<CreateSpecificationCommand, ModifySpecificationStatusCommand> commands) throws JxcException {
		for (Map.Entry<CreateSpecificationCommand, ModifySpecificationStatusCommand> command : commands.entrySet()) {
			CreateSpecificationCommand createSpecCommand =command.getKey();
			try {
				Specification specification = createSpecification(createSpecCommand);
				if (specification != null) {
					ModifySpecificationStatusCommand specificationStatus = command.getValue();
					specificationStatus.setSpecificationId(specification.getId());
					updateSpecificationStatus(specificationStatus);
				}
			} catch (JxcException e) {
				throw new JxcException(null, "规格:" + createSpecCommand.getSpecificationName() + "，导入错误，" + e.getMessage());
			}
		}
	}

	public Specification updateSpecificationStatus(ModifySpecificationStatusCommand command) {
		SpecificationQO specificationQo = new SpecificationQO();
		specificationQo.setId(command.getSpecificationId());
		Specification specification = queryUnique(specificationQo);
		if (specification != null) {
			SpecificationStatus status = new SpecificationStatus();
			status.setUsing(command.getUsing());
			specification.setStatus(status);
			specification = update(specification);
			String using = command.getUsing() ? "启用" : "禁用";
			logger.debug(this.getClass(), "czh", "修改商品规格状态 " + specification.getSpecName() + ":" + using, command.getOperatorName(), command.getOperatorType(),
					command.getOperatorAccount(), "");
		}
		return specification;
	}

	/**
	 * 检验该规格是否有商品使用
	 * 
	 * @param specificationId
	 * @return 0:无 1:有
	 */
	public int checkSpecificationProduct(String specificationId) {
		SpecDetailQO qo = new SpecDetailQO();
		qo.setSpecificationId(specificationId);
		int count = specDetailService.queryCount(qo);
		if (count > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * 检验该规格值是否有商品使用
	 * 
	 * @param specificationId
	 *            规格id
	 * @param name
	 *            规格值名称
	 * @return true：无
	 */
	public boolean checkUpdateSpecValue(ModifySpecificationCommand command) {
		SpecDetailQO qo = new SpecDetailQO();
		qo.setSpecificationId(command.getSpecificationId());
		List<SpecDetail> specDetails = specDetailService.queryList(qo);
		SpecValueQO specValueQo = new SpecValueQO();
		specValueQo.setSpecificationId(command.getSpecificationId());
		List<String> values = command.getSpecValueList();
		List<SpecValue> specValues = specValueService.queryList(specValueQo);
		List<String> updateValues = new ArrayList<String>();
		// 判断原规格值是否包含在新规格值中如果不包含则加入修改规格值集合
		for (SpecValue specValue : specValues) {
			if (!values.contains(specValue.getSpecValue())) {
				updateValues.add(specValue.getSpecValue());
			}
		}
		// 取出所有已被使用的规格值
		Set<String> detailValues = new HashSet<String>();
		for (SpecDetail detail : specDetails) {
			detailValues.add(detail.getSpecValue().getSpecValue());
		}
		for (String value : updateValues) {
			if (detailValues.contains(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断规格唯一性
	 * 
	 * @param specIficationName
	 * @return
	 */
	public boolean checkUniqueSpecification(String specificationName, String categoryId) {
		SpecificationQO qo = new SpecificationQO();
		qo.setSpecName(specificationName);
		qo.setProductCategoryId(categoryId);
		Specification specification = queryUnique(qo);
		if (specification != null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断商品状态
	 * 
	 * @param specificationId
	 * @return
	 */
	public boolean checkSpecificationStatus(String specificationId) {
		SpecificationQO qo = new SpecificationQO();
		qo.setId(specificationId);
		Specification specification = queryUnique(qo);
		if (specification != null) {
			return specification.getStatus().getUsing();
		} else {
			return false;
		}
	}

	public boolean checkSpecValIsExistedAtCategory(String v, String cateId) {
		SpecificationQO specQo = new SpecificationQO();
		specQo.setProductCategoryId(cateId);
		List<Specification> specifictionList = specificationDao.queryList(specQo);
		for (Specification specification : specifictionList) {
			SpecValueQO specValueQo = new SpecValueQO();
			specValueQo.setSpecificationId(specification.getId());
			List<SpecValue> specValueList = specValueService.queryList(specValueQo);
			for (SpecValue specValue : specValueList) {
				if (v.equals(specValue.getSpecValue())) {
					return true;
				}
			}
		}
		return false;

	}
}
