package lxs.app.service.app;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.Date;

import lxs.app.dao.app.LinkPageDAO;
import lxs.domain.model.app.LinkPage;
import lxs.pojo.command.app.LinkPageAddCommand;
import lxs.pojo.qo.app.LinkPageQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 
 * @类功能说明：引导页service
 * @类修改者：
 * @修改日期：2015-10-26 上午11:17:36
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：jinyy
 * @创建时间：2015-10-26 上午11:17:36
 */
@Service
@Transactional
public class LinkPageService extends
		BaseServiceImpl<LinkPage, LinkPageQO, LinkPageDAO> {

	@Autowired
	private LinkPageDAO linkPageDAO;

	@Override
	protected LinkPageDAO getDao() {
		return linkPageDAO;
	}

	/**
	 * 
	 * 
	 * @方法功能说明：添加/修改引导页
	 * @修改者名字：jinyy
	 * @修改时间：2015-10-26 上午11:26:57
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void addLinkPage(LinkPageAddCommand command, int i) {
		HgLogger.getInstance().info("jinyy", "【LinkPageService】【addLinkPage】【LinkPageAddCommand】"+JSON.toJSONString(command));

		if (command.getLinkpageid() == null) {//添加
			LinkPage linkPage = new LinkPage();
			linkPage.setId(UUIDGenerator.getUUID());
			linkPage.setImageURL(command.getImagePath().get(i));
			linkPage.setCreateDate(new Date());
			if (i == 0) {
				linkPage.setImageType(LinkPage.START_PAGE);//启动页面
			} else {
				linkPage.setImageType(LinkPage.LINK_PAGE);//引导页面
			}
			linkPage.setSort(i);
			save(linkPage);
		} else {//修改

			LinkPage linkPage = linkPageDAO.get(command.getLinkpageid().get(i));
			linkPage.setImageURL(command.getImagePath().get(i));
			linkPage.setCreateDate(new Date());
			if (i == 0) {
				linkPage.setImageType(LinkPage.START_PAGE);//启动页面
			} else {
				linkPage.setImageType(LinkPage.LINK_PAGE);//引导页面
			}
			linkPage.setSort(i);
			update(linkPage);
		}

	}

}
