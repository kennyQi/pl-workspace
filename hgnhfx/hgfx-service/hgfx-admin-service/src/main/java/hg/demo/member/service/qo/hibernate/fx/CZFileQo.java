package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.CZFileSQO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;

public class CZFileQo extends BaseHibernateQO<String> {
	private static final long serialVersionUID = 1L;
	private String id;
	private String status;
	private String fileName;
	public String startDate;
	public String endDate;
	public String type;
	public Date timestamp;
	public Date feedbacktime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getFeedbacktime() {
		return feedbacktime;
	}

	public void setFeedbacktime(Date feedbacktime) {
		this.feedbacktime = feedbacktime;
	}

	public static CZFileQo build(CZFileSQO sqo) {
		CZFileQo qo = new CZFileQo();
		qo.setLimit(sqo.getLimit());
		if (sqo != null) {

			if (StringUtils.isNotBlank(sqo.getId())) {
				qo.setId(sqo.getId());
			}
			if (StringUtils.isNotBlank(sqo.getStatus())) {
				qo.setStatus(sqo.getStatus());
			}
			if (StringUtils.isNotBlank(sqo.getFileName())) {
				qo.setFileName(sqo.getFileName());
			}
			if (StringUtils.isNotBlank(sqo.getStartDate())) {
				qo.setStartDate(sqo.getStartDate());
			}

			if (StringUtils.isNotBlank(sqo.getEndDate())) {
				qo.setEndDate(sqo.getEndDate());
			}
			if (StringUtils.isNotBlank(sqo.getType())) {
				qo.setType(sqo.getType());
			}
		}
		return qo;
	}

}
