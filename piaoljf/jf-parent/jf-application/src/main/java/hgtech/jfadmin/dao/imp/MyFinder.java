/**
 * 
 */
package hgtech.jfadmin.dao.imp;

import java.util.Map;

import org.hibernate.Query;

import hg.common.component.hibernate.Finder;

/**
 * @author xinglj
 *
 */
public class MyFinder extends Finder {
	private Object[] arrayParamValues;
	/**
	 * 设置按sql语句中的？顺序，设置参数
	 * @param paras
	 * @return
	 */
	public Finder setParams(Object[] pvalues) {

		arrayParamValues = pvalues;
		return this;
	}
	
	@Override
	public Query setParamsToQuery(Query q) {
 
		if (arrayParamValues != null) {
			for (int i = 0; i < arrayParamValues.length; i++) {
				q.setParameter(i, arrayParamValues[i]);
			}
		}else
			return super.setParamsToQuery(q);
		return q;
	}
	
}
