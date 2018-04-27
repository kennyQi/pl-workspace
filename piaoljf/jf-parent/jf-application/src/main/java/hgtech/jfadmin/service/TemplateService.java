/**
 * @文件名称：TemplateService.java
 * @类路径：hgtech.jfaddmin.service
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月13日下午4:05:37
 */
package hgtech.jfadmin.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import hgtech.jfcal.model.RuleTemplate;


/**
 * @类功能说明：模版服务
 * @类修改者：
 * @修改日期：2014年10月13日下午4:05:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月13日下午4:05:37
 *
 */
public interface TemplateService extends BaseService {
	
	/**
	 * 
	 * @方法功能说明：save 
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月27日下午5:58:19
	 * @修改内容：
	 * @参数：@param template
	 * @参数：@param clazz
	 * @参数：@param src
	 * @参数：@throws IOException
	 * @return:void
	 * @throws
	 */
	public abstract void saveTempldate(RuleTemplate template, MultipartFile clazz, MultipartFile src)
			throws IOException;
	/**
	 * @方法功能说明：   保存模板的class，java文件
	 * @修改者名字：<a href=pengel@hgtech365.com>pengel</a>
	 * @修改时间：2014年12月19日下午4:10:15
	 * @version：
	 * @修改内容：
	 * @参数：@param dto  规则模板类
	 * @参数：@param clazz  class文件的路径
	 * @参数：@param src  java文件的路径
	 * @参数：@throws IOException
	 * @return:void
	 * @throws
	 */
	public abstract void saveTempldate(RuleTemplate dto, String clazz,
			String src)throws IOException;

	/**
	 * @方法功能说明：删除模板
	 * @修改者名字：<a href=pengel@hgtech365.com>pengel</a>
	 * @修改时间：2014年12月19日下午4:12:29
	 * @version：1.0
	 * @修改内容：
	 * @参数：@param code   模板编号
	 * @return:void
	 * @throws
	 */
	public void delete(String code);
	/**
	 * @方法功能说明：   根据模板编号得到规则模板
	 * @修改者名字：<a href=pengel@hgtech365.com>pengel</a>
	 * @修改时间：2014年12月19日下午4:13:07
	 * @version：
	 * @修改内容：
	 * @参数：@param code  模板编号
	 * @参数：@return
	 * @return:RuleTemplate 
	 * @throws
	 */
	public RuleTemplate get(String code);
	/**
	 * 
	 * @方法功能说明：根据模版类名求得 存储的文件位置
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月27日下午5:58:10
	 * @修改内容：
	 * @参数：@param template
	 * @return:void
	 * @throws
	 */
	public abstract void setClazzandSrcFilename(RuleTemplate template);

	/**
	 * @方法功能说明：  得到规则模板的路径
	 * @修改者名字：<a href=pengel@hgtech365.com>pengel</a>
	 * @修改时间：2014年12月19日下午4:14:24
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public abstract String getTemplatePath();

	
}
