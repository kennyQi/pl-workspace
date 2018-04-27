package hg.dzpw.admin.controller.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import hg.common.page.Pagination;
import hg.dzpw.app.pojo.qo.HJBTransferRecordQo;
//import hg.dzpw.app.service.local.HJBTransferRecordLocalService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HJBTransRecordController {
	
//	@Autowired
//	private HJBTransferRecordLocalService hjbTransferRecordService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping("/hjb/transrecord")
	public ModelAndView list(@RequestParam(value="pageNum", required = false)Integer pageNum,
            				 @RequestParam(value="numPerPage", required = false)Integer pageSize,
            				 @RequestParam(value="transType", required = false)Integer transType,
            				 @RequestParam(value="recordDateBegin", required = false)String recordDateBegin,
            				 @RequestParam(value="recordDateEnd", required = false)String recordDateEnd,
            				 @RequestParam(value="hasResponse", required = false)String hasResponse,
            				 @RequestParam(value="status", required = false)String status,
            				 @RequestParam(value="payCstNo", required = false)String payCstNo,
            				 @RequestParam(value="rcvCstNo", required = false)String rcvCstNo,
            				 @RequestParam(value="userId", required = false)String userId,
            				 @RequestParam(value="hjbOrderNo", required = false)String hjbOrderNo,
            				 @RequestParam(value="errorCode", required = false)String errorCode){
		
		Pagination pagination = new Pagination();
		HJBTransferRecordQo qo = new HJBTransferRecordQo();
		
		//转账类型
		if(transType!=null)
			qo.setType(transType);
		
		if(StringUtils.isNotBlank(recordDateBegin)){
			try {
				qo.setRecordDateBegin(sdf.parse(recordDateBegin+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setRecordDateBegin(null);
			}
		}
		
		if(StringUtils.isNotBlank(recordDateEnd)){
			try {
				qo.setRecordDateEnd(sdf.parse(recordDateEnd+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setRecordDateEnd(null);
			}
		}
		
		//是否有返回结果
		if(StringUtils.isNotBlank(hasResponse)){
			if("1".equals(hasResponse))
				qo.setHasResponse(true);
			
			if("0".equals(hasResponse))
				qo.setHasResponse(false);
		}
		
		//是否成功
		if(StringUtils.isNotBlank(status)){
			qo.setStatus(status);
		}
		
		//付款方ID
		if(StringUtils.isNotBlank(payCstNo)){
			qo.setPayCstNo(payCstNo);
			qo.setPayCstNoLike(true);
		}
		
		//收款方ID
		if(StringUtils.isNotBlank(rcvCstNo)){
			qo.setRcvCstNo(rcvCstNo);
			qo.setRcvCstNoLike(true);
		}
		
		//HJB企业用户操作员ID
		if(StringUtils.isNotBlank(userId)){
			qo.setUserId(userId);
		}
		
		//HJB平台交易订单号
		if(StringUtils.isNotBlank(hjbOrderNo)){
			qo.setHjbOrderNo(hjbOrderNo);
		}
		
		//错误代码
		if(StringUtils.isNotBlank(errorCode)){
			qo.setErrorCode(errorCode);
		}
		
		//降序排列
		qo.setRecordDateOrder(-1);
		
		if(pageNum!=null){
			pagination.setPageNo(pageNum);
		}else{
			pagination.setPageNo(1);
		}
		
		if(pageSize!=null){
			pagination.setPageSize(pageSize);
		}else{
			pagination.setPageSize(20);
		}
		
		pagination.setCondition(qo);
//		pagination = this.hjbTransferRecordService.queryPagination(pagination);
		
		ModelAndView mav = new ModelAndView("/report/hjbtrans/list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("transType", transType);
		mav.addObject("recordDateBegin", recordDateBegin);
		mav.addObject("recordDateEnd", recordDateEnd);
		mav.addObject("hasResponse", hasResponse);
		mav.addObject("status", status);
		mav.addObject("payCstNo", payCstNo);
		mav.addObject("rcvCstNo", rcvCstNo);
		mav.addObject("userId", userId);
		mav.addObject("hjbOrderNo", hjbOrderNo);
		mav.addObject("errorCode", errorCode);
		return mav;
	}
	
}
