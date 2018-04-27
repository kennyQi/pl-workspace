package hg.service.image.pojo.qo;


/**
 * @类功能说明：图片规格DTO——每个ImageSpec对应FastDFS中的一个文件
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年8月15日下午2:02:34
 */
public class ImageSpecQo extends ImageBaseQo {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 图片
	 */
	private ImageQo image;

	/**
	 * 该规格图片在同一张图中的规格key
	 */
	private String key;
	
	/**
	 * 扩展参数
	 */
	private ExtArgsQo extArgs;
	
	public ImageSpecQo(){}
	public ImageSpecQo(String projectId, String envName){
		super(projectId, envName);
	}
	public ImageSpecQo(String id){
		super(id);
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key == null ? null : key.trim();
	}
	public ExtArgsQo getExtArgs() {
		return extArgs;
	}
	public void setExtArgs(ExtArgsQo extArgs) {
		this.extArgs = extArgs;
	}
	public ImageQo getImage() {
		return image;
	}
	public void setImage(ImageQo image) {
		this.image = image;
	}
}