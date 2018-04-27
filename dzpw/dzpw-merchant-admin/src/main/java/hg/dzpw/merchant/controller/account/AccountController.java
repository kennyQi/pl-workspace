package hg.dzpw.merchant.controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import hg.common.util.DwzJsonResultUtil;
import hg.dzpw.app.dao.ScenicSpotDao;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
//import hg.dzpw.app.service.api.hjb.HJBPayService;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.scenicspot.ScenicSpotCertificateInfo;
import hg.dzpw.domain.model.scenicspot.ScenicSpotSettleInfo;
import hg.dzpw.merchant.component.manager.MerchantSessionUserManager;
import hg.dzpw.merchant.controller.BaseController;
import hg.dzpw.pojo.api.hjb.HJBRegisterRequestDto;
import hg.dzpw.pojo.api.hjb.HJBRegisterResponseDto;

/**
 * @time 2015-05-04
 * @author Guotx
 * @description 支付账户操作控制器
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController {
//	@Autowired
//	private HJBPayService hjbPayService;
	@Autowired
	private ScenicSpotLocalService scenSer;

	/**
	 * 返回添加账户视图
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addAccount")
	public String addAccount(Model model, HttpServletRequest request) {
		String scenicSpotId = MerchantSessionUserManager
				.getSessionUserId(request);// 获取登录用户id，即景区id
		if (scenicSpotId != null && !"".equals(scenicSpotId)) {
			ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
			scenicSpotQo.setId(scenicSpotId);
			scenicSpotQo.setCityFetchAble(true);
			scenicSpotQo.setProvinceFetchAble(true);
			scenicSpotQo.setAreaFetchAble(true);
			scenicSpotQo.setFetchAllDevices(true);

			// 执行查询，根据景区id查询该景区的详细信息
			ScenicSpot scenicSpot = scenSer.queryUnique(scenicSpotQo);

			if (null != scenicSpot)
				// throw new
				// DZPWException(DZPWException.SCENICSPOT_NOT_EXISTS,"景区不存在或已删除！");
				model.addAttribute("scenicSpot", scenicSpot);
		}

		return "/account/addAccount.html";
	}

	@ResponseBody
	@RequestMapping(value = "/submitAccount")
	public String doAddAccount(Model model, HttpServletResponse response,
			@ModelAttribute HJBRegisterRequestDto hjbRegisterRequestDto,
			HttpServletRequest request) {
		// String accountType=request.getParameter("accountType");
		String phone = request.getParameter("phone");
		String loginName = request.getParameter("loginName");
		String orgName = request.getParameter("orgName");
		String contact = request.getParameter("contact");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String certificateCode = request.getParameter("organizationCode");
		String mobile = request.getParameter("mobile");
		HJBRegisterRequestDto registerRequestDto = new HJBRegisterRequestDto();
		registerRequestDto.setAddress(address);
		registerRequestDto.setCstName(orgName);///
		registerRequestDto.setCallerIp(request.getRemoteAddr());
		registerRequestDto.setEmail(email);
		registerRequestDto.setPhone(phone);
		registerRequestDto.setMobile(mobile);
		registerRequestDto.setOperatorName(contact);
		registerRequestDto.setOperatorEmail(email);
		registerRequestDto.setOperatorMobile(mobile);
		registerRequestDto.setCtfNo(certificateCode);
		registerRequestDto.setCtfType("4");
		registerRequestDto.setLoginName(loginName);

//		HJBRegisterResponseDto registerResponseDto = hjbPayService
//				.register(registerRequestDto);
		HJBRegisterResponseDto registerResponseDto = new HJBRegisterResponseDto();
		
		String resultCode = null;
		String resultMessage = null;
		String operate = null;
		if (registerResponseDto != null) {
			// 调用注册接口成功
			String returnCode = registerResponseDto.getErrorCode();
			//未获取到errorCode注册成功
			if (returnCode.equals("") || returnCode == null) {
				resultCode = DwzJsonResultUtil.STATUS_CODE_200;
				resultMessage = "注册成功！";
				operate = "closeCurrent";
				// 修改本地数据
				String id = MerchantSessionUserManager
						.getSessionUserId(request);
				String cstNo = registerResponseDto.getCstNo();
				String orgCode = registerRequestDto.getCtfNo();
				String operatorNo = registerResponseDto.getOperatorNo();
				// 保存信息到本地
				modifyScenSpot(id, orgCode, cstNo, operatorNo);
			} else if (returnCode.equals("1")) {
				// 注册失败,填写信息有误
				resultCode = DwzJsonResultUtil.STATUS_CODE_200;
				resultMessage = registerResponseDto.getErrorMessage();
			} else if (returnCode.equals("2")) { // 用户已注册
				resultCode = DwzJsonResultUtil.STATUS_CODE_200;
				resultMessage = registerResponseDto.getErrorMessage();
			}

		} else {
			// 接口调用失败
			resultCode = DwzJsonResultUtil.STATUS_CODE_300;
			resultMessage = "远程接口调用出错,请与系统管理员联系！";
		}
		return DwzJsonResultUtil.createJsonString(resultCode, resultMessage,
				operate, "", "", "", "");
	}

	/**
	 * 将部分注册信息保存到本地
	 * 
	 * @param id
	 *            景区id
	 * @param orgCode
	 *            组织机构证
	 * @param cstNo
	 *            注册成功返回的企业编号
	 */
	private void modifyScenSpot(String id, String orgCode, String cstNo, String operatorNo) {
		ScenicSpotQo sQo=new ScenicSpotQo();
		sQo.setId(id);
		// 先查询出要修改的实体
		ScenicSpot scenicSpot = scenSer.queryUnique(sQo);
		// 获取资质信息
		ScenicSpotCertificateInfo info = scenicSpot.getCertificateInfo();
		ScenicSpotSettleInfo settleInfo=scenicSpot.getSettleInfo();
		info.setOrganizationCode(orgCode);
		settleInfo.setCstNo(cstNo);
		settleInfo.setOperatorNo(operatorNo);
		scenicSpot.setCertificateInfo(info);

		scenSer.update(scenicSpot);
	}
}
