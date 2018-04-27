import com.alibaba.fastjson.JSON;
import hg.framework.common.util.file.FdfsFileInfo;
import hg.framework.common.util.file.FdfsFileUtil;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;

/**
 * @author zhurz
 */
public class DFSTest {

	@Test
	public void testFastDFS() {
		FdfsFileUtil.init();
		FdfsFileInfo fileInfo = FdfsFileUtil.upload(new File("D:\\work\\Documents\\匀速移动.gif"), new HashMap<String, String>());
		System.out.println(JSON.toJSONString(fileInfo, true));
	}
}
