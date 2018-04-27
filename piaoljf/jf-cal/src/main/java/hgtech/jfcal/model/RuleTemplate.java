/**
 * @文件名称：RuleTemplate.java
 * @类路径：hgtech.jfcal.model
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月13日下午2:48:05
 */
package hgtech.jfcal.model;

import hgtech.jf.entity.Entity;
import hgtech.jf.entity.StringUK;

/**
 * @类功能说明：模版编码（class名字）、说明、类文件名，源代码文件名
 * @类修改者：
 * @修改日期：2014年10月13日下午2:48:05
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月13日下午2:48:05
 *
 */
public class RuleTemplate implements Entity<StringUK>{
	public String	code, name;
	/**
	 * 根据code计算出的成员变量
	 */
	public String clazzFile, srcFile;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the clazz
	 */
	public String getClazzFile() {
		return clazzFile;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setClazzFile(String clazz) {
		this.clazzFile = clazz;
	}

	/**
	 * @return the src
	 */
	public String getSrcFile() {
		return srcFile;
	}

	/**
	 * @param src the src to set
	 */
	public void setSrcFile(String src) {
		this.srcFile = src;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#getUK()
	 */
	@Override
	public StringUK readUK() {
		return new StringUK(code);
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#setUK(java.lang.Object)
	 */
	@Override
	public void putUK(StringUK uk) {
		code=uk.getS();
		
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#syncTransentPersistence()
	 */
	@Override
	public void syncUK() {
		// TODO Auto-generated method stub
		
	}
}
