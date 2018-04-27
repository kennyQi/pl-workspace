//package hg.dzpw.app.service.api.hjb;
//
//import org.apache.http.client.ClientProtocolException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import hg.dzpw.app.common.util.HttpClientUtil;
//import hg.dzpw.app.common.util.SignatureUtil;
//import hg.dzpw.app.common.util.XmlUtil;
//import hg.dzpw.pojo.api.hjb.HJBRegisterRequestDto;
//import hg.dzpw.pojo.api.hjb.HJBRegisterResponseDto;
//import hg.dzpw.pojo.api.hjb.HJBSignQueryRequestDto;
//import hg.dzpw.pojo.api.hjb.HJBSignQueryResponseDto;
//import hg.dzpw.pojo.api.hjb.HJBTransferRequestDto;
//import hg.dzpw.pojo.api.hjb.HJBTransferResponseDto;
//import hg.dzpw.pojo.common.HjbPayServiceConfig;
//
//
///**
// * @类功能说明：汇金宝接口服务
// * @类修改者：
// * @修改日期：2015-4-22上午11:06:34
// * @修改说明：
// * @公司名称：浙江汇购科技有限公司
// * @作者：guotx
// * @创建时间：2015-4-22上午11:06:34
// */
//@Service
//public class HJBPayService {
//	@Autowired
//	private HjbPayServiceConfig config;
//	
//	private String getSignTransUrl(){
//		return config.getHJBServerUrl()
//				+ "/PayRoute/TransferAction.transfer.do";
//	}
//	private String getRegeditUrl(){
//		return config.getHJBServerUrl()
//			+ "/PayRoute/UserRegistAction.regist.do";
//	}
//	private String getSignQueryUrl(){ 
//		return config.getHJBServerUrl()
//			+ "/PayRoute/OrderSignQuery.SignQuery.do";
//	}
//	// * 单笔转账接口
//	public HJBTransferResponseDto signTransfer(HJBTransferRequestDto dto) {
//		return callService(HJBTransferResponseDto.class, dto, getSignTransUrl());
//	}
//
//	// * 企业用户注册接口
//	public HJBRegisterResponseDto register(HJBRegisterRequestDto dto) {
//		return callService(HJBRegisterResponseDto.class, dto, getRegeditUrl());
//	}
//	
//	// * 单笔订单查证接口
//	public HJBSignQueryResponseDto signQuery(HJBSignQueryRequestDto dto) {
//		return callService(HJBSignQueryResponseDto.class, dto, getSignQueryUrl());
//	}
//
//	/**
//	 * 调用汇金宝接口服务，返回封装好的数据实体
//	 * @param returnType 指定需要返回的实体类型
//	 * @param dataObject 封装的请求数据实体
//	 * @param serviceUrl 请求接口URL
//	 * @return 封装好的实体数据
//	 */
//	private <T>T callService(Class<T> returnType,Object dataObject,String serviceUrl){
//		//将数据对象转换成xml
//		String srcString = XmlUtil.object2Xml(dataObject);
//		String reqData = null;	//加密的请求数据
//		String result = null; // 返回的加密数据
//		try {
//			// 加密请求数据
//			reqData = SignatureUtil.sm2SignDettach(srcString);
//			System.out.println("加密后的数据："+srcString);
//			System.out.println("请求URL："+serviceUrl);
//			// 发送请求获取返回数据
//			result = HttpClientUtil.sendMessage(reqData, serviceUrl);
//			
//			// 解密
//			result=SignatureUtil.openSM2EnvelopMessage(result.getBytes("UTF-8"));
//			if(!SignatureUtil.sm2VerifyDettach(result)){
//				//验签失败
//				return null;
//			}
//			result = XmlUtil.removeNode(result, "signatureInfo");
//			System.out.println("移除sign后：\n" + result);
//			T t=XmlUtil.xml2Object(returnType, result);
//			return t;
//		} catch(ClientProtocolException e){
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//		
//	}
//	
//}
