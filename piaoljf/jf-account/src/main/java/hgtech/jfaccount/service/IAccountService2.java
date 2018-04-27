package hgtech.jfaccount.service;

import hgtech.jf.JfChangeApply;

public interface IAccountService2 {

	public abstract void transferInNOREPLY(JfChangeApply order);

	public abstract void transferOutNOREPLY(JfChangeApply order);

}