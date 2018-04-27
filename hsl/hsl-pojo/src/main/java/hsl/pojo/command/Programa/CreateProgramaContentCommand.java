package hsl.pojo.command.Programa;
import hg.common.component.BaseCommand;
/**
 * @类功能说明：添加栏目内容
 * @类修改者：
 * @修改日期：2015年4月23日下午2:34:47
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年4月23日下午2:34:47
 */
public class CreateProgramaContentCommand extends BaseCommand{
	private static final long serialVersionUID = 1L;
	/**
	 * 栏目ID
	 */
	private String programaId;
	/**
	 * 内容
	 */
	private String content;
	public String getContent() {
		return content;
	}
	public String getProgramaId() {
		return programaId;
	}
	public void setProgramaId(String programaId) {
		this.programaId = programaId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
