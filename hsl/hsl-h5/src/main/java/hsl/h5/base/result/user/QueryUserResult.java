package hsl.h5.base.result.user;
import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.user.UserDTO;

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
public class QueryUserResult extends ApiResult {

	public UserDTO userDTO;

	public final static String RESULT_USER_NOTFOUND = "-1"; // 用户不存在

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

}
