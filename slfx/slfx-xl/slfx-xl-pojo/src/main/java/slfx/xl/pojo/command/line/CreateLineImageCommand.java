package slfx.xl.pojo.command.line;

import hg.common.component.BaseCommand;
import hg.common.util.file.FdfsFileInfo;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：创建线路图片
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年4月24日上午10:12:01
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class CreateLineImageCommand  extends BaseCommand{ 
	/**
	 * 线路id
	 */
	private String lineId;
	/**
	 * 行程id
	 */
	private String dayRouteId;
	/**
	 * 图片名称
	 */
	private String name;
	/**
	 * 图片标识
	 */
	private String imageId;
	/**
	 * 上传图片后返回的Json串
	 */
	private String fdfsFileInfoJSON;
	/**
	 * 线路图片
	 */
	private FdfsFileInfo lineImageFileInfo;
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getDayRouteId() {
		return dayRouteId;
	}
	public void setDayRouteId(String dayRouteId) {
		this.dayRouteId = dayRouteId;
	}
	public String getFdfsFileInfoJSON() {
		return fdfsFileInfoJSON;
	}
	public void setFdfsFileInfoJSON(String fdfsFileInfoJSON) {
		this.fdfsFileInfoJSON = fdfsFileInfoJSON;
	}
	public FdfsFileInfo getLineImageFileInfo() {
		if (lineImageFileInfo == null
				&& StringUtils.isNotBlank(fdfsFileInfoJSON)) {
			setLineImageFileInfo(JSON.parseObject(fdfsFileInfoJSON,
					FdfsFileInfo.class));
		}
		return lineImageFileInfo;
	}
	public void setLineImageFileInfo(FdfsFileInfo lineImageFileInfo) {
		this.lineImageFileInfo = lineImageFileInfo;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}