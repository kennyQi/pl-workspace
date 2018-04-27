package hsl.web.controller.coupon;
import com.alibaba.fastjson.JSON;
import hg.log.util.HgLogger;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.dto.account.AccountDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.qo.account.AccountQO;
import hsl.spi.inter.account.AccountService;
import hsl.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Controller
@RequestMapping("/accountController")
public class AccountController extends BaseController{
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @方法功能说明:查询账户余额
	 * @创建者名字：zhaows
	 * @创建时间：2015-9-7上午8:56:40
	 * @参数：@param request
	 * @参数：@param response
	 * @return:void
	 * @throws
	 */
	@RequestMapping("/queryAccount")
	@ResponseBody
	public String queryAccount(HttpServletRequest request,HttpServletResponse response){//查询用户账户
		try {
			UserDTO user = getUserBySession(request);
			AccountQO account=new AccountQO();
			//String userId=request.getParameter("userId");
			account.setUserID(user.getId());
			account.setConsumeOrderSnapshots(true);
			AccountDTO accountDTO=accountService.queryUnique(account);
			Double consumptionBalance=0.00;
			HgLogger.getInstance().info("chenxy", "查询账户余额->account->" + JSON.toJSONString(accountDTO));
			double newBanlance=0D;
			if(accountDTO!=null&&accountDTO.getConsumeOrderSnapshots()!=null){
				for(AccountConsumeSnapshotDTO consumeOrderSnapshots:accountDTO.getConsumeOrderSnapshots()){
					if(consumeOrderSnapshots.getStatus()==1){
						consumptionBalance+=consumeOrderSnapshots.getPayPrice();
					}
				}

				 newBanlance=accountDTO.getBalance()-consumptionBalance;
			}

			if(newBanlance<0){
				newBanlance=0.00;
			}
			String balance=String.valueOf(newBanlance);
			return balance;
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy", "查询账户余额->出错" + HgLogger.getStackTrace(e));
			return "0.00";
		}
	}
}
