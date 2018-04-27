package slfx.jp.pojo.dto.supplier.yg;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：退废票种类DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:58:24
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class RefundActionTypeDTO extends BaseJpDTO{
	
   private String id;
   
   /** 接口错误代码 */
   private String errorCode;
   
   /** 错误描述信息 */
   private String errorMsg;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
   
}