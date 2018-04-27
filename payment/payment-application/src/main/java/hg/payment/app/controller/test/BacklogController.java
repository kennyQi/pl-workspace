package hg.payment.app.controller.test;

import hg.system.service.BacklogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backlog")
public class BacklogController {
	
	@Autowired
	private BacklogService backlogService;
	
	@RequestMapping("/test")
	public void testBacklog(HttpServletRequest request, HttpServletResponse response,Model model){

	}

}
