package hgtech.jfaccount.listener;

import hgtech.jfaccount.Domain;
/**
 * 商户预付费账户Listener
 * @author xinglj
 * 
 *
 */
public interface IDomainAccountListener {
	
	/**
	 * 停发事件
	 * @param sh
	 * @param oldjf
	 */
	public void suspend(Domain sh, long oldjf) ;
	/**
	 * 警戒事件
	 * @param sh
	 * @param oldjf
	 */
	public void alarm(Domain sh, long oldjf) ;
}
