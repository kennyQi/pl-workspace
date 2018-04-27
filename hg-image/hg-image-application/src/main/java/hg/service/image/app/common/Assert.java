package hg.service.image.app.common;

import hg.common.component.BaseModel;
import hg.service.image.base.BaseCommand;
import hg.service.image.pojo.exception.ImageException;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：图片服务参数判断工具类
 * @修改者名字：chenys
 * @修改时间：2014年12月15日 下午6:22:47
 * @修改内容：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年12月15日 下午10:58:26
 */
public class Assert{
	/**
	 * @方法功能说明：环境非空判断
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-16下午10:37:25
	 * @param projectId
	 * @param envName
	 * @throws ImageException
	 */
	public static void notPorjectandEnv(String projectId,String envName) throws ImageException{
		if(StringUtils.isBlank(projectId) || StringUtils.isBlank(envName))
			throw new ImageException(ImageException.NEED_OTHERS_NOTEXIST,"项目id或者环境名称不能为空");
	}
	
	/**
	 * @方法功能说明：指令非空判断
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-16下午10:37:25
	 * @param command
	 * @param code
	 * @param name
	 * @throws ImageException
	 */
	public static void notCommand(BaseCommand command,int code,String name) throws ImageException{
		if (null == command)
			throw new ImageException(code,name+"不能为空");
	}
	
	/**
	 * @方法功能说明：字符非空判断
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-16下午10:37:25
	 * @param param
	 * @param code
	 * @param name
	 * @throws ImageException
	 */
	public static void notEmpty(String param,int code,String name) throws ImageException{
		if(StringUtils.isBlank(param))
			throw new ImageException(code,name+"不能为空");
	}
	
	/**
	 * @方法功能说明：List列表非空判断
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-16下午10:37:25
	 * @param list
	 * @param code
	 * @param name
	 * @throws ImageException
	 */
	@SuppressWarnings("rawtypes")
	public static void notList(List list,int code,String name) throws ImageException{
		if(null == list || list.size() < 1)
			throw new ImageException(code,name+"不能为空");
	}
	
	/**
	 * @方法功能说明：图片服务Model非空判断
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-16下午10:40:55
	 * @param album
	 * @throws ImageException
	 */
	public static void notNull(BaseModel model,int code,String name) throws ImageException{
		if(null == model)
			throw new ImageException(code,name+"不存在或已删除");
	}
	
}