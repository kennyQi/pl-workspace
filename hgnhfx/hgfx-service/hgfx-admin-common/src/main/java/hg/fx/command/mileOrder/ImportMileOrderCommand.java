package hg.fx.command.mileOrder;

import hg.framework.common.base.BaseSPICommand;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 导入订单命令
 * @date 2016-6-16上午10:48:14
 * @since
 */
@SuppressWarnings("serial")
public class ImportMileOrderCommand extends BaseSPICommand{
	public ImportMileOrderCommand() {
		list = new LinkedList<CreateMileOrderCommand>();
	}

	//校验之后的成功的条数，里程数
	private int okRows=0;
	
	private Long okMiles=0L;
	
	private List<CreateMileOrderCommand> list;

	public List<CreateMileOrderCommand> getList() {
		return list;
	}

	public void setList(List<CreateMileOrderCommand> list) {
		this.list = list;
	}
	
	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}


	private String distributorId;

	public int getOkRows() {
		return okRows;
	}

	public void setOkRows(int okRows) {
		this.okRows = okRows;
	}

	public Long getOkMiles() {
		return okMiles;
	}

	public void setOkMiles(Long okMiles) {
		this.okMiles = okMiles;
	}
	
}
