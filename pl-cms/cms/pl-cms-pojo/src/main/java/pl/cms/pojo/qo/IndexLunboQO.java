package pl.cms.pojo.qo;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

@QOConfig(daoBeanId = "indexLunboDao")
@SuppressWarnings("serial")
public class IndexLunboQO extends BaseQo {
	
	@QOAttr(name = "isShow" )
	private Boolean isShow;

	// 排序字段
	@QOAttr(name = "createDate", type = QOAttrType.ORDER)
	private int orderByCreateDate;

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public int getOrderByCreateDate() {
		return orderByCreateDate;
	}

	public void setOrderByCreateDate(int orderByCreateDate) {
		this.orderByCreateDate = orderByCreateDate;
	}
}
