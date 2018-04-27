package hgtech.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 概率抽奖.按多少分之一的概率抽奖。如按1/100的概率抽奖。
 * @author xinglj
 *
 */
public class RandomPrize {
	//要设置的多少分之1。如概率为1/1000,那么base为1000.
	private int base;	
	private int theGoal; //目标设为基数的中间数字
	
	public RandomPrize(int pbase){
		if(pbase<1)
			throw new RuntimeException("要求基数>=1!要设置多少分之1。如概率为1/1000,那么base为1000");
		setBase(pbase);
	}
	public RandomPrize() {
	}
	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		if(base<1)
			throw new RuntimeException("要求基数>=1!要设置多少分之1。如概率为1/1000,那么base为1000");
		this.base = base;
		theGoal = base/2;
	}

	public boolean action(){
		int rand= (int) (Math.random()*base);
		if(rand == theGoal)
			return true;
		else
			return false;
	}
	
	public static void main(String[] args) {
		RandomPrize r= new RandomPrize();
		
		r.setBase(10);
		int ok = 0;
		List<Integer> id=new LinkedList<>();
		for(int i=0;i<100;i++){
			final boolean act = r.action();
			if(act){
				ok ++;
				id .add(i);
			}
		}
		System.out.println(ok);
		System.out.println(id);
	}
	
}
