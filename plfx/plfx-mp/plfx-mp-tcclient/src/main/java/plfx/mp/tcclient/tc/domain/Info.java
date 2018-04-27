package plfx.mp.tcclient.tc.domain;
/**
 * 须知明细列表
 * @author zhangqy
 *
 */
public class Info {
	/**
	 * 须知排序
	 */
	private Integer nId;
	/**
	 * 须知名称
	 */
	private String nName;
	/**
	 * 须知内容
	 */
	private String nContent;
	public Integer getNId() {
		return nId;
	}
	public void setNId(Integer nId) {
		this.nId = nId;
	}
	public String getNName() {
		return nName;
	}
	public void setNName(String nName) {
		this.nName = nName;
	}
	public String getNContent() {
		return nContent;
	}
	public void setNContent(String nContent) {
		this.nContent = nContent;
	}
	
}
