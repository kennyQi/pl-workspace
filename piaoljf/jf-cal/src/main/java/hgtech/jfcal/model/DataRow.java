package hgtech.jfcal.model;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @类功能说明：以map方式存储的数据行
 * @类修改者：
 * @修改日期：2014年11月4日上午11:12:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年11月4日上午11:12:24
 *
 */
public class DataRow implements Serializable{
	public Map<String, Object> row = new HashMap<String, Object>();
	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Object get(Object key) {
		return row.get(key);
	}
	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(String key, Object value) {
		return row.put(key, value);
	}
	String[] colnames;
	Class[] coltypes;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return row.toString();
	}
}
