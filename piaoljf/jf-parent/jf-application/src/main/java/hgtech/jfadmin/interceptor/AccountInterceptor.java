/**
 * @AccountInterceptor.java Create on 2015年1月28日上午11:16:55
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfadmin.interceptor;

import hg.common.util.UUIDGenerator;
import hg.hjf.app.dao.operationlog.OperationLogDao;
import hgtech.jf.JfChangeApply;
import hgtech.jfadmin.dao.CalFlowDao;
import hgtech.jfadmin.hibernate.CalFlowHiberEntity;
import hgtech.jfcal.model.CalResult;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明：拦截 AccountService的一切关于账户的写操作
 * @类修改者：
 * @修改日期：2015年1月28日上午11:16:55
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月28日上午11:16:55
 * @version：
 */
public class AccountInterceptor implements MethodInterceptor {

    @Resource
    CalFlowDao calDao;
    @Resource
    OperationLogDao operationLogDao;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
     * .MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
    	Object p1 = invocation.getArguments()[0];
    	if (p1 instanceof JfChangeApply){    	
			if(operationLogDao.isDayEnd())
				throw new RuntimeException("正在进行系统日终处理，不能进行账户操作！");
    	}

	if (p1 instanceof JfChangeApply && 
		// 计算cal已经有日志。
		!invocation.getMethod().getName().startsWith("cal")) {
	    
	    JfChangeApply order = (JfChangeApply) p1;
	    // 账户写操作，要记录
	    ArrayList<CalFlowHiberEntity> cals = new ArrayList<>();
	    CalFlowHiberEntity cal = new CalFlowHiberEntity();
	    cal.calTime = new Date();
	    cal.in_batchNo = order.getbatchNo();
	    cal.in_userCode = order.getuserCode();
	    cal.out_jf = order.getjf();
	    cal.in_remark = order.getremark();
	    cal.in_tradeFlowId = order.gettradeFlowId();
	   
	    try {
			if (invocation.getArguments()[1] instanceof String)
				cal.setIn_tradeFlowJson((String) invocation.getArguments()[1]);
			else{
				String s = JSONObject.toJSONString(order);
				cal.setIn_tradeFlowJson(s); 
			}
		} catch (Exception e1) {
			System.err.println("error setIn_tradeFlowJson,in_tradeFlowId: "+cal.in_tradeFlowId);
			e1.printStackTrace();
		}
	    System.out.println(cal);
	    try {
		Object ret = invocation.proceed();
		cal.out_resultCode = "Y";
		cal.out_resultText = "操作成功";
		cals.add(cal);
		calDao.save2(cals);
		System.out.println("交易成功，日志已记录:"+invocation.getMethod().getName()+" "+p1);
		return ret;
	    } catch (Throwable e) {
		cal.out_resultCode = "N";
		cal.out_resultText = "操作失败：" + e.getMessage();
		cals.add(cal);
		calDao.save2(cals);
		System.out.println("交易失败，日志已记录:"+invocation.getMethod().getName()+" "+p1);
		throw e;
	    }
	} else
	    return invocation.proceed();
    }
}
