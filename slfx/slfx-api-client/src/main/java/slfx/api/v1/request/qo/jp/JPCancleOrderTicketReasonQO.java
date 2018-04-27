package slfx.api.v1.request.qo.jp;

import slfx.api.base.ApiPayload;

/**
 * 
 * @类功能说明：政策查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月1日下午4:46:31
 * @版本：V1.0
 *
 */
public class JPCancleOrderTicketReasonQO extends ApiPayload {
	
	/**
	 * @FieldsserialVersionUID:TODO
	 */
	private static final long serialVersionUID = -6432007342373646142L;
	private String platCode;

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	

}
