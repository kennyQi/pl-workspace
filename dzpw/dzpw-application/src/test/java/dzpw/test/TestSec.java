package dzpw.test;

import hg.dzpw.app.common.util.alipay.AlipayCore;

import java.text.SimpleDateFormat;

public class TestSec {
	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			String randomBatchNo = AlipayCore.getRandomBatchNo();
			System.out.println(randomBatchNo);
		}
	}
}
