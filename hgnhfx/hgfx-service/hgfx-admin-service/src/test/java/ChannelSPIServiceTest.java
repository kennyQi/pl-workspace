import java.util.List;

import hg.framework.common.model.LimitQuery;
import hg.fx.domain.Channel;
import hg.fx.spi.ChannelSPI;
import hg.fx.spi.qo.ChannelSQO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 下午4:22:00
 * @版本： V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class ChannelSPIServiceTest {

	@Autowired
	private ChannelSPI channelSPI;

	@Test
	public void testQueryUnique() {
		ChannelSQO sqo = new ChannelSQO();
		sqo.setId("9d211334b4b148edb9a3eca20aaaac09");
		//sqo.setNeedProducts(true);//需要则传true，否则可不设置

		Channel channel = channelSPI.queryUnique(sqo);
//		System.out.println(JSON.toJSONString(channel));
	}

	//@Test
	public void testQueryList() {
		ChannelSQO sqo = new ChannelSQO();
		sqo.setChannelName("南航_测试");

		List<Channel> list = channelSPI.queryList(sqo);
		for (Channel channel : list) {
//			System.out.println(JSON.toJSONString(channel));
		}
	}

	//@Test
	public void testQueryPagination() {
		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setPageNo(1);
		limitQuery.setPageSize(10);
		ChannelSQO sqo = new ChannelSQO();
		sqo.setChannelName("南航_测试");
		sqo.setLimit(limitQuery);

		List<Channel> list = channelSPI.queryPagination(sqo).getList();
		for (Channel channel : list) {
//			System.out.println(JSON.toJSONString(channel));
		}
	}

}
