package hg.fx.util;

import hg.framework.common.model.Pagination;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PageUtil {
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

