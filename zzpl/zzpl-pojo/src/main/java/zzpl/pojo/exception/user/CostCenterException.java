package zzpl.pojo.exception.user;

import zzpl.pojo.exception.BaseException;

@SuppressWarnings("serial")
public class CostCenterException extends BaseException {

	public CostCenterException(String message) {
		super(message);
	}

	public final static String ADD_COST_CENTER_ERROR = "添加成本中心错误";
}
