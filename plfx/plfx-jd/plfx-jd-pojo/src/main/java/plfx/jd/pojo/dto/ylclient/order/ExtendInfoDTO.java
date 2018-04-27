package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;

public class ExtendInfoDTO implements Serializable{
	/**
	 * 扩展字段
	 */
	protected String string1;
	/**
	 * 扩展字段
	 */
	protected String string2;
	/**
	 * 扩展字段
	 */
	protected String string3;
	/**
	 * 扩展字段
	 */
	protected int int1;
	/**
	 * 扩展字段
	 */
	protected int int2;
	/**
	 * 扩展字段
	 */
	protected int int3;
	protected String partnerParameter;

	public String getString1() {
		return string1;
	}

	public void setString1(String string1) {
		this.string1 = string1;
	}

	public String getString2() {
		return string2;
	}

	public void setString2(String string2) {
		this.string2 = string2;
	}

	public String getString3() {
		return string3;
	}

	public void setString3(String string3) {
		this.string3 = string3;
	}

	public int getInt1() {
		return int1;
	}

	public void setInt1(int int1) {
		this.int1 = int1;
	}

	public int getInt2() {
		return int2;
	}

	public void setInt2(int int2) {
		this.int2 = int2;
	}

	public int getInt3() {
		return int3;
	}

	public void setInt3(int int3) {
		this.int3 = int3;
	}

	public String getPartnerParameter() {
		return partnerParameter;
	}

	public void setPartnerParameter(String partnerParameter) {
		this.partnerParameter = partnerParameter;
	}

}
