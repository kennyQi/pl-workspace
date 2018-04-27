package lxs.pojo.qo.line;
import hg.common.component.BaseQo;
import lxs.pojo.dto.line.LineOrderStatusDTO;

/**
 *@类功能说明：线路游玩人qo
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：tandeng
 *@创建时间：2014年12月18日下午3:10:49
 *
 */
@SuppressWarnings("serial")
public class LineOrderTravelerQO extends BaseQo {

	/**
	 * 排序条件
	 */
	private Boolean createDateAsc;
	
	/**
	 * 线路订单id
	 */
	private String lineOrderId;
	
	/**
	 * 线路订单状态
	 */
	private LineOrderStatusDTO lineOrderStatusDTO;
		
	
	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}

	public String getLineOrderId() {
		return lineOrderId;
	}

	public void setLineOrderId(String lineOrderId) {
		this.lineOrderId = lineOrderId;
	}

	public LineOrderStatusDTO getLineOrderStatusDTO() {
		return lineOrderStatusDTO;
	}

	public void setLineOrderStatusDTO(LineOrderStatusDTO lineOrderStatusDTO) {
		this.lineOrderStatusDTO = lineOrderStatusDTO;
	}

	
}
