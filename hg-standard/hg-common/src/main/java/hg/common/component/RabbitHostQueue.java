package hg.common.component;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.amqp.core.Queue;

/**
 * @类功能说明：使用队列名+_+ip作为实际队列名
 * @类修改者：
 * @修改日期：2014-10-21上午11:11:14
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-21上午11:11:14
 */
public class RabbitHostQueue extends Queue {

	public RabbitHostQueue(String name) throws UnknownHostException {
		super(name + "_" + InetAddress.getLocalHost().getHostAddress());
	}

}
