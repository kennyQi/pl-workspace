package pic;

import java.util.Date;

public class suanshu {

	public static void main(String[] args) {
		double a1,a2,b1,b2,b3,c1,c2,c3;
		long start = (new Date()).getTime();
		for(a1=0;a1<999;a1++){
			for(a2=0;a2<999;a2++){
				if(a1+a2-9==4){
					for(b3=0;b3<999;b3++){
						for(c3=0;c3<999;c3++){
							if(9-b3-c3==4){
								for(b1=0;b1<999;b1++){
									for(b2=0;b2<999;b2++){
										if(b1-(b2*b3)==4){
											for(c1=1;c1<999;c1++){
												for(c2=0;c2<999;c2++){
													if(c1+c2-c3==4){
														if((a1+(b1/c1)==4)&&(a2-(b2*c2)==4)){
															System.out.println("hehe");
															System.out.println(a1+" "+a2+" "+9);
															System.out.println(b1+" "+b2+" "+b3);
															System.out.println(c1+" "+c2+" "+c3);
															long end = (new Date()).getTime();
															System.out.println("cal time:"+(end-start)/1000+"s");
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		long end = (new Date()).getTime();
		System.out.println("end time:"+(end-start)/1000+"s");
	}
}
