/**
 * @文件名称：TemplateServiceImp.java
 * @类路径：hgtech.jfaddmin.service
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月13日下午4:13:34
 */
package hgtech.jfadmin.service.imp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import hg.common.page.Pagination;
import hgtech.jf.JfProperty;
import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.EntityDao;
import hgtech.jfadmin.dao.RuleDao;
import hgtech.jfadmin.hibernate.RuleHiberEntity;
import hgtech.jfadmin.service.TemplateService;
import hgtech.jfadmin.util.RuleUtil;
import hgtech.jfcal.model.RuleTemplate;

/**
 * @类功能说明：模版服务层
 * @类修改者：
 * @修改日期：2014年10月13日下午4:13:34
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月13日下午4:13:34
 *
 */
@Transactional
@Service("templateService")
public class TemplateServiceImp extends BaseServiceImp implements TemplateService{
	@Resource
	RuleDao ruleDao ;
	
	@Resource
	public void setTemplateDao(EntityDao dao){
		entityDao=dao;
	}
	
	
	/**
	 * @方法功能说明：保存模版
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月16日上午10:40:39
	 * @修改内容：
	 * @参数：@param dto
	 * @参数：@param clazz
	 * @参数：@param src
	 * @参数：@throws IOException
	 * @return:void
	 * @throws
	 */
	@Override
	public void saveTempldate(RuleTemplate template, MultipartFile clazz,
			MultipartFile src) throws IOException {
		File clazzf = new File(template.clazzFile);
		File srcf = new File(template.srcFile);
		//save .class and .java file
		setClazzandSrcFilename(template);
		File path = srcf.getParentFile();
		if(!path.exists())
			path.mkdirs();
		clazz.transferTo(clazzf);
		src.transferTo(srcf);
		
		//save object
		entityDao.refresh();
		add(template);
	}

	public void saveTempldate(RuleTemplate template,String clazz,String src  ) throws IOException {
		setClazzandSrcFilename(template);
		File clazzf = new File(template.clazzFile);
		File srcf = new File(template.srcFile);
		File clazzSrcFile=new File(clazz);
		File srcFile=new File(src);
		//save .class and .java file
		File path = srcf.getParentFile();
		if(!path.exists())
			path.mkdirs();
		FileUtils.copyFile(clazzSrcFile, clazzf);
		FileUtils.copyFile(srcFile, srcf);
//		clazz.transferTo(clazzf);
//		src.transferTo(srcf);
		
		//save object
		entityDao.refresh();
		add(template);
	}
	
	
	/**
	 * @方法功能说明：根据模版类名求得 存储的文件位置
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月16日下午2:36:09
	 * @修改内容：
	 * @参数：@param template
	 * @return:void
	 * @throws
	 */
	@Override
	public void setClazzandSrcFilename(RuleTemplate template) {
		String templatepath = getTemplatePath();
		template.clazzFile = templatepath+template.code.replace('.', '/')+".class";
		template.srcFile = templatepath+template.code.replace('.', '/')+".java";
	}


	/**
	 * @方法功能说明：模版存放位置
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月29日上午11:00:47
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@Override
	public String getTemplatePath() {
		String templatepath =(JfProperty.getProperties().getProperty("jfPath")+"/template/");
		return templatepath;
	}
	
	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.imp.BaseServiceImp#findPagination(hg.common.page.Pagination)
	 */
	@Override
	public Pagination findPagination(Pagination pagination) {
		System.out.println("call refresh");
		super.refresh();
		Pagination page = super.findPagination(pagination);
		for(Object t:page.getList()){
			RuleTemplate temp=(RuleTemplate) t;
			setClazzandSrcFilename(temp);
		}
		return page;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.TemplateService#delete(java.lang.String)
	 */
	@Override
	public void delete(String code) {
		entityDao.refresh();
		Boolean result= true;
		StringUK uk = new StringUK(code);
		RuleTemplate t=(RuleTemplate) entityDao.get(uk);
		List<RuleHiberEntity> ruleList=ruleDao.queryByTemplate(code);
		if(ruleList.size()>0){
			for(RuleHiberEntity rule:ruleList){
				if(RuleUtil.isValid(rule.getStatus(), rule.getStartDate(), rule.getEndDate())){
					result=false;
				}
			}
			if(!result){
				throw new RuntimeException("模版已被规则使用！"); //删前询问，直接删除。
			}
		}
		boolean b=new File(t.getClazzFile()).delete();
		b=new File(t.getSrcFile()).delete();
		entityDao.delete(uk);
		entityDao.flush();
	}


	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.TemplateService#get(java.lang.String)
	 */
	@Override
	public RuleTemplate get(String code) {
		entityDao.refresh();
		return (RuleTemplate) entityDao.get(new StringUK(code));
	}
}
