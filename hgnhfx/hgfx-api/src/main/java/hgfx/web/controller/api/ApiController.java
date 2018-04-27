package hgfx.web.controller.api;

import java.util.ArrayList;
import java.util.List;

import hg.demo.member.common.domain.model.system.JsonUtil;
import hg.fx.command.mileOrder.ApiCreateMileOrderCommand;
import hg.fx.command.mileOrder.CreateMileOrderCommand;
import hg.fx.command.mileOrder.ImportMileOrderCommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.MileOrder;
import hg.fx.domain.MileOrderDto;
import hg.fx.domain.Product;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.MileOrderSPI;
import hg.fx.spi.ProductSPI;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.spi.qo.MileOrderSQO;
import hg.fx.spi.qo.ProductSQO;
import hg.fx.util.ResultDto;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 系统接口。
 * 提交订单、查询订单状态

 * @author xxinglj
 *
 */
@Controller
@RequestMapping(value="/api")
public class ApiController {
	
	/**
	 * 执行成功
	 */
	public static final int CODE_OK = 1;
	/**
	 * 订单不存在
	 */
	public static final int CODE_NOTEXIST = -101;
	/**
	 * 签名错误
	 */
	public static final int CODE_SIGNERROR = -100;
	@Autowired
	MileOrderSPI mileOrderSPI;
	@Autowired
	DistributorSPI distributorSPI;
	@Autowired
	ProductSPI productSPI;
	
	/**
	 * 提交订单
	 * api/submitOrder?appId=2c24e68161c24c3690921520d6cccbd5&orderCode=20160606&csairCard=015305153869&csairName=xlj&num=100&time=33333333&sign=729b5299be7c652fa4856a03873e1588
	 * @param request
	 * @param model
	 * @param createCommand
	 * @return 
	 * code为订单状态：
	 * 	CODE_SIGNERROR
	 * STATUS_CANCEL = -1
	 STATUS_NO_CHECK = 1;
	 STATUS_CHECK_PASS = 2;
	* 
	 */
	@ResponseBody
	@RequestMapping(value="/submitOrder")
	public String submit(HttpServletRequest request, Model model, ApiCreateMileOrderCommand apicreateCommand) {
		ApiResult result = new ApiResult();
		final String appId = request.getParameter("appId");
		DistributorSQO qo = new DistributorSQO();
		qo.setCode(appId);
		Distributor dis = distributorSPI.queryUnique(qo);
		String signKey = dis.getSignKey();
		
		String signError = SignUtil.checkSign(request, signKey);
		
		if(signError!=null){
			result.setText(signError);
			result.setCode(CODE_SIGNERROR);
		}else{
			
			apicreateCommand.setDistributorId(dis.getId());
		
			ImportMileOrderCommand cmd = new ImportMileOrderCommand();
			cmd.setDistributorId(dis.getId());
			List<CreateMileOrderCommand> list = new ArrayList<>();
			CreateMileOrderCommand createCommand=new CreateMileOrderCommand();
			BeanUtils.copyProperties(apicreateCommand, createCommand);
			
			//check 商品
			ProductSQO sqo=new ProductSQO();
			sqo.setProdCode(apicreateCommand.getProductCode());
			Product product = productSPI.queryUnique(sqo);
			if(product==null){
				result.setCode(MileOrder.STATUS_CANCEL);
				result.setText("订单已取消，原因为不存在编号为这个的商品："+apicreateCommand.getProductCode());
				return JSONObject.toJSONString(result);
			}
			
			createCommand.setProduct(product);
			list.add(createCommand);
			cmd.setList(list);
			//导入，检查格式
			  final CreateMileOrderCommand returnImportCommand = mileOrderSPI.importBatch(cmd).getList().get(0);
			if(returnImportCommand.getErrorTip()!=null){
				result.setCode(MileOrder.STATUS_CANCEL);
				result.setText("订单已取消，原因为"+returnImportCommand.getErrorTip());
			}else{
				//提交，扣备付金
				final CreateMileOrderCommand returnCommand = mileOrderSPI.submitBatch(cmd).getList().get(0);
				result.setCode(returnCommand.getSavedOrder().getStatus());
				if(returnCommand.getSavedOrder().getStatus()==MileOrder.STATUS_CANCEL){
					result.setText("订单已取消， "+returnCommand.getSavedOrder().getErrorReason());
				}else{
					result.setText("订单已受理，状态为"+returnCommand.getSavedOrder().getStatusName());
				}
			}
		}
		return JSONObject.toJSONString(result);
	}
	
	/**
	 * 查询订单状态
	 * \/api/queryOrder?appId=2c24e68161c24c3690921520d6cccbd5&orderCode=20160606&time=33333333&sign=5e72499f8fb49395d79b9cb4dd42b9cf
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryOrder")
	public String query(HttpServletRequest request, Model model) {
		ApiResult result = new ApiResult();
		final String appId = request.getParameter("appId");//商户编号
		final String orderCode = request.getParameter("orderCode");
		
		//check sign
		DistributorSQO qo = new DistributorSQO();
		qo.setCode(appId);
		Distributor dis = distributorSPI.queryUnique(qo);
		String signKey = dis.getSignKey();
		String signError = SignUtil.checkSign(request, signKey);
		
		if(signError!=null){
			result.setText(signError);
			result.setCode(CODE_SIGNERROR);
			return JSONObject.toJSONString(result);
		}
		
		//query order
		MileOrderSQO sqo=new MileOrderSQO();
		sqo.setDistributorId(dis.getId());
		sqo.setOrderCode(orderCode);
		MileOrder order = mileOrderSPI.queryUnique(sqo);
		if(order==null){
			result.setText("订单不存在");
			result.setCode(CODE_NOTEXIST);
		}else{
			MileOrderDto orderdto = new MileOrderDto();
			BeanUtils.copyProperties(order, orderdto);
			result.setText( JSONObject.toJSONString(orderdto));
			result.setCode(CODE_OK);
		}
		
		return JSONObject.toJSONString(result);
	}
	
}
