package jxc.api.controller.ems;

import hg.pojo.dto.ems.EmsTraceDTO;
import hg.pojo.dto.ems.WmsStockInConfirmDTO;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import jxc.api.util.XmlUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ems")
public class EMSController {
	
	
	@ResponseBody
	@RequestMapping("/services")
	public String execute(HttpServletRequest request) {
		
		//验签
		String result=check(request);
		
		if(!"T".equals(result)){
			return result;
		}
		
		//根据service名称跳转相应方法
		String service=request.getParameter("service");
		
		if("WmsStockInConfirm".equals(service)){
			
			return wmsStockInConfirm(request);
			
		}else if("WmsStockOutConfirm".equals(service)){
			
			return wmsStockOutConfirm(request);
			
		}else if("WmsOrderStatusUpdate".equals(service)){
			
			return wmsOrderStatusUpdate(request);
			
		}else if("EmsTrace".equals(service)){
			
			return emsTrace(request);
			
		}else{
			
			result="<Response><success>F</success><code>003</code><reason>service不正确 </reason></Response>";

			return result;
			
		}
		
	}
	
	/**
	 * 入库单收货确认回传
	 * @param request
	 * @return
	 */
	public String wmsStockInConfirm(HttpServletRequest request){
		
		
		try {
//			WmsStockInConfirmDTO wmsStockInConfirmDTO=XmlUtil.toBean(request.getParameter("content"), WmsStockInConfirmDTO.class);
			
//			List<ProdDetail> details=new ArrayList<ProdDetail>();
//			for (StockInConfirmProduct stockInConfirmProduct : wmsStockInConfirm.getDetails()) {
//				ProdDetail prodDetail=new ProdDetail();
//				prodDetail.setSkuCode(stockInConfirmProduct.getSku_code());
//				prodDetail.setAcceptNum(Integer.parseInt(stockInConfirmProduct.getCount()));
//				details.add(prodDetail);
//			}
//			
//			Timestamp acceptTime = DateUtil.getSystemTimestamp();
//			
//			
//			this.acceptProdService.batchAcceptProd("EMS操作员", acceptTime, wmsStockInConfirm.getAsn_id(), details);
			
		} catch (Exception e) {
			
			String error="<Response><success>F</success><code>005</code><reason>"+e.getMessage()+"</reason></Response>";
	
			return error;
		}
		
		String success="<Response><success>T</success></Response>";

		
		return success;
	}
	
	/**
	 * 出库单出库确认回传
	 * @param request
	 * @return
	 */
	public String wmsStockOutConfirm(HttpServletRequest request){
		

		
		try {
//			WmsStockOutConfirm wmsStockOutConfirm=XmlUtil.toBean(request.getParameter("content"), WmsStockOutConfirm.class);
//			
//			NoutStock outStock=new NoutStock();
//			outStock.setOutstockNo(wmsStockOutConfirm.getOrder_id());
//			this.outstockService.confirmOutstock("EMS操作员", outStock);
			
		} catch (Exception e) {
			String error="<Response><success>F</success><code>005</code><reason>"+e.getMessage()+"</reason></Response>";
			
			return error;
		}
		
		String success="<Response><success>T</success></Response>";

		
		return success;
	}
	
	/**
	 * 订单状态回传
	 * @param request
	 * @return
	 */
	public String wmsOrderStatusUpdate(HttpServletRequest request){

		
		try {
//			WmsOrderStatusUpdate wmsOrderStatusUpdate=XmlUtil.toBean(request.getParameter("content"), WmsOrderStatusUpdate.class);
//			
//			//当订单状态为发货的时候，走手工确认出库，因为EMS出库确认不带物流号
//			if("SHIP".equalsIgnoreCase(wmsOrderStatusUpdate.getStatus())){
//				OutstockModel outstockModel = this.outstockService.getOutstockModel(wmsOrderStatusUpdate.getOrder_id());
//				if (!OutstockConstants.STATE_CKQR.equals(outstockModel.getOutstockState())) {
//					List<OutstockDetailVo> detailList = this.outstockService.getOutstockDetailList(wmsOrderStatusUpdate.getOrder_id());
//					outstockModel.setOutstockState(OutstockConstants.STATE_CKQR);// 确认出库
//					outstockModel.setOutstockTime(DateUtil.getSystemTimestamp());
//					outstockModel.setOutstockPerson("EMS操作员");
//					this.outstockService.confrimOustock(outstockModel, detailList);
//				}
//				
//			}
			
			//写入接口日志
			
		} catch (Exception e) {
			String error="<Response><success>F</success><code>005</code><reason>"+e.getMessage()+"</reason></Response>";
			
			return error;
		}
		
		String success="<Response><success>T</success></Response>";

		
		return success;
	}
	
	/**
	 * 运单轨迹回传
	 * @param request
	 * @return
	 */
	public String emsTrace(HttpServletRequest request){

		
		//对推送过来的xml做处理
		String xml=request.getParameter("content");
		xml=xml.replaceAll("<RequestEmsInfo>", "");
		xml=xml.replaceAll("</RequestEmsInfo>", "");

		xml="<RequestEmsInfo><traceList>"+xml+"</traceList></RequestEmsInfo>";
		
		EmsTraceDTO emsTrace=XmlUtil.toBean(xml, EmsTraceDTO.class);

		String success="<Response>";
		
		String transactionId="";
		try {
		
			for(int i=0;i<emsTrace.getRow().size();i++){
				if(emsTrace.getRow().get(i)!=null){
//					transactionId=emsTrace.getRow().get(i).getTransaction_id();
//					
//					SendGoodsModel model = this.outstockService.getSendGoodsModel(emsTrace.getRow().get(i).getOrder_id());
//					
//					if(model==null){
//						model=new SendGoodsModel();
//						model.setOutstockNo(emsTrace.getRow().get(i).getOrder_id());
//						model.setSendNo(emsTrace.getRow().get(i).getMailno());
//						model.setDeliverNo("01");
//						model.setSendTime(DateUtil.getSystemTimestampStr());
//						model.setSendState("03");
//						this.outstockService.addSendGoodsModel(model);
//					}else if(EMSConstants.success.equalsIgnoreCase(emsTrace.getRow().get(i).getOperate_type())){
//						model.setSendState("01");
//						this.outstockService.updateSendGoodsModel(model);
//					}else if(EMSConstants.fail.equalsIgnoreCase(emsTrace.getRow().get(i).getOperate_type())){
//						model.setSendState("06");
//						model.setRemark(emsTrace.getRow().get(i).getExt_attr2());
//						this.outstockService.updateSendGoodsModel(model);
//					}
//					
					success+="<row><transaction_id>"+transactionId+"</transaction_id>";
					success+="<success>T</success><code>004</code><reason>成功</reason></row>";
					
				}else{
					success+="<row><transaction_id></transaction_id>";
					success+="<success>F</success><code>005</code><reason>解析失败</reason></row>";
				}
			}
		
			success+="</Response>";
			
			return success;
				
		} catch (Exception e) {
			
			e.printStackTrace();

			success+="<row><transaction_id>"+transactionId+"</transaction_id>";
			success+="<success>F</success><code>005</code><reason>失败</reason></row>";
			success+="</Response>";
			
			return success;
		}
	}
	
	
	/**
	 * 验签
	 * @param request
	 * @return
	 */
	@SuppressWarnings("restriction")
	public String check(HttpServletRequest request){
		try {	
		request.setCharacterEncoding("UTF-8");
		//判断appkey是否正确
		String appkey=request.getParameter("appkey");
		if(!LogisticProperties.getInstance().get("ems_appkey").equals(appkey)){
			String result="<Response><success>F</success><code>001</code><reason>appkey不正确</reason></Response>";
			return result;
		}
		
		//判断sign是否正确
		String content=request.getParameter("content");
		System.out.println(content);
		String text=content+LogisticProperties.getInstance().get("ems_key_value");
		System.out.println(text);
		//MD5加密
		String 	localSign = DigestUtils.md5Hex(text.getBytes("UTF-8"));
		//base64加密
		localSign=new String(new sun.misc.BASE64Encoder().encode(localSign.getBytes("UTF-8")));
		System.out.println(localSign);
		String sign=request.getParameter("sign");
		if(!sign.equals(localSign)){
			String result="<Response><success>F</success><code>002</code><reason>MD5验签失败</reason></Response>";
			return result;
		}
		
		return "T";
		
		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	


}
