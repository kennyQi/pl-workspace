package hg.system.common.freemarker;

import hg.common.util.XmlEscapeUtils;
import hg.system.cache.MetaTagCacheManager;
import hg.system.model.seo.MetaTag;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("metaTagDirective")
public class SEOMetaTagDirective implements TemplateDirectiveModel {

	@Autowired
	private MetaTagCacheManager cacheManager;

	@Override
	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Object obj = env.getVariable("Request");
		MetaTag metaTag = null;
		if (obj != null && obj instanceof HttpRequestHashModel) {
			HttpRequestHashModel requestHashModel = (HttpRequestHashModel) obj;
			HttpServletRequest request = requestHashModel.getRequest();
			// 查询META标签
			metaTag = cacheManager.getMetaTag(request.getServletPath());
		}
		Writer out = env.getOut();
		// <title>title</title>
		// <meta name="keywords" content="keywords"/>
		// <meta name="description" content="description"/>
		if (metaTag != null) {
			out.write("<title>");
			out.write(XmlEscapeUtils.escape(metaTag.getTitle()));
			out.write("</title>\r\n");
			out.write("<meta name=\"keywords\" content=\"");
			out.write(XmlEscapeUtils.escape(metaTag.getKeywords()));
			out.write("\"/>\r\n");
			out.write("<meta name=\"description\" content=\"");
			out.write(XmlEscapeUtils.escape(metaTag.getDescription()));
			out.write("\"/>\r\n");
		} else {
			String title = ((SimpleScalar) params.get("title")).getAsString();
			out.write("<title>");
			if (title != null)
				out.write(XmlEscapeUtils.escape(title));
			out.write("</title>\r\n");
		}
	}

}
