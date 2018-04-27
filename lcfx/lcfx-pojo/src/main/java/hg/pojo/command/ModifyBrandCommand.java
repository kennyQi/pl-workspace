package hg.pojo.command;


/**
 * 修改商品品牌
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ModifyBrandCommand extends JxcCommand {

	/**
	 * 品牌id
	 */
	private String brandId;
	
	/**
	 * 品牌中文名称
	 */
	private String chineseName;
	
	/**
	 * 品牌英文名称
	 */
	private String englishName;
	
	/**
	 * 备注
	 */
	private String remark;

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

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
