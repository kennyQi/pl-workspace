package hsl.h5.control;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import hg.log.util.HgLogger;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.dto.account.AccountDTO;
import hsl.pojo.qo.account.AccountQO;
import hsl.spi.inter.account.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("accountController")
public class AccountController extends HslCtrl{
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
	@RequestMapping("queryAccount")
	@ResponseBody
	public String queryAccount(HttpServletRequest request,HttpServletResponse response){//查询用户账户
		try {
			String userId = getUserId(request);
			AccountQO account=new AccountQO();
			account.setUserID(userId);
			account.setConsumeOrderSnapshots(true);
			AccountDTO accountDTO=accountService.queryUnique(account);
			Double consumptionBalance=0.00;
			HgLogger.getInstance().info("chenxy", "查询账户余额->account->" + JSON.toJSONString(accountDTO));
			double newBanlance=0.0;
			if(accountDTO!=null&&accountDTO.getConsumeOrderSnapshots()!=null&&accountDTO.getConsumeOrderSnapshots().size()>0) {
				for (AccountConsumeSnapshotDTO consumeOrderSnapshots : accountDTO.getConsumeOrderSnapshots()) {
					if (consumeOrderSnapshots.getStatus() == 1) {
						consumptionBalance += consumeOrderSnapshots.getPayPrice();
					}
				}
				
			   newBanlance=accountDTO.getBalance()-consumptionBalance;
			}
		   
			if(newBanlance<=0){
				newBanlance=0.00;
			}
			String balance=String.valueOf(newBanlance);
			return balance;
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy","查询账户余额->出错"+HgLogger.getStackTrace(e));
			return "0.00";
		}
	}
}
