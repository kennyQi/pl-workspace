package hsl.pojo.qo.coupon;

import hg.common.component.BaseQo;

public class HslCouponQO extends BaseQo {
	private static final long serialVersionUID = 1L;
	/**
	 * 是否使用
	 */
	private boolean isUsed;
	/**
	 * 是否取消
	 */
	private boolean isCancel;
	/**
	 * 是否过期
	 */
	private boolean isOverdue;
	/**
	 * 发放方式
	 * 1、注册发放
	 * 2、订单满送
	 */
	private int issueWay;
	/**
	 * 账户名
	 */
	private String loginName;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 是否支持登录名模糊查询
	 */
	private boolean loginNameLike;
	
	/**
	 * 是否支持卡券编号模糊查询
	 */
	private boolean idLike;
	
	/**
	 * 卡券状态:
	 * 0.全部
	 * 1.未使用
	 * 2.已使用
	 * 3.过期
	 * 4.废弃
	 */
	private int currentValue;
	private int[] currentValues;
	/**
	 * 卡券状态数组可以联合查询
	 */
	private Object[] statusTypes = {};
	/**
	 * 事件类型
	 */
	private int eventType;
	/**
	 * 订单ID
	 */
	private String orderId;
	
	private String couponId;
	/**
	 * 订单价格
	 */
	private double orderPrice;
	/**
	 * 使用条件 0、不限	1、订单满多少可用
	 */
	private int condition;
	/**
	 * 标示是否来自h5端，h5的卡券排序为按面值排序，pc端为按创建时间
	 */
	private boolean fromH5 = false;
	/**
	 * 有转赠记录
	 */
	private Boolean hasTransferRecord;
	/**
	 * 转赠来自的账户
	 */
	private String fromLoginName;
	/**
	 * 是否是可用的优惠卷
	 */
	private String isAvailable;
	
	/**
	 * 卡券是否通过卡券事件来查询
	 */
	private boolean useEvent;
	/**
	 * 是否占用
	 */
	private boolean isOccupy;
	/**
	 * 使用种类
	 */
	private String usedKind;

	public boolean isUseEvent() {
		return useEvent;
	}

	public void setUseEvent(boolean useEvent) {
		this.useEvent = useEvent;
	}

	public boolean isFromH5() {
		return fromH5;
	}

	public void setFromH5(boolean fromH5) {
		this.fromH5 = fromH5;
	}

	public int getCondition() {
		return condition;
	}

	/**
	 * 使用条件 0、不限	1、订单满多少可用
	 */
	public void setCondition(int condition) {
		this.condition = condition;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public boolean isCancel() {
		return isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	public boolean isOverdue() {
		return isOverdue;
	}

	public void setOverdue(boolean isOverdue) {
		this.isOverdue = isOverdue;
	}

	public int getIssueWay() {
		return issueWay;
	}

	public void setIssueWay(int issueWay) {
		this.issueWay = issueWay;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public boolean getLoginNameLike() {
		return loginNameLike;
	}

	public void setLoginNameLike(boolean loginNameLike) {
		this.loginNameLike = loginNameLike;
	}

	public boolean getIdLike() {
		return idLike;
	}

	public void setIdLike(boolean idLike) {
		this.idLike = idLike;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public Object[] getStatusTypes() {
		return statusTypes;
	}

	public void setStatusTypes(Object[] statusTypes) {
		this.statusTypes = statusTypes;
	}

	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	public boolean isOccupy() {
		return isOccupy;
	}

	public void setOccupy(boolean isOccupy) {
		this.isOccupy = isOccupy;
	}

	public Boolean getHasTransferRecord() {
		return hasTransferRecord;
	}

	public void setHasTransferRecord(Boolean hasTransferRecord) {
		this.hasTransferRecord = hasTransferRecord;
	}

	public String getFromLoginName() {
		return fromLoginName;
	}

	public void setFromLoginName(String fromLoginName) {
		this.fromLoginName = fromLoginName;
	}

	public boolean isLoginNameLike() {
		return loginNameLike;
	}

	public boolean isIdLike() {
		return idLike;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
		if (currentValue > 0)
			this.currentValues = new int[]{currentValue};
	}

	public int[] getCurrentValues() {
		return currentValues;
	}

	public void setCurrentValues(int... currentValues) {
		this.currentValues = currentValues;
		if (currentValues != null && currentValues.length > 0)
			this.currentValue = currentValues[0];
	}

	public String getUsedKind() {
		return usedKind;
	}

	public void setUsedKind(String usedKind) {
		this.usedKind = usedKind;
	}
}
