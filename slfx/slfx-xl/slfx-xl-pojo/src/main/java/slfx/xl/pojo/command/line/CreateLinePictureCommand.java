package slfx.xl.pojo.command.line;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：创建线路图片
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月18日上午9:59:23
 * @版本：V1.0
 */
public class CreateLinePictureCommand extends BaseCommand{ 
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 位置(图片访问地址)
	 */
	private String site;
	
	/**
	 * 文件夹Id
	 */
	private String folderId;
	
	public CreateLinePictureCommand(){
		super();
	}
	public CreateLinePictureCommand(String name,String site,String folderId) {
		super();
		setName(name);
		setSite(site);
		setFolderId(folderId);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site == null ? null : site.trim();
	}
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId == null ? null : folderId.trim();
	}
}