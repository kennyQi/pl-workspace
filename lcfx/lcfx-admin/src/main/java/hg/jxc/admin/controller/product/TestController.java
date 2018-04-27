package hg.jxc.admin.controller.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hg.common.util.DwzJsonResultUtil;
import hg.common.util.JsonUtil;
import hg.jxc.admin.common.HtmlCodeUtil;
import hg.jxc.admin.common.HtmlUtil;
import hg.jxc.admin.common.HtmlUtilElite;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.controller.BaseController;
import hg.system.model.meta.Province;
import hg.system.qo.ProvinceQo;
import hg.system.service.ProvinceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;

import jxc.domain.model.product.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {
	@Autowired
	ProvinceService provinceService;

	@RequestMapping("tree")
	public String dwzTreeTest() {

		return "/test/treeBringBack.html";
	}

	@RequestMapping("tree_data")
	public String dwzTreeTest2() {

		return "/test/treeLookup.html";
	}

	@RequestMapping("revice")
	public String reviceDate(@RequestParam String[] specification, HttpServletRequest request, HttpServletResponse response) {
		for (String s : specification) {

			String[] spl = request.getParameterValues(s);
			System.out.println();
		}
		return null;

	}

	@RequestMapping("address")
	public String addressTest(Model model) {
		List<Province> provinceList = provinceService.queryList(new ProvinceQo());
		model.addAttribute("provinceList", provinceList);

		return "test/address.html";

	}

	@RequestMapping("service")
	public String addressservice(Model model) {

		return "";

	}

	@RequestMapping("upload")
	@ResponseBody
	public String upload(Model model) {

		return "aaaa.jpg";
	}

	@RequestMapping("ajaxupload")
	@ResponseBody
	public String ajaxupload(Model model) {
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("status", "error");
		retMap.put("message", "请上传jpg、png、gif格式的图片！");

		return JsonUtil.parseObject(retMap, false);
	}

	@RequestMapping("to_ajaxupload")
	public String toaxUpload(Model model) {

		return "test/ajaxupload.html";
	}

	@RequestMapping("to_upload")
	public String toUpload(Model model) {
		return "test/upload.html";
	}

	@RequestMapping("ret")
	@ResponseBody
	public String testRet(Model model) {
		return DwzJsonResultUtil.createJsonString("200", "ok", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, null);
	}

	@RequestMapping("tab")
	public String tab(Model model) {
		return "test/supplier_add.html";
	}

	@RequestMapping("/html_util")
	public String toHtmlutilTestPage(Model model) {
		Product product = new Product();
		product.setId("id");
		product.setProductName("名字");
		model.addAttribute("productJson",JsonUtil.parseObject(product, false));
		return "test/html_util.html";
	}
	@RequestMapping("/html_util2")
	public String toHtmlutilTestPage2(Model model) {
		return "test/html_util2.html";
	}

	@RequestMapping("/get_dataaaa")
	@ResponseBody
	public String getdata() {
		HtmlCodeUtil hu = new HtmlCodeUtil(0);
		hu.addSelctL("aaa", "sid", "onchange=\"alert();\"");
		hu.addSelctOption("aaa1", "v");
		hu.addSelctOption("aaa2", "v2");
		hu.addSelctR();
		JsonResultUtil jru = new JsonResultUtil();
		jru.addAttr("val", "111");
		jru.addAttr("val2", hu.outputHtml());
		jru.addAttr("val3", "zzzz");
		jru.addAttr("val3", "zzzz");

		ProvinceQo qo = new ProvinceQo();
		List<Province> ql = provinceService.queryList(qo);
		return JsonUtil.parseObject(ql, true);
	}

	@RequestMapping("/get_data_html")
	public String get_data_html(Model model,Integer aa,Integer bb,int[] cc) {
		
		
//try {
//	Thread.sleep(1000);
//} catch (InterruptedException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
model.addAttribute("hello", "hello");
		return "test/1.html";
	}
}
