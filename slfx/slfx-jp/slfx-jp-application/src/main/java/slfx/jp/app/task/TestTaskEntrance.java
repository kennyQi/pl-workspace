package slfx.jp.app.task;

import javax.annotation.Resource;

import slfx.jp.app.service.local.JPOrderLocalService;

public class TestTaskEntrance {
	
	@Resource
	private JPOrderLocalService jpOrderLocalService;
	
	public void run(){
		System.out.println("jpOrderLocalService===="+jpOrderLocalService);
	}

}
