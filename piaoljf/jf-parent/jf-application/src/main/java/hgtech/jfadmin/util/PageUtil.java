/**
 * @文件名称：Util.java
 * @类路径：hgtech.jfaddmin.util
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月13日下午5:29:15
 */
package hgtech.jfadmin.util;

import hg.common.page.Pagination;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月13日下午5:29:15
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月13日下午5:29:15
 *
 */
public class PageUtil {

	/**
	 * @方法功能说明：一个集合中的某页部分
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月13日下午5:27:43
	 * @修改内容：
	 * @参数：@param values
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public static Pagination getPage(Collection values, int pageNo, int pageSize) {
		int totalCount=values.size();
		Pagination page=new Pagination(pageNo, pageSize, totalCount);
		List<?> list=new LinkedList();
		list.addAll(values);
		int i = pageNo*pageSize;
		int toIndex=(i>values.size()?values.size():i);
		int fromIndex=(pageNo-1)*pageSize;
		
		page.setList(list.subList(fromIndex, toIndex));
		return page;
	}

}
