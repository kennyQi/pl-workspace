package pay.record.app.service.spi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pay.record.app.service.base.BasePayRecordSpiServiceImpl;
import pay.record.app.service.local.pay.LinePayRecordLocalService;
import pay.record.pojo.command.ModifyLinePayReocrdSpiCommand;
import pay.record.pojo.command.line.CreateLineUTJPayReocrdSpiCommand;
import pay.record.pojo.dto.LinePayRecordDTO;
import pay.record.pojo.qo.pay.LinePayRecordQO;
import pay.record.spi.inter.pay.LinePayRecordService;

@Service("linePayRecordService")
public class LinePayRecordServiceImpl extends BasePayRecordSpiServiceImpl<LinePayRecordDTO, LinePayRecordQO, LinePayRecordLocalService>  implements LinePayRecordService{
	@Autowired
	LinePayRecordLocalService lineRecordLocalService;
	
	@Override
	protected LinePayRecordLocalService getService() {
		return lineRecordLocalService;
	}

	@Override
	protected Class<LinePayRecordDTO> getDTOClass() {
		return LinePayRecordDTO.class;
	}

}
