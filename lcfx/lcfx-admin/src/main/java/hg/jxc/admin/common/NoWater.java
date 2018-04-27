package hg.jxc.admin.common;

import java.awt.image.BufferedImage;
import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.Configurable;

/**
 * @类功能说明:验证码组件 
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @版本：V1.0
 */
public class NoWater extends Configurable implements GimpyEngine {

	@Override
	public BufferedImage getDistortedImage(BufferedImage baseImage) {
		 return baseImage;
	}
}