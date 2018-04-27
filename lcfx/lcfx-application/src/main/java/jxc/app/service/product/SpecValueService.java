package jxc.app.service.product;

import hg.common.component.BaseServiceImpl;
import hg.pojo.dto.product.SpecValueDTO;
import hg.pojo.qo.SpecValueQO;
import jxc.app.dao.product.SpecValueDao;
import jxc.domain.model.product.SpecValue;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecValueService extends BaseServiceImpl<SpecValue, SpecValueQO, SpecValueDao> {
	@Autowired
	private SpecValueDao specValueDao;

	@Override
	protected SpecValueDao getDao() {
		return specValueDao;
	}

	public SpecValue createSpecValue(SpecValueDTO specValueDTO) {
		SpecValue specValue = new SpecValue();
		specValue.createSpecValue(specValueDTO);
		specValue = save(specValue);
		return specValue;
	}

	public void removeSpecValue(String id) {
		SpecValue specValue = get(id);
		specValue.setStatusRemoved(true);
		update(specValue);
	}

	public SpecValue queryUniqueWithSpec(SpecValueQO qo) {
		SpecValue SpecValue = queryUnique(qo);
		Hibernate.initialize(SpecValue.getSpecification().getProductCategory());
		return SpecValue;
	}

}
