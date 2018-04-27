package jxc.domain.model.product;

import hg.common.component.BaseModel;
import hg.pojo.dto.product.SpecValueDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.util.CodeUtil;

/**
 * 商品规格值
 * @author liujz
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_JXC_PRODUCT+"SPEC_VALUE")
public class SpecValue extends JxcBaseModel {

	/**
	 * 属性值
	 */
	@Column(name="SPEC_VALUE",length=32)
	private String specValue;
	
	/**
	 * 所属属性
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SPECIFICATION_ID")
	private Specification specification;
	


	public String getSpecValue() {
		return specValue;
	}

	public void setSpecValue(String specValue) {
		this.specValue = specValue;
	}

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public void createSpecValue(SpecValueDTO specValueDTO) {
		setId(CodeUtil.createSpecValueCode());
		specification = new Specification();
		specification.setId(specValueDTO.getSpecificationId());
		setSpecification(specification);
		setSpecValue(specValueDTO.getName());
		
		setStatusRemoved(false);
	}


}
