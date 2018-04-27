package lxs.api.v1.response.user;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.user.UserDTO;

/**
 * 
 * @类功能说明：查询用户返回信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月4日下午2:20:40
 * 
 */
public class QueryUserResponse extends ApiResponse {

	public UserDTO user;

	public final static String RESULT_USER_NOTFOUND = "-100"; // 用户不存在

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
