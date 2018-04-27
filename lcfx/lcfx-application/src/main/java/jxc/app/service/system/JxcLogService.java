package jxc.app.service.system;

import hg.common.page.Pagination;
import hg.pojo.dto.log.JxcLog;
import hg.pojo.qo.JxcLogQo;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jxc.app.dao.system.JxcLogDao;
import jxc.app.repository.JxcRepository;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.module.SimpleAbstractTypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JxcLogService {
	@Autowired
	JxcLogDao jxcLogDao;

	@Autowired
	JxcRepository jxcRepository;


	
	public Pagination queryPagination(Pagination pagination) {
		return jxcLogDao.queryPagination(pagination);
	}
	
	public HSSFWorkbook exportJxcLog(JxcLogQo qo) {
		List<JxcLog> logs = jxcLogDao.queryList(qo);
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet("操作日志");
		// 表格的字段
		String[] headers = "时间,用户名,姓名,用户类型,操作记录".split(",");
		// 属性的字段
		String[] column = "createDate,operatorAccount,operatorName,operatorType,content"
				.split(",");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		Iterator<JxcLog> it = logs.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			JxcLog t = (JxcLog) it.next();

			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				String getMethodName = "get"
						+ column[i].substring(0, 1).toUpperCase()
						+ column[i].substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,	new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});

					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (value != null) {
						String textValue;
						if(i == 0){
							textValue = sdf.format((Date)value);
						}else{
							textValue = value.toString();
						}
						cell.setCellValue(textValue);
					} else {
						cell.setCellValue("");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return workbook;
	}
}
