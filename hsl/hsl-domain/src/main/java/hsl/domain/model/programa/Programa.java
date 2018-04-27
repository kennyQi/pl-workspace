package hsl.domain.model.programa;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.Programa.CreateProgramaCommand;
/**
 * @类功能说明：栏目管理
 * @备注：
 * @修改说明：
 * @作者：zhaows
 * @创建时间：2015-4-21 9:30:59
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_HSL_AD+"PROGRAMA")
public class Programa extends BaseModel{
	/***栏目名称*/
	@Column(name="NAME")
	private String name;
	/***栏目位置*/
	@Column(name="LOCATION")
	private  Integer location;
	/***栏目状态*/
	@Column(name="STATUS",columnDefinition=M.NUM_COLUM)
	private Integer status;
	/***栏目内容*/
	@OneToMany(mappedBy = "programa",cascade = CascadeType.ALL)
	private List<ProgramaContent> programaContentList;
	 /*** 创建时间*/
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getLocation() {
		return location;
	}
	public void setLocation(Integer location) {
		this.location = location;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @方法功能说明：创建栏目
	 * @修改者名字：zhaows
	 * @修改时间：2015年4月21日下午10:22:26
	 * @修改内容：
	 * @参数：@param createNoticeCommand
	 * @return:void
	 * @throws
	 */
	public void createPrograma(CreateProgramaCommand createProgramaCommand){
		setId(UUIDGenerator.getUUID());
		setCreateDate(new Date());
		setName(createProgramaCommand.getName());
		setLocation(createProgramaCommand.getLocation());
		setStatus(createProgramaCommand.getStatus());
	}
	public List<ProgramaContent> getProgramaContentList() {
		return programaContentList;
	}
	public void setProgramaContentList(List<ProgramaContent> programaContentList) {
		this.programaContentList = programaContentList;
	}
	
	
}
