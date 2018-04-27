package hg.demo.member.service.qo.hibernate.fx;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.ImportHistorySQO;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 上午11:53:05
 * @版本： V1.0
 */
@QOConfig(daoBeanId = "importHistoryDAO")
public class ImportHistoryQO extends BaseHibernateQO<String> {

	private static final long serialVersionUID = 1L;

	/**
	 * 是否查询用户
	 */
	private boolean queryDistributorUser = false;
	
	/**
	 * 导入时间 ： 开始时间
	 */
	@QOAttr(name = "importDate", type = QOAttrType.GE)
	private Date beginImportDate;

	/**
	 * 导入时间 ： 结束时间
	 */
	@QOAttr(name = "importDate", type = QOAttrType.LE)
	private Date endImportDate;

	/**
	 * 内连接查询导入人
	 */
	@QOAttr(name = "dstributorUser", type = QOAttrType.LEFT_JOIN)
	private DistributorUserQO distributorUserQO;

	/**
	 * 导入人
	 */
	@QOAttr(name = "dstributorUser.id", type = QOAttrType.EQ)
	private String distributorUserId;
	
	/**
	 *  文件业务内容MD5串
	 */
	@QOAttr(name = "contentMD5", type = QOAttrType.EQ)
	private String contentMD5;
	
	/**
	 * 商户
	 */
	@QOAttr(name = "distributor", type = QOAttrType.LEFT_JOIN)
	private DistributorQO distributorQO;

	@SuppressWarnings("unchecked")
	public static ImportHistoryQO build(ImportHistorySQO sqo) {
		ImportHistoryQO qo = new ImportHistoryQO();
		qo.setBeginImportDate(sqo.getBeginImportDate());
		qo.setEndImportDate(sqo.getEndImportDate());
		qo.setContentMD5(sqo.getContentMD5());
		if (StringUtils.isNotBlank(sqo.getDstributorUserId())) {
			DistributorUserQO dstributorUserqQo = new DistributorUserQO();
			dstributorUserqQo.setId(sqo.getDstributorUserId());
			qo.setDistributorUserQO(dstributorUserqQo);
			qo.setDistributorUserId(sqo.getDstributorUserId());
		}
		if(StringUtils.isNotBlank(sqo.getDistributorID())){
			DistributorQO distributorQO = new DistributorQO();
			distributorQO.setId(sqo.getDistributorID());
			qo.setDistributorQO(distributorQO);
		}
		qo.setLimit(sqo.getLimit());
		return qo;
	}

	public Date getBeginImportDate() {
		return beginImportDate;
	}

	public void setBeginImportDate(Date beginImportDate) {
		this.beginImportDate = beginImportDate;
	}

	public Date getEndImportDate() {
		return endImportDate;
	}

	public void setEndImportDate(Date endImportDate) {
		this.endImportDate = endImportDate;
	}

	public DistributorUserQO getDistributorUserQO() {
		return distributorUserQO;
	}

	public void setDistributorUserQO(DistributorUserQO distributorUserQO) {
		this.distributorUserQO = distributorUserQO;
	}

	public String getDistributorUserId() {
		return distributorUserId;
	}

	public void setDistributorUserId(String distributorUserId) {
		this.distributorUserId = distributorUserId;
	}

	public DistributorQO getDistributorQO() {
		return distributorQO;
	}

	public void setDistributorQO(DistributorQO distributorQO) {
		this.distributorQO = distributorQO;
	}

	public boolean isQueryDistributorUser() {
		return queryDistributorUser;
	}

	public void setQueryDistributorUser(boolean queryDistributorUser) {
		this.queryDistributorUser = queryDistributorUser;
	}

	public String getContentMD5() {
		return contentMD5;
	}

	public void setContentMD5(String contentMD5) {
		this.contentMD5 = contentMD5;
	}

}
