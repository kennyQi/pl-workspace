package lxs.pojo.exception.mp;

import lxs.pojo.BaseException;

@SuppressWarnings("serial")
public class QueryScenicSpotInfoException extends BaseException{

	public QueryScenicSpotInfoException (String message) {
		super(-1,message);
	}
	
	public final static String PARAMETER_ILLEGAL="参数错误";
}
