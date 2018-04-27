package hg.system.common.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 
 * @类功能说明：图片裁剪_操作属性类
 * @类修改者：zzb
 * @修改日期：2014年9月17日上午10:47:40
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月17日上午10:47:40
 * 
 */
public class ImageCutCommand {

	/**
	 * 图片url
	 */
	private String imgUrl;

	/**
	 * 左间距
	 */
	private Integer left;

	/**
	 * 上间距
	 */
	private Integer top;

	/**
	 * 裁剪宽度
	 */
	private Integer width;

	/**
	 * 裁剪高度
	 */
	private Integer height;

	/**
	 * 压缩宽度
	 */
	private String newWidth;

	/**
	 * 压缩高度
	 */
	private String newHeight;
	
	/**
	 * 
	 * @方法功能说明：是否需要裁剪
	 * @修改者名字：zzb
	 * @修改时间：2014年9月17日上午10:56:49
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean needCut() {
		
		if(getLeft() != null || getTop() != null 
				|| getWidth() != null 
				|| getHeight() != null)
			return true;
		return false;
	}

	/**
	 * 
	 * @方法功能说明：是否需要压缩
	 * @修改者名字：zzb
	 * @修改时间：2014年9月17日上午10:56:59
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean needCompre() {
		
		if(getNewHeight() != null || getNewWidth() != null)
			return true;
		return false;
	}
	
	/**
	 * 
	 * @方法功能说明：填充裁剪默认值
	 * @修改者名字：zzb
	 * @修改时间：2014年9月17日上午11:29:06
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void setCutDefalut() {
		
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new URL(getImgUrl()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int srcWidth = bi.getWidth(); 		// 源图宽度
		int srcHeight = bi.getHeight(); 	// 源图高度
		
		setLeft(getLeft() == null ? 0 : getLeft());
		setTop(getTop() == null ? 0 : getTop());
		
		setWidth(getWidth() == null ? (srcWidth - getLeft()) : getWidth());
		setHeight(getHeight() == null ? (srcHeight - getTop()) : getHeight());
	}
	
	/**
	 * 
	 * @方法功能说明：填充压缩默认值
	 * @修改者名字：zzb
	 * @修改时间：2014年9月17日下午12:00:32
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void setCompreDefalut() {
		
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new URL(getImgUrl()));
			
			int srcWidth = bi.getWidth(); 		// 源图宽度
			int srcHeight = bi.getHeight(); 	// 源图高度
			
			setNewHeight(getNewHeight() == null ? String.valueOf((int)(Double.parseDouble(getNewWidth()) 
							/ srcWidth * srcHeight)) : getNewHeight());
			setNewWidth(getNewWidth() == null ? String.valueOf((int)(Double.parseDouble(getNewHeight()) 
					/ srcHeight * srcWidth)) : getNewWidth());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getNewWidth() {
		return newWidth;
	}

	public void setNewWidth(String newWidth) {
		this.newWidth = newWidth;
	}

	public String getNewHeight() {
		return newHeight;
	}

	public void setNewHeight(String newHeight) {
		this.newHeight = newHeight;
	}

}