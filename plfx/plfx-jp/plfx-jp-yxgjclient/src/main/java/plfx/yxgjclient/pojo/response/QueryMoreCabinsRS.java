package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.pojo.param.QueryMoreCabinsResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 更多舱位queryMoreCabins实体
 * @author guotx
 * 2015-06-29
 */
@XStreamAlias("queryMoreCabinsRS")
public class QueryMoreCabinsRS extends BaseResult{
	/**
	 * 更多舱位返回列表
	 */
	private QueryMoreCabinsResult queryMoreCabinsResult;

	public QueryMoreCabinsResult getQueryMoreCabinsResult() {
		return queryMoreCabinsResult;
	}

	public void setQueryMoreCabinsResult(QueryMoreCabinsResult queryMoreCabinsResult) {
		this.queryMoreCabinsResult = queryMoreCabinsResult;
	}

	
}
