package hsl.h5.control;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 生成随机26字母验证码图片
 * @author 胡永伟
 */
public class RandEnCodeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String EN_BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final int WIDTH = 96;
	private static final int HEIGHT = 30;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//创建图片
		BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		
		//设置背景
		setBackground(g);
		
		//画干扰线
		drawRandLines(g);
	    		
		//写随机中文字
		String code = writeRandCN((Graphics2D)g);
		
		//图片生效
		g.dispose();
		
		//设置session
		request.getSession().setAttribute("rand_code_for_bind", code);
		
		//将图片发送给客户端
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	private void setBackground(Graphics g) {
		g.setColor(getRandColor(200,250));
	    g.fillRect(0, 0, WIDTH, HEIGHT);
	}

	private void drawRandLines(Graphics g) {
		g.setColor(getRandColor(160,200));
		for(int x=0;x<155;x++) {
			int x1 = new Random().nextInt(WIDTH);
			int x2 = new Random().nextInt(WIDTH);
			int y1 = new Random().nextInt(HEIGHT);
			int y2 = new Random().nextInt(HEIGHT);
			g.drawLine(x1, y1, x2, y2);
		}
	}

	private String writeRandCN(Graphics2D g) {
		int x = 10;
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		Random random = new Random();
		String str = null;
		String code = "";
		for(int i=0;i<4;i++) {
			g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
			int degree =random.nextInt()%30;
			str = EN_BASE.charAt(random.nextInt(EN_BASE.length()))+"";
			code += str;
			g.rotate(degree*Math.PI/180, x, 20);
			g.drawString(str, x, 20);
			g.rotate(-degree*Math.PI/180, x, 20);
			x += 20;
		}
		return code;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private Color getRandColor(int fc,int bc){//给定范围获得随机颜色
	    Random random = new Random();
	    if(fc>255) fc=255;
	    if(bc>255) bc=255;
	    int r=fc+random.nextInt(bc-fc);
	    int g=fc+random.nextInt(bc-fc);
	    int b=fc+random.nextInt(bc-fc);
	    return new Color(r,g,b);
	}
	
}
