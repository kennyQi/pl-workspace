package hsl.domain.model.yxjp;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;

import javax.persistence.*;
import java.util.Date;

/**
 * 操作日志
 *
 * @author zhurz
 * @since 1.7
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_YJ + "OPERATE_LOG")
public class YXJPOrderOperateLog extends BaseModel {

	/**
	 * 所属订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID")
	private YXJPOrder fromOrder;

	/**
	 * 操作时间
	 */
	@Column(name = "OPRATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date oprateDate;

	/**
	 * 操作人
	 */
	@Column(name = "OPERATOR", length = 64)
	private String operator;

	/**
	 * 操作内容
	 */
	@Column(name = "CONTENT", length = 1024)
	private String content;

	/**
	 * 调试信息
	 */
	@Column(name = "DEBUG_INFO", columnDefinition = M.TEXT_COLUM)
	private String debugInfo;

	public YXJPOrderOperateLog() {
	}

	public YXJPOrderOperateLog(YXJPOrder fromOrder, String operator, String content, String debugInfo) {
		setId(UUIDGenerator.getUUID());
		this.fromOrder = fromOrder;
		this.operator = operator;
		this.content = content;
		this.debugInfo = debugInfo;
		this.oprateDate = new Date();
	}

	public YXJPOrder getFromOrder() {
		return fromOrder;
	}

	public void setFromOrder(YXJPOrder fromOrder) {
		this.fromOrder = fromOrder;
	}

	public Date getOprateDate() {
		return oprateDate;
	}

	public void setOprateDate(Date oprateDate) {
		this.oprateDate = oprateDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDebugInfo() {
		return debugInfo;
	}

	public void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
}