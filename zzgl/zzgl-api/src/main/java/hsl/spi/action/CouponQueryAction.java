package hsl.spi.action;

import java.util.List;

import hg.log.util.HgLogger;
import hsl.api.base.ApiRequest;
import hsl.api.v1.request.qo.company.MemberQO;
import hsl.api.v1.request.qo.coupon.CouponQueryQO;
import hsl.api.v1.response.coupon.CouponQueryResponse;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.spi.inter.Coupon.CouponService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("couponQueryAction")
public class CouponQueryAction implements HSLAction{
	@Autowired
	private HgLogger hgLogger;
	@Autowired
	private CouponService couponService;
	@Override
	public String execute(ApiRequest apiRequest) {
		CouponQueryQO couponQueryQO = JSON.parseObject(apiRequest.getBody()
				.getPayload(), CouponQueryQO.class);
		hgLogger.info("yuqz", "查询卡劵请求"+JSON.toJSONString(couponQueryQO));
		return queryCoupon(couponQueryQO);
	}
	private String queryCoupon(CouponQueryQO couponQueryQO) {
		HslCouponQO hslCouponQO = new HslCouponQO();
		CouponQueryResponse couponQueryResponse = new CouponQueryResponse();
		if(StringUtils.isNotBlank(couponQueryQO.getUserId())){
			hslCouponQO.setUserId(couponQueryQO.getUserId());
		}else{
			couponQueryResponse.setMessage("输入参数不正确");
			couponQueryResponse.setResult(couponQueryResponse.RESULT_CODE_FAIL);
			return JSON.toJSONString(couponQueryResponse);
		}
		
		if(couponQueryQO.getIsNoCanUsed() == null){
			couponQueryResponse.setMessage("输入参数不正确");
			couponQueryResponse.setResult(couponQueryResponse.RESULT_CODE_FAIL);
			return JSON.toJSONString(couponQueryResponse);
		}else if(couponQueryQO.getIsNoCanUsed()){
			Object[] statusTypes = {2,3,4};
			hslCouponQO.setStatusTypes(statusTypes);
		}else{
			Object[] statusTypes = {1};
			hslCouponQO.setStatusTypes(statusTypes);
		}
		
		List<CouponDTO> couponDTOList= couponService.queryList(hslCouponQO);
		if(couponDTOList == null || couponDTOList.size() <= 0){
			couponQueryResponse.setMessage("查询失败");
			couponQueryResponse.setResult(couponQueryResponse.RESULT_CODE_FAIL);
			return JSON.toJSONString(couponQueryResponse);
		}
		couponQueryResponse.setCouponDTOList(couponDTOList);
		couponQueryResponse.setMessage("查询成功");
		couponQueryResponse.setResult(couponQueryResponse.RESULT_CODE_OK);
		return JSON.toJSONString(couponQueryResponse);
	}

}
