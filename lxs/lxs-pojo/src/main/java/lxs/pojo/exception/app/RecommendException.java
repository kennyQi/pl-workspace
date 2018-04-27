package lxs.pojo.exception.app;

import lxs.pojo.BaseException;

@SuppressWarnings("serial")
public class RecommendException extends BaseException {

	public RecommendException(String message) {
		super(message);
	}

	public final static String COUNT_UPPER_LIMIT = "推荐最多30条";
	
	public final static String RECOMMEND_NULL = "推荐未找到";
	
}
