package hg.service.ad.pojo.exception;

import hg.service.ad.base.BaseException;

@SuppressWarnings("serial")
public class ADException extends BaseException {
	
	public ADException(Integer code, String message) {
		super(code, message);
	}
}
