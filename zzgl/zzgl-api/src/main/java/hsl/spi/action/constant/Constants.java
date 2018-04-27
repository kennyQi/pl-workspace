package hsl.spi.action.constant;

import hsl.api.v1.response.coupon.ConsumeCouponResponse;
import hsl.api.v1.response.mp.MPQueryDatePriceResponse;
import hsl.api.v1.response.mp.MPQueryOrderResponse;
import hsl.api.v1.response.mp.MPQueryScenicSpotsResponse;
import hsl.api.v1.response.user.BindWXResponse;
import hsl.api.v1.response.user.SendValidCodeResponse;
import hsl.api.v1.response.user.UserCheckResponse;
import hsl.api.v1.response.user.UserRegisterResponse;
import hsl.api.v1.response.user.UserUpdatePasswordResponse;
import hsl.pojo.exception.CouponException;
import hsl.pojo.exception.MPException;
import hsl.pojo.exception.UserException;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static  Map<Integer, String>exceptionMap=new HashMap<Integer, String>();
	
	static {
		exceptionMap.put(UserException.RESULT_MOBILE_UNBIND, SendValidCodeResponse.RESULT_MOBILE_UNBIND);
		exceptionMap.put(UserException.RESULT_MOBILE_BIND, SendValidCodeResponse.RESULT_MOBILE_BIND);
		exceptionMap.put(UserException.RESULT_MOBILE_WRONG, SendValidCodeResponse.RESULT_MOBILE_WRONG);
		exceptionMap.put(UserException.RESULT_VALICODE_WRONG, UserRegisterResponse.RESULT_VALICODE_WRONG);
		exceptionMap.put(UserException.RESULT_LOGINNAME_REPEAT, UserRegisterResponse.RESULT_LOGINNAME_REPEAT);
		exceptionMap.put(UserException.VALIDCODE_OVERTIME, UserRegisterResponse.VALIDCODE_OVERTIME);
		exceptionMap.put(UserException.RESULT_VALICODE_INVALID, UserRegisterResponse.RESULT_VALICODE_INVALID);
		exceptionMap.put(UserException.RESULT_LOGINNAME_NOTFOUND, UserCheckResponse.RESULT_LOGINNAME_NOTFOUND);
		exceptionMap.put(UserException.RESULT_AUTH_FAIL, UserCheckResponse.RESULT_AUTH_FAIL);
		exceptionMap.put(UserException.RESULT_BINDING_REPEAT, BindWXResponse.RESULT_BINDING_REPEAT);
		exceptionMap.put(UserException.RESULT_HGLOGINNAME_NOTFOUND, BindWXResponse.RESULT_HGLOGINNAME_NOTFOUND);
		exceptionMap.put(UserException.RESULT_HGLOGINNAME_BINDING_REPEAT, BindWXResponse.RESULT_HGLOGINNAME_BINDING_REPEAT);
		exceptionMap.put(UserException.RESULT_PASSWORD_WRONG, BindWXResponse.RESULT_PASSWORD_WRONG);
		exceptionMap.put(UserException.OLD_PASSWORD_WRONG, UserUpdatePasswordResponse.OLD_PASSWORD_WRONG);
		exceptionMap.put(UserException.USER_NOT_FOUND, UserUpdatePasswordResponse.USER_NOT_FOUND);
		exceptionMap.put(MPException.RESULT_SCENICSPOT_NOTFOUND, MPQueryScenicSpotsResponse.RESULT_SCENICSPOT_NOTFOUND);
		exceptionMap.put(MPException.RESULT_ORDER_NOTFOUND, MPQueryOrderResponse.RESULT_ORDER_NOTFOUND);
		exceptionMap.put(MPException.RESULT_DATEPRICE_NOTFOUND, MPQueryDatePriceResponse.RESULT_DATEPRICE_NOTFOUND);
		exceptionMap.put(CouponException.RESULT_COUPON_NOCANCEL, ConsumeCouponResponse.RESULT_COUPON_NOCANCEL);
		exceptionMap.put(CouponException.COUPON_UNAVAILABLE, ConsumeCouponResponse.COUPON_UNAVAILABLE);
		exceptionMap.put(CouponException.ACTIVITY_STATUS_ERROR, ConsumeCouponResponse.ACTIVITY_STATUS_ERROR);

	}
}
