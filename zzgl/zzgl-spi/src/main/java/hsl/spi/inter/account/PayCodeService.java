package hsl.spi.inter.account;
import hsl.pojo.command.account.PayCodeCommand;
import hsl.pojo.dto.account.PayCodeDTO;
import hsl.pojo.qo.account.PayCodeQO;
import hsl.spi.inter.BaseSpiService;

public interface PayCodeService extends BaseSpiService<PayCodeDTO,PayCodeQO>{
	/**
	 * 用户充值
	 */
	public String recharge (PayCodeCommand payCodeCommand)  throws Exception;
	/**
	 * 重写分页查询:懒加载不能加载出用户信息
	 */
	//public Pagination queryPagination(Pagination pagination);
	
}
