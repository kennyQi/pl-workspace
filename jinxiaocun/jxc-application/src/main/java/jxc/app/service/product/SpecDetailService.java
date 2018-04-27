package jxc.app.service.product;

import hg.common.component.BaseServiceImpl;
import hg.pojo.dto.product.SpecDetailDTO;
import hg.pojo.qo.SpecDetailQO;
import jxc.app.dao.product.SpecDetailDao;
import jxc.domain.model.product.SkuProduct;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.product.SpecValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SpecDetailService extends BaseServiceImpl<SpecDetail, SpecDetailQO, SpecDetailDao>{
	@Autowired
	SpecDetailDao specDetailDao;
	
	@Override
	protected SpecDetailDao getDao() {
		return specDetailDao;
	}

	public SpecDetail createSpecDetail(String[] detail) {
		SpecDetail specDetail = new SpecDetail();
		specDetail.createSpecDetail(detail);
		specDetail = save(specDetail);
		return specDetail;
	}

	public void createSkuSpecDetail(SpecValue specVal, SkuProduct skuProduct) {
		SpecDetail specDetail = new SpecDetail();
		specDetail.create(specVal,skuProduct);
		save(specDetail);
		
	}
	
	
	
	
	
}
