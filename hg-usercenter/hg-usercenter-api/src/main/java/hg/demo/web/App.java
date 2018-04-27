package hg.demo.web;

import hg.demo.web.controller.UserApiController;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       String sign = UserApiController.genSign("A", "2016-07-01 15:40:00");
       System.out.println(sign);
    }
}
