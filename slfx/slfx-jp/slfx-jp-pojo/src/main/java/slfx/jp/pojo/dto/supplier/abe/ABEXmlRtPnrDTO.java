package slfx.jp.pojo.dto.supplier.abe;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * @类功能说明：ABE XmlRtPnr_1_1 接口返回DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-8-27下午6:00:19
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class ABEXmlRtPnrDTO extends BaseJpDTO{
	
	/**
	 * PNR TEXT
	 */
	private String hostText;
	
	/**
	 * BIG PNR
	 */
	private String pnrNo;

	public String getHostText() {
		return hostText;
	}

	public void setHostText(String hostText) {
		this.hostText = hostText;
	}

	public String getPnrNo() {
		return pnrNo;
	}

	public void setPnrNo(String pnrNo) {
		this.pnrNo = pnrNo;
	}
	
	
}
