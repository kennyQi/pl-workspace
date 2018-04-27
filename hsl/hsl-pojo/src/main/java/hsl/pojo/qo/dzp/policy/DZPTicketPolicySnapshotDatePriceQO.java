package hsl.pojo.qo.dzp.policy;

import hg.common.component.BaseQo;

/**
 * 电子票-门票政策价格日历QO
 * Created by hgg on 2016/3/8.
 */
public class DZPTicketPolicySnapshotDatePriceQO extends BaseQo{

    /**
     * 游玩日期(yyyyMMdd字符串)
     */
    private String date;
    /**
     * 价格
     */
    private Double price;

    /**
     * 所属门票政策快照ID
     */
    private String ticketPolicySnapshotId;

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

    public String getTicketPolicySnapshotId() {
        return ticketPolicySnapshotId;
    }

    public void setTicketPolicySnapshotId(String ticketPolicySnapshotId) {
        this.ticketPolicySnapshotId = ticketPolicySnapshotId;
    }
}
