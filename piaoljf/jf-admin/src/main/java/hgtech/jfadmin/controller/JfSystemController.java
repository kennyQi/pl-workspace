/**
 * @文件名称：JfSystemController.java
 * @类路径：hgtech.jfadmin.controller
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年10月28日上午9:46:59
 */
package hgtech.jfadmin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hg.common.model.DwzTreeNode;
import hg.common.util.DwzTreeUtil;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthPerm;
import hg.system.model.auth.AuthRole;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.Domain;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.TradeType;
import hgtech.jfadmin.service.JfSystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ucenter.admin.controller.BaseController;

/**
 * @类功能说明：系统管理：机构、交易类型、积分类型等
 * @类修改者：
 * @修改日期：2014年10月28日上午9:46:59
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年10月28日上午9:46:59
 *
 */

@Controller
@RequestMapping(value = "/system")
public class JfSystemController extends BaseController{
	
	@Autowired
	JfSystemService jfSystemService;
	
	/**
	 * @方法功能说明：机构查询管理
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:28:06
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/organ/list")
	public String listDomain(HttpServletRequest request, HttpServletResponse response, Model model){
		
		List<Domain> domainList=jfSystemService.getSystemList();
		model.addAttribute("domainList",domainList);
		
		return "/system/organList.html";
	}
	
	/**
	 * @方法功能说明：交易类型查看
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:28:48
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/trade/lists")
	public String tradeList(HttpServletRequest request, HttpServletResponse response, Model model){
		List<TradeType> list=jfSystemService.getTradeList();
		List<DwzTreeNode> dtnlist=new ArrayList<DwzTreeNode>();
		if(list!=null){
			for(TradeType trade:list){
				DwzTreeNode dtn=new DwzTreeNode();
				dtn.setId(trade.code);
				if(trade.upperType!=null){
					dtn.setParentId(trade.upperType.code);
				}
					dtn.setDisplayName(trade.name);
					dtn.setTvalue(trade.code);
					dtn.setTname("permIds");
					dtnlist.add(dtn);
			}
		}//tree treeFolder treeCheck expand,tree treeFolder collapse
		String permTreeHtml=DwzTreeUtil.createDwzTreeListHtml(dtnlist, "tree treeFolder collapse");
		model.addAttribute("permTreeHtml", permTreeHtml);
		
		
		return "/system/tradeList.html";
	}
	
	/**
	 * @方法功能说明：账户类型查看
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:30:10
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/accounttype/list")
	public String accounttypeList(HttpServletRequest request, HttpServletResponse response, Model model){
		
		List<JfAccountType> accList=jfSystemService.getAccountList();
		List<DwzTreeNode> dtnlist=new ArrayList<DwzTreeNode>();
		if(accList!=null){
			for(JfAccountType trade:accList){
				DwzTreeNode dtn=new DwzTreeNode();
				dtn.setId(trade.getCode());
				if(trade.upper!=null){
					dtn.setParentId(trade.upper.getCode());
				}
					dtn.setDisplayName(trade.getName());
					dtn.setTvalue(trade.getCode());
					dtn.setTname("permIds");
					dtnlist.add(dtn);
			}
		}//tree treeFolder treeCheck expand,tree treeFolder collapse
		String permTreeHtml=DwzTreeUtil.createDwzTreeListHtml(dtnlist, "tree treeFolder collapse");
		model.addAttribute("permTreeHtml", permTreeHtml);
		
		return "/system/accountTypeList.html";
	}
	

}
