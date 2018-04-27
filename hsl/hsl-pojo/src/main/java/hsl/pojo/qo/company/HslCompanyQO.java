package hsl.pojo.qo.company;

import hg.common.component.BaseQo;

/**
 * 公司查询的QO
 * @author zhuxy
 *
 */
@SuppressWarnings("serial")
public class HslCompanyQO extends BaseQo {
	/**
	 * 用户Id
	 */
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
