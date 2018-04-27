package plfx.jp.app.service.spi.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.jp.app.service.local.pay.PayRecordLocalService;
import plfx.jp.app.service.spi.base.BaseJpSpiServiceImpl;
import plfx.jp.pojo.dto.pay.PayRecordDTO;
import plfx.jp.qo.pay.PayRecordQO;
import plfx.jp.spi.inter.pay.PayRecordService;

@Service("payRecordService")
public class PayRecordServiceImpl extends BaseJpSpiServiceImpl<PayRecordDTO, PayRecordQO, PayRecordLocalService>  implements PayRecordService{
	@Autowired
	PayRecordLocalService payRecordLocalService;
	
	@Override
	protected PayRecordLocalService getService() {
		return payRecordLocalService;
	}

	@Override
	protected Class<PayRecordDTO> getDTOClass() {
		return PayRecordDTO.class;
	}

}
