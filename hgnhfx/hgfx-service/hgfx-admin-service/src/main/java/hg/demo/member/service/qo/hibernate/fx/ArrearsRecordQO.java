package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.ArrearsRecordSQO;

import org.apache.commons.lang.StringUtils;

/**
 * @author cangs
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId="arrearsRecordDAO")
public class ArrearsRecordQO extends BaseHibernateQO<String>{

	/**
	 * 先按照审核状态降序排序
	 * 再按照申请时间排序
	 */
	public static String ORDERWAY_1="0001";
	/**
	 * 商户
	 */
	@QOAttr(name = "distributor", type = QOAttrType.LEFT_JOIN)
	private DistributorQO distributorQO;
	
	/**
	 * 排序方式(自定义)
	 */
	private String orderWay;

	public static ArrearsRecordQO build(ArrearsRecordSQO sqo) {
		ArrearsRecordQO qo = new ArrearsRecordQO();
		if(StringUtils.isNotBlank(sqo.getDistributorID())){
			DistributorQO distributorQO = new DistributorQO();
			distributorQO.setId(sqo.getDistributorID());
			qo.setDistributorQO(distributorQO);
		}
		qo.setOrderWay(sqo.getOrderWay());
		qo.setId(sqo.getArrearsRecordID());
		qo.setLimit(sqo.getLimit());
		return qo;
	}
	

	public String getOrderWay() {
		return orderWay;
	}

	public void setOrderWay(String orderWay) {
		this.orderWay = orderWay;
	}

	public DistributorQO getDistributorQO() {
		return distributorQO;
	}

	public void setDistributorQO(DistributorQO distributorQO) {
		this.distributorQO = distributorQO;
	}
}
