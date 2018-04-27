/**
 * @文件名称：FindSession.java
 * @类路径：hgtech.jfcal.rulelogic
 * @描述：TODO
 * @作者：xy
 * @时间：2014-9-4上午10:46:43
 */
package hgtech.jfcal.rulelogic;

 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @类功能说明：读取模板，查找模板中的中建数据
 * @公司名称：浙江汇购科技有限公司
 * @作者：xy
 * @创建时间：2014-12-12
 * 
 */
public class FindSession {
	
	/**
	 * 读取规则模板中的中间数据
	 * @param stream 
	 * @return map 中间数据map
	 */
	static public Map<String, String> findMiddleData(InputStream stream) {
		Map<String, String> mapData = new HashMap<String, String>();
		LineNumberReader r = null;
		try {
			r = new LineNumberReader(new InputStreamReader(stream, "utf-8"));
			while (true) {
				String lineStr = r.readLine();
				if (null == lineStr) {
					break;
				}
				int index = lineStr.indexOf("//@SESSIONREMARK@");
				// @SESSIONREMARK@countDays@签到连续天数
				if (index > 0) {
					int strlen = lineStr.length();
					String[] markTmp = lineStr.substring(index, strlen).split("@");
					mapData.put(markTmp[2], markTmp[3]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != r) {
					r.close();
				}
				if (null != stream) {
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mapData;
	}

}
