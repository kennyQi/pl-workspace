package lxs.api.v1.dto.mp;

import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class RegionDTO extends BaseDTO{
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 编码
	 */
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
