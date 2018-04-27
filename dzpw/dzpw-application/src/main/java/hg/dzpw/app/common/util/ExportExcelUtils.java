package hg.dzpw.app.common.util;

import java.io.File;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @类功能说明：查询结果导出EXCEL工具
 * @类修改者：
 * @修改日期：2014-11-17下午2:45:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-17下午2:45:52
 */
public class ExportExcelUtils {
	
	/**
	 * @throws Exception 
	 * @方法功能说明：创建表格
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-17下午5:15:09
	 * @修改内容：
	 * @参数：@param header			标题
	 * @参数：@param title			列描述
	 * @参数：@param body				主体
	 * @参数：@param out				输出路径
	 * @参数：@return
	 * @return:File					创建的表格文件
	 * @throws
	 */
	public static File createExcelFile(String header, String[] title, String[][] body, String out) throws Exception {
		
		// 检查文件路径
		File outFile = new File(out);
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}
		
		try {
			// 创建表格
			WritableWorkbook book = Workbook.createWorkbook(outFile);
			WritableSheet sheet = book.createSheet("导出", 0);
			
			// 设置列宽度
			int colNum = title.length;
			for (int i = 0; i < colNum; i++) {
				sheet.setColumnView(i, 20);
			}
			
			// 去掉整个sheet中的网格线
			// sheet.getSettings().setShowGridLines(false);

			// 合并单元格
			sheet.mergeCells(0, 0, colNum - 1, 0);

			Label label = new Label(0, 0, header, getHeaderCellStyle());
			sheet.addCell(label);

			Label tempLabel = null;
			// 表头输出
			int titleLen = title.length;
			// 循环写入表头内容
			for (int i = 0; i < titleLen; i++) {
				tempLabel = new Label(0 + i, 1, title[i], getBodyCellStyle());
				sheet.addCell(tempLabel);
			}
			// 表体输出
			int bodyLen = body.length;
			// 循环写入表体内容
			for (int j = 0; j < bodyLen; j++) {
				String[] bodyTempArr = body[j];
				for (int k = 0; k < bodyTempArr.length; k++) {
					WritableCellFormat tempCellFormat = null;
					tempCellFormat = getBodyCellStyle();
					tempLabel = new Label(0 + k, 2 + j, bodyTempArr[k],
							tempCellFormat);
					sheet.addCell(tempLabel);
				}
			}
			book.write();
			book.close();
		} catch (Exception e) {
			throw e;
		}

		return outFile;

	}

	/**
	 * @方法功能说明：表头单元格样式的设定
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-17下午5:16:36
	 * @修改内容：
	 * @参数：@return
	 * @return:WritableCellFormat
	 * @throws
	 */
	private static WritableCellFormat getHeaderCellStyle() {

		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 20,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat headerFormat = new WritableCellFormat(
				NumberFormats.TEXT);
		
		try {
			headerFormat.setFont(font);
			headerFormat.setBackground(Colour.YELLOW);
			headerFormat.setBorder(Border.NONE, BorderLineStyle.THIN,
					Colour.BLACK);
			headerFormat.setAlignment(Alignment.CENTRE);
		} catch (WriteException e) {
			System.err.println("表头单元格样式设置失败！");
		}
		
		return headerFormat;
	}

	/**
	 * @方法功能说明：主体单元格样式的设定
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-17下午5:16:52
	 * @修改内容：
	 * @参数：@return
	 * @return:WritableCellFormat
	 * @throws
	 */
	private static WritableCellFormat getBodyCellStyle() {

		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 12,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat bodyFormat = new WritableCellFormat(font);
		
		try {
			bodyFormat.setBackground(Colour.WHITE);
			bodyFormat
					.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			bodyFormat.setAlignment(Alignment.LEFT);

		} catch (WriteException e) {
			System.err.println("表体单元格样式设置失败！");
		}
		
		return bodyFormat;
	}
	
	public static void main(String[] args) throws Exception {
		
		createExcelFile("支付对账单导出", new String[] { "编号", "票号", "联票名称", "下单时间",
				"付款时间", "应付金额（￥）", "实付金额（￥）", "景区名称", "实际结算金额（￥）" },
				new String[][] {
						{ "1", "lp13515131321", "五岳联票", "2014-12-14 12:12:12",
								"2014-12-14 12:30:12", "1000.00", "800.00",
								"嵩山", "400.00" },
						{ "2", "lp13515131322", "五岳联票", "2014-12-14 12:12:12",
								"2014-12-14 12:30:12", "1000.00", "800.00",
								"嵩山", "400.00" } },
				"C:\\Users\\Administrator\\Desktop\\test\\test3.xls");
	}

	/**
	 * 销售统计的报表
	 * @author CaiHuan
	 * @param header
	 * @param title
	 * @param body
	 * @param out
	 * @return
	 * @throws Exception
	 */
public static File createMyExcel(String header, String[] title, String[][] body, String out,String navTab) throws Exception {
		
		// 检查文件路径
		File outFile = new File(out);
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}
		
		try {
			// 创建表格
			WritableWorkbook book = Workbook.createWorkbook(outFile);
			WritableSheet sheet = book.createSheet("导出", 0);
			
			// 设置列宽度
			int colNum = title.length;
			for (int i = 0; i < colNum; i++) {
				sheet.setColumnView(i, 20);
			}
			
			// 去掉整个sheet中的网格线
			// sheet.getSettings().setShowGridLines(false);

			// 合并单元格
			sheet.mergeCells(0, 0, colNum - 1, 0);
			
			//第二栏
			Label navTabLabel = new Label(0,1,navTab,getBodyCellStyle());
			sheet.mergeCells(0, 1, colNum - 1, 1);
			sheet.addCell(navTabLabel);
			Label label = new Label(0, 0, header, getHeaderCellStyle());
			sheet.addCell(label);

			Label tempLabel = null;
			// 表头输出
			int titleLen = title.length;
			// 循环写入表头内容
			for (int i = 0; i < titleLen; i++) {
				tempLabel = new Label(0 + i, 2, title[i], getBodyCellStyle());
				sheet.addCell(tempLabel);
			}
			// 表体输出
			int bodyLen = body.length;
			// 循环写入表体内容
			for (int j = 0; j < bodyLen; j++) {
				String[] bodyTempArr = body[j];
				for (int k = 0; k < bodyTempArr.length; k++) {
					WritableCellFormat tempCellFormat = null;
					tempCellFormat = getBodyCellStyle();
					tempLabel = new Label(0 + k, 3 + j, bodyTempArr[k],
							tempCellFormat);
					sheet.addCell(tempLabel);
				}
			}
			book.write();
			book.close();
		} catch (Exception e) {
			throw e;
		}

		return outFile;

	}
}
