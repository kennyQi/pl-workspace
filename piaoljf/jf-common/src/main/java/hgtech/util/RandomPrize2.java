package hgtech.util;

import java.util.HashMap;

/**
 * 根据给定的各奖项概率（0。0~1。0），抽奖返回奖项级别（1，2，3。。。或null） 算法：1）根据小数点最多的作为分母 2）通分 3）设置各奖项 。。。
 * 抽奖时，随机产生一个数字，看是哪个奖项
 * 
 * @author xinglj
 * 
 */
public class RandomPrize2 {

	private int fenmu;
	private HashMap<Integer, Integer> prizeMap;

	public RandomPrize2(String[] gv) {
		// 1)
		int maxLen = 0;
		float fgv[] = new float[gv.length];
		int i = 0;
		for (String s : gv) {
			Float g = Float.parseFloat(s);
			fgv[i++] = g;
			if (g < 0.0f || g > 1.0)
				throw new RuntimeException("奖项概率（0。0~1。0）!");

			if (s.length() > maxLen)
				maxLen = s.length();
		}
		fenmu = (int) Math.pow(10 , (maxLen - 2));

		prizeMap = new HashMap<>(fenmu);
		System.out.println("抽奖总基数"+fenmu);
		for (i = 0; i < fgv.length; i++) {
			// 2)通分,
			int fenzi = (int) (fgv[i] * fenmu);
			// 3)设置各奖项.为此等级 产生这么多奖品，放进奖品盒子
			for (int j = 0; j < fenzi; j++) {
				prizeMap.put(randomPrizeNum(), i+1);
			}
			System.out.println("奖项"+(i+1)+"基数为"+fenzi);
		}

	}


	private int randomNum() {
		return (int) (Math.random() * fenmu);
	}
	
	//产生一个奖品号码，奖品号码不能和已有奖品号码重复
	private int randomPrizeNum() {
		while (true) {
			int r = randomNum();
			if (!prizeMap.containsKey(r))
				return r;
		}
	}
	
	/**
	 * 
	 * @return 奖项级别（1，2，。。。）or null.
	 */
	public Integer chouj(){
		return prizeMap.get(randomNum());
	}
	
	public static void main(String[] args) {
		RandomPrize2 r=new RandomPrize2(new String[]{"0","0.001","0.8"});
		int count[] = new int[3];
		int nulc = 0;
		for(int i=0;i<10000;i++){
			final Integer chouj = r.chouj();
			if(chouj!=null)
				count[chouj-1] ++;
			else
				nulc++;
		}
		for(int i:count)
			System.out.println(i);
		System.out.println(nulc);
	}
}
