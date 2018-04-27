package hg.fx.util;


import hg.framework.common.model.Pagination;
import hg.fx.command.mileOrder.CreateMileOrderCommand;
import hg.fx.command.mileOrder.ImportMileOrderCommand;
import hg.fx.domain.MileOrder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class MileOrderServiceUtil   {
	 
	/**
	 * 按预提交订单数量比较器
	 */
	private final static Comparator<? super CreateMileOrderCommand> ascCountComparator =new Comparator() {
		@Override
		public int compare(Object o1, Object o2) {
			CreateMileOrderCommand c1=(CreateMileOrderCommand) o1;
			CreateMileOrderCommand c2=(CreateMileOrderCommand) o2;
			final int anotherInteger = c2.getNum()*c2.getUnitPrice();
			final Integer aInteger = c1.getNum()*c1.getUnitPrice();
			return aInteger.compareTo(anotherInteger);
		}
	};

		/**
		 * 按预提交订单数量比较器,倒叙
		 */
		private final static  Comparator<? super CreateMileOrderCommand> descCountComparator =new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				CreateMileOrderCommand c1=(CreateMileOrderCommand) o1;
				CreateMileOrderCommand c2=(CreateMileOrderCommand) o2;
				final Integer anotherInteger = c2.getNum()*c2.getUnitPrice();
				final Integer aInteger = c1.getNum()*c1.getUnitPrice();
				return anotherInteger.compareTo(aInteger);
			}
		};
			
  

	/**
	 * 未提交之前的订单分页
	 * @param importMileOrderCommand
	 * @return
	 */
	public static Pagination<CreateMileOrderCommand> getPage(ImportMileOrderCommand importMileOrderCommand,Pagination<CreateMileOrderCommand> pagination) {
		
		return PageUtil.getPage(importMileOrderCommand.getList(), pagination.getPageNo(), pagination.getPageSize());
	}
	
	/**
	 * 未提交之前的订单排序，按数量
	 * @param importMileOrderCommand
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static  List<CreateMileOrderCommand> sortByCountAsc(List<CreateMileOrderCommand> commandList) {
		Collections.sort(commandList, ascCountComparator);
		return commandList;
	}	
	/**
	 * 未提交之前的订单排序，按数量倒叙
	 * @param importMileOrderCommand
	 * @return
	 */
	public static List<CreateMileOrderCommand> sortByCountDesc(List<CreateMileOrderCommand> commandList) {
		Collections.sort(commandList, descCountComparator);
		return commandList;
	}		

	/**
	 * 运营端订单列表导出
	 * @author zqq
	 * @since hgfx-admin-common
	 * @date 2016-6-6 下午4:32:29 
	 * @param list
	 * @return
	 */
	public static HSSFWorkbook exportOrder2Excel(List<MileOrder> list){
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("1");
		String[] headers = "订单编号，商品名称，渠道商，商户公司，订单生成时间，会员帐号，会员姓名，数量，单价（积分），消耗积分，审核人，审核时间，通过理由，确认人，确认时间，拒绝理由，订单状态"
				.split("，");

		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
		}

		;
		for (int i = 0; i < list.size(); i++) {
			MileOrder order = list.get(i);

			HSSFRow line = sheet.createRow(i + 1);

			line.createCell(0).setCellValue(order.getFlowCode());
			try {
				line.createCell(1).setCellValue(order.getProductName());
			} catch (Exception e) {
				line.createCell(1).setCellValue("");
			}
			try {
				line.createCell(2).setCellValue(order.getChannelName());
			} catch (Exception e) {
				line.createCell(2).setCellValue("");
			}
			try {
				line.createCell(3).setCellValue(order.getMerName());
			} catch (Exception e) {
				line.createCell(3).setCellValue("");
			}
			line.createCell(4).setCellValue(
					Tools.dateToStringForExport(order.getImportDate()));
			line.createCell(5).setCellValue(order.getCsairCard());
			line.createCell(6).setCellValue(order.getCsairName());
			line.createCell(7).setCellValue(order.getNum());
			line.createCell(8).setCellValue(order.getUnitPrice());
			line.createCell(9).setCellValue(order.getCount());
			try {//如果是订单取消状态 审核人为-- 正常订单显示系统
				if(order.getStatus() == MileOrder.STATUS_CANCEL){
					line.createCell(10).setCellValue("--");
				}else if(order.getCheckPerson()==null){
					line.createCell(10).setCellValue("系统");
				}else{
					line.createCell(10).setCellValue(order.getAduitPerson());
				}
			} catch (Exception e) {
				line.createCell(11).setCellValue("--");
			}
			if(order.getStatus() == MileOrder.STATUS_CANCEL){
				line.createCell(11).setCellValue("--");
			}else{
			line.createCell(11).setCellValue(
					Tools.dateToStringForExport(order.getCheckDate()));
			}
			line.createCell(12).setCellValue(order.getReason());
			line.createCell(13).setCellValue(order.getConfirmPerson());
			line.createCell(14).setCellValue(Tools.dateToStringForExport(order.getConfirmDate()));
			line.createCell(15).setCellValue(order.getRefuseReason());
			line.createCell(16).setCellValue(order.getStatusName());

		}
		return workbook;		
	}
	
	
	/**
	 * 商户端订单列表导出
	 * @author zqq
	 * @since hgfx-admin-common
	 * @date 2016-6-6 下午4:32:29 
	 * @param list
	 * @return
	 */
	public static HSSFWorkbook exportOrder3Excel(List<MileOrder> list){
		HSSFWorkbook workbook = new HSSFWorkbook();
		//设置表头
		HSSFSheet sheet = workbook.createSheet("1");
		String[] headers = "订单编号，商品名称，账号，姓名，数量，订单提交时间，订单提交人，订单状态"
				.split("，");

		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
		}

		;//设置内容
		for (int i = 0; i < list.size(); i++) {
			MileOrder order = list.get(i);

			HSSFRow line = sheet.createRow(i + 1);

			line.createCell(0).setCellValue(order.getFlowCode());
			try {
				line.createCell(1).setCellValue(order.getProductName());
			} catch (Exception e) {
				line.createCell(1).setCellValue("");
			}
			try {
				line.createCell(2).setCellValue(order.getCsairCard());
			} catch (Exception e) {
				line.createCell(2).setCellValue("");
			}
			try {
				line.createCell(3).setCellValue(order.getCsairName());
			} catch (Exception e) {
				line.createCell(3).setCellValue("");
			}
			line.createCell(4).setCellValue(order.getNum());
			line.createCell(5).setCellValue(
					Tools.dateToStringForExport(order.getImportDate()));
			line.createCell(6).setCellValue(order.getImportUser());
			line.createCell(7).setCellValue(order.getStatusName());
		}
		return workbook;		
	}
	
	public static void main(String[] args) {
		
		ImportMileOrderCommand imp = new ImportMileOrderCommand();
		CreateMileOrderCommand cr = new CreateMileOrderCommand();
		cr.setCsairCard("111111111");
		cr.setCsairName("100ren");
		cr.setNum(100);
		imp.getList().add(cr);
		
		cr.setCsairCard("22222211");
		cr.setCsairName("200ren");
		cr.setNum(200);
		imp.getList().add(cr);

		//分页
		Pagination<CreateMileOrderCommand> pagination = new Pagination<>();
		pagination.setPageNo(2);
		pagination.setPageSize(1);
//		System.out.println( "expect 200ren:"+ getPage(imp, pagination).getList().get(0).getCsairName() );
		
		//排序
//		System.out.println("expect 200ren "+ sortByCountDesc(imp.getList()).get(0).getCsairName());
	}
}	