package plfx.xl.pojo.command.line;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：创建图片文件夹
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月18日上午9:59:23
 * @版本：V1.0
 */
public class CreateLinePictureFolderCommand extends BaseCommand{ 
	private static final long serialVersionUID = 1L;
 
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 是否主文件夹
	 */
	private boolean matter = false;
	
	/**
	 * 线路Id
	 */
	private String lineId;
	
	public CreateLinePictureFolderCommand(){
		super();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public boolean isMatter() {
		return matter;
	}
	public void setMatter(boolean matter) {
		this.matter = matter;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId == null ? null : lineId.trim();
	}
}