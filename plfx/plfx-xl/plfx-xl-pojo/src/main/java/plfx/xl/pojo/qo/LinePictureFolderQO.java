package plfx.xl.pojo.qo;

import hg.common.component.BaseQo;

/**
 * @类功能说明：线路图片文件夹QO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月18日下午1:59:23
 * @版本：V1.0
 */
public class LinePictureFolderQO extends BaseQo {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	private String name;
	private boolean nameLike;
	
	/**
	 * 是否主文件夹
	 */
	private Boolean matter;
	
	/**
	 * 所属的线路
	 */
	private LineQO lineQO;
	private boolean lineFetchAble = false;
	
	/**
	 * 创建时间(排序：>0 asc <0 desc)
	 */
	private int createDateSort;

	public LinePictureFolderQO(){
		super();
	}
	public LinePictureFolderQO(String id){
		super();
		setId(id);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public boolean isNameLike() {
		return nameLike;
	}
	public void setNameLike(boolean nameLike) {
		this.nameLike = nameLike;
	}
	public Boolean isMatter() {
		return matter;
	}
	public void setMatter(Boolean matter) {
		this.matter = matter;
	}
	public LineQO getLineQO() {
		return lineQO;
	}
	public void setLineQO(LineQO lineQO) {
		this.lineQO = lineQO;
	}
	public boolean isLineFetchAble() {
		return lineFetchAble;
	}
	public void setLineFetchAble(boolean lineFetchAble) {
		this.lineFetchAble = lineFetchAble;
	}
	public int getCreateDateSort() {
		return createDateSort;
	}
	public void setCreateDateSort(int createDateSort) {
		this.createDateSort = createDateSort;
	}
}