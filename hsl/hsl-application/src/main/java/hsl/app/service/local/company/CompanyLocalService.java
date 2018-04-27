package hsl.app.service.local.company;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;
import hg.system.common.util.EntityConvertUtils;
import hsl.app.dao.CompanyDao;
import hsl.app.dao.DepartmentDao;
import hsl.app.dao.MemberDao;
import hsl.app.dao.UserDao;
import hsl.domain.model.company.Company;
import hsl.domain.model.company.Department;
import hsl.domain.model.company.Member;
import hsl.domain.model.user.User;
import hsl.pojo.command.CreateCompanyCommand;
import hsl.pojo.command.CreateDepartmentCommand;
import hsl.pojo.command.CreateMemberCommand;
import hsl.pojo.command.DeleteMemberCommand;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.company.DepartmentDTO;
import hsl.pojo.dto.company.MemberDTO;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.company.HslDepartmentQO;
import hsl.pojo.qo.company.HslMemberQO;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class CompanyLocalService extends BaseServiceImpl<Company, HslCompanyQO, CompanyDao>{
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private DepartmentDao departmentDao;
	@Autowired
	private MemberDao memberDao;
	@Override
	protected CompanyDao getDao() {
		return companyDao;
	}
	public CompanyDTO addCompany(CreateCompanyCommand cmd){
//		Company c=BeanMapperUtils.map(cmd, Company.class);
//		c.setCompanyName(cmd.getCompanyName());
		String userid=cmd.getUserId();
		User user=userDao.get(userid);
		Company company=new Company();
		company.setId(UUIDGenerator.getUUID());
		company.setCompanyName(cmd.getCompanyName());
		company.setUser(user);
		companyDao.save(company);
		CompanyDTO dto=BeanMapperUtils.map(company, CompanyDTO.class);
		return dto;
	}
	
	public DepartmentDTO addDepart(CreateDepartmentCommand cmd){
		Company company=companyDao.get(cmd.getCompanyId());
		Department department=new Department();
		department.setId(UUIDGenerator.getUUID());
		department.setCompany(company);
		department.setDeptName(cmd.getDeptName());
		if(StringUtils.isNotBlank(cmd.getParDeptId())){
			department.setParDeptId(cmd.getParDeptId());
		}
		departmentDao.save(department);
		DepartmentDTO dto=BeanMapperUtils.map(department, DepartmentDTO.class);
		return dto;
	}
	
	public MemberDTO addMember(CreateMemberCommand cmd){
		Department department=departmentDao.get(cmd.getDeptId());
		Member m=BeanMapperUtils.map(cmd, Member.class);
		m.setDepartment(department);
		m.setId(UUIDGenerator.getUUID());
		memberDao.save(m);
		MemberDTO dto=BeanMapperUtils.map(m, MemberDTO.class);
		return dto;
	}
	
	public MemberDTO delMember(DeleteMemberCommand cmd){
		Member member=memberDao.get(cmd.getUserId());
		if(member!=null){
			member.setIsDel(1);//1为离职
			memberDao.update(member);
		}
		MemberDTO dto=BeanMapperUtils.map(member, MemberDTO.class);
		return dto;
	}
	
	public  List<MemberDTO> getMembers(HslMemberQO qo){
		List<Member> list = new ArrayList<Member>();
		if(null != qo.getPageNo() && null != qo.getPageSize()){
			list=memberDao.queryList(qo, (qo.getPageNo()-1)*qo.getPageSize(), qo.getPageSize());
		}else{
			list=memberDao.queryList(qo);
		}
		ArrayList<MemberDTO> dtolist=new ArrayList<MemberDTO>();
		for(Member member:list){
			MemberDTO dto=BeanMapperUtils.map(member, MemberDTO.class);
			dtolist.add(dto);
		}
		return dtolist;
	}
	
	public Pagination getMemberPagination(Pagination p){
		p=memberDao.queryPagination(p);
		@SuppressWarnings("unchecked")
		List<Member> list=(List<Member>) p.getList();
		ArrayList<MemberDTO> dtolist=new ArrayList<MemberDTO>();
		for(Member member:list){
			MemberDTO dto=BeanMapperUtils.map(member, MemberDTO.class);
			dtolist.add(dto);
		}
		p.setList(dtolist);
		return p;
	}
	
	public List<DepartmentDTO> getDepartments(HslDepartmentQO qo){
		List<Department> list= departmentDao.queryList(qo);
		ArrayList<DepartmentDTO> dtolist=new ArrayList<DepartmentDTO>();
		for(Department department:list){
			Hibernate.initialize(department.getCompany());
			DepartmentDTO dto=EntityConvertUtils.convertDtoToEntity(department, DepartmentDTO.class);
			dtolist.add(dto);
		}
		return dtolist;
	}
	
	public List<CompanyDTO> getCompanys(HslCompanyQO qo){
		List<Company> list=companyDao.queryList(qo);
		ArrayList<CompanyDTO> dtolist=new ArrayList<CompanyDTO>();
		for(Company company:list){
			CompanyDTO dto=BeanMapperUtils.map(company, CompanyDTO.class);
			dtolist.add(dto);
		}
		return dtolist;
	}
	
	public MemberDTO getMember(HslMemberQO qo){
		List<MemberDTO> list=getMembers(qo);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public void addMemberList(List<CreateMemberCommand> cmdlist) throws Exception{
		try {
			for(CreateMemberCommand command:cmdlist){
				addMember(command);
			}
		} catch (Exception e) {
			throw new Exception("import members fail!");
		}
	}
}
