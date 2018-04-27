package plfx.mp.tcclient.tc.pojo.base.response;

import plfx.mp.tcclient.tc.pojo.Result;
/**
 * 名称查询区划信息返回信息
 * @author zhangqy
 *
 */
public class ResultDivisionInfoByName extends Result {
	/**
	 * 区划Id
	 */
	private Integer id;
	/**
	 * 	父级区划Id
	 */
	private Integer parentId;
	/**
	 * 区划等级
	 */
	private Integer divisionLevel;
	/**
	 * 名称缩写
	 */
	private String shortName;
	/**
	 * 名称
	 */
	private String name;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getDivisionLevel() {
		return divisionLevel;
	}
	public void setDivisionLevel(Integer divisionLevel) {
		this.divisionLevel = divisionLevel;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	
	
}
