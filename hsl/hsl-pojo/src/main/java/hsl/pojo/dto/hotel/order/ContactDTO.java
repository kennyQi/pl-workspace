package hsl.pojo.dto.hotel.order;
import hsl.pojo.util.enumConstants.EnumGender;

import java.io.Serializable;
/**
 * 
 * @类功能说明：住户人信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年3月26日上午10:32:04
 * @版本：V1.0
 *
 */
public class ContactDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 姓名
	 */
    protected String name;
    /**
     * email
     */
    protected String email;
    /**
     * 手机
     */
    protected String mobile;
    /**
     * 电话
     */
    protected String phone;
    /**
     * 传真
     */
    protected String fax;
    /**
     * 性别
     */
    protected EnumGender gender;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public EnumGender getGender() {
		return gender;
	}
	public void setGender(EnumGender gender) {
		this.gender = gender;
	}
	
}
