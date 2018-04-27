package hg.fx.enums;
/**
 * 里程订单排序方式枚举
 * @author zqq
 * @date 2016-6-6上午10:28:54
 * @since
 */
public enum MileOrderOrderWayEnum {
	ORDER_BY_NUM_DESC("按积分数量降序排序",-1),
	ORDER_BY_NUM_AES("按积分数量升序排序",2),
	ORDER_BY_CREATEDATE_DESC("按订单时间降序排序",-3),
	ORDER_BY_CREATEDATE_AES("按订单数量升序排序",4);
	
	private String name;
	private int value;
	private MileOrderOrderWayEnum(String name , int value){
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}
