package hg.demo.member.service.dao.mybatis;

import hg.fx.domain.Product;

import java.util.List;

/**
 * 商户使用商品
 * @author Caihuan
 * @date   2016年6月3日
 */
public interface ProductInUseMyBatisDao {

	/**
	 * 商户未使用商品列表
	 * @author Caihuan
	 * @date   2016年6月3日
	 */
	public List<Product> productNotUseList(String distributorId);
}
