package hsl.domain.model.dzp.policy;

import hg.common.component.BaseModel;
import hsl.domain.model.M;

import javax.persistence.*;

/**
 * 门票每日价格
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_DZP + "POLICY_SNAPSHOT_DATE_PRICE")
public class DZPTicketPolicySnapshotDatePrice extends BaseModel {

	/**
	 * 所属门票政策快照
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICY_SNAPSHOT_ID")
	private DZPTicketPolicySnapshot ticketPolicySnapshot;

	/**
	 * 游玩日期(yyyyMMdd字符串)
	 */
	@Column(name = "POLICY_DATE", length = 32)
	private String date;

	/**
	 * 价格
	 */
	@Column(name = "PRICE", columnDefinition = M.MONEY_COLUM)
	private Double price;

	public DZPTicketPolicySnapshot getTicketPolicySnapshot() {
		return ticketPolicySnapshot;
	}

	public void setTicketPolicySnapshot(DZPTicketPolicySnapshot ticketPolicySnapshot) {
		this.ticketPolicySnapshot = ticketPolicySnapshot;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
