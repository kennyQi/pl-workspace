package hg.system.qo;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：每个ImageSpec对应fastdfs中的一个文件
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月15日下午2:02:34
 * 
 */
@SuppressWarnings("serial")
public class ImageSpecQo extends BaseQo {

	/**
	 * 所属图片
	 */
	private ImageQo imageQo;

	/**
	 * 该规格图片在同一张图中的规格key
	 */
	private String key;

	/**
	 * FdfsFileInfo JSON
	 */
	private String fileInfo;
	
	/**
	 * md5值
	 */
	private String md5;

	
	public ImageQo getImageQo() {
		return imageQo;
	}

	public void setImageQo(ImageQo imageQo) {
		this.imageQo = imageQo;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = "\"md5\":\"" + md5 + "\"";
	}

}