package lxs.api.v1.request.command.user;

import lxs.api.base.ApiPayload;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("serial")
public class ChangePasswordCommand extends ApiPayload {
	/***
	 * 用户ID
	 */
	private String userId;
	/***
	 * 旧密码
	 */
	private String oldPassWord;
	/***
	 * 新密码
	 */
	private String newPassWord;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOldPassWord() {
		return oldPassWord;
	}
	public void setOldPassWord(String oldPassWord) {
		this.oldPassWord = oldPassWord;
	}
	public String getNewPassWord() {
		return newPassWord;
	}
	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}
	public static void main(String[] arg){
		ChangePasswordCommand c = new ChangePasswordCommand();
		c.setUserId("2406d64b0cf34887a41b571c4b37e858");
		c.setOldPassWord("fadtyrvcmndi");
		c.setNewPassWord("dyfgurvbxtsk");
		System.out.println(JSON.toJSON(c));
		
	}
}
