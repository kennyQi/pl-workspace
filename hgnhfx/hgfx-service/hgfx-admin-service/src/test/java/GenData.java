

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hg.framework.common.util.ExcelUtils;
import hg.fx.util.ExcelUtil;
import hg.fx.util.ExcelUtil.ExcelType;
import hg.hjf.nh.NHFile;

public class GenData {

		public static void main(String[] args) throws IOException {
			genFile();
		}
		


		/**生成导入订单的文件
		 * @throws IOException
		 */
		  static void genFile() throws IOException {
			List<List> rows=new ArrayList<>();
			rows.add(Arrays.asList(new String[]{"明珠会员卡号","明珠会员姓名","里程数"}));
			
			int begin=25000;
			for(int i=0;i<5000;i++){
				 String card= NHFile.genCardNo(begin+i);
				 rows.add(Arrays.asList(new String[]{card,"明珠会员姓名","100"}));
			}
			
			ExcelUtil.writeToExcelFile(rows, ExcelType.xls, new File("D:\\gitwork1.5\\lcfxnew\\doc\\import6.xls"));
		}
}
