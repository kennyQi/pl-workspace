package hg.dzpw.pojo.common;
/**
 * 
 * @类功能说明：景区操作的COMMAND
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-4下午5:41:31
 * @版本：
 */
public class DZPWDealerBaseCommand extends BaseCommand {
	    
	private static final long serialVersionUID = 1L;

	private String dealerId;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	
}
