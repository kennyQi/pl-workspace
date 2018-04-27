package lxs.admin.common;
import java.io.IOException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.TemplateException;
/**
 * @类功能说明：freemarker配置类
 * @类修改者：zzb
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzb
 * @创建时间：2015年1月16日下午2:47:54
 * @版本：V1.0
 */
public class ShiroTagFreeMarkerConfigurer extends FreeMarkerConfigurer {
	@Override
	public void afterPropertiesSet() throws IOException, TemplateException {
		super.afterPropertiesSet();
		// 增加 shiro标签
		this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
	}

}
