/**
 * @文件名称：JfFlowDetail.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年11月26日上午11:34:52
 */
package hgtech.jfaccount;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @类功能说明：某笔积分使用详细。如消耗100分，这100分使用的哪些获得明细
 * @类修改者：
 * @修改日期：2014年11月26日上午11:34:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年11月26日上午11:34:52
 * 
 */
@javax.persistence.Entity
@Table(name = "JF_USE")
public class JfUseDetail implements Serializable {
	
	public JfUseDetail(){
		
	}
	
	@Id
	@Column(name = "use_flow_id")
	public String useFlowId;// 消耗流水id
	@Id
	@Column(name = "get_flow_id")
	public String getFlow;// 获得流水id
	@Column(name = "jf")
	public long jf;// 分值
	
	
	@Column(name = "status")
	public String status;//同jfflow.status

	/**
	 * @类名：JfUseDetail.java
	 * @描述：积分使用
	 * @@param useFlowId
	 * @@param getFlow
	 * @@param jf
	 */
	public JfUseDetail(String useFlowId, String getFlow, long jf) {
		super();
		this.useFlowId = useFlowId;
		this.getFlow = getFlow;
		this.jf = jf;
	}

	/**
	 * @return the useFlowId
	 */
	public String getUseFlowId() {
		return useFlowId;
	}

	/**
	 * @param useFlowId
	 *            the useFlowId to set
	 */
	public void setUseFlowId(String useFlowId) {
		this.useFlowId = useFlowId;
	}

	/**
	 * @return the getFlow
	 */
	public String getGetFlow() {
		return getFlow;
	}

	/**
	 * @param getFlow
	 *            the getFlow to set
	 */
	public void setGetFlow(String getFlow) {
		this.getFlow = getFlow;
	}

	/**
	 * @return the jf
	 */
	public long getJf() {
		return jf;
	}

	/**
	 * @param jf
	 *            the jf to set
	 */
	public void setJf(long jf) {
		this.jf = jf;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
