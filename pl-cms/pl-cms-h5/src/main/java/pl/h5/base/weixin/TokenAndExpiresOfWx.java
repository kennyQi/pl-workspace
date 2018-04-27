package pl.h5.base.weixin;
/**
 * 说明:
 * @author 卢利如
 * @version 1.0
 * @since 2013-8-22
 */
public class TokenAndExpiresOfWx {
	
	private String access_token;
	private Integer expires_in;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public Integer getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
	
}
