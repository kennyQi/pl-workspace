package slfx.xl.pojo.qo;

import hg.common.component.BaseQo;

/**
 * @类功能说明：线路图片文件夹QO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月18日下午1:59:23
 * @版本：V1.0
 */
public class LinePictureQO extends BaseQo {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	private String name;
	private boolean nameLike;
	
	/**
	 * 所属的文件夹
	 */
	private LinePictureFolderQO folderQO;
	private boolean folderFetchAble = false;
	
	/**
	 * 创建时间(排序：>0 asc <0 desc)
	 */
	private int createDateSort;

	public LinePictureQO(){
		super();
	}
	public LinePictureQO(String id){
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
	public LinePictureFolderQO getFolderQO() {
		return folderQO;
	}
	public void setFolderQO(LinePictureFolderQO folderQO) {
		this.folderQO = folderQO;
	}
	public boolean isFolderFetchAble() {
		return folderFetchAble;
	}
	public void setFolderFetchAble(boolean folderFetchAble) {
		this.folderFetchAble = folderFetchAble;
	}
	public int getCreateDateSort() {
		return createDateSort;
	}
	public void setCreateDateSort(int createDateSort) {
		this.createDateSort = createDateSort;
	}
}