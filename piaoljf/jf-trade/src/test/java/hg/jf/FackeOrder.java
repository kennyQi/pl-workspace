/**
 * @FackeOrder.java Create on 2015年1月18日上午11:41:00
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.jf;

import hg.jf.state.TradeStateAction;

import javax.swing.JOptionPane;

public class FackeOrder implements TradeStateAction {
	
	public boolean resultOut;
	public boolean resultIn;

	/* (non-Javadoc)
	 * @see hg.jf.OrderStateAction#tranfserOut()
	 */
	@Override
	public void tranfserOut(){
		resultOut= (javax.swing.JOptionPane.showConfirmDialog(null, "")==JOptionPane.YES_OPTION);
	}
	
	/* (non-Javadoc)
	 * @see hg.jf.OrderStateAction#transferIn()
	 */
	@Override
	public void transferIn(){
		resultIn= (javax.swing.JOptionPane.showConfirmDialog(null, "")==JOptionPane.YES_OPTION);
	}
	/* (non-Javadoc)
	 * @see hg.jf.OrderStateAction#isOutOk()
	 */
	@Override
	public boolean isOutOk(){
		return resultOut;
	}
	/* (non-Javadoc)
	 * @see hg.jf.OrderStateAction#isInOk()
	 */
	@Override
	public boolean isInOk(){
		return resultIn;
	}
}