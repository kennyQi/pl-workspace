package hgtech.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

public class FileUtil {
	/**
	 * 对一行处理后返回，
	 * @author Administrator
	 *
	 */
	static class RowHandle{
		public String handleRow(String row){
			return row;
		}
	}
	
 	/**
	 * 
	 * 扫描目录，安装 一个文件不超过maxRowPerFile行数，将大文件分为若干个文件，新生成的文件会在 “<dir>-output”下
	 * @param dir
	 * @param maxRowPerFile
	 * @param firstRow  may null。如“姓名，年龄”
	 * @param rowHandle	
	 * @param fileNameFilter
	 * @param dest
	 * @param extName may null。如".csv"
	 * @throws IOException
	 */
	public static void splitFile(File dir, int maxRowPerFile,String firstRow, RowHandle rowHandle,  FilenameFilter fileNameFilter, File dest, String extName) throws IOException{
		if(!dest.exists())
			dest.mkdirs();
		
		for(File sub: dir.listFiles( fileNameFilter)){
			if(sub.isDirectory()){
				final File subdest = new File(dest, sub.getName()+"-out");
				splitFile(sub, maxRowPerFile,firstRow,rowHandle,fileNameFilter, subdest,extName);
			}else {
				split(sub, dest,extName,maxRowPerFile,firstRow,rowHandle);
			}
		}
	}

	
	/**
	 * 将文件分为每个文件不超过maxRowPerFile行的若干小文件，放在目录 dest下
	 * @param sub
	 * @param destDir
	 * @param extName 
	 * @param maxRowPerFile
	 * @param firstRow 
	 * @param rowHandle 
	 * @throws IOException 
	 */
	public static void split(File file, File destDir, String extName, int maxRowPerFile, String firstRow, RowHandle rowHandle) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(file));
		BufferedWriter wr;
		String ln;
		int batch=1,
				i=0;
		wr = new BufferedWriter(new FileWriter(getBatchFile(file, destDir, extName, batch)));
		if(firstRow!=null)
			wr.write(firstRow+"\r\n");
		while( (ln=bf.readLine())!=null){
			i++;
			if(i>maxRowPerFile){
				batch++;
				i=1;
				wr.flush();
				wr.close();
				wr = new BufferedWriter(new FileWriter(getBatchFile(file, destDir, extName, batch)));
				if(firstRow!=null)
					wr.write(firstRow+"\r\n");				
			}
			ln = rowHandle.handleRow(ln);
			wr.write(ln+"\r\n");
		}
		wr.flush();
		wr.close();
		bf.close();
			
	}

	static File getBatchFile(File file, File destDir, String extName, int batch) {
		int in=file.getName().indexOf('.');
		String pre = in != -1 ? file.getName().substring(0, in) : file.getName();
		String ext = in != -1 ? file.getName().substring(in+1)	: "";
		return new File(destDir, pre + "_"+batch + "."+ (extName!=null?extName:ext));
	}
	
	public static void main(String[] args) throws IOException {
		FilenameFilter fileNameFilter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if(new File(dir,name).isDirectory())
					return true;
				else{
					if(dir.list().length==1)
						return true;
					else
						return name.contains("市");
				}
			}
		};
		String dir = 
				args[0];
				//	"E:\\导入用户大文件测试";
		final int maxRowPerFile = 
				//50000;
				Integer.parseInt(args[1]);
		splitFile(new File(dir), maxRowPerFile, "acc,mob,name,id,if,valid",
		new RowHandle(){
			@Override
			public String handleRow(String row) {
				return row+","+row;
			}
		}, 
		fileNameFilter, new File(dir +"-out"),"csv");
	}
}
