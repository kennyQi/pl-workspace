package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.fx.domain.fixedprice.FixedPriceInterval;
import hg.fx.spi.qo.FixedPriceIntervalSQO;

public interface FixedPriceIntervalSPI extends BaseServiceProviderInterface{

	FixedPriceInterval queryFixedPriceInterval(FixedPriceIntervalSQO sqo);
	
	void createFixedPriceInterval(FixedPriceInterval fixedPriceInterval,String authUserID); 
	
	FixedPriceInterval queryLastFixedPriceInterval(FixedPriceIntervalSQO sqo);
}
