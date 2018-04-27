package hg.dzpw.app.service.local;

import java.net.URI;
import java.net.URISyntaxException;

import hg.common.component.BaseServiceImpl;
import hg.common.util.file.FdfsFileUtil;
import hg.dzpw.app.dao.ScenicSpotPicDao;
import hg.dzpw.app.pojo.qo.ScenicSpotPicQo;
import hg.dzpw.domain.model.scenicspot.ScenicSpotPic;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ScenicSpotPicLocalService extends BaseServiceImpl<ScenicSpotPic, ScenicSpotPicQo, ScenicSpotPicDao>{

	@Autowired
	private ScenicSpotPicDao scenicSpotPicDao;
	@Override
	protected ScenicSpotPicDao getDao() {
		return scenicSpotPicDao;
	}
/**
 * 
 * @描述： 删除远程fastdfs文件
 * @author: guotx 
 * @version: 2015-12-11 下午4:38:37
 */
	public boolean deleteRemotePic(String picUrl){
		URI uri=null;
		try {
			uri = new URI(picUrl);
		} catch (URISyntaxException e) {
			HgLogger.getInstance().error("gtx", "删除fastdfs图片路径错误");
//			e.printStackTrace();
		}
		String path=uri.getPath();
		int end=path.indexOf('/', 1);
		String groupName=path.substring(1,end);
		String remoteFileName=path.substring(groupName.length()+2);
		FdfsFileUtil.init();
		int returnCode=FdfsFileUtil.deleteFile(groupName, remoteFileName);
		if (returnCode==0) {
			HgLogger.getInstance().info("guotx", "删除景区文件"+picUrl+"成功！");
			return true;
		}
		return false;
	}
}
