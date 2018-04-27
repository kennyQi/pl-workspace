package hsl.spi.inter.company;

import hg.common.component.BaseQo;
import hg.common.page.Pagination;
import hsl.pojo.command.CreateCompanyCommand;
import hsl.pojo.command.CreateDepartmentCommand;
import hsl.pojo.command.CreateMemberCommand;
import hsl.pojo.command.DeleteMemberCommand;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.company.DepartmentDTO;
import hsl.pojo.dto.company.MemberDTO;
import hsl.pojo.dto.company.TravelDTO;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.company.HslDepartmentQO;
import hsl.pojo.qo.company.HslMemberQO;
import hsl.pojo.qo.company.HslTravelQO;
import hsl.spi.inter.BaseSpiService;

import java.util.List;

/**
 * 组织结构和商旅管理操作
 * @author wuyg
 *
 */
public interface CompanyService extends BaseSpiService<TravelDTO,BaseQo>{
	/**
	 * 查询公司列表
	 * @param id
	 * @return
	 */
	public List<CompanyDTO> getCompanys(HslCompanyQO qo);
	/**
	 * 获得部门列表
	 * @param id
	 * @return
	 */
	public List<DepartmentDTO> getDepartments(HslDepartmentQO qo);
	/**
	 * 获得人员列表
	 * @param id
	 * @return
	 */
	public List<MemberDTO> getMembers(HslMemberQO qo);
	/**
	 * member分页
	 * @param qo
	 * @return
	 */
	public Pagination getMemberPagination(Pagination p);
	/**
	 * 添加公司
	 * @param cmd
	 * @return
	 */
	public CompanyDTO addCompany(CreateCompanyCommand cmd);
	/**
	 * 添加部门
	 * @param cmd
	 * @return
	 */
	public DepartmentDTO addDepartment(CreateDepartmentCommand cmd);
	/**
	 * 添加成员
	 * @param cmd
	 * @return
	 */
	public MemberDTO addMember(CreateMemberCommand cmd);
	/**
	 * 修改成员
	 * @param cmd
	 * @return
	 */
	public MemberDTO updateMember(CreateMemberCommand cmd);
	/**
	 * 删除成员
	 * @param cmd
	 * @return
	 */
	public MemberDTO delMember(DeleteMemberCommand cmd);
	//差旅
	/**
	 * 差旅查询
	 * @param qo
	 * @return
	 */
	public List<TravelDTO> getTravelInfo(HslTravelQO qo);
	
	/**
	 * 差旅查询分页
	 * @param qo
	 * @return
	 */
	public Pagination getTravelInfoPage(HslTravelQO qo);
	/**
	 * 批量导入member
	 * @param cmdlist
	 * @throws Exception
	 */
	public void importMembers(List<CreateMemberCommand> cmdlist) throws Exception;
}
