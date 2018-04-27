package slfx.jp.qo.api;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：退废票种类查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:14:46
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class GetRefundActionTypeQO extends BaseQo {

	/**
	 * 三字编码
	 * （例如：001）见附件一、平台代码表
	 */
	private String platCode;

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	
	
}
