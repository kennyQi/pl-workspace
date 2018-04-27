package hsl.domain.model.programa;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.Programa.CreateProgramaContentCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @类功能说明：栏目内容
 * @备注：
 * @修改说明：
 * @作者：zhaows
 * @创建时间：2015-4-21 9:54:59
 */
@Entity
@Table(name = M.TABLE_PREFIX_HSL_AD + "PROGRAMACONTENT")
public class ProgramaContent extends BaseModel {
	private static final long serialVersionUID = 1L;

	/** 栏目内容 */
	@Column(name = "CONTENT")
	private String content;
	/**
	 *所属栏目 
	 */
	@ManyToOne
	@JoinColumn(name = "PROGRAMA_ID")
	private Programa programa;
	/**
	 * @方法功能说明：创建栏目内容
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月23日下午2:48:16
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void createProgramaContent(CreateProgramaContentCommand command){
		this.setId(UUIDGenerator.getUUID());
		this.setContent(command.getContent());
		programa=new Programa();
		programa.setId(command.getProgramaId());
		this.setPrograma(programa);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Programa getPrograma() {
		return programa;
	}
	public void setPrograma(Programa programa) {
		this.programa = programa;
	}
}
