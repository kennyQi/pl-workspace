/**
 * @文件名称：JfShowController.java
 * @类路径：hgtech.jfadmin.controller
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年10月31日上午8:44:51
 */
package hgtech.jfadmin.controller;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hgtech.jf.JfProperty;
import hgtech.jf.piaol.PiaolAdjustBean;
import hgtech.jf.piaol.SetupSpiApplicationContext;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.AdjustBean;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfFlow;
import hgtech.jfaccount.JfTradeType;
import hgtech.jfaccount.TradeType;
import hgtech.jfaccount.service.AccountService;
import hgtech.jfadmin.dto.jfAdjustDto;
import hgtech.jfadmin.dto.showDto;
import hgtech.jfadmin.hibernate.CalFlowHiberEntity;
import hgtech.jfadmin.service.AccountQueryService;
import hgtech.jfadmin.service.FlowService;
import hgtech.jfadmin.service.JfCalService;
import hgtech.jfadmin.service.imp.FlowServiceImpl;
import hgtech.jfcal.model.RuleTemplate;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.json.JSONObject;

import ucenter.admin.controller.BaseController;

/**
 * @类功能说明：账户查询Controller
 * @类修改者：
 * @修改日期：2014年10月31日上午8:44:51
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年10月31日上午8:44:51
 * 
 **                            *url中传递参数如下：。 名字 解释 function 调用的功能： query 积分帐号查询
 *                            flow 积分流水查询
 * 
 */

@Controller
@RequestMapping("/account/jf")
public class JfShowController extends BaseController {
	/**
	 * @FieldsUTF_8:TODO
	 */
	private static final String UTF_8 = "UTF-8";
	public static final String navTabId = "jfQuery";
	public static final String rel = "jbsxBoxJfShow";

	@Autowired
	FlowService flowService;

	@Autowired
	JfCalService calService;

	@Autowired
	AccountQueryService accountQueryService;

	@Autowired
	AccountService accountService;

	/**
	 * @方法功能说明：积分帐号查询
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:25:52
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param dto
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/query")
	public String doQuery(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute showDto dto) {
		Pagination paging = dto.getPagination();
		/*String user = (String) request.getSession().getAttribute(
				"adjustUserCode");
		request.getSession().removeAttribute("adjustUserCode");
		// System.out.println(user);
		if (user != null || !"".equals(user)) {
			dto.code = user;
		}*/
		paging.setCondition(dto);
		if (!("".equals(dto.getCode()) || dto.getCode() == null)) {
			SetupSpiApplicationContext.init();
			paging = accountQueryService.findPagination(paging);
			List<JfAccount> accounts = (List<JfAccount>) paging.getList();
			for (JfAccount account : accounts) {
				account.syncUK();
				// account.getUk().setType(SetupPiaolApplicationContext.findType(account.getAcct_type()));
			}
		}
		model.addAttribute("pagination", paging);
		model.addAttribute("dto", dto);
		return "/jf/jfSearch.html";
	}

	/**
	 * 
	 * @方法功能说明：跳转到积分查询界面
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:26:41
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param dto
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	/*
	 * @RequestMapping("/flow") public String getFlowByUserId(HttpServletRequest
	 * request,HttpServletResponse response,Model model,@ModelAttribute showDto
	 * dto){ Pagination paging = dto.getPagination(); SimpleDateFormat sdf=new
	 * SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * dto.setEndDate(sdf.format(new Date())); dto.setStartDate(sdf.format(new
	 * Date())); model.addAttribute("dto",dto); model.addAttribute("pagination",
	 * paging); return "/jf/flowSearch.html";
	 * 
	 * }
	 */

	/**
	 * 
	 * @方法功能说明：积分流水查询
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:27:09
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param dto
	 * @参数：@return
	 * @参数：@throws ParseException
	 * @return:String
	 * @throws
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/flow")
	public String getFlow(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute showDto dto) throws ParseException {
		Pagination paging = dto.getPagination();
		paging.setCondition(dto);

		if ("".equals(dto.getCode()) || dto.getCode() == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			dto.setEndDate(sdf.format(new Date()));
			dto.setStartDate(sdf.format(new Date()));
		} else {

			paging = flowService.findPagination(paging);
			List<JfFlow> list = (List<JfFlow>) paging.getList();
			for (JfFlow flow : list) {
				// flow.setTrade_type(JfTradeType.findType(flow.trade_type).name);
				// flow.setTradeType(JfTradeType.findType(flow.trade_type));
				// flow.getTradeType().getName();
				flow.account.syncUK();
				flow.status = JfFlow.statusMap.get(flow.status).toString();
			}
		}

		paging.setCondition(dto);
		model.addAttribute("pagination", paging);
		model.addAttribute("dto", dto);

		return "/jf/flowSearch.html";
	}

	/**
	 * @方法功能说明：跳转到一个已有账户的调整页面
	 * @修改者名字：pengel
	 * @修改时间：2014年11月25日上午10:23:54
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param dto
	 * @参数：@return
	 * @return:String
	 * @throws
	 */

	@RequestMapping(value = "/toAdjust")
	public String creat(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute showDto dto) {

		model.addAttribute("user", dto.getCode());
		model.addAttribute("type", dto.getType());
		model.addAttribute("typeName",
				SetupSpiApplicationContext.findType(dto.getType()).getName());

		return "/jf/jfAdjustA.html";
	}

	/**
	 * @方法功能说明：积分调整实现
	 * @修改者名字：pengel
	 * @修改时间：2014年11月25日上午10:24:53
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param dto
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/adjustJF")
	public String adjustAccountjf(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute jfAdjustDto dto) {

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
			calService.adjustAcountJf(ad,Integer.valueOf(score));
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "调整失败";
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId, null, null, rel); 

	}

	/**
	 * @方法功能说明：跳转到积分计算流水调整页面
	 * @修改者名字：pengel
	 * @修改时间：2014年11月25日上午10:23:54
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param dto
	 * @参数：@return
	 * @return:String
	 * @throws
	 */

	@RequestMapping(value = "/toFlowAdjust")
	public String creatFlowADjust(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		String id = request.getParameter("flowId");
		String tradeType = request.getParameter("accType");
		String jf = request.getParameter("jf");
		// String remark="这是什么情况啊这是？";
		String remark = request.getParameter("remark");
		/*
		 * System.out.println(remark); System.out.println(id);
		 * System.out.println(tradeType); System.out.println(jf);
		 */
		model.addAttribute("typeName",
				SetupSpiApplicationContext.findType(tradeType).getName());
		model.addAttribute("flowId", id);
		model.addAttribute("accType", tradeType);
		model.addAttribute("jf", jf);
		model.addAttribute("remark", remark);

		return "/jf/flowAdjust.html";
	}

	@ResponseBody
	@RequestMapping(value = "/flowAdjust")
	public String flowADjust(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute jfAdjustDto dto) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "调整成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		String remark1 = dto.getDescription();
		String remark2 = dto.getDescription2();
		String remark = remark1 + remark2;
		dto.setDescription(remark);
		flowService.flowAdjust(dto);

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "jfFlow", null, null, rel);
	}

	/**
	 * @方法功能说明：积分明细撤销
	 * @修改者名字：pengel
	 * @修改时间：2014年11月25日上午10:24:53
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param dto
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/undoflow")
	public String undojf(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "撤销成功";
		String callbackType = DwzJsonResultUtil.FLUSH_FORWARD;

		try {
			String id = request.getParameter("flowId");
			String tradeType = request.getParameter("tradeType");
			String jf = request.getParameter("jf");
			int ijf = Integer.parseInt(jf);
			if (ijf >= 0)
				accountService.undoGotjf(id);
			else
				accountService.undoCostjf(id, JfProperty.getJfValidYear());
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "撤销失败";
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "jfFlow", null, null, rel);

	}

	/**
	 * @方法功能说明：用户特定月份的积分流水对账查询
	 * @修改者名字：pengel
	 * @修改时间：2014年11月24日上午11:02:26
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param dto
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/check")
	public String checkSearch(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute showDto dto) {
		Pagination paging = dto.getPagination();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar ca = Calendar.getInstance();
		if (dto.startDate == null || "".equals(dto.startDate)) {

			dto.startDate = sdf.format(new Date());
		}
		model.addAttribute("dto", dto);
		if (!"".equals(dto.code) && dto.code != null && dto.startDate != null
				&& !"".equals(dto.startDate)) {
			try {
				showDto dto1 = new showDto();
				dto1.setCode(dto.code);
				dto1.setStartDate(dto.startDate);
				ca.setTime(sdf.parse(dto1.startDate));
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				dto1.startDate = sdf2.format(ca.getTime());
				ca.add(ca.MONTH, 1);
				ca.add(ca.DATE, -1);
				dto1.endDate = sdf2.format(ca.getTime());
				paging.setCondition(dto1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paging = flowService.findPagination(paging);
			// List<JfFlow> list=(List<JfFlow>)paging.getList();
			// for(JfFlow flow:list){
			// flow.setTradeType(JfTradeType.findType(flow.trade_type));
			// }
		}

		paging.setCondition(dto);
		model.addAttribute("pagination", paging);
		return "/jf/jfCheck.html";
	}

	/**
	 * @方法功能说明：跳转到积分兑换页面
	 * @修改者名字：pengel
	 * @修改时间：2014年11月25日上午10:27:05
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@return
	 * @参数：@throws Exception
	 * @参数：@throws IOException
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/toExchange")
	public String toExchangejf(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {

		return "";
	}

	/**
	 * @方法功能说明：积分兑换
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
		String json = request.getParameter("data");

		String jsonResult = "已提交，请稍后查询。";
		try {
			calService.exchange(json);
		} catch (Throwable e) {
			jsonResult = "积分兑换失败 " + e.getMessage();
		}

		return jsonResult;
	}

	@RequestMapping("/download")
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//response.setContentType("text/html;charset=utf-8");
//		response.setCharacterEncoding(UTF_8);
		
		ModelAndView m = new ModelAndView();

		String code = request.getParameter("code");
		String date = request.getParameter("date");
		//code = new String(code.getBytes("ISO-8859-1"), UTF_8);
		showDto dto = new showDto();
		Pagination paging = dto.getPagination();
		dto.setCode(code);
		dto.setStartDate(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar ca = Calendar.getInstance();
		if (!"".equals(dto.code) && dto.code != null && dto.startDate != null
				&& !"".equals(dto.startDate)) {
			try {
				ca.setTime(sdf.parse(dto.startDate));
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				dto.startDate = sdf2.format(ca.getTime());
				ca.add(ca.MONTH, 1);
				ca.add(ca.DATE, -1);
				dto.endDate = sdf2.format(ca.getTime());
				paging.setCondition(dto);
				List<JfFlow> list = flowService.getjfFlowList(paging);
				//将数据转为byte
				 ByteArrayOutputStream barr=new ByteArrayOutputStream();
				getBytes(list, barr);
				byte[] byteArray = barr.toByteArray();
				
				ca.setTime(sdf.parse(date));
				String filename = "用户" + code + ca.get(ca.YEAR) + "年"
						+ ca.get(ca.MONTH) + "月的对账单.csv";
				
				writeUtf8File(response, filename, byteArray);
				
			} catch (ParseException e) {
				 //TODO Auto-generated catch block
				e.printStackTrace();
			} 

		}
		// templateService.setClazzandSrcFilename(template);
		// String downLoadPath
		// =type.equalsIgnoreCase("java")?template.getSrcFile():template.getClazzFile();
		// String
		// fileName=type.equalsIgnoreCase("java")?code+".java":code+".class";

		// download(request, response, downLoadPath, fileName);
		return null;
	}

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月4日下午7:45:00
	 * @version：
	 * @修改内容：
	 * @参数：@param response
	 * @参数：@param filename
	 * @参数：@param byteArray
	 * @参数：@throws UnsupportedEncodingException
	 * @参数：@throws IOException
	 * @return:void
	 * @throws
	 */
	public static void writeUtf8File(HttpServletResponse response, String filename,
			byte[] byteArray) throws UnsupportedEncodingException, IOException {
		response.setContentType("application/octet-stream;");
		response.setHeader(
				"Content-disposition",
				"attachment; filename="
						+ new String(filename.getBytes("utf-8"), "ISO8859-1"));

		java.io.BufferedOutputStream bos = null;
		try{
			 bos = new BufferedOutputStream(response.getOutputStream());
			
			response.setHeader("Content-Length", String.valueOf(3+byteArray.length));
			bos.write(0xef); //utf-8文件头
			bos.write(0xbb);
			bos.write(0xbf);
		
			bos.write(byteArray);
			bos.flush();
			response.flushBuffer();
			}finally {
				if (bos != null) {
					bos.close();
				}
		}
	}

	/**
	 * @方法功能说明：用utf8 带bom头的格式输出
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月4日下午7:41:34
	 * @version：
	 * @修改内容：
	 * @参数：@param response
	 * @参数：@param byteArray
	 * @参数：@throws IOException
	 * @return:void
	 * @throws
	 */
	public static void responseUtf8Stream(HttpServletResponse response,
			byte[] byteArray) throws IOException {
		java.io.BufferedOutputStream bos = null;
		try{
			 bos = new BufferedOutputStream(response.getOutputStream());
			
			response.setHeader("Content-Length", String.valueOf(3+byteArray.length));
			bos.write(0xef); //utf-8文件头
			bos.write(0xbb);
			bos.write(0xbf);

			bos.write(byteArray);
			bos.flush();
			response.flushBuffer();
			}finally {
				if (bos != null) {
					bos.close();
				}
		}
	}

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月4日下午7:33:07
	 * @version：
	 * @修改内容：
	 * @参数：@param list
	 * @参数：@param barr
	 * @参数：@throws IOException
	 * @参数：@throws UnsupportedEncodingException
	 * @return:void
	 * @throws
	 */
	public void getBytes(List<JfFlow> list, ByteArrayOutputStream barr)
			throws IOException, UnsupportedEncodingException {
		String string = "流水生成时间,积分交易类型,积分值,摘要,失效日期\r\n";
		 barr.write(string.getBytes(UTF_8));
		//write.write(string);
		for (JfFlow flow : list) {
			String s = "";
			s += flow.getInsertTime() + ",";

			s += flow.tradeType.getName() + ",";
			s += flow.getJf() + ",";
			s += flow.getDetail() + ",";
			s += flow.getInvalidDate() + ",";
			//write.write(s);
			//write.newLine();
			//write.flush();
			s+="\r\n";
			 barr.write(s.getBytes(UTF_8));
		}
	}

}
