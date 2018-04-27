/**
 * @文件名称：JfServiceController.java
 * @类路径：hgtech.jfadmin.controller
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月20日上午11:13:07
 */
package hgtech.jfadmin.controller;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.JSONUtils;
import hg.common.util.Md5FileUtil;
import hgtech.jf.JfProperty;
import hgtech.jf.entity.JsonUtil;
import hgtech.jf.piaol.PiaolAdjustBean;
import hgtech.jf.piaol.SetupSpiApplicationContext;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jf.security.JfSecurityUtil;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.AdjustBean;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfFlow;
import hgtech.jfaccount.SetupAccountContext;
import hgtech.jfaccount.service.AccountService;
import hgtech.jfadmin.dto.jfAdjustDto;
import hgtech.jfadmin.dto.showDto;
import hgtech.jfadmin.service.FlowService;
import hgtech.jfadmin.service.JfCalService;
import hgtech.jfadmin.util.Security;
import hgtech.jfcal.model.CalResult;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @类功能说明：积分对外服务接口
 * @类修改者：
 * @修改日期：2014年10月20日上午11:13:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月20日上午11:13:07
 * 
 *                             url中传递参数如下：。 名字 解释 function 调用的功能： queryjf:查询积分
 *                             queryflow:查询积分流水 querybill:查询账单
 *                             querysys:查询积分计算服务是否可用 data 功能对应的数据
 * 
 * 
 */
@Controller
@RequestMapping("/service")
public class JfServiceController {
	public static final String navTabId = "jfQuery";
	public static final String rel = "jbsxBoxJfShow";

	@Autowired
	JfCalService calService;

	@Autowired
	FlowService flowService;

	@Autowired
	hgtech.jfadmin.service.AccountQueryService accountQueryService;

	@Autowired
	AccountService accountService;

	/**
	 * 
	 * @类功能说明：服务结果
	 * @类修改者：
	 * @修改日期：2014年11月19日下午5:57:44
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：xinglj
	 * @创建时间：2014年11月19日下午5:57:44
	 * 
	 */
	public static class ServiceResultDto {
		/**
		 * 是否正确计算
		 */
		boolean status;
		/**
		 * 返回信息详细描述。如什么规则，多少分
		 */
		String text;

	}

	/**
	 * 
	 * @方法功能说明：积分计算
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月20日上午11:29:31
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@return
	 * @参数：@throws Exception
	 * @参数：@throws IOException
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/caljf")
	public String caljf(HttpServletRequest request, HttpServletResponse response)
			throws Exception, IOException {
		String jsonResult = "请求数据有误";

		String json = request.getParameter("data");

		if (SetupAccountContext.sysDomain.getIsDetectionSignature()) {
			String time = request.getParameter("time");
			String sign = request.getParameter("sign");
			if ("".equals(sign) || null == sign) {
				return "请填写签名";
			}

			String newSign = json + time
					+ SetupAccountContext.sysDomain.getPassK();

			if (!sign.equals(Md5FileUtil.MD5(newSign))) {
				return jsonResult;
			}
		}
		jsonResult = calService.cal(json);
		return jsonResult;
	}

	/**
	 * 
	 * @方法功能说明：积分计算,并入账
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月20日上午11:29:31
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@return
	 * @参数：@throws Exception
	 * @参数：@throws IOException
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/calcommitjf")
	public String calAndCommitjf(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String jsonResult  ;

		String json = request.getParameter("data");

		if (SetupAccountContext.sysDomain.getIsDetectionSignature()) {

			String time = request.getParameter("time");
			String sign = request.getParameter("sign");
			if ("".equals(sign) || sign == null) {
				return jsonResult = "请填写签名！";
			}
			String newSign = json + time
					+ SetupAccountContext.sysDomain.getPassK();
			if (!sign.equals(Md5FileUtil.MD5(newSign))) {
				return "签名错误！";
			}
		}

		try {
			Collection<CalResult> res = calService.calAndCommit(json);
			boolean isok = true;
			String text = "";
			for (CalResult r : res) {
				if (r.getOut_resultCode().equalsIgnoreCase("N")) {
					isok = false;
					text += r.getIn_rule().name + "<br>";
				} else
					text += "规则：" + r.getIn_rule().name + ",积分：" + r.out_jf
							+ "<br>";
			}
			if (!isok)
				// jsonResult="至少有一条规则积分计算失败。失败的规则：<br>"+text;
				jsonResult = "至少有一条规则积分计算失败。请查询计算日志。";
			else
				jsonResult = "计算入账成功。" + (text.length() == 0 ? "无匹配的规则" : text);
		} catch (Throwable e) {
			jsonResult = "积分计算失败 " + e.getMessage();
			e.printStackTrace();
		}

		return jsonResult;
	}

	/**
	 * 
	 * @方法功能说明：撤销积分获得明细
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月27日下午2:16:50
	 * @修改内容：
	 * @参数：@param request flowid:要撤销的积分获得明细id
	 * @参数：@param response
	 * @参数：@return
	 * @参数：@throws Exception
	 * @参数：@throws IOException
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/uncommitjf")
	public String uncommitjf(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String id = request.getParameter("flowid");

		String jsonResult = "撤销积分获得明细成功。";
		try {
			accountService.undoGotjf(id);

		} catch (Throwable e) {
			jsonResult = "撤销积分获得明细失败 " + e.getMessage();
			e.printStackTrace();
		}

		return jsonResult;
	}

	/**
	 * 
	 * @方法功能说明：撤销积分获得明细
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月27日下午2:16:50
	 * @修改内容：
	 * @参数：@param request flowid:要撤销的积分获得明细id
	 * @参数：@param response
	 * @参数：@return
	 * @参数：@throws Exception
	 * @参数：@throws IOException
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/unexchangejf")
	public String unexchangejf(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String id = request.getParameter("flowid");

		String jsonResult = "撤销积分消耗明细成功。";
		try {
			accountService.undoCostjf(id, JfProperty.getJfValidYear());

		} catch (Throwable e) {
			jsonResult = "撤销积分消耗明细失败 " + e.getMessage();
			e.printStackTrace();
		}

		return jsonResult;
	}

	/**
	 * 
	 * @方法功能说明：积分调整，为对外服务提供的功能
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月20日上午11:29:31
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@return
	 * @参数：@throws Exception
	 * @参数：@throws IOException
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/adjustjf")
	public String adjustjf(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String jsonResult = "请求数据有误";
		String json = request.getParameter("data");

		if (SetupAccountContext.sysDomain.getIsDetectionSignature()) {
			String time = request.getParameter("time");
			String sign = request.getParameter("sign");
			if ("".equals(sign) || null == sign) {
				return "请填写签名";
			}

			String newSign = json + time
					+ SetupAccountContext.sysDomain.getPassK();
			// System.out.println(newSign);
			if (!sign.equals(Md5FileUtil.MD5(newSign))) {
				return jsonResult;
			}
		}

		jsonResult = "已提交，请稍后查询。";
		try {
			calService.adjust(json);
		} catch (Throwable e) {
			jsonResult = "调整失败 " + e.getMessage();
		}

		return jsonResult;
	}
	@ResponseBody
	@RequestMapping("/transferout")
	public String transferout(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String jsonResult = "请求数据有误";
		String json = request.getParameter("data");

		if (SetupAccountContext.sysDomain.getIsDetectionSignature()) {
			String time = request.getParameter("time");
			String sign = request.getParameter("sign");
			if ("".equals(sign) || null == sign) {
				return "请填写签名";
			}

			String newSign = json + time
					+ SetupAccountContext.sysDomain.getPassK();
			// System.out.println(newSign);
			if (!sign.equals(Md5FileUtil.MD5(newSign))) {
				return jsonResult;
			}
		}

		jsonResult = "已提交，请稍后查询。";
		try {
			calService.transferout(json);
		} catch (Throwable e) {
			jsonResult = "调整失败 " + e.getMessage();
		}

		return jsonResult;
	}
	
	@ResponseBody
	@RequestMapping("/transferin")
	public String transferin(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String jsonResult = "请求数据有误";
		String json = request.getParameter("data");

		if (SetupAccountContext.sysDomain.getIsDetectionSignature()) {
			String time = request.getParameter("time");
			String sign = request.getParameter("sign");
			if ("".equals(sign) || null == sign) {
				return "请填写签名";
			}

			String newSign = json + time
					+ SetupAccountContext.sysDomain.getPassK();
			// System.out.println(newSign);
			if (!sign.equals(Md5FileUtil.MD5(newSign))) {
				return jsonResult;
			}
		}

		jsonResult = "已提交，请稍后查询。";
		try {
			calService.transferin(json);
		} catch (Throwable e) {
			jsonResult = "调整失败 " + e.getMessage();
			e.printStackTrace();
		}

		return jsonResult;
	}
	/**
	 * 
	 * @方法功能说明：积分消耗，对外服务接口
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月20日下午3:25:24
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@return
	 * @参数：@throws Exception
	 * @参数：@throws IOException
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/exchangejf")
	public String exchangejf(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		String jsonResult = "请求数据有误";
		String json = request.getParameter("data");

		if (SetupAccountContext.sysDomain.getIsDetectionSignature()) {
			String time = request.getParameter("time");
			String sign = request.getParameter("sign");
			if ("".equals(sign) || null == sign) {
				return "请填写签名";
			}

			String newSign = json + time
					+ SetupAccountContext.sysDomain.getPassK();
			if (!sign.equals(Md5FileUtil.MD5(newSign))) {
				return jsonResult;
			}
		}

		jsonResult = "已提交，请稍后查询。";
		try {
			calService.exchange(json);
		} catch (Throwable e) {
			jsonResult = "积分兑换失败 " + e.getMessage();
		}
		return jsonResult;
	}
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月4日下午5:34:10
	 * @version：
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/md5")
	public String genmd5(HttpServletRequest request,
			HttpServletResponse response){
			String json = request.getParameter("data");
			String time = request.getParameter("time");
			String pass = request.getParameter("pass");
			if(pass==null || pass.length()==0)
				return "请填pass";
			String newSign = json + time + pass;
			return Md5FileUtil.MD5(newSign) ;
	}

	/**
	 * 
	 * @方法功能说明：查询积分
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月5日下午2:40:20
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param dto
	 * @参数：@return
	 * @参数：@throws Exception
	 * @参数：@throws IOException
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/queryjf")
	public String queryjf(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute showDto dto)
			throws Exception, IOException {

		String jsonResult = "访问数据出错";

		String time = request.getParameter("time");
		String sign = request.getParameter("sign");
		String code = request.getParameter("code");
		if (SetupAccountContext.sysDomain.getIsDetectionSignature()) {
			if ("".equals(sign) || null == sign) {
				jsonResult = "请填写签名";
				return jsonResult;
			}
			String passK = SetupAccountContext.sysDomain.getPassK();
			String md5 = JfSecurityUtil.md5(time, code, passK);
			if (!sign.equals(md5)) {
				return jsonResult;
			}
		}

		checkSecurity(request, response);
		Pagination paging = dto.getPagination();
		
		List<JfAccount> list =new ArrayList<>();
		//多个用户用逗号分开
		for(String c:code.split(",")){
		    dto.setCode(c);
		    paging.setCondition(dto);
		    paging = accountQueryService.findPagination(paging);
		    Collection<? extends JfAccount> list2 = (Collection<? extends JfAccount>) paging.getList();
		    list.addAll(list2);
		}
		
		for (JfAccount account : list) {
			// acct_type 特例，一般保存账户类型code，这里暂时存放了名字给页面
			account.acct_type = SetupAccountContext.acctTypeDao.get(
					account.acct_type).getName();

		}

//		jsonResult = hg.common.util.JsonUtil.parseObject(paging, false);
		paging.setList(list);
		jsonResult = JsonUtil.tojson(paging);
		return jsonResult;
	}

	/**
	 * @方法功能说明：检查安全
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月7日上午11:00:04
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @return:void
	 * @throws
	 */
	private void checkSecurity(HttpServletRequest request,
			HttpServletResponse response) {
		String ipProperty = SetupAccountContext.sysDomain.ip;
		// System.out.println(ipProperty);
		if (ipProperty != null && ipProperty.trim().length() > 0) {
			String clientip = request.getRemoteAddr();
			// System.out.println(clientip);
			if (!clientip.equals(ipProperty)) {
				throw new RuntimeException(Security.ERROR_DOMAINACCESS
						+ " 实际ip " + clientip + " 认可的ip " + ipProperty);
			}
		}
	}

	/**
	 * 
	 * @方法功能说明：查询流水
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月5日下午2:40:30
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param dto
	 * @参数：@return
	 * @参数：@throws Exception
	 * @参数：@throws IOException
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/queryflow")
	public String queryflow(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute showDto dto)
			throws Exception, IOException {
		String jsonResult = "访问数据不正确";

		if (SetupAccountContext.sysDomain.getIsDetectionSignature()) {
			String userName = dto.getCode();
			String startDate = dto.getStartDate();
			String endDate = dto.getEndDate();
			String sign = request.getParameter("sign");
			String time = request.getParameter("time");
			if ("".equals(sign) || null == sign) {
				return "请填写签名";
			}
			String newSign = userName + startDate + endDate + time
					+ SetupAccountContext.sysDomain.getPassK();
			if (!sign.equals(Md5FileUtil.MD5(newSign))) {
				return jsonResult;
			}
		}

		Pagination paging = dto.getPagination();
		paging.setCondition(dto);

		paging = flowService.findPagination(paging);
		List<JfFlow> list = (List<JfFlow>) paging.getList();

		jsonResult = hg.common.util.JsonUtil.parseObject(paging, false);
		// paging = JSON.parseObject(jsonResult, Pagination.class);
		// System.out.println(paging);
		return jsonResult;
	}

	/**
	 * 
	 * @方法功能说明： 到积分调整页面
	 * @修改者名字：xy
	 * @修改时间：2014年11月03日上午10:13:33
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toAdjustJF")
	public String toAdjustJF(HttpServletRequest request,
			HttpServletResponse response,Model model) {
		LinkedList<WithChildren<JfAccountType>> ruleTypeList = SetupAccountContext.topAcctType
				.getSubList();
		model.addAttribute("ruleTypeList", ruleTypeList);
		String user=request.getParameter("code");
		model.addAttribute("code",user);
		return "/jf/jfAdjust.html";
	}

	/**
	 * 
	 * @方法功能说明： 积分调整，通过页面方式
	 * @修改者名字：xy
	 * @修改时间：2014年11月03日上午10:13:33
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/adjustAccountJF")
	public String adjustAccountjf(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute jfAdjustDto dto) {

		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "调整成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		try {
			String account = dto.getAccount();
			String score = dto.getScore();
			String accountType = dto.getAccountType();
			String description = dto.getDescription();
				AdjustBean adb = new AdjustBean();
				adb.setAccountTypeId(accountType);
				adb.setUserCode(account);
				adb.setJf(Integer.valueOf(score));
				adb.setRemark(description);
				
				PiaolAdjustBean ad = new PiaolAdjustBean();
				ad.bean = adb;
				try {
					calService.adjustAcountJf(ad,Integer.valueOf(score));
				} catch (Exception e) {
					statusCode = DwzJsonResultUtil.STATUS_CODE_500;
					message=e.getMessage();
				}
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "调整失败";

		}
		request.getSession().setAttribute("adjustUserCode", dto.getAccount());
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId, null, null, rel);
	}

	/**
	 * 
	 * @方法功能说明： 到积分调整页面
	 * @修改者名字：xy
	 * @修改时间：2014年11月03日上午10:13:33
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toBatchAdjustJF")
	public String toBatchAdjustJF(Model model) {
		LinkedList<WithChildren<JfAccountType>> ruleTypeList = SetupAccountContext.topAcctType
				.getSubList();
		model.addAttribute("ruleTypeList", ruleTypeList);
		return "/jf/jfBatchAdjust.html";
	}

	/**
	 * 
	 * @方法功能说明： 积分调整，通过页面方式
	 * @修改者名字：xy
	 * @修改时间：2014年11月03日上午10:13:33
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/batchAdjustJF")
	public String batchAdjustJF(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("file") MultipartFile file,
			@ModelAttribute jfAdjustDto dto, Model model) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "批量调整完成，请查看页面！";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		if (!file.isEmpty()) {
			// 1.创建服务器路径creating the directory to store file
			String rootPath = System.getProperty("catalina.home");
			File dir = new File(rootPath + File.separator + "tmpFiles");
			if (!dir.exists()) {
				dir.mkdir();
			}
			String name=file.getName();
			// 2.上传文件create the file on server
			try {
				byte[] b = file.getBytes();
				File serverFile = new File(dir.getAbsoluteFile()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(b);
				stream.close();
			} catch (Exception e) {
				statusCode = DwzJsonResultUtil.STATUS_CODE_500;
				message = "上传文件失败！";
				return DwzJsonResultUtil.createJsonString(statusCode, message,
						callbackType, null, null, null, null);
			}

			// 3.判断文件是否存在,如果文件存在解析文件
			File isFile = new File(dir.getAbsoluteFile() + File.separator
					+ name);
			if (isFile.exists()) {
				String msg = ""; // 错误返回信息
				int count = 0; // 调整积分总数量
				int errorNum = 0; // 错误的数量
				List<AdjustBean> jfList = new ArrayList<AdjustBean>();
				Iterable<CSVRecord> records = null;
				InputStream is = null;
				try {
					is = new FileInputStream(isFile);
					Reader in = new InputStreamReader(is, "UTF-8");
					records = CSVFormat.EXCEL.parse(in);

					if (null != records) {
						// 校验文件
						for (CSVRecord r : records) {
							count++;
							if(count >1){
								String userId = r.get(0);
								String score = r.get(1);
								// 校验文件列
								if ((userId.length() == 0)
										|| (userId.length() > 50)
										|| (!isNumber(score))) {
									errorNum++;
									msg += userId + "、";
								} else {
									if (msg.isEmpty()) {
										AdjustBean abean = new AdjustBean();
										abean.setUserCode(userId);
										abean.setJf(Integer.valueOf(score));
										jfList.add(abean);
									}
								}
							}
						}
					}
				} catch (Exception e1) {
					statusCode = DwzJsonResultUtil.STATUS_CODE_500;
					message = "解析文件失败！";
					return DwzJsonResultUtil.createJsonString(statusCode,
							message, callbackType, null, null, null, null);
				} finally {
					if (null != is) {
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				// 4.文件校验失败， 提醒用户重新导入
				if (!msg.isEmpty()) {
					model.addAttribute("resultMsg", msg);
					model.addAttribute("colCount", count);
					model.addAttribute("errorCol", errorNum);
					return "/jf/jfBatchAdjust.html";
				}
				// 5.文件校验成功，调整积分
				else {
					// 批量调整积分
					try {
						calService.batchadjustJf(dto, jfList);
					} catch (Exception e) {
						e.printStackTrace();
						message=e.getMessage() +",可能文件中调整负数积分列中有不存在的用户，请检查！";
						statusCode=DwzJsonResultUtil.STATUS_CODE_500;
					}
				}
			}
		}
//		return DwzJsonResultUtil.createSimpleJsonString(statusCode, message);
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId, null, null, rel);
	}

	/**
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/download")
	public void adjustTemplateDownload(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		
		String str = "积分批量调整模板.csv";
		BufferedReader r = null;
		FileInputStream fis=null;
		InputStreamReader isr=null;
		BufferedWriter w = null;
		try {
			//File file = new File("/adjustTemplate.csv");
			response.setContentType("application/octet-stream;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(str.getBytes("utf-8"), "ISO8859-1"));
			w = new BufferedWriter(response.getWriter());
			fis=new FileInputStream(request.getSession().getServletContext().getRealPath("/")+"csv/adjustTemplate.csv");
			isr=new InputStreamReader(fis,"utf-8");
			//r = new BufferedReader(new FileReader(request.getSession().getServletContext().getRealPath("/")+"csv/adjustTemplate.csv"));
			r = new BufferedReader(isr);
			String s = null;
			while ((s = r.readLine()) != null) {
				w.write(s);
				w.newLine();
				w.flush();
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(fis!=null){
					fis.close();
				}
				if(r!=null){
					r.close();
				}
				if(w!=null){
					w.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		PiaolTrade t = new PiaolTrade();
		t.user = "tom";
		t.date = "20140110";
		t.tradeName = "sign";
		String j = JSONObject.toJSONString(t);
		System.out.println(j);
		System.out.println(JSONObject.parseObject(j, PiaolTrade.class));

		// 生成调整的json
		List<AdjustBean> cals = new ArrayList<AdjustBean>();
		AdjustBean c = new AdjustBean();
		c.userCode = "xlj";
		c.remark = "兑奖了";
		c.jf = -100;
		SetupSpiApplicationContext.init();
		c.accountTypeId = "acct_grow";
		cals.add(c);
		System.out.println(JSONObject.toJSONString(cals,
				SerializerFeature.WriteClassName));

		LogFactory.getLog(JfServiceController.class).info("adfsdf");
		
		
	}

	/**
	 * 判断字符串是否为数字类型
	 * 
	 * @param str
	 * @return
	 */
	private boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("^[-]?\\d+$");
		return pattern.matcher(str).matches();
	}
}
