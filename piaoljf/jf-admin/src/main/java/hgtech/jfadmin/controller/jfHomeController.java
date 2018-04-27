/**
 * @文件名称：jfHomeController.java
 * @类路径：hgtech.jfadmin.controller
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年11月7日上午9:54:15
 */
package hgtech.jfadmin.controller;

import hgtech.jf.piaol.SetupSpiApplicationContext;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.Domain;
import hgtech.jfadmin.service.JfSystemService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ucenter.admin.controller.BaseController;

/**
 * @类功能说明：机构安全信息查看Controller
 * @类修改者：
 * @修改日期：2014年11月7日上午9:54:15
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年11月7日上午9:54:15
 * *url中传递参数如下：。
名字	解释	
function	调用的功能：
list 机构信息列表	
 *
 */

@Controller
@RequestMapping(value = "/home")
public class jfHomeController extends BaseController{
	
	@Autowired
	JfSystemService jfSystemService;
	
	/**
	 * 
	 * @方法功能说明：机构安全信息查询
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:18:23
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model){
		List<Domain> domainlist=jfSystemService.getSystemList();
        model.addAttribute("domainList",domainlist);
		return "/home/homeList.html";
	}
	

}
