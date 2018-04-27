/**
 * @文件名称：AccountServiceImpl.java
 * @类路径：hgtech.jfadmin.service.imp
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年11月4日上午10:15:54
 */
package hgtech.jfadmin.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.page.Pagination;
import hgtech.jf.piaol.SetupSpiApplicationContext;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfFlow;
import hgtech.jfaccount.dao.AccountDao;
import hgtech.jfadmin.dto.TransferStatQo;
import hgtech.jfadmin.service.AccountQueryService;

/**
 * @类功能说明：账户查询Service
 * @类修改者：
 * @修改日期：2014年11月4日上午10:15:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年11月4日上午10:15:54
 *
 */
@Transactional
@Service("accountQuery")
public class AccountQueryServiceImpl implements AccountQueryService {
	
	@Resource
	AccountDao accountDao;

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.AccountService#findPagination(hg.common.page.Pagination)
	 */
	@Override
	public Pagination findPagination(Pagination pagination) {
	SetupSpiApplicationContext.init();	
		return accountDao.findPagination(pagination);
	}

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.AccountQueryService#querybyCode(java.lang.String)
	 */
	@Override
	public List<JfAccount> querybyCode(String userCode) {
	    return accountDao.querybyCode(userCode);
	}
	@Override
	public JfAccount  querybyCode(String userCode,String acctType) {
	    return accountDao.querybyCode(userCode,acctType);
	}

}
