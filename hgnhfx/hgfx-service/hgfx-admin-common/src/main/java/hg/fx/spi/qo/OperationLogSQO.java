package hg.fx.spi.qo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import hg.framework.common.base.BaseSPIQO;

/**
 * 操作日志QO
 * */
@SuppressWarnings("serial")
public class OperationLogSQO extends BaseSPIQO{
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**运营端  操作人ID*/
	private String operatorId;
	
	/** 操作开始时间 */
	private Date startDate;
	
	/** 操作开始时间 */
	private Date endDate;

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		try {
			if (StringUtils.isNotBlank(startDate))
				this.startDate = sdf.parse(startDate+" 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		try {
			if (StringUtils.isNotBlank(endDate))
				this.endDate = sdf.parse(endDate+" 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
