package hg.pojo.command;


/**
 * 创建商品品牌
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class CreateBrandCommand extends JxcCommand {
	
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
