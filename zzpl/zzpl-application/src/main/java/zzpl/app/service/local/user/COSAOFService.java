package zzpl.app.service.local.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.COSAOFDAO;
import zzpl.app.dao.user.CompanyDAO;
import zzpl.app.dao.user.CostCenterDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.service.local.convert.CosaofConvert;
import zzpl.domain.model.user.COSAOF;
import zzpl.pojo.command.user.SettingCosaofCommand;
import zzpl.pojo.dto.jp.status.COSAOFStatus;
import zzpl.pojo.dto.user.CosaofDTO;
import zzpl.pojo.qo.user.COSAOFQO;
import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;

@Service
@Transactional
public class COSAOFService extends BaseServiceImpl<COSAOF, COSAOFQO, COSAOFDAO> {

	@Autowired
	private COSAOFDAO cosaofDAO;
	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private CostCenterDAO costCenterDAO;
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * @Title: setting 
	 * @author guok
	 * @Description: 结算
	 * @Time 2015年8月13日上午11:34:58
	 * @param command void 设定文件
	 * @throws
	 */
	public void setting(SettingCosaofCommand command) {
		COSAOF cosaof = cosaofDAO.get(command.getId());
		cosaof.setCosaofStatus(COSAOFStatus.SETTLED);
		cosaofDAO.update(cosaof);
	}
	
	/**
	 * @Title: getList 
	 * @author guok
	 * @Description: 结算列表转换
	 * @Time 2015年8月13日下午5:45:39
	 * @param pagination
	 * @return Pagination 设定文件
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Pagination getList(Pagination pagination) {
		Pagination pagination2 = cosaofDAO.queryPagination(pagination);
		pagination2.setList(CosaofConvert.getList((List<COSAOF>) pagination2.getList(),flightOrderDAO,passengerDAO,costCenterDAO,companyDAO,userDAO));
		return pagination2;
	}
	
	/**
	 * @Title: getCosaofDTOs 
	 * @author guok
	 * @Description: 查询列表
	 * @Time 2015年9月23日上午11:26:23
	 * @param cosaofQO
	 * @return List<CosaofDTO> 设定文件
	 * @throws
	 */
	public List<CosaofDTO> getCosaofDTOs(COSAOFQO cosaofQO) {
		List<COSAOF> cosaofs = cosaofDAO.queryList(cosaofQO);
		List<CosaofDTO> cosaofDTOs = CosaofConvert.getList(cosaofs,flightOrderDAO,passengerDAO,costCenterDAO,companyDAO,userDAO);
		return cosaofDTOs;
	}

	@Override
	protected COSAOFDAO getDao() {
		return cosaofDAO;
	}

}
