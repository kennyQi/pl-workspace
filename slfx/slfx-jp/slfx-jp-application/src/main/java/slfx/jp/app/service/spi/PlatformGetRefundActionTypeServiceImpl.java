package slfx.jp.app.service.spi;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.app.service.local.PlatformGetRefundActionTypeLocalService;
import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypesDTO;
import slfx.jp.spi.inter.PlatformGetRefundActionTypeService;

/**
 * 
 * @类功能说明：平台退废票类型SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:48:07
 * @版本：V1.0
 *
 */
@Service("jpPlatformGetRefundActionTypeService")
public class PlatformGetRefundActionTypeServiceImpl implements PlatformGetRefundActionTypeService {

	@Autowired
	private PlatformGetRefundActionTypeLocalService platformGetRefundActionTypeLocalService;
	@Override
	public YGRefundActionTypesDTO getRefundActionType(final String platCode) {
		YGRefundActionTypesDTO platformDto
			= platformGetRefundActionTypeLocalService.getRefundActionType(platCode);
		YGRefundActionTypesDTO apoDto = new YGRefundActionTypesDTO();
		BeanUtils.copyProperties(platformDto, apoDto);
		return apoDto;
	}

	@Override
	public YGRefundActionTypesDTO getAdminRefundActionType(
			String platCode) {
		YGRefundActionTypesDTO platformDto = platformGetRefundActionTypeLocalService
				.getRefundActionType(platCode);
		return platformDto;
	}

}
