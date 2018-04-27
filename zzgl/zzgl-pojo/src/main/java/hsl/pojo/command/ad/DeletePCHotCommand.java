package hsl.pojo.command.ad;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class DeletePCHotCommand extends BaseCommand{
	private String id;
	
	/**
	 * 广告id
	 */
	private String adId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}
	
}
