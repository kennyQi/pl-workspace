package hg.dzpw.merchant.controller.reghjb;

import java.util.HashMap;

import hg.common.util.DwzJsonResultUtil;
import hg.common.util.JsonUtil;
//import hg.dzpw.app.service.api.hjb.HJBPayService;
import hg.dzpw.pojo.api.hjb.HJBRegisterRequestDto;
import hg.dzpw.pojo.api.hjb.HJBRegisterResponseDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @time 2015-11-20
 * @author Guotx
 * @description 景区端添加经销商时为经销商注册汇金宝账户
 */
@Controller
@RequestMapping("/hjbreg")
public class HjbAccountController {
//	@Autowired
//	private HJBPayService hjbPayService;

	/**
	 * 返回添加账户视图
	 * @return
	 */
	@RequestMapping(value = "/addAccount")
	public String addAccount(Model model, HttpServletRequest request,
			@RequestParam(value="address",required=false) String address, 
			@RequestParam(value="linkMan",required=false) String linkMan,
			@RequestParam(value="telephone",required=false) String telephone, 
			@RequestParam(value="email",required=false) String email,
			@RequestParam(value="cstName",required=false) String cstName) {
		model.addAttribute("address", address);
		model.addAttribute("linkMan", linkMan);
		model.addAttribute("telephone", telephone);
		model.addAttribute("email", email);
		model.addAttribute("cstName",cstName);
		return "/dealer/reghjb/regAccount.html";
	}

	/**
	 * 调用注册汇金宝账户接口
	 * @param model
	 * @param response
	 * @param hjbRegisterRequestDto
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/submitAccount")
	public String doAddAccount(Model model, HttpServletResponse response,
			@ModelAttribute HJBRegisterRequestDto hjbRegisterRequestDto,
			HttpServletRequest request) {
		hjbRegisterRequestDto.setCallerIp(request.getRemoteAddr());
		hjbRegisterRequestDto.setCtfType("4");
		// 调用注册接口成功
//		HJBRegisterResponseDto registerResponseDto = hjbPayService
//				.register(hjbRegisterRequestDto);
		HJBRegisterResponseDto registerResponseDto = new HJBRegisterResponseDto();
		
		String resultCode = null;
		String resultMessage = null;
		HashMap<String, String> resultMap=new HashMap<String, String>();
		if (registerResponseDto != null) {
			String returnCode = registerResponseDto.getErrorCode();
			// 未获取到errorCode注册成功
			if (returnCode == null || returnCode.equals("")) {
				resultCode = DwzJsonResultUtil.STATUS_CODE_200;
				resultMessage = "注册成功！";
				resultMap.put("cstNo", registerResponseDto.getCstNo());
				resultMap.put("operatorNo", registerResponseDto.getOperatorNo());
			} else if (returnCode.equals("1")) {
				// 注册失败,填写信息有误
				resultCode = DwzJsonResultUtil.STATUS_CODE_300;
				resultMessage = registerResponseDto.getErrorMessage();
			} else if (returnCode.equals("2")) { // 用户已注册
				resultCode = DwzJsonResultUtil.STATUS_CODE_300;
				resultMessage = registerResponseDto.getErrorMessage();
			}

		} else {
			// 接口调用失败
			resultCode = DwzJsonResultUtil.STATUS_CODE_300;
			resultMessage = "远程接口调用出错,请与系统管理员联系！";
		}
		resultMap.put("statusCode", resultCode);
		resultMap.put("message", resultMessage);
		return JsonUtil.parseObject(resultMap, false);
	}
}
