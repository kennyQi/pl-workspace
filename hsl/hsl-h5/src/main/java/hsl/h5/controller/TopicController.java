package hsl.h5.controller;

import hsl.h5.control.HslCtrl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 专题
 *
 * @author zhurz
 */
@Controller
public class TopicController extends HslCtrl {

	/**
	 * 2015双十一
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/topic/guanggunjie")
	public String guanggunjie(HttpServletRequest request) {


		return "topic/guanggunjie";
	}
}
