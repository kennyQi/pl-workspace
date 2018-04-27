package zzpl.app.service.local.log;

import hg.common.component.BaseCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

	@Autowired
	private SystemCommunicationLogService systemCommunicationLogService;
	
	public void saveLog(BaseCommand command){
		final BaseCommand baseCommand=command;
		new Thread(){
			public void run(){
				systemCommunicationLogService.saveLocalLog(baseCommand);
			}
		}.start();
	}
	
}
