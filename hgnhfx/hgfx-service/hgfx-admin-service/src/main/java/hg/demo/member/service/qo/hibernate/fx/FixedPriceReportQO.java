package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;

import java.util.Date;

@SuppressWarnings("serial")
public class FixedPriceReportQO extends BaseHibernateQO<String>{
		
		/**
		 * 商品ID
		 * */
		@QOAttr(name="prodId",type = QOAttrType.EQ)
		private String prodId;
		
		/**
		 * 商户ID
		 * */
		@QOAttr(name="distributorId",type = QOAttrType.EQ)
		private String distributorId;
		
		/**
		 * 开始时间
		 */
		private Date startDate;
		/**
		 * 结束时间
		 */
		private Date endDate;
		public String getProdId() {
			return prodId;
		}
		public void setProdId(String prodId) {
			this.prodId = prodId;
		}
		public String getDistributorId() {
			return distributorId;
		}
		public void setDistributorId(String distributorId) {
			this.distributorId = distributorId;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

		
}
