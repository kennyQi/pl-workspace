package hg.demo.web.controller.distributor;

import hg.demo.web.common.UserInfo;
import hg.demo.web.controller.BaseController;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.command.DistributorRegister.AduitDistributorRegisterCommand;
import hg.fx.domain.DistributorRegister;
import hg.fx.domain.OperationLog;
import hg.fx.spi.DistributorRegisterSPI;
import hg.fx.spi.qo.DistributorRegisterSQO;
import hg.fx.util.SmsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商户注册申请审核
 * 
 * @author zqq
 * @date 2016-7-15上午11:16:22
 * @since
 */
@Controller
@RequestMapping("/distributorRegister")
public class DistributorRegisterAduitController extends BaseController {

	@Autowired
	private DistributorRegisterSPI drService;
	@Autowired
	private SmsUtil sendSms;
	
	private static final String SMS_MSG_REGIST_SUCC = "【汇购科技】恭喜您的里程兑换平台账号%s注册成功.";

	/**
	 * 商户注册申请列表
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-7-15 下午2:44:10
	 * @param sqo
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	public String getList(
			@ModelAttribute DistributorRegisterSQO sqo,
			Model model,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "numPerPage", defaultValue = "20") Integer pageSize) {
		// 分页设置
		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setPageNo(pageNum);
		limitQuery.setPageSize(pageSize);
		// 按状态升序，时间降序
		sqo.setOrderWay(2);
		sqo.setLimit(limitQuery);
		Pagination<DistributorRegister> pagination = drService
				.queryPagination(sqo);
		model.addAttribute("sqo", sqo);
		model.addAttribute("pagination", pagination);

		return "/distributor/distributorRegisterList.ftl";

	}

	/**
	 * 商户注册申请通过审核
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-7-15 下午2:43:59
	 * @param request
	 * @param cmd
	 * @param model
	 * @return
	 */
	@RequestMapping("/aduitPass")
	@ResponseBody
	public Map<String, Object> aduitPass(HttpServletRequest request,
			@ModelAttribute AduitDistributorRegisterCommand cmd, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 处理结果
		List<String> res = new ArrayList<String>();
		String content = "";
		UserInfo userInfo = getUserInfo(request.getSession());
		//给审核过程加锁
		synchronized(this) { 
		try {
			// 审核通过
			cmd.setIsPass(true);
			res = drService.aduitDistributorRegister(cmd);
			// 记录日志
			content = "商户注册申请通过审核,审核人:" + userInfo.getDisplayName() + ";记录[";
			StringBuffer sb = new StringBuffer();
			//提前设置结果
			map.put("statusCode", 200);
			map.put("callbackType", null);
			map.put("message", "操作成功");
			for (String phone : res) {
				try {
					
				// 处理成功
				sb.append(phone + ",");
				System.out.println(phone + "审核成功");
				//  发送短信通知
				DistributorRegisterSQO qo = new DistributorRegisterSQO();
				qo.setPhone(phone);
				qo.setStatus(DistributorRegister.DISTRIBUTOR_REGISTER_CHECK_SUCC);
				DistributorRegister dr = drService.queryUnique(qo);
				String msg = String.format(SMS_MSG_REGIST_SUCC, dr.getLoginName());
				//System.out.println(msg);
				sendSms.sendSms(phone, msg);
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("短信发送失败!号码:"+phone);
					if(map.get("statusCode")!=null&&300!=((Integer)map.get("statusCode")).intValue()){
					map.put("statusCode", 300);
					map.put("callbackType", null);
					map.put("message","短信发送失败!详情见操作日志" );}
					//记入日志
					saveLog("审核通过短信通知失败!号码:"+phone, request,
							OperationLog.OPERATION_TYPE_ADUIT_DISTRIBUTORREGIST);
				}
			}
			sb.append("]");
			content = content + sb.toString();
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_ADUIT_DISTRIBUTORREGIST);

		} catch (Exception e) {
			// 如果是批量操作记录多个id
			if (cmd.getIds() != null && cmd.getIds().size() != 0) {
				content = "商户注册申请通过审核操作失败,审核人:" + userInfo.getDisplayName()
						+ ";记录id[";
				StringBuffer sb = new StringBuffer();
				for (String id : cmd.getIds()) {
					sb.append(id + ",");
				}
				sb.append("]");
				content = content + sb.toString();
			} else {
				content = "商户注册申请通过审核操作失败,审核人:" + userInfo.getDisplayName()
						+ ";记录id:" + cmd.getId();
			}
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_ADUIT_DISTRIBUTORREGIST);
			map.put("statusCode", 300);
			map.put("callbackType", null);
			map.put("message", "操作失败");

		}
		}
		return map;

	}

	/**
	 * 商户注册申请拒绝审核
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-7-15 下午2:43:39
	 * @param request
	 * @param cmd
	 * @param model
	 * @return
	 */
	@RequestMapping("/aduitRefuse")
	@ResponseBody
	public Map<String, Object> aduitRefuse(HttpServletRequest request,
			@ModelAttribute AduitDistributorRegisterCommand cmd, Model model) {
		// 处理结果
		List<String> res = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		String content = "";
		UserInfo userInfo = getUserInfo(request.getSession());
		//给审核过程加锁
				synchronized(this) {
		try {
			// 审核拒绝
			cmd.setIsPass(false);
			res = drService.aduitDistributorRegister(cmd);
			// 记录日志
			content = "商户注册申请拒绝审核,审核人:" + userInfo.getDisplayName() + ";记录[";
			StringBuffer sb = new StringBuffer();
			for (String phone : res) {
				// 处理成功
				sb.append(phone + ",");
				System.out.println(phone + "拒绝审核处理成功");
			}
			sb.append("]");
			content = content + sb.toString();
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_ADUIT_DISTRIBUTORREGIST);
			map.put("statusCode", 200);
			map.put("callbackType", null);
			map.put("message", "操作成功");

		} catch (Exception e) {
			// 如果是批量操作记录多个id
			if (cmd.getIds() != null && cmd.getIds().size() != 0) {
				content = "商户注册申请通过审核操作失败,审核人:" + userInfo.getDisplayName()
						+ ";记录id[";
				StringBuffer sb = new StringBuffer();
				for (String id : cmd.getIds()) {
					sb.append(id + ",");
				}
				sb.append("]");
				content = content + sb.toString();
			} else {
				content = "商户注册申请通过审核操作失败,审核人:" + userInfo.getDisplayName()
						+ ";记录id:" + cmd.getId();
			}
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_ADUIT_DISTRIBUTORREGIST);
			map.put("statusCode", 300);
			map.put("callbackType", null);
			map.put("message", "操作失败");

		}
				}
		return map;

	}
}
