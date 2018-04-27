package plfx.xl.app.component;

import hg.common.component.BaseCommand;
import hg.common.component.BaseModel;
import hg.common.component.BaseQo;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import plfx.xl.pojo.exception.SlfxXlException;

/**
 * @类功能说明：Service参数判断工具类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月15日 下午10:58:26
 */
public class Assert {
	/**
	 * @方法功能说明：字符非空判断
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-16下午10:37:25
	 * @param param
	 * @param code
	 * @param name
	 * @throws SlfxXlException
	 */
	public static void notEmpty(String param,String code,String name) throws SlfxXlException {
		if(StringUtils.isBlank(param))
			throw new SlfxXlException(code,name+"不能为空");
	}
	
	/**
	 * @方法功能说明：List列表非空判断
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-16下午10:37:25
	 * @param list
	 * @param code
	 * @param name
	 * @throws SlfxXlException
	 */
	@SuppressWarnings("rawtypes")
	public static void notList(List list,String code,String name) throws SlfxXlException {
		if(null == list || list.size() < 1)
			throw new SlfxXlException(code,name+"不能为空");
	}
	
	/**
	 * @方法功能说明：指令非空判断
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-16下午10:37:25
	 * @param command
	 * @param code
	 * @param name
	 * @throws SlfxXlException
	 */
	public static void notCommand(BaseCommand command,String code,String name) throws SlfxXlException {
		if(null == command)
			throw new SlfxXlException(code,name+"不能为空");
	}
	
	/**
	 * @方法功能说明：Model非空判断
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-16下午10:40:55
	 * @param qo
	 * @param code
	 * @param name
	 * @throws SlfxXlException
	 */
	public static void notQo(BaseQo qo,String code,String name) throws SlfxXlException {
		if(null == qo)
			throw new SlfxXlException(code,name+"不能为空");
	}
	
	/**
	 * @方法功能说明：Model非空判断
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-16下午10:40:55
	 * @param model
	 * @param code
	 * @param name
	 * @throws SlfxXlException
	 */
	public static void notModel(BaseModel model,String code,String name) throws SlfxXlException {
		if(null == model)
			throw new SlfxXlException(code,name+"不存在或已删除");
	}
}