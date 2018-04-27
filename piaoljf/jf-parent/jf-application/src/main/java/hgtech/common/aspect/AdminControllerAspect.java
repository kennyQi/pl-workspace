package hgtech.common.aspect;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import hg.common.component.BaseCommand;
import hg.common.component.BaseQo;
import hg.common.page.Pagination;
import hg.common.util.UUIDGenerator;
import hg.hjf.domain.model.log.OperationLog;
import hg.log.po.normal.HgLog;
import hg.log.util.HgLogger;
import hg.logservice.OperationLogService;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;

//import slfx.api.base.ApiPayload;
//import slfx.api.base.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
/**
 * controller aspect.将登录名，ip等信息保存到threadlocal，以便其他同线程程序使用。
 * @author xinglj
 *
 */
public class AdminControllerAspect {

	@Autowired
	OperationLogService operationLogService;

	 
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

		boolean islog=false;
		
		Class<? extends Object> class1 = pjp.getTarget().getClass();
		String name = pjp.getSignature().getName();
		
		//不记日志的操作
		islog = !name.startsWith("get") && !name.startsWith("init") && !name.startsWith("find") && !name.startsWith("list")
				&& !name.startsWith("to_");

		
		OperationLog oplog = new OperationLog();
		if(islog){

			Object[] objs = pjp.getArgs();
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] instanceof HttpServletRequest) {
					// 获得请求数据
					HttpServletRequest request = (HttpServletRequest) objs[i];
					oplog.setOperData(getParameterData(request));

					//操作用户
					Subject currentUser = SecurityUtils.getSubject();
					AuthUser usr= (AuthUser) currentUser.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
					if(usr!=null)
					//登录用户
						oplog.setLoginName(usr.getLoginName());
					else{
					//未登录用户
						oplog.setLoginName(request.getParameter("loginName"));
					}
					
					//线程上记录操作员，以便service层获取。
					oplog.setEntry(OperationLog.entryAdmin);
					oplog.setIp(request.getRemoteAddr());
					OperationLog.threadLocal.set(oplog);
				}

			}

			
		}

		long timebegin = System.currentTimeMillis();
		Object returnObj = null;
		try {
			returnObj = pjp.proceed();
						
			if(returnObj!=null)
				oplog.setReturnData(returnObj.toString());
			oplog.setOperOk(true);
			return returnObj;
		} catch (Exception e) {

			oplog.setFailReason(e.getMessage());
			oplog.setOperOk(false);
			throw e;
		} finally {
			if(islog){
				long time = System.currentTimeMillis() - timebegin;

				oplog.setId(UUIDGenerator.getUUID());
				oplog.setOperTime(new Date());
				oplog.setOperType(class1.getSimpleName()+"."+ name);
				
				//service上已记录log，所以controller上不记了。
//				operationLogService.save(oplog);
				System.out.println("AdminControllerAspect:"+oplog);
			}
		}
	}

	private String getParameterData(HttpServletRequest request) {
		StringBuffer sb= new StringBuffer();
		for(Object k:request.getParameterMap().keySet()) {
			if(k.equals("password"))
				continue;
			sb.append(k+"="+request.getParameter(k.toString())+" ");
		}
		return sb.toString();
	}

}
