package hgtech.jfadmin.interceptor;

import javax.annotation.Resource;

import hg.common.component.BaseCommand;
import hg.common.component.BaseQo;
import hg.common.page.Pagination;
import hg.log.po.normal.HgLog;
import hg.log.util.HgLogger;
import hgtech.jfadmin.dao.CalFlowDao;

import org.aspectj.lang.ProceedingJoinPoint;

//import slfx.api.base.ApiPayload;
//import slfx.api.base.ApiResponse;


import com.alibaba.fastjson.JSON;

public class AccountAspect {

	@Resource
	CalFlowDao calDao;

	/**
	 * 是否需要 记录日志
	 */
	private boolean monitor = true;
	
	public boolean isMonitor() {
		return monitor;
	}
	
	 
	
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
	    return pjp.proceed();
	}

}
