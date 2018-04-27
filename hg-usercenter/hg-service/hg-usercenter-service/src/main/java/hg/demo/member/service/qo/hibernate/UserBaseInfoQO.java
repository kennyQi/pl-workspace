package hg.demo.member.service.qo.hibernate;

import org.springframework.beans.BeanUtils;

import hg.demo.member.common.spi.qo.UserBaseInfoSQO;
import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;

/**
 * 
* <p>Title: UserBaseInfoQO</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月27日 下午2:16:20
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "userBaseInfoDAO")
public class UserBaseInfoQO extends BaseHibernateQO<String> {
	
	/**
	 * 名称
	 */
	@QOAttr(name = "userName", type = QOAttrType.EQ)
	private String userName;

	/**
	 * 名称
	 */
	@QOAttr(name = "email", type = QOAttrType.EQ)
	private String email;
	
	/**
	 * 
	 */
	@QOAttr(name = "phone", type = QOAttrType.LIKE_ANYWHERE)
	private String phone;
	
	public static UserBaseInfoQO build(UserBaseInfoSQO sqo) {
		UserBaseInfoQO qo = new UserBaseInfoQO();
		BeanUtils.copyProperties(sqo, qo);
		qo.setLimit(sqo.getLimit());
		return qo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
