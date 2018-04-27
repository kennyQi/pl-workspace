package hg.demo.web.controller.report;

import hg.demo.web.controller.BaseController;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.domain.Distributor;
import hg.fx.domain.Product;
import hg.fx.domain.rebate.RebateReport;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.ProductSPI;
import hg.fx.spi.RebateReportSPI;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.spi.qo.ProductSQO;
import hg.fx.spi.qo.RebateReportSQO;
import hg.fx.util.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("rebateReport")
public class RebateReportController extends BaseController{
	
	@Autowired
	private RebateReportSPI rebateReportSPIService;
	@Autowired
	private ProductSPI productSPI;
	@Autowired
	private DistributorSPI distributorSPI;
	
	@RequestMapping("/view")
	public String view(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
			@RequestParam(value = "numPerPage",defaultValue = "20")int pageSize,
			@RequestParam(value = "startDate",defaultValue = "")String startDate,
			@RequestParam(value = "endDate",defaultValue = "")String endDate,
			@RequestParam(value = "distributorID",defaultValue = "")String distributorID,
			@RequestParam(value = "prodId",defaultValue = "")String prodId){
		//查询商品
		List<Product> products = productSPI.queryProductList(new ProductSQO());
		model.addAttribute("products",products);
		model.addAttribute("productID",products.get(0).getId());
		//查询商户  启用、定价模式
		DistributorSQO distributorSQO = new DistributorSQO();
		distributorSQO.setStatus(1);
		distributorSQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_REBATE);
		List<Distributor> distributors = distributorSPI.queryList(distributorSQO);
		model.addAttribute("distributors",distributors);
		RebateReportSQO sqo = new RebateReportSQO();
		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setPageNo(pageNum);
		limitQuery.setPageSize(pageSize);
		limitQuery.setByPageNo(true);
		sqo.setLimit(limitQuery);
		if(StringUtils.isNotBlank(distributorID)){
			sqo.setDistributorId(distributorID);
		}
		if(StringUtils.isNotBlank(prodId)){
			sqo.setProdId(prodId);
		}
		if(StringUtils.isNotBlank(startDate)){
			endDate=startDate+"-01 000000";
			startDate+="-01 000000";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(simpleDateFormat.parse(startDate));
				calendar.add(Calendar.MONTH, 1);
				sqo.setStartDate(calendar.getTime());
				calendar = Calendar.getInstance();
				calendar.setTime(simpleDateFormat.parse(startDate));
				calendar.add(Calendar.MONTH, 2);
				sqo.setEndDate(calendar.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Pagination<RebateReport> pagination = rebateReportSPIService.queryPagination(sqo);
		List<RebateReport> rebateReports = pagination.getList();
		model.addAttribute("pagination", pagination);
		model.addAttribute("rebateReports", rebateReports);
		model.addAttribute("distributorID", distributorID);
		model.addAttribute("prodId", prodId);
		if(StringUtils.isNotBlank(startDate)){
			model.addAttribute("startDate", startDate.split(" ")[0].substring(0, 7));
		}
		return "rebate/rebateReport.html";
	}
	
	@RequestMapping("/exportt")
	public void export(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
			@RequestParam(value = "numPerPage",defaultValue = "9999999999")int pageSize,
			@RequestParam(value = "startDate",defaultValue = "")String startDate,
			@RequestParam(value = "endDate",defaultValue = "")String endDate,
			@RequestParam(value = "distributorID",defaultValue = "")String distributorID,
			@RequestParam(value = "prodId",defaultValue = "")String prodId){
		
		RebateReportSQO sqo = new RebateReportSQO();
		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setPageNo(pageNum);
		limitQuery.setPageSize(9999999);
		limitQuery.setByPageNo(true);
		sqo.setLimit(limitQuery);
		if(StringUtils.isNotBlank(distributorID)){
			sqo.setDistributorId(distributorID);
		}
		if(StringUtils.isNotBlank(prodId)){
			sqo.setProdId(prodId);
		}
		if(StringUtils.isNotBlank(startDate)){
			endDate=startDate+"-01 000000";
			startDate+="-01 000000";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(simpleDateFormat.parse(startDate));
				calendar.add(Calendar.MONTH, 1);
				sqo.setStartDate(calendar.getTime());
				calendar = Calendar.getInstance();
				calendar.setTime(simpleDateFormat.parse(startDate));
				calendar.add(Calendar.MONTH, 2);
				sqo.setEndDate(calendar.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Pagination<RebateReport> pagination = rebateReportSPIService.queryPagination(sqo);
		List<RebateReport> list = pagination.getList();
			HSSFWorkbook excel = exportOrder2Excel(list);
			try {
				String path = this.getClass().getResource("/").toString().replace("file:", "");
				int end = path.length() - "WEB-INF/classes/".length();  
			    path = path.substring(0, end)+File.separator+"excel";
			    String isoName = "\u8FD4\u5229\u5546\u6237\u6708\u62A5";
			    String fileName = path+ File.separator+"123123123"+DateUtil.formatDateTime3(new Date())+".xls";
				outputExcel(excel, response,path,fileName);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	static HSSFWorkbook exportOrder2Excel(List<RebateReport> list){
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("1");
		String[] headers = "商户ID，商户公司名称，渠道商，商品名称，月消费里程数，折扣比例，返利数量"
				.split("，");

		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
		}
		for (int i = 0; i < list.size(); i++) {
			RebateReport rebateReport = list.get(i);

			HSSFRow line = sheet.createRow(i + 1);

			try {
				line.createCell(0).setCellValue(rebateReport.getDistributorId());
			} catch (Exception e) {
				line.createCell(0).setCellValue("");
			}
			try {
				line.createCell(1).setCellValue(rebateReport.getDistributorName());
			} catch (Exception e) {
				line.createCell(1).setCellValue("");
			}
			try {
				line.createCell(2).setCellValue(rebateReport.getChannelName());
			} catch (Exception e) {
				line.createCell(2).setCellValue("");
			}
			try {
				line.createCell(3).setCellValue(rebateReport.getProdName());
			} catch (Exception e) {
				line.createCell(3).setCellValue("");
			}
			try {
				line.createCell(4).setCellValue(rebateReport.getConsumeNum());
			} catch (Exception e) {
				line.createCell(4).setCellValue("");
			}
			try {
				line.createCell(5).setCellValue(rebateReport.getPercent());
			} catch (Exception e) {
				line.createCell(5).setCellValue("");
			}
			try {
				line.createCell(6).setCellValue(rebateReport.getRebateNum());
			} catch (Exception e) {
				line.createCell(6).setCellValue("");
			}
		}
		return workbook;		
	}
	
	/**
	  * excel导出
	  * @author zqq
	  * @since hgfx-admin-web
	  * @date 2016-6-3 上午9:50:48 
	  * @param wb
	  * @param response
	  * @throws IOException
	  */
	 public void outputExcel(HSSFWorkbook wb, HttpServletResponse response,String path,String fileName) throws IOException {
			InputStream inStream = null;
			try {
				// 创建指定位置目录
			    File f = new File(path);
			    if(!f.exists()){
			    	f.mkdir();
			    }
			    //将文件写在本地
				FileOutputStream fout = new FileOutputStream(fileName);

				wb.write(fout);
				fout.close();
				// 将文件输入并下载 读到流中
				inStream = new FileInputStream(fileName); // 文件的存放路径
				// 设置输出的格式
				String name = "返利商户月报"+ DateUtil.formatDate1(new Date()) ;
				response.reset();
				String isoName = parseGBK(name);
				response.setContentType("application/x-msdownload MSEXCEL");
				response.setHeader("Content-Disposition", "attachment;filename=\""
						+ isoName+ ".xls" + "\"");
				// 循环取出流中的数据
				byte[] b = new byte[100];
				int len;
				while ((len = inStream.read(b)) > 0) {
					response.getOutputStream().write(b, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
	public static String parseGBK(String sIn) {
		if (sIn == null || sIn.equals(""))
			return sIn;
		try {
			return new String(sIn.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException usex) {
			return sIn;
		}
	}

}
