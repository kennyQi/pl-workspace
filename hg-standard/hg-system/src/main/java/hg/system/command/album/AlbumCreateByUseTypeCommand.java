package hg.system.command.album;

import hg.common.component.BaseCommand;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：根据useType创建相册
 * @类修改者：zzb
 * @修改日期：2014年9月29日下午5:13:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月29日下午5:13:33
 * 
 */
@SuppressWarnings("serial")
public class AlbumCreateByUseTypeCommand extends BaseCommand {

	/**
	 * useType(必传)
	 */
	private String useType;

	/**
	 * useType配置(必传)
	 */
	private Map<String, JSONObject> useTypeConfig;

	/**
	 * 替换值obj(必传)
	 */
	private JSONObject replaceMap;

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public Map<String, JSONObject> getUseTypeConfig() {
		return useTypeConfig;
	}

	public void setUseTypeConfig(Map<String, JSONObject> useTypeConfig) {
		this.useTypeConfig = useTypeConfig;
	}

	public JSONObject getReplaceMap() {
		return replaceMap;
	}

	public void setReplaceMap(JSONObject replaceMap) {
		this.replaceMap = replaceMap;
	}

}
