package plfx.mp.tcclient.tc.pojo;
/**
 * 请求信息
 * @author zhangqy
 *
 */
public class Request {
	/**
	 * 请求头部信息
	 */
	private RequestHead header;
	/**
	 * 请求参数
	 */
	private Param param;
	
	/**
	 * 去掉默认的空构造，强制要求带上请求头部和参数
	 * @param header
	 * @param param
	 */
	public Request(RequestHead header, Param param) {
		super();
		this.header = header;
		this.param = param;
	}
	
	public RequestHead getHeader() {
		return header;
	}
	public void setHeader(RequestHead header) {
		this.header = header;
	}
	public Param getParam() {
		return param;
	}
	public void setParam(Param param) {
		this.param = param;
	}
	
}
