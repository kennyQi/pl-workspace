package hg.common.component;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @类功能说明：日期类编辑者（用于将日期格式(yyyy-MM-dd或yyyy-MM-dd HH:mm:ss)的参数转换成Date型参数）
 * @类修改者：
 * @修改日期：2015-1-7上午10:50:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-1-7上午10:50:54
 */
public class DateParamEditor extends PropertyEditorSupport {

	private Date parseDate(String dateStr) {
		if (dateStr == null)
			return null;

		dateStr = dateStr.trim();
		SimpleDateFormat dateFormat = null;

		String datePattern = "yyyy-MM-dd";
		String dateTimePattern = "yyyy-MM-dd HH:mm:ss";

		Date date = null;

		if (dateStr.length() == datePattern.length())
			dateFormat = new SimpleDateFormat(datePattern);
		else if (dateStr.length() == dateTimePattern.length())
			dateFormat = new SimpleDateFormat(dateTimePattern);

		try {
			date = dateFormat.parse(dateStr);
		} catch (Exception e) { }

		return date;
	}

	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return (value != null ? new SimpleDateFormat("yyyy-MM-dd").format(value) : "");
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(parseDate(text));
	}
}
