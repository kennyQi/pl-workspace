package plfx.yxgjclient.pojo.response;

import plfx.yxgjclient.pojo.param.MatchPoliciesBySegInfoResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 航段获取政策matchPoliciesBySegInfo结果实体
 * @author guotx
 * 2015-06-30
 */
@XStreamAlias("matchPoliciesBySegInfoRS")
public class MatchPoliciesBySegInfoRS extends BaseResult{
	private MatchPoliciesBySegInfoResult matchPoliciesBySegInfoResult;

	public MatchPoliciesBySegInfoResult getMatchPoliciesBySegInfoResult() {
		return matchPoliciesBySegInfoResult;
	}

	public void setMatchPoliciesBySegInfoResult(
			MatchPoliciesBySegInfoResult matchPoliciesBySegInfoResult) {
		this.matchPoliciesBySegInfoResult = matchPoliciesBySegInfoResult;
	}

}
