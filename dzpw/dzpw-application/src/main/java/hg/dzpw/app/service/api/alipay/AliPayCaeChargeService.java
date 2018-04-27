package hg.dzpw.app.service.api.alipay;

import hg.dzpw.app.common.AlipayConfig;
import hg.dzpw.app.common.util.alipay.AlipayReflectUtil;
import hg.dzpw.app.common.util.alipay.AlipayResponseUtil;
import hg.dzpw.app.common.util.alipay.AlipaySubmitUtil;
import hg.dzpw.pojo.api.alipay.CaeChargeParameter;
import hg.dzpw.pojo.api.alipay.CaeChargeResponse;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 
 * @类功能说明：支付宝代扣接口服务
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-1上午11:05:31
 * @版本：
 */
@Service
public class AliPayCaeChargeService {
	/**
	 * 
	 * @描述： 根据扣款参数拼接请求form表单html
	 * @param caeChargeParameter 接口请求参数对象
	 * @param strMethod form表单请求方法
	 * @param strButtonName 提交按钮的显示名称
	 * @author: guotx 
	 * @version: 2016-3-1 下午3:20:32
	 */
    public String buildFormHtml(CaeChargeParameter caeChargeParameter, String strMethod, String strButtonName){
    	Map<String, String> sParaTemp = AlipayReflectUtil.buildParameterMap(caeChargeParameter);
    	return AlipaySubmitUtil.buildRequest(sParaTemp, strMethod, strButtonName);
    }

    /**
     * 
     * @描述： 通过http远程调用支付宝扣款接口
     * @param caeChargeParameter 接口参数对象
     * @author: guotx 
     * @version: 2016-3-1 下午3:35:49
     */
    public CaeChargeResponse chargeHttpRequest(CaeChargeParameter caeChargeParameter){
    	
    	//获取请求参数map
    	Map<String, String> sParaTemp = AlipayReflectUtil.buildParameterMap(caeChargeParameter);
    	
    	//对部分参数encode处理
    	AlipaySubmitUtil.encodeParameter(sParaTemp);
    	String result = null;
    	try {
			result = AlipaySubmitUtil.buildRequest("", "", sParaTemp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AlipayResponseUtil.caeChargeStrToBean(result);
    }
    
    
    /**
     * @方法功能说明：设置代扣请求的配置参数
     * @修改者名字：yangkang
     * @修改时间：2016-3-15上午10:52:09
     * @参数：@param caeChargeParameter
     * @return:void
     */
    public void setConfigParameters(CaeChargeParameter caeChargeParameter){
    	
    	caeChargeParameter.set_input_charset(AlipayConfig.input_charset);
    	caeChargeParameter.setService(AlipayConfig.CAE_CHARGE_SERVICE_NAME);
    	caeChargeParameter.setSign_type(AlipayConfig.sign_type);
    	caeChargeParameter.setCharge_type("trade");
    	caeChargeParameter.setPartner(AlipayConfig.partner);
    	caeChargeParameter.setTrans_account_in(AlipayConfig.partner + AlipayConfig.ACCOUNT_SUFFIX);
    	caeChargeParameter.setType_code(AlipayConfig.partner + AlipayConfig.TYPE_CODE_SUFFIX);
    }
}
