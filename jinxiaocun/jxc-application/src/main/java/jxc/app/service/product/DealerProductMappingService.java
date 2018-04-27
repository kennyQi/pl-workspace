package jxc.app.service.product;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.DeleteDealerProductMappingCommand;
import hg.pojo.command.ModifyDealerProductMappingCommand;
import hg.pojo.dto.product.DealerProductMappingDTO;
import hg.pojo.qo.DealerProductMappingQO;
import hg.pojo.qo.ProjectQO;
import hg.pojo.qo.SkuProductQO;

import java.util.ArrayList;
import java.util.List;

import jxc.app.dao.product.DealerProductMappingDao;
import jxc.app.service.system.ProjectService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.product.DealerProductMapping;
import jxc.domain.model.product.SkuProduct;
import jxc.domain.model.system.Project;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DealerProductMappingService
		extends
		BaseServiceImpl<DealerProductMapping, DealerProductMappingQO, DealerProductMappingDao> {

	@Autowired
	private DealerProductMappingDao dealerProductMappingDao;

	@Autowired
	private SkuProductService skuProductService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private JxcLogger logger;

	@Override
	protected DealerProductMappingDao getDao() {
		return dealerProductMappingDao;
	}

	public List<DealerProductMapping> createDealerProductMapping(
			ModifyDealerProductMappingCommand command) {
		List<DealerProductMappingDTO> dealerProductMapping = command
				.getDealerProductMappingList();
		List<DealerProductMapping> dealerProductMappings = new ArrayList<DealerProductMapping>();
		for (DealerProductMappingDTO dealerProductMappingDTO : dealerProductMapping) {
			DealerProductMapping mapping = new DealerProductMapping();
			if (StringUtils.isNotBlank(dealerProductMappingDTO
					.getDealerProductCode())) {
				if (StringUtils.isBlank(dealerProductMappingDTO.getDealerProductMappingId())) {
					mapping.createDealerProductMapping(dealerProductMappingDTO);
					save(mapping);
					logger.debug(this.getClass(), "czh", "新增商品对照"
							+ mapping.getSkuProduct().getId(),
							command.getOperatorName(),
							command.getOperatorType(),
							command.getOperatorAccount(), "");
				} else {
					SkuProductQO skuProductQo = new SkuProductQO();
					skuProductQo.setId(dealerProductMappingDTO
							.getSkuProductId());
					SkuProduct skuProduct = skuProductService
							.queryUnique(skuProductQo);
					ProjectQO projectQo = new ProjectQO();
					projectQo.setId(dealerProductMappingDTO.getProjectId());
					Project project = projectService.queryUnique(projectQo);
					if (skuProduct != null && project != null) {
						mapping.modifyDealerProductMapping(
								dealerProductMappingDTO, project, skuProduct);
						update(mapping);
						logger.debug(this.getClass(), "czh", "修改商品对照"
								+ mapping.getSkuProduct().getId(),
								command.getOperatorName(),
								command.getOperatorType(),
								command.getOperatorAccount(), "");
					}
				}
			}
			dealerProductMappings.add(mapping);
		}
		return dealerProductMappings;
	}

	
	@Transactional(rollbackFor = Exception.class)
	public void deleteDealerProductMapping(
			DeleteDealerProductMappingCommand command) {
		List<String> ids = command.getDealerProductMappingId();
		for (String id : ids) {
			if ("".equals(id)) {
				continue;
			}
			DealerProductMappingQO qo = new DealerProductMappingQO();
			qo.setId(id);
			qo.setSequence(command.getSequence());
			DealerProductMapping mapping = queryUnique(qo);
			if (mapping != null) {
				logger.debug(this.getClass(), "czh",
						"删除商品对照" + mapping.getDealerProductCode(),
						command.getOperatorName(), command.getOperatorType(),
						command.getOperatorAccount(), "");
				deleteById(id);
			}
		}
	}
	
	public void deleteByProductId(String id){
		DeleteDealerProductMappingCommand command = new DeleteDealerProductMappingCommand();
		List<DealerProductMapping> mappings = null;
		List<String> ids = new ArrayList<String>();
		SkuProductQO skuProductQo = new SkuProductQO();
		skuProductQo.setProductId(id);
		List<SkuProduct> skuProducts = skuProductService.queryList(skuProductQo);
		for (SkuProduct skuProduct : skuProducts) {
			DealerProductMappingQO qo = new DealerProductMappingQO();
			qo.setSkuProductId(skuProduct.getId());
			mappings = queryList(qo);
			for (DealerProductMapping mapping : mappings) {
				ids.add(mapping.getId());
			}
		}
		command.setDealerProductMappingId(ids);
		deleteDealerProductMapping(command);
	}
}
