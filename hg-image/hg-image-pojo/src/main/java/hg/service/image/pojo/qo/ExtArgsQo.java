package hg.service.image.pojo.qo;


/**
 * @类功能说明：扩展参数
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenys
 * @创建时间：2014-10-30上午11:17:56
 */
public class ExtArgsQo extends ImageBaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 扩展参数1
	 */
	private String arg1;

	/**
	 * 扩展参数2
	 */
	private String arg2;

	/**
	 * 扩展参数3
	 */
	private String arg3;

	/**
	 * 扩展参数4
	 */
	private String arg4;

	public String getArg1() {
		return arg1;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1 == null ? null : arg1.trim();
	}

	public String getArg2() {
		return arg2;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2 == null ? null : arg2.trim();
	}

	public String getArg3() {
		return arg3;
	}

	public void setArg3(String arg3) {
		this.arg3 = arg3 == null ? null : arg3.trim();
	}

	public String getArg4() {
		return arg4;
	}

	public void setArg4(String arg4) {
		this.arg4 = arg4 == null ? null : arg4.trim();
	}
}