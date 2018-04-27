package hg.dzpw.dealer.client.api.v1.response;

import java.util.List;

import hg.dzpw.dealer.client.common.ApiResponse;
import hg.dzpw.dealer.client.dto.useRecord.UseRecordDTO;


@SuppressWarnings("serial")
public class UseRecordResponse extends ApiResponse{
	
	/** 缺少必填参数 */
	public final static String RESULT_REQUIRED_PARAM = "-1";
	
	/**
	 * 票号不存在
	 */
	public final static String RESULT_TICKET_NO_NOT_EXISTS = "-2";
	
	
	private List<UseRecordDTO> useRecords;


	public List<UseRecordDTO> getUseRecords() {
		return useRecords;
	}


	public void setUseRecords(List<UseRecordDTO> useRecords) {
		this.useRecords = useRecords;
	}
	
}
