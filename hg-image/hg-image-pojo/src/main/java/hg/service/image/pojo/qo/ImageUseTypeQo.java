package hg.service.image.pojo.qo;

/**
 * @类功能说明：创建图片用途规格集合
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午4:27:20
 */
public class ImageUseTypeQo extends ImageBaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 用途名称
	 */
	private String title;
	private boolean titleLike;

	/**
	 * 图片规格KEY用途 使用JSON保存
	 */
	private String imageSpecKeysJson;

	public ImageUseTypeQo(){}
	public ImageUseTypeQo(String projectId, String envName){
		super(projectId, envName);
	}
	public ImageUseTypeQo(String id){
		super(id);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}
	public String getImageSpecKeysJson() {
		return imageSpecKeysJson;
	}
	public void setImageSpecKeysJson(String imageSpecKeysJson) {
		this.imageSpecKeysJson = imageSpecKeysJson == null ? null : imageSpecKeysJson.trim();
	}
	public boolean isTitleLike() {
		return titleLike;
	}
	public void setTitleLike(boolean titleLike) {
		this.titleLike = titleLike;
	}
}