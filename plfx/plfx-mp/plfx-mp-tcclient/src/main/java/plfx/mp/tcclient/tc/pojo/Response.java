package plfx.mp.tcclient.tc.pojo;
/**
 * 同城接口调用返回参数
 * @author zhangqy
 *
 */
public class Response<T extends Result> {
	/**
	 * 请求响应头部
	 */
	private ResponseHead head;
	/**
	 * 请求响应结果
	 */
	private T result;
	
	public void setResponse(ResponseHead head,T t){
		this.head=head;
		this.result=t;
	}
	public ResponseHead getHead() {
		return head;
	}
	public void setHead(ResponseHead head) {
		this.head = head;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	
}
