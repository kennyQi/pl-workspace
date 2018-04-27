package hg.pojo.exception;

@SuppressWarnings("serial")
public class ProductException extends BaseException {

	public ProductException (Integer code, String message){
		super(code,message);
	}

	/**
	 * 品牌中文名称重复
	 */
	public final static Integer RESLUT_BRAND_NAME_REPEAT=1;
	
	/**
	 * 品牌已使用
	 */
	public final static Integer RESLUT_BRAND_USE=2;
	
	/**
	 * 分类名称重复
	 */
	public final static Integer RESLUT_CATEGORY_NAME_REPEAT=3;
	
	/**
	 * 分类已使用
	 */
	public final static Integer RESLUT_CATEGORY_USE=4;
	
	/**
	 * 分类含有规格,非叶子节点
	 */
	public final static Integer RESLUT_CATEGORY_HAVING_SPECFICATION=5;
	
	/**
	 * 分类上级节点错误
	 */
	public final static Integer RESLUT_CATEGORY_PARENT_CATEGORY_WRONG=6;
	
	/**
	 * 规格值列表为空
	 */
	public final static Integer RESLUT_SPECVALUELIST_EMPTY=7;
	
	/**
	 * 规格名称重复
	 */
	public final static Integer RESLUT_SPECIFICATION_NAME_REPEAT=8;
	
	/**
	 * 规格已使用
	 */
	public final static Integer RESLUT_SPECIFICATION_USE=9;
	
	
	/*
	 * 单位名称已存在
	 */
	public final static Integer UNIT_NAME_EXIST=10;
}
