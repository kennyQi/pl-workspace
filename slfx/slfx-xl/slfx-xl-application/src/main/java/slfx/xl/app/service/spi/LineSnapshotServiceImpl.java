package slfx.xl.app.service.spi;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.xl.app.component.base.BaseXlSpiServiceImpl;
import slfx.xl.app.service.local.LineLocalService;
import slfx.xl.app.service.local.LineSnapshotLocalService;
import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.dto.line.LineSnapshotDTO;
import slfx.xl.pojo.qo.LineSnapshotQO;
import slfx.xl.spi.inter.LineSnapshotService;
/**
 * 
 * @类功能说明：线路快照Service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年2月4日下午5:08:53
 * @版本：V1.0
 *
 */
@Service("lineSnapshotService")
public class LineSnapshotServiceImpl extends BaseXlSpiServiceImpl<LineSnapshotDTO,LineSnapshotQO,LineSnapshotLocalService> implements LineSnapshotService {
	@Resource
	private LineSnapshotLocalService lineSnapshotLocalService;
	@Autowired
	private LineLocalService lineLocalService;
	
	@Override
	public void createLineSnapshot(LineDTO lineDTO) {
			lineSnapshotLocalService.save(lineDTO);
	}

	@Override
	protected LineSnapshotLocalService getService() {
		return lineSnapshotLocalService;
	}

	@Override
	protected Class<LineSnapshotDTO> getDTOClass() {
		return LineSnapshotDTO.class;
	}

}
