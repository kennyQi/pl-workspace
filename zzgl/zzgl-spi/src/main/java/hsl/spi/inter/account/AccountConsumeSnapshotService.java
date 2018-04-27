package hsl.spi.inter.account;

import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;
import hsl.spi.inter.BaseSpiService;

public interface AccountConsumeSnapshotService  extends BaseSpiService<AccountConsumeSnapshotDTO,AccountConsumeSnapshotQO>{
public void deleteById(String id);
	/**
	 * 修改订单快照
	 * @param ids
	 * @return
	 */
public String update(String ids);
}
