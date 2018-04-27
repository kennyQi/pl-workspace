package plfx.yxgjclient.pojo.request;


import plfx.yxgjclient.pojo.param.MatchPoliciesBySegInfoParams;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 航段获取政策matchPoliciesBySegInfo查询实体
 * 
 * @author guotx 2015-06-30
 */
@XStreamAlias("matchPoliciesBySegInfoRQ")
public class MatchPoliciesBySegInfoRQ {
	public MatchPoliciesBySegInfoRQ(){
		matchPoliciesBySegInfoParams=new MatchPoliciesBySegInfoParams();
	}
	private String sign;
	private MatchPoliciesBySegInfoParams matchPoliciesBySegInfoParams;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public MatchPoliciesBySegInfoParams getMatchPoliciesBySegInfoParams() {
		return matchPoliciesBySegInfoParams;
	}

	public void setMatchPoliciesBySegInfoParams(
			MatchPoliciesBySegInfoParams matchPoliciesBySegInfoParams) {
		this.matchPoliciesBySegInfoParams = matchPoliciesBySegInfoParams;
	}
}
