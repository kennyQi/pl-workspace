package jxc.domain.model.product;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreateBrandCommand;
import hg.pojo.command.ModifyBrandCommand;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.util.CodeUtil;

/**
 * 商品品牌表
 * 
 * @author liujz
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_PRODUCT + "BRAND")
public class Brand extends JxcBaseModel {

	/**
	 * 品牌编码
	 */
	@Column(name = "BRAND_CODE", length = 4)
	private String brandCode;

	/**
	 * 品牌中文名称
	 */
	@Column(name = "CHINESE_NAME", length = 50)
	private String chineseName;

	/**
	 * 品牌英文名称
	 */
	@Column(name = "ENGLISH_NAME", length = 64)
	private String englishName;

	/**
	 * 品牌简介
	 */
	@Column(name = "REMARK", length = 255)
	private String remark;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void createBrand(CreateBrandCommand command) {
		setId(UUIDGenerator.getUUID());
		setCreateDate(new Date());
		setBrandCode(CodeUtil.createBrandCode());
		setChineseName(command.getChineseName());
		setEnglishName(command.getEnglishName());
		setRemark(command.getRemark());
		setStatusRemoved(false);
		
		createDate = new Date();
	}

	public void modifyBrand(ModifyBrandCommand command) {

		setChineseName(command.getChineseName());
		setEnglishName(command.getEnglishName());
		setRemark(command.getRemark());
	}
}
