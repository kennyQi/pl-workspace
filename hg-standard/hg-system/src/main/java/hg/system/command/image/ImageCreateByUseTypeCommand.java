package hg.system.command.image;

import hg.common.component.BaseCommand;
import hg.system.model.image.Image;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：根据useType创建图片附件
 * @类修改者：zzb
 * @修改日期：2014年9月29日上午9:00:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月29日上午9:00:17
 * 
 */
@SuppressWarnings("serial")
public class ImageCreateByUseTypeCommand extends BaseCommand {

	/**
	 * 创建好的image, default(必传)
	 */
	private Image image;

	/**
	 * 类型 (必传)
	 */
	private Integer useType;

	/**
	 * useType和key的对应关系(必传)
	 */
	private Map<String, JSONObject> useTypeImageKeys;

	/**
	 * 图片key(必传)
	 */
	private Map<String, JSONObject> imageSpecKey;

	/**
	 * 图片key, json(必传)
	 */
	private JSONArray imageSpecKeyJson;
	
	/**
	 * 图片静态路径
	 */
	private String imageStaticUrl;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}

	public Map<String, JSONObject> getUseTypeImageKeys() {
		return useTypeImageKeys;
	}

	public void setUseTypeImageKeys(Map<String, JSONObject> useTypeImageKeys) {
		this.useTypeImageKeys = useTypeImageKeys;
	}

	public Map<String, JSONObject> getImageSpecKey() {
		return imageSpecKey;
	}

	public void setImageSpecKey(Map<String, JSONObject> imageSpecKey) {
		this.imageSpecKey = imageSpecKey;
	}

	public JSONArray getImageSpecKeyJson() {
		return imageSpecKeyJson;
	}

	public void setImageSpecKeyJson(JSONArray imageSpecKeyJson) {
		this.imageSpecKeyJson = imageSpecKeyJson;
	}

	public String getImageStaticUrl() {
		return imageStaticUrl;
	}

	public void setImageStaticUrl(String imageStaticUrl) {
		this.imageStaticUrl = imageStaticUrl;
	}
	
}
