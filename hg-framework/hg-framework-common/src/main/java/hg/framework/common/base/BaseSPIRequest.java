package hg.framework.common.base;

import java.io.Serializable;

/**
 * 基础服务提供者接口请求对象
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
public abstract class BaseSPIRequest extends BaseObject implements Serializable {

	/**
	 * 来源平台标识
	 * <pre>
	 *     说明：
	 *     一个工程可以部署成多个平台，比如这个工程是标准商城，平台可能就是A商城、B商城、C商城、……，
	 *     这些平台都使用这个工程的代码，只是配置不同。
	 * </pre>
	 */
	private String fromPlatform;

	public String getFromPlatform() {
		return fromPlatform;
	}

	public void setFromPlatform(String fromPlatform) {
		this.fromPlatform = fromPlatform;
	}
}
