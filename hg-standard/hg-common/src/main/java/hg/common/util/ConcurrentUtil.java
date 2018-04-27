package hg.common.util;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @类功能说明：线程内 绑定/关闭 session
 * @类修改者：zzb
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzb
 * @创建时间：2015年1月4日上午10:19:34
 * @版本：V1.0
 */
public class ConcurrentUtil {

	/**
	 * @方法功能说明：绑定session
	 * @修改者名字：zzb
	 * @修改时间：2015年1月4日上午10:20:05
	 * @修改内容：
	 * @参数：@param sessionFactory
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public static boolean bindHibernateSessionToThread(SessionFactory sessionFactory) {  
	    if (TransactionSynchronizationManager.hasResource(sessionFactory)) {  
	        // Do not modify the Session: just set the participate flag.  
	        return true;  
	    } else {  
	        Session session = sessionFactory.openSession();  
	        session.setFlushMode(FlushMode.MANUAL);  
	        SessionHolder sessionHolder = new SessionHolder(session);  
	        TransactionSynchronizationManager.bindResource(sessionFactory, sessionHolder);  
	    }  
	    return false;  
	}  
	
	/**
	 * @方法功能说明：关闭session
	 * @修改者名字：zzb
	 * @修改时间：2015年1月4日上午10:20:13
	 * @修改内容：
	 * @参数：@param participate
	 * @参数：@param sessionFactory
	 * @return:void
	 * @throws
	 */
	public static void closeHibernateSessionFromThread(boolean participate, Object sessionFactory) {  
		  
	    if (!participate) {  
	        SessionHolder sessionHolder = (SessionHolder)TransactionSynchronizationManager.unbindResource(sessionFactory);  
	        SessionFactoryUtils.closeSession(sessionHolder.getSession());  
	    }  
	}  
}
