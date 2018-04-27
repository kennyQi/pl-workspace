package hsl.pojo.message;
import hg.common.component.BaseAmqpMessage;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.dto.user.UserDTO;
/**
 * @类功能说明：基础队列消息
 * @类修改者：
 * @修改日期：2014-10-20下午2:10:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-20下午2:10:54
 *
 * @param <T>
 */
public class CouponMessage extends BaseAmqpMessage<CreateCouponCommand>{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户注册成功时填充消息内容
	 * @param dto
	 */
	public void paddingUserRegisterContent(UserDTO dto){
		CreateCouponCommand command=new CreateCouponCommand();
		command.setLoginName(dto.getAuthInfo().getLoginName());
		command.setMobile(dto.getContactInfo().getMobile());
		command.setRealName(dto.getBaseInfo().getName());
//		command.setDetail("sdsdsd");
		command.setEmail(dto.getContactInfo().getEmail());
		command.setUserId(dto.getId());
		command.setSourceDetail("注册成功");
		setContent(command);
	}
	
	/**
	 * 设置消息内容
	 * @param command
	 */
	public void setMessageContent(CreateCouponCommand command){
		setContent(command);
	}
}
