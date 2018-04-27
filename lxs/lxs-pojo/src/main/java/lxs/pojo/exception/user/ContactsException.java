package lxs.pojo.exception.user;

import lxs.pojo.BaseException;


@SuppressWarnings("serial")
public class ContactsException extends BaseException {

	
	public ContactsException(Integer code, String message) {
		super(code, message);
	}
	
	/**
	 * 该联系人已存在
	 */
	public final static Integer RESULT_CONTACTS_REPEAT = 35; 
	/**
	 * 该联系人已存在
	 */
	public final static Integer RESULT_CONTACTS_USER_NOT_FOUND = 20; 
	
}
