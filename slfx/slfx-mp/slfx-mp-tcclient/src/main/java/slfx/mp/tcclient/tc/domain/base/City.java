package slfx.mp.tcclient.tc.domain.base;
/**
 * 城市
 * @author zhangqy
 *
 */
public class City {
	/**
	 * 城市ID 
	 */
	private Integer id;
	/**
	 * 城市名
	 */
	private String name;
	/**
	 * 首字母
	 */
	private String prefixLetter;
	/**
	 * 英文名称
	 */
	private String enName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrefixLetter() {
		return prefixLetter;
	}
	public void setPrefixLetter(String prefixLetter) {
		this.prefixLetter = prefixLetter;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	
	
}
