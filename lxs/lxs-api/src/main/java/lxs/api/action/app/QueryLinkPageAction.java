package lxs.api.action.app;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.response.app.QueryLinkPageResponse;
import lxs.api.v1.response.app.QueryLinkPageResponse.LinkImage;
import lxs.app.service.app.LinkPageService;
import lxs.domain.model.app.LinkPage;
import lxs.pojo.qo.app.LinkPageQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
/**
 * 启动页，引导页
 * @author jinyy
 *
 */
@Component("QueryLinkPageAction")
public class QueryLinkPageAction implements LxsAction {

	@Autowired
	private LinkPageService linkPageService;

	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev",
				"【QueryLinkPageAction】" + "进入action");

		QueryLinkPageResponse linkPageResponse = new QueryLinkPageResponse();

		List<LinkImage> linkImages = new ArrayList<QueryLinkPageResponse.LinkImage>();

		try {
			LinkPageQO linkPageQO = new LinkPageQO();

			List<LinkPage> linkPages = linkPageService.queryList(linkPageQO);

			HgLogger.getInstance().info("lxs_dev",
					"【QueryCarouselAction】【linkPages长度】" + linkPages.size());
			if (linkPages != null && linkPages.size() == 5) {
				for (LinkPage linkPage : linkPages) {
					if (linkPage.getImageType() == 1) {
						QueryLinkPageResponse.HeadImage headImage = linkPageResponse.new HeadImage();

						headImage.setHeadImageURL(SysProperties.getInstance().get("fileUploadPath")+linkPage.getImageURL());
						headImage.setCreateDate(linkPage.getCreateDate());
						linkPageResponse.setHeadImage(headImage);

					}
					if (linkPage.getImageType() == 2) {
						QueryLinkPageResponse.LinkImage linkImage = linkPageResponse.new LinkImage();
						linkImage.setSort(linkPage.getSort());
						linkImage.setLinkImageURL(SysProperties.getInstance().get("fileUploadPath")+linkPage.getImageURL());
						linkImage.setCreateDate(linkPage.getCreateDate());
						linkImages.add(linkImage);
						linkPageResponse.setLinkImages(linkImages);

					}

				}

				linkPageResponse.setMessage("查询成功");
				linkPageResponse.setResult(ApiResponse.RESULT_CODE_OK);
				HgLogger.getInstance().info("lxs_dev",
						"【QueryLinkPageAction】【长度】" + linkPages.size());
				HgLogger.getInstance().info("lxs_dev",
						"【QueryLinkPageAction】" + "查询成功");
			} else {
				QueryLinkPageResponse.HeadImage headImage = linkPageResponse.new HeadImage();
				linkPageResponse.setMessage("查询成功");
				linkPageResponse.setHeadImage(headImage);
				linkPageResponse.setLinkImages(linkImages);
				linkPageResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
				HgLogger.getInstance().info("lxs_dev",
						"【QueryLinkPageAction】" + "查询成功");
			}
		} catch (Exception e) {
			linkPageResponse.setMessage("查询失败");
			linkPageResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("lxs_dev", "【QueryLinkPageAction】"+"linkPageResponse:"+JSON.toJSONString(linkPageResponse));
		return JSON.toJSONString(linkPageResponse);
	}

}
