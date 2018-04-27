package hg.log.po.domainevent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.po.base.BaseLog;

/**
 * 
 * @类功能说明：记录汇购各系统中的领域事件
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年7月31日下午2:40:54
 *
 */
@Document
public class DomainEvent extends BaseLog {

	private String modelName;

	private String methodName;

	private List<String> params;

	private Integer modelVersion;
	
	private String operate;

	/**
	 * 
	 * @类名：DomainEvent.java
	 * @描述：TODO
	 * @@param modelName 	模型实体名称
	 * @@param methodName 	方法名称
	 * @@param mParams		方法参数，非String一率转json
	 */
	public DomainEvent(String modelName, String methodName, String ... mParams) {
		setId(UUIDGenerator.getUUID());
		setCreateDate(new Date());
		setProjectId(SysProperties.getInstance().get("projectId"));
		setEnvId(SysProperties.getInstance().get("envId"));
		this.modelName = modelName;
		this.methodName = methodName;
		this.params = new ArrayList<String>();
		for (int i = 0; i < mParams.length; i++) {
			this.params.add(mParams[i]);
		}
	}
	
	public DomainEvent() {
		super();
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public Integer getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(Integer modelVersion) {
		this.modelVersion = modelVersion;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

}
