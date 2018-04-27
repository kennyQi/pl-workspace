package hg.service.image.spi.inter;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @类功能说明：文件系统服务
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30下午5:02:17
 */
public interface FastdfsService_1 {
	/**
	 * @方法功能说明：获取 FAST DFS 配置（配置文件里的文本）
	 * @修改者名字：zhurz
	 * @修改时间：2014-10-30下午5:33:45
	 * @修改内容：
	 * @参数：
	 * @return:String
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	String getFastdfsConfig_1() throws IOException, URISyntaxException;	
}