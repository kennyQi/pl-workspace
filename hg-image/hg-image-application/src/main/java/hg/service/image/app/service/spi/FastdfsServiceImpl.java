package hg.service.image.app.service.spi;

import hg.service.image.spi.inter.FastdfsService_1;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

/**
 * @类功能说明：DFS文件服务Service接口实现类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月6日 下午2:46:44
 */
@Service("spiFastdfsServiceImpl_1")
public class FastdfsServiceImpl implements FastdfsService_1 {
	/**
	 * @方法功能说明：获取 FAST DFS 配置（配置文件里的文本）
	 * @修改者名字：zhurz
	 * @修改时间：2014-10-30下午5:33:45
	 * @修改内容：
	 * @return:String
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	@Override
	public String getFastdfsConfig_1() throws IOException, URISyntaxException {
		String classPath = this.getClass().getResource("/").toURI().getPath();
//		String classPath = new File(this.getClass().getResource("/").getFile()).getCanonicalPath();//获取项目跟路径(ClassPath)
		File file = new File(classPath+"/fastdfs_client.conf");//FastDFS配置文件
		return FileUtils.readFileToString(file, "UTF-8");
	}
}