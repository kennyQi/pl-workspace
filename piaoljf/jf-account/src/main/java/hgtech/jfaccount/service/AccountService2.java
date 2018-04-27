/**
 * @文件名称：AccountManage.java
 * @类路径：hgtech.jfaccount.service
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-22上午10:32:41
 */
package hgtech.jfaccount.service;

import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hgtech.jf.JfChangeApply;
import hgtech.jf.JfProperty;
import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.EntityDao;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfAccountUK;
import hgtech.jfaccount.JfFlow;
import hgtech.jfaccount.JfTradeType;
import hgtech.jfaccount.JfUseDetail;
import hgtech.jfaccount.JfUser;
import hgtech.jfaccount.dao.AccountDao;
import hgtech.jfaccount.dao.FlowDao;
import hgtech.jfaccount.dao.UseDao;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @类功能说明：对方无响应时候的汇积分账户操作的service。另起事务
 * @类修改者：
 * @修改日期：2014-9-22上午10:32:41
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-22上午10:32:41
 * 
 */
@Transactional(propagation=Propagation.REQUIRES_NEW)
@Service
public class AccountService2 implements IAccountService2 {
	@Autowired
	AccountService accountService;
	/**
	 * 商户没有返回时候记录转入信息
	 * @param order
	 */
	public void transferInNOREPLY(JfChangeApply order/*,String orderid*/){
		accountService.gotJf(order,JfTradeType.in, JfFlow.STATUS_NOREPLY,accountService. INT_NOFLOWVALID/*,orderid*/);
	}
	
	public void transferOutNOREPLY(JfChangeApply order ){
		order.setFlowStatus(JfFlow.STATUS_NOREPLY);
		accountService.costJf(order, JfTradeType.out, JfProperty.getJfValidYear());
	}
}
