package jxc.domain.model.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreatePaymentMethodCommand;
import hg.pojo.command.CreateUnitCommand;
import hg.pojo.command.ModifyPaymentMethodCommand;
import hg.pojo.command.ModifyUnitCommand;
import hg.pojo.command.RemovePaymentMethodCommand;
import hg.pojo.command.RemoveUnitCommand;

/**
 * 结算方式
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_SYETEM + "PAYMENT_METHOD")
public class PaymentMethod extends JxcBaseModel {
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	@Column(name = "PAYMENT_METHOD_NAME", length = 50)
	private String paymentMethodName;

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public void createPaymentMethod(CreatePaymentMethodCommand command) {
		createDate = new Date();

		setId(UUIDGenerator.getUUID());
		setPaymentMethodName(command.getPaymentMethodName());
		setStatusRemoved(false);
	}

	public void modifyPaymentMethod(ModifyPaymentMethodCommand command) {
		setPaymentMethodName(command.getPaymentMethodName());
	}

	public void removePaymentMethod(RemovePaymentMethodCommand command) {
		setId(command.getId());
		setStatusRemoved(true);

	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
