package hg.pojo.dto.ems;


import java.io.Serializable;
import java.util.List;

/**
 * 运单轨迹回传
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class EmsTraceDTO implements Serializable{


	private List<TraceDTO> traceList;

	public List<TraceDTO> getRow() {
		return traceList;
	}

	public void setRow(List<TraceDTO> traceList) {
		this.traceList = traceList;
	}
	
	
}
