package jxc.app.service.product;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateCategoryCommand;
import hg.pojo.command.DeleteCategoryCommand;
import hg.pojo.command.ModifyCategoryCommand;
import hg.pojo.exception.JxcException;
import hg.pojo.exception.ProductException;
import hg.pojo.qo.CategoryQO;
import hg.pojo.qo.ProductQO;
import hg.pojo.qo.SpecificationQO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxc.app.dao.product.ProductCategoryDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.product.Product;
import jxc.domain.model.product.ProductCategory;
import jxc.domain.model.product.Specification;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService extends BaseServiceImpl<ProductCategory, CategoryQO, ProductCategoryDao> {
	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private SpecificationService specificationService;

	@Autowired
	private JxcLogger logger;

	@Override
	protected ProductCategoryDao getDao() {
		return productCategoryDao;
	}

	public ProductCategory createCategory(CreateCategoryCommand command) throws ProductException {

		if (categoryNameIsExisted(command.getName(), null, true)) {
			throw new ProductException(null, "该分类名称已存在");
		}

		String pcId = command.getParentCategoryId();
		if (StringUtils.isNotBlank(pcId)) {
			ProductCategory pCategory = new ProductCategory();
			pCategory.setId(command.getParentCategoryId());
			if (isEndCategory(pcId) && checkCategoryProduct(pCategory) > 0) {
				throw new ProductException(null, "上级分类已被使用，不能添加子分类");
			}
		}

		ProductCategory category = new ProductCategory();
		category.createCategory(command);
		category = save(category);
		logger.debug(this.getClass(), "czh", "新增商品分类" + command.getName(), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(),
				"");
		return category;
	}

	public ProductCategory updateCategory(ModifyCategoryCommand command) throws ProductException {
		if (categoryNameIsExisted(command.getName(), command.getCategoryId(), false)) {
			throw new ProductException(null, "该分类名称已存在");
		}
		CategoryQO categoryQO = new CategoryQO();
		categoryQO.setId(command.getCategoryId());
		ProductCategory category = queryUnique(categoryQO);
		ProductCategory parentCategory = null;
		if (StringUtils.isNotBlank(command.getParentCategoryId())) {
			CategoryQO qo = new CategoryQO();
			qo.setId(command.getParentCategoryId());
			parentCategory = queryUnique(qo);
		}
		category.modifyCategory(command, parentCategory);
		category = update(category);
		updateCategoryStatus(category);
		logger.debug(this.getClass(), "czh", "修改商品分类" + command.getName(), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(),
				"");

		return category;
	}


	private void updateCategoryStatus(ProductCategory category) {
		if (category.getStatus().getUsing()) {
			ProductCategory parentCategory = category.getParentCategory();
			if (parentCategory != null) {
				parentCategory.setStatus(category.getStatus());
				update(parentCategory);
				updateCategoryStatus(parentCategory);
			}
		} else {
			CategoryQO categoryQo = new CategoryQO();
			categoryQo.setParentCategoryId(category.getId());
			List<ProductCategory> childCategoryList = queryList(categoryQo);
			if (childCategoryList != null && childCategoryList.size() > 0) {
				for (ProductCategory childCategory : childCategoryList) {
					childCategory.setStatus(category.getStatus());
					update(childCategory);
					updateCategoryStatus(childCategory);
				}
			}
		}
	}

	public void deleteCategory(DeleteCategoryCommand command) throws JxcException {
		List<String> ids = command.getCategoryIdList();
		
		for (String id : ids) {
			
			ProductCategory productCategory=new ProductCategory();
			productCategory.setId(id);
			if (checkCategoryProduct(productCategory)>0) {
				throw new JxcException(null, "该分类下有商品或规格，不能删除");
			}
		}
		for (String id : ids) {
			CategoryQO qo = new CategoryQO();
			qo.setId(id);
			ProductCategory category = queryUnique(qo);
			List<ProductCategory> categoryList = findChildCategory(category);
			for (int i = categoryList.size() - 1; i > -1; i--) {
				ProductCategory productCategory = categoryList.get(i);
				logger.debug(this.getClass(), "czh", "删除商品分类" + productCategory.getName(), command.getOperatorName(), command.getOperatorType(),
						command.getOperatorAccount(), "");
				productCategory.setStatusRemoved(true);
				update(productCategory);
			}

		}

	}

	private List<Product> findProductByCategory(String categoryId) {
		ProductQO qo = new ProductQO();
		qo.setProductCategoryId(categoryId);
		return productService.queryList(qo);
	}

	private List<Specification> findSpecificationByCategory(String categoryId) {
		SpecificationQO qo = new SpecificationQO();
		qo.setProductCategoryId(categoryId);
		return specificationService.queryList(qo);
	}

	/**
	 * 检查当前分类下是否有商品及规格
	 * 
	 * @param categoryId
	 * @return 0:无 1:商品及规格 2:规格
	 */
	public int checkCategoryProduct(ProductCategory category) {
		List<ProductCategory> categoryList = new ArrayList<ProductCategory>();
		if (isEndCategory(category.getId())) {
			categoryList.add(category);
		} else {
			categoryList.addAll(findEndCategory(category.getId(), null));
		}
		for (ProductCategory productCategory : categoryList) {
			List<Product> prods = findProductByCategory(productCategory.getId());
			if (prods.size() > 0) {
				return 1;
			}
			List<Specification> specs = findSpecificationByCategory(productCategory.getId());
			if (specs.size() > 0) {
				return 2;
			}
		}
		return 0;
	}

	/**
	 * 查找当前节点下的所有子节点
	 * 
	 * @param id
	 * @return
	 */
	public List<ProductCategory> findChildCategory(ProductCategory category) {
		List<ProductCategory> returnChildCategoryList = new ArrayList<ProductCategory>();
		returnChildCategoryList.add(category);
		CategoryQO categoryQo = new CategoryQO();
		categoryQo.setParentCategoryId(category.getId());
		List<ProductCategory> childCategoryList = queryList(categoryQo);
		if (childCategoryList != null && childCategoryList.size() > 0) {
			for (ProductCategory childCategory : childCategoryList) {
				returnChildCategoryList.add(childCategory);
				List<ProductCategory> list = findChildCategory(childCategory);
				if (list != null && list.size() > 0) {
					returnChildCategoryList.addAll(list);
				}
			}
		}
		return returnChildCategoryList;
	}

	/**
	 * 查找当前节点下的所有末节点
	 * 
	 * @param id
	 * @return
	 */
	public Set<ProductCategory> findEndCategory(String id, Set<ProductCategory> categoryList) {
		if (categoryList == null) {
			categoryList = new HashSet<ProductCategory>();
		}
		CategoryQO categoryQo = new CategoryQO();
		categoryQo.setParentCategoryId(id);
		List<ProductCategory> childCategoryList = queryList(categoryQo);
		if (childCategoryList != null && childCategoryList.size() > 0) {
			for (ProductCategory childCategory : childCategoryList) {
				Set<ProductCategory> list = findEndCategory(childCategory.getId(), categoryList);
				if (list != null && list.size() > 0) {
					categoryList.addAll(list);
				}
			}
		} else {
			categoryList.add(get(id));
		}
		return categoryList;
	}

	/**
	 * 判断该分类是否为末节点
	 * 
	 * @param id
	 * @return
	 */
	public boolean isEndCategory(String id) {
		CategoryQO categoryQo = new CategoryQO();
		categoryQo.setParentCategoryId(id);
		int i = queryCount(categoryQo);
		if (i > 0) {
			return false;
		}
		return true;
	}

	public ProductCategory queryUnique4edit(CategoryQO qo) {
		ProductCategory category = queryUnique(qo);
		Hibernate.initialize(category.getParentCategory());
		return category;
	}

	public boolean categoryNameIsExisted(String name, String id, boolean isCreate) {
		CategoryQO categoryQO = new CategoryQO();
		categoryQO.setName(name);
		ProductCategory c = queryUnique(categoryQO);

		if (isCreate) {
			if (c != null) {
				return true;
			}
		} else {
			if (c != null && !id.equals(c.getId())) {
				return true;
			}
		}
		return false;

	}

}
