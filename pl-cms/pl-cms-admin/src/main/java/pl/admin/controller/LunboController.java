package pl.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.app.service.IndexLunboServiceImpl;

@Controller
@RequestMapping("/lunbo")
public class LunboController extends BaseController {
	
	@Autowired
	private IndexLunboServiceImpl indexLunboServiceImpl;
	
	public void name() {
		
	}
}
