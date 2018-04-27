package hsl.pojo.command.Programa;

import java.util.List;

import hsl.pojo.command.ad.HslCreateAdCommand;
import hsl.pojo.dto.programa.ProgramaContentDTO;

public class CreateProgramaCommand extends HslCreateAdCommand{
	private static final long serialVersionUID = 1L;
	/**栏目名称*/
	private String name;
	/**栏目位置*/
	private Integer location;
	public static final Integer POSITION_MAINHOME=0;//首页
	public static final Integer POSITION_LINEHOME=1;//线路首页
	/**栏目状态*/
	private Integer status;
	public static final Integer ALL=0;//全部
	public static final Integer SHOW = 1;//显示
	public static final Integer NOT_SHOW = 2;//隐藏
	/**栏目内容*/
	private	List<ProgramaContentDTO> programaContentList;
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
	public List<ProgramaContentDTO> getProgramaContentList() {
		return programaContentList;
	}
	public void setProgramaContentList(List<ProgramaContentDTO> programaContentList) {
		this.programaContentList = programaContentList;
	}
	
	
}
