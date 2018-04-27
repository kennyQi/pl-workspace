package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.fx.domain.rebate.RebateInterval;
import hg.fx.spi.qo.RebateIntervalSQO;

public interface RebateIntervalSPI extends BaseServiceProviderInterface{

	public RebateInterval queryUnique(RebateIntervalSQO sqo);
}
