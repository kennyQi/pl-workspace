import java.util.Calendar;
import java.util.Random;

public class CreateOrderIdTest {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();

		String[] formats = new String[] { "%09d",// 随机码
				"%s",// 年
				"%s",// 类型
				"%02d",// 时
				"%02d",// 日
				"%02d",// 秒
				"%x",// 月
				"%02d"// 分
		};
		Object[] formatValues = new Object[] {
				new Random().nextInt(1000000000),// 随机码
				Integer.toString(calendar.get(Calendar.YEAR) - 2015 + 10, 36),// 年
				"A",// 类型
				calendar.get(Calendar.HOUR),// 时
				calendar.get(Calendar.DATE),// 日
				calendar.get(Calendar.SECOND),// 秒
				calendar.get(Calendar.MONTH) + 1,// 月
				calendar.get(Calendar.MINUTE) // 分
		};
		StringBuilder sb = new StringBuilder();
		for (String format : formats)
			sb.append(format);
		System.out.println(sb.toString());
		System.out.println(String.format(sb.toString(), formatValues)
				.toUpperCase());
		System.out.println(Integer.toString(26 + 10 - 1, 26 + 10));
	}
}
