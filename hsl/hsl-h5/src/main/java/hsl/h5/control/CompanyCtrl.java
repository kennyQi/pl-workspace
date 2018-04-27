package hsl.h5.control;

import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.domain.model.coupon.CouponStatus;
import hsl.h5.base.result.api.ApiResult;
import hsl.h5.base.result.company.CompanyListResult;
import hsl.h5.base.result.company.CompanySearchResult;
import hsl.h5.base.result.company.DepartmentResult;
import hsl.h5.base.result.company.MemberListResult;
import hsl.h5.base.result.company.MemberResult;
import hsl.h5.base.result.coupon.CouponQueryResult;
import hsl.h5.base.utils.CachePool;
import hsl.h5.base.utils.OpenidTracker;
import hsl.h5.exception.HslapiException;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.company.CompanyListDTO;
import hsl.pojo.dto.company.CompanySearchDTO;
import hsl.pojo.dto.company.DepartmentDTO;
import hsl.pojo.dto.company.MemberDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.company.HslCompanySearchQO;
import hsl.pojo.qo.company.HslDepartmentQO;
import hsl.pojo.qo.company.HslMemberQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.company.CompanyService;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：部门员工通讯录Action
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2014年11月6日上午11:03:29
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping("company")
public class CompanyCtrl extends HslCtrl{

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CouponService couponService;
	
	public final static String PAGE_LIST = "company/company";
	
	public final static String PAGE_MEMBERLIST = "company/memberList";
	
	public final static String PAGE_MEMBERDETAIL = "company/memberDetail";
	
	public final static String PAGE_MYCOUPON = "company/myCoupon";
	
	public final static String PAGE_SEARCHRESULT = "company/searchResult";
	
	public final static String PAGE_ERROR = "error";
	
	public final static String PAGE_COUPONDETAIL = "company/myCouponDetail";
			
	/**
	 * 
	 * @方法功能说明：获取部门员工通讯录列表
	 * @修改者名字：yuqz
	 * @修改时间：2014年11月6日上午11:10:37
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("list")
	public String list(HslCompanyQO qo, Model model,HttpServletRequest request){
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->list获取部门员工通讯录列表" + JSON.toJSONString(qo));
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		HslMemberQO hslMemberQO = new HslMemberQO();
		String userid = "";
		try {
			if(isWCBrowser(request)){
				userid = CachePool.getUser(OpenidTracker.get());
				HgLogger.getInstance().info("yuqz", "CompanyCtrl->list获取部门员工通讯录列表->微信端取得userId：" + userid);
			}else{
				userid= getUserId(request);
				HgLogger.getInstance().info("yuqz", "CompanyCtrl->list获取部门员工通讯录列表->非微信端取得userId：" + userid);
			}
		} catch (HslapiException e) {
			HgLogger.getInstance().error("yuqz",
					"获取用户信息失败:" + HgLogger.getStackTrace(e));
		}
		qo.setUserId(userid);
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->list获取部门员工通讯录公司列表请求开始：" + qo);
		List<CompanyListDTO> companyLists = new ArrayList<CompanyListDTO>();
		List<CompanyDTO> companys = companyService.getCompanys(qo);
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->list获取部门员工通讯录公司列表完成，结果：" + companys);
		for(int i = 0; i < companys.size(); i++){
			CompanyDTO dto = companys.get(i);
			HslCompanyQO hslCompanyQO = new HslCompanyQO();
			hslCompanyQO.setId(dto.getId());	
			hslDepartmentQO.setCompanyQO(hslCompanyQO);
			HgLogger.getInstance().info("yuqz", "CompanyCtrl->list获取部门员工通讯录部门列表请求开始：" + hslDepartmentQO);
			List<DepartmentDTO> departments = companyService.getDepartments(hslDepartmentQO);
			HgLogger.getInstance().info("yuqz", "CompanyCtrl->list获取部门员工通讯录部门列表完成，结果：" + departments);
			for(int j = 0; j < departments.size(); j++){
				hslMemberQO.setDepartmentId(departments.get(j).getId());
				HgLogger.getInstance().info("yuqz", "CompanyCtrl->list获取部门员工通讯录成员列表请求开始：" + hslMemberQO);
				List<MemberDTO> members = companyService.getMembers(hslMemberQO);
				HgLogger.getInstance().info("yuqz", "CompanyCtrl->list获取部门员工通讯录成员列表完成，结果：" + members);
				departments.get(j).setTotalCount(members.size());
			}
			CompanyListDTO companyListDTO = new CompanyListDTO();
			companyListDTO.setCompanyId(dto.getId());
			companyListDTO.setCompanyName(dto.getCompanyName());
			companyListDTO.setDepartmentList(departments);
			companyLists.add(companyListDTO);
		}
		CompanyListResult companyListResult = new CompanyListResult();
		companyListResult.setCompanyList(companyLists);
		companyListResult.setResult(ApiResult.RESULT_CODE_OK);
		companyListResult.setMessage("查询成功");
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->list获取部门员工通讯录列表完成->结果：" + JSON.toJSONString(companyListResult));
		model.addAttribute("companyListResult", companyListResult);
		return PAGE_LIST;
	}
	
	/**
	 * 
	 * @方法功能说明：部门员工通讯录搜索功能
	 * @修改者名字：yuqz
	 * @修改时间：2014年11月6日下午1:32:04
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("search")
	public String search(HslCompanySearchQO qo, Model model, HttpServletRequest request){
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->companySearch部门员工通讯录搜索功能" + JSON.toJSONString(qo));
		CompanySearchResult companySearchResult = new CompanySearchResult();
		companySearchResult.setResult(companySearchResult.COMPANY_NOT_EXIST);//默认搜索结果没有
		HslCompanyQO hslCompanyQO = new HslCompanyQO();
		try {
			hslCompanyQO.setUserId(getUserId(request));
		} catch (HslapiException e) {
			HgLogger.getInstance().error("yuqz",
					"获取用户信息失败:" + HgLogger.getStackTrace(e));
		}
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		HslMemberQO hslMemberQO = new HslMemberQO();
		List<CompanySearchDTO> companySearchList = new ArrayList<CompanySearchDTO>();
		List<CompanyDTO> companys = companyService.getCompanys(hslCompanyQO);
		if(companys == null || companys.size() <= 0){
			companySearchResult.setResult(companySearchResult.COMPANY_NOT_EXIST);
			companySearchResult.setMessage("没有组织");
			return JSON.toJSONString(companySearchResult);
		}
		
		if(StringUtils.isNotBlank(qo.getSearchName())){
			hslMemberQO.setSearchName(qo.getSearchName());
		}
		
		//先在成员里搜索
		boolean isExist = false;//查询结果是否有成员
		int totalItemSize = 0;//成员查询结果数量
		int i = 0,j = 0, k = 0;//提升性能
		for( i = 0; i < companys.size(); i++){
			CompanySearchDTO companySearchDTO = new CompanySearchDTO();
			CompanyDTO dto = companys.get(i);
			hslCompanyQO.setId(dto.getId());	
			hslDepartmentQO.setCompanyQO(hslCompanyQO);
			List<DepartmentDTO> departments = companyService.getDepartments(hslDepartmentQO);
			for( j = 0; j < departments.size(); j++){
				hslMemberQO.setDepartmentId(departments.get(j).getId());
				List<MemberDTO> members = companyService.getMembers(hslMemberQO);
				if(members.size() > 0){
					isExist = true;
					totalItemSize = totalItemSize + members.size();
					for( k = 0; k < members.size(); k++){
						companySearchDTO = new CompanySearchDTO();
						companySearchDTO.setCompanyId(dto.getId());
						companySearchDTO.setCompanyName(dto.getCompanyName());
						companySearchDTO.setDepartId(departments.get(j).getId());
						companySearchDTO.setDepartName(departments.get(j).getDeptName());
						companySearchDTO.setMemberName(members.get(k).getName());
						companySearchDTO.setJob(members.get(k).getJob());
						companySearchDTO.setMobile(members.get(k).getMobilePhone());
						companySearchDTO.setMemberId(members.get(k).getId());
						companySearchDTO.setCertificateID(members.get(k).getCertificateID());
						companySearchList.add(companySearchDTO);
					}
				}
			}
		}
		
		if(StringUtils.isNotBlank(qo.getSearchName())){
			hslDepartmentQO.setSearchName(qo.getSearchName());
		}
		
		//如果成员没值就在部门里面搜
		i = 0;j = 0; 
 		if(!isExist || totalItemSize == 0){
			companySearchList.clear();
//			companySearchList = new ArrayList<CompanySearchDTO>();
			for( i = 0; i < companys.size(); i++){
				CompanyDTO dto = companys.get(i);
				hslCompanyQO.setId(dto.getId());	
				hslDepartmentQO.setCompanyQO(hslCompanyQO);
				List<DepartmentDTO> departments = companyService.getDepartments(hslDepartmentQO);
				for( j = 0; j < departments.size(); j++){
					hslMemberQO.setDepartmentId(departments.get(j).getId());
					List<MemberDTO> members = companyService.getMembers(hslMemberQO);
					departments.get(j).setTotalCount(members.size());
					CompanySearchDTO companySearchDTO = new CompanySearchDTO();
					companySearchDTO.setCompanyName(dto.getCompanyName());
					companySearchDTO.setCompanyId(dto.getId());
					companySearchDTO.setDepartName(departments.get(j).getDeptName());
					companySearchDTO.setDepartId(departments.get(j).getId());
					companySearchDTO.setMemberCount(members.size());
					companySearchList.add(companySearchDTO);
					companySearchResult.setResult(companySearchResult.COMPANY_NOMEMBER);//搜索结果无成员时
				}
			}
		}else{
			companySearchResult.setResult(companySearchResult.COMPANY_MEMBER);
		}
		companySearchResult.setCompanySearchList(companySearchList);
		companySearchResult.setMessage("查询成功");
//		model.addAttribute("companySearchResult", companySearchResult);
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->companySearch部门员工通讯录搜索功能完成->结果：" + JSON.toJSONString(companySearchResult));
		return JSON.toJSONString(companySearchResult);
	}
	
	/**
	 * 
	 * @方法功能说明：部门员工通讯录搜索功能
	 * @修改者名字：yuqz
	 * @修改时间：2014年11月6日下午1:32:04
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("searchView")
	public String searchView(HslCompanySearchQO qo, Model model, HttpServletRequest request){
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->companySearch部门员工通讯录搜索功能" + JSON.toJSONString(qo));
		CompanySearchResult companySearchResult = new CompanySearchResult();
		companySearchResult.setResult(companySearchResult.COMPANY_NOT_EXIST);//默认搜索结果没有
		HslCompanyQO hslCompanyQO = new HslCompanyQO();
		try {
			String userId = "";
			if(isWCBrowser(request)){
				userId = CachePool.getUser(OpenidTracker.get());
				HgLogger.getInstance().info("yuqz", "CompanyCtrl->companySearch部门员工通讯录搜索功能->微信端取得userId：" + userId);
			}else{
				userId= getUserId(request);
				HgLogger.getInstance().info("yuqz", "CompanyCtrl->companySearch部门员工通讯录搜索功能->非微信端取得userId：" + userId);
			}
			hslCompanyQO.setUserId(userId);
		} catch (HslapiException e) {
			HgLogger.getInstance().error("yuqz",
					"获取用户信息失败:" + HgLogger.getStackTrace(e));
			return PAGE_ERROR;
		}
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		HslMemberQO hslMemberQO = new HslMemberQO();
		List<CompanySearchDTO> companySearchList = new ArrayList<CompanySearchDTO>();
		List<CompanyDTO> companys = companyService.getCompanys(hslCompanyQO);
		if(companys == null || companys.size() <= 0){
			companySearchResult.setResult(companySearchResult.COMPANY_NOT_EXIST);
			companySearchResult.setMessage("没有组织");
			model.addAttribute("companySearchResult", companySearchResult);
			return PAGE_SEARCHRESULT;
		}
		
		if(StringUtils.isNotBlank(qo.getSearchName())){
			hslMemberQO.setSearchName(qo.getSearchName());
		}else{
			hslMemberQO.setSearchName("");
		}
		
		//先在成员里搜索
		boolean isExist = false;//查询结果是否有成员
		int totalItemSize = 0;//成员查询结果数量
		int i = 0,j = 0, k = 0;//提升性能
		for( i = 0; i < companys.size(); i++){
			CompanySearchDTO companySearchDTO = new CompanySearchDTO();
			CompanyDTO dto = companys.get(i);
			hslCompanyQO.setId(dto.getId());	
			hslDepartmentQO.setCompanyQO(hslCompanyQO);
			List<DepartmentDTO> departments = companyService.getDepartments(hslDepartmentQO);
			for( j = 0; j < departments.size(); j++){
				hslMemberQO.setDepartmentId(departments.get(j).getId());
				List<MemberDTO> members = companyService.getMembers(hslMemberQO);
				if(members.size() > 0){
					isExist = true;
					totalItemSize = totalItemSize + members.size();
					for( k = 0; k < members.size(); k++){
						companySearchDTO = new CompanySearchDTO();
						companySearchDTO.setCompanyId(dto.getId());
						companySearchDTO.setCompanyName(dto.getCompanyName());
						companySearchDTO.setDepartId(departments.get(j).getId());
						companySearchDTO.setDepartName(departments.get(j).getDeptName());
						companySearchDTO.setMemberName(members.get(k).getName());
						companySearchDTO.setJob(members.get(k).getJob());
						companySearchDTO.setMobile(members.get(k).getMobilePhone());
						companySearchDTO.setMemberId(members.get(k).getId());
						companySearchDTO.setCertificateID(members.get(k).getCertificateID());
						companySearchList.add(companySearchDTO);
					}
				}
			}
		}
		
		if(StringUtils.isNotBlank(qo.getSearchName())){
			hslDepartmentQO.setSearchName(qo.getSearchName());
		}else{
			hslMemberQO.setSearchName("");
		}
		
		//如果成员没值就在部门里面搜
		i = 0;j = 0; 
 		if(!isExist || totalItemSize == 0){
			companySearchList.clear();
//			companySearchList = new ArrayList<CompanySearchDTO>();
			for( i = 0; i < companys.size(); i++){
				CompanyDTO dto = companys.get(i);
				hslCompanyQO.setId(dto.getId());	
				hslDepartmentQO.setCompanyQO(hslCompanyQO);
				List<DepartmentDTO> departments = companyService.getDepartments(hslDepartmentQO);
				for( j = 0; j < departments.size(); j++){
					hslMemberQO.setDepartmentId(departments.get(j).getId());
					List<MemberDTO> members = companyService.getMembers(hslMemberQO);
					departments.get(j).setTotalCount(members.size());
					CompanySearchDTO companySearchDTO = new CompanySearchDTO();
					companySearchDTO.setCompanyName(dto.getCompanyName());
					companySearchDTO.setCompanyId(dto.getId());
					companySearchDTO.setDepartName(departments.get(j).getDeptName());
					companySearchDTO.setDepartId(departments.get(j).getId());
					companySearchDTO.setMemberCount(members.size());
					companySearchList.add(companySearchDTO);
					companySearchResult.setResult(companySearchResult.COMPANY_NOMEMBER);//搜索结果无成员时
				}
			}
		}else{
			companySearchResult.setResult(companySearchResult.COMPANY_MEMBER);
		}
 		
 		if(companySearchList.size() == 0){
 			companySearchResult.setResult(companySearchResult.COMPANY_NORESULT);//搜索成员部门都无结果时
 		}
		companySearchResult.setCompanySearchList(companySearchList);
		companySearchResult.setMessage("查询成功");
		model.addAttribute("companySearchResult", companySearchResult);
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->companySearch部门员工通讯录搜索功能完成->结果：" + JSON.toJSONString(companySearchResult));
		return PAGE_SEARCHRESULT;
	}
	
	/**
	 * 
	 * @方法功能说明：根据部门id查询成员列表
	 * @修改者名字：yuqz
	 * @修改时间：2014年11月10日下午3:17:39
	 * @修改内容：
	 * @参数：@param hslMemberQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("queryMembers")
	public String queryMembers(HttpServletRequest request, HslMemberQO hslMemberQO, Model model){
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->queryMembers根据部门id查询成员列表" + JSON.toJSONString(hslMemberQO));
		List<MemberDTO> memberList = companyService.getMembers(hslMemberQO);
		
		//获取部门名称和组织名称
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		HslCompanyQO hslCompanyQO = new HslCompanyQO();
		hslDepartmentQO.setCompanyQO(hslCompanyQO);
		hslDepartmentQO.setId(hslMemberQO.getDepartmentId());
		DepartmentDTO department = companyService.getDepartments(hslDepartmentQO).get(0);
		hslCompanyQO.setId(department.getCompanyDTO().getId());
		CompanyDTO company = companyService.getCompanys(hslCompanyQO).get(0);
		
		MemberListResult memberListResult = new MemberListResult();
		memberListResult.setDepartId(department.getId());
		memberListResult.setDepartName(department.getDeptName());
		memberListResult.setCompanyName(company.getCompanyName());
		memberListResult.setMemberList(memberList);
		memberListResult.setResult(ApiResult.RESULT_CODE_OK);
		memberListResult.setMessage("查询成功");
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->queryMembers根据部门id查询成员列表完成->结果：" + JSON.toJSONString(memberListResult));
		model.addAttribute("memberListResult", memberListResult);
		return PAGE_MEMBERLIST;
	}
	
	/**
	 * 
	 * @方法功能说明：根据部门id,pageNo,pageSize查询成员列表
	 * @修改者名字：yuqz
	 * @修改时间：2014年12月10日下午2:04:18
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param hslMemberQO
	 * @参数：@param model
	 * @throws
	 */
	@RequestMapping("queryMembersMore")
	public void queryMembersMore(HttpServletRequest request, HslMemberQO hslMemberQO, PrintWriter out){
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->queryMembers根据部门id查询成员列表" + JSON.toJSONString(hslMemberQO));
		List<MemberDTO> memberList = companyService.getMembers(hslMemberQO);
		
		//获取部门名称和组织名称
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		hslDepartmentQO.setId(hslMemberQO.getDepartmentId());
		DepartmentDTO department = companyService.getDepartments(hslDepartmentQO).get(0);
		HslCompanyQO hslCompanyQO = new HslCompanyQO();
		hslCompanyQO.setId(department.getCompanyDTO().getId());
		CompanyDTO company = companyService.getCompanys(hslCompanyQO).get(0);
		
		MemberListResult memberListResult = new MemberListResult();
		memberListResult.setDepartId(department.getId());
		memberListResult.setDepartName(department.getDeptName());
		memberListResult.setCompanyName(company.getCompanyName());
		memberListResult.setMemberList(memberList);
		memberListResult.setMemberCount(memberList.size());
		memberListResult.setResult(ApiResult.RESULT_CODE_OK);
		memberListResult.setMessage("查询成功");
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->queryMembers根据部门id查询成员列表完成->结果：" + JSON.toJSONString(memberListResult));
		out.print(JSON.toJSONString(memberListResult));
	}
	
	/**
	 * 
	 * @方法功能说明：根据成员id查询成员信息
	 * @修改者名字：yuqz
	 * @修改时间：2014年11月10日下午4:03:46
	 * @修改内容：
	 * @参数：@param hslMemberQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("queryMemberDetail")
	public String queryMemberDetail(HttpServletRequest request, HslMemberQO hslMemberQO, Model model){
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->queryMemberDetail进入根据成员id查询成员信息请求" + JSON.toJSONString(hslMemberQO));
		MemberDTO member = companyService.getMembers(hslMemberQO).get(0);
		MemberResult memberResult = new MemberResult();
		try {
			String userId = "";
			if(isWCBrowser(request)){
				userId = CachePool.getUser(OpenidTracker.get());
			}else{
				userId= getUserId(request);
			}
			UserDTO userDTO = getUserByUserId(userId);
			memberResult.setHeadImgUrl(userDTO.getBaseInfo().getImage());
		} catch (HslapiException e) {
			HgLogger.getInstance().error("yuqz",
					"获取用户信息失败:" + HgLogger.getStackTrace(e));

		}
		
		//获取部门名称和组织名称
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		HslCompanyQO hslCompanyQO = new HslCompanyQO();
		hslDepartmentQO.setCompanyQO(hslCompanyQO);
		hslDepartmentQO.setId(hslMemberQO.getDepartmentId());
		DepartmentDTO department = companyService.getDepartments(hslDepartmentQO).get(0);
		hslCompanyQO.setId(department.getCompanyDTO().getId());
		CompanyDTO company = companyService.getCompanys(hslCompanyQO).get(0);
		
		memberResult.setDepartName(department.getDeptName());
		memberResult.setCompanyName(company.getCompanyName());
				
		memberResult.setMember(member);
		memberResult.setResult(ApiResult.RESULT_CODE_OK);
		memberResult.setMessage("查询成功");
		HgLogger.getInstance().info("yuqz", "CompanyCtrl->queryMemberDetail根据成员id查询成员信息完成->结果：" + JSON.toJSONString(memberResult));
		model.addAttribute("memberResult", memberResult);
		return PAGE_MEMBERDETAIL;
	}
	
	/**
	 * 
	 * @方法功能说明：卡劵查询
	 * @修改者名字：yuqz
	 * @修改时间：2014年11月6日下午2:07:07
	 * @修改内容：
	 * @参数：@param hslCouponQO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("couponQuery")
	public String couponQuery(HslCouponQO hslCouponQO, Model model, HttpServletRequest request){
		HgLogger.getInstance().info("yuqz", "company->couponQuery进入卡劵查询请求" + JSON.toJSONString(hslCouponQO));
		try {
			String userId = "";
			if(isWCBrowser(request)){
				userId = CachePool.getUser(OpenidTracker.get());
			}else{
				userId= getUserId(request);
			}
			hslCouponQO.setUserId(userId);
		} catch (HslapiException e) {
			HgLogger.getInstance().error("yuqz",
					"获取用户信息失败:" + HgLogger.getStackTrace(e));

		}
		if(hslCouponQO.getIsAvailable().equals("true")){
			Object[] statusTypes = {CouponStatus.TYPE_NOUSED};
			hslCouponQO.setStatusTypes(statusTypes);
			model.addAttribute("index", 0);
		}else{
			Object[] statusTypes = {CouponStatus.TYPE_ISUSED,CouponStatus.TYPE_OVERDUE,CouponStatus.TYPE_CANCEL};
			hslCouponQO.setStatusTypes(statusTypes);
			model.addAttribute("index", 1);
		}
		hslCouponQO.setFromH5(true);
		HgLogger.getInstance().info("yuqz", "company->couponQuery开始卡劵查询" + JSON.toJSONString(hslCouponQO));
		List<CouponDTO> couponDTOList= couponService.queryList(hslCouponQO);
		CouponQueryResult couponQueryResult = new CouponQueryResult();
		if(couponDTOList == null || couponDTOList.size() < 0){
			couponQueryResult.setMessage("查询失败");
			couponQueryResult.setResult(couponQueryResult.RESULT_CODE_FAIL);
			return JSON.toJSONString(couponQueryResult);
		}
		couponQueryResult.setCouponDTOList(couponDTOList);
		couponQueryResult.setMessage("查询成功");
		couponQueryResult.setResult(couponQueryResult.RESULT_CODE_OK);
		HgLogger.getInstance().info("yuqz", "company->couponQuery卡劵查询成功：" + JSON.toJSONString(couponQueryResult));
		model.addAttribute("couponQueryResult",couponQueryResult);
		return PAGE_MYCOUPON;
	}
	
	/**
	 * 
	 * @方法功能说明：根据卡劵id查询卡劵信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年1月15日下午4:34:04
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("couponDetail")
	public String couponDetail(@RequestParam("id") String id, Model model){
		HslCouponQO hslCouponQO = new HslCouponQO();
		hslCouponQO.setId(id);
		CouponDTO couponDTO = couponService.queryUnique(hslCouponQO);
		model.addAttribute("couponDTO",couponDTO);
		return PAGE_COUPONDETAIL;
	}
	
	/**
	 * 根据部门Id获取员工
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value="/organize/getMembers")
	@ResponseBody
	public String getMembers(@RequestParam("deptId") String deptId){
		HgLogger.getInstance().debug("zhuxy", "Ajax查询员工数据");
		HslMemberQO hslMemberQO = new HslMemberQO();
		hslMemberQO.setDepartmentId(deptId);
		List<MemberDTO> memberList = companyService.getMembers(hslMemberQO);
		HgLogger.getInstance().info("zhuxy", "Ajax查询员工数据，成员列表:"+JSON.toJSONString(memberList));
		return JSON.toJSONString(memberList);
	}
	
	/**
	 * 根据公司Id获取部门
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value="/organize/getDepartments")
	@ResponseBody
	public String getDepartments(@RequestParam("companyId") String companyId){
		HgLogger.getInstance().debug("zhuxy", "Ajax查询部门数据");
		HslDepartmentQO hslDepartmentQO = new HslDepartmentQO();
		HslCompanyQO hslCompanyQO = new HslCompanyQO();
		hslCompanyQO.setId(companyId);
		hslDepartmentQO.setCompanyQO(hslCompanyQO);
		List<DepartmentDTO> departmentList = companyService.getDepartments(hslDepartmentQO);
		List<DepartmentResult> deptlist = new ArrayList<DepartmentResult>();
		if(departmentList!=null&&departmentList.size()>0){
			for(DepartmentDTO deDto : departmentList){
				DepartmentResult result = BeanMapperUtils.map(deDto, DepartmentResult.class);
				HslMemberQO hslMemberQO = new HslMemberQO();
				hslMemberQO.setDepartmentId(result.getId());
				List<MemberDTO> memberList = companyService.getMembers(hslMemberQO);
				if(memberList!=null){
					result.setMemberCount(memberList.size());
				}
				deptlist.add(result);
			}
		}
		HgLogger.getInstance().info("zhuxy", "Ajax查询员工数据,部门数据："+JSON.toJSONString(deptlist));
		return JSON.toJSONString(deptlist);
	}
}
