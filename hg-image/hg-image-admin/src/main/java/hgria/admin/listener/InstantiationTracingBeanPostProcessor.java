package hgria.admin.listener;

import hg.common.util.SpringContextUtil;
import hg.common.util.file.FdfsFileUtil;
import hg.service.image.spi.inter.FastdfsService_1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class InstantiationTracingBeanPostProcessor implements
		ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			
			// 1. 加载图片服务 的 FASTDFS配置
			FastdfsService_1 fastdfsService = SpringContextUtil.getApplicationContext().getBean(FastdfsService_1.class);
			try {
				// 1.1  读取配置
				String fastdfsConfig = fastdfsService.getFastdfsConfig_1();
				// 1.2  创建本地文件, 写入配置
				String classPath = "";
				try {
					classPath = new File(FdfsFileUtil.class.getResource("/").getFile())
							.getCanonicalPath();//获取项目跟路径(ClassPath)
				} catch (IOException e) {
					e.printStackTrace();
				}

				String configFilePath = classPath + "/fastdfs_client.conf";//FastDFS配置文件路径
				File uploadFile = new File(configFilePath);

				FileWriter fw = new FileWriter(uploadFile);
				fw.write(fastdfsConfig);
				
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

	}
}
