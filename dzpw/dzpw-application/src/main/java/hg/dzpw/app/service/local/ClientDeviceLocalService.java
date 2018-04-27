package hg.dzpw.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.dzpw.app.dao.ClientDeviceDao;
import hg.dzpw.app.pojo.qo.ClientDeviceQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.domain.model.scenicspot.ClientDevice;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ClientDeviceLocalService extends BaseServiceImpl<ClientDevice, ClientDeviceQo, ClientDeviceDao> {
	
	@Autowired
	private ClientDeviceDao dao;

	@Override
	protected ClientDeviceDao getDao() {
		return dao;
	}

	/**
	 * @方法功能说明：入园设备编号是否存在
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-25下午2:43:34
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@param scenicSpotId
	 * @参数：@return	排除scenicSpotId景区已有的设备，检查入园设备编号是否存在。
	 * @return:boolean
	 * @throws
	 */
	public boolean deviceIdExists(String id, String scenicSpotId) {
		ClientDeviceQo qo = new ClientDeviceQo();
		qo.setId(id);
		if (StringUtils.isNotBlank(scenicSpotId)) {
			ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
			scenicSpotQo.setIdNotIn(new String[] { scenicSpotId });
			qo.setScenicSpotQo(scenicSpotQo);
		}
		return getDao().queryCount(qo) > 0 ? true : false;
	}

}