package slfx.mp.tcclient.tc.domain;

import java.util.List;

/**
 * 购票须知
 * @author zhangqy
 *
 */
public class Notice {
	/**
	 * 类型
	 */
	private Integer nType;
	/**
	 * 类型名称
	 */
	private String nTypeName;
	/**
	 * 明细
	 */
	private List<Info> nInfo;
	public Integer getNType() {
		return nType;
	}
	public void setNType(Integer nType) {
		this.nType = nType;
	}
	public String getNTypeName() {
		return nTypeName;
	}
	public void setNTypeName(String nTypeName) {
		this.nTypeName = nTypeName;
	}
	public List<Info> getNInfo() {
		return nInfo;
	}
	public void setNInfo(List<Info> nInfo) {
		this.nInfo = nInfo;
	}
	
}
