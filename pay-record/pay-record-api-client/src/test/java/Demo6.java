import java.io.FileReader;
import java.io.IOException;


/*写一个方法,输入一个文件名和一个字符串,统计这个字符串在这个文件中出现的次数。*/
public class Demo6
{
	
	public static int tongJi(String file,String str)throws IOException
	{
		FileReader rea=new FileReader(file);
		
		
		int num=0;
		int c;
		while((c=rea.read())!=-1)
		{
			while(c==str.charAt(0))
			{
				for(int i=1;i<str.length();i++)
				{
					c=rea.read();
					if(c!=str.charAt(i))
					{
						break;
					}
					if(i==str.length()-1)
					{
						num++;
					}
				}
			}
		}
		return num;	
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(tongJi("F:\\java.txt","Demo"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
