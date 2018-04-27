package hgfx.web.controller.mileOrder;

import hg.framework.common.util.Md5Util;
import hg.framework.common.util.file.FdfsFileInfo;
import hg.framework.common.util.file.FdfsFileUtil;
import hg.fx.command.importHistory.CreateImportHistoryCommand;
import hg.fx.command.mileOrder.CreateMileOrderCommand;
import hg.fx.command.mileOrder.ImportMileOrderCommand;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.Product;
import hg.fx.domain.ProductInUse;
import hg.fx.spi.ImportHistorySPI;
import hg.fx.spi.MileOrderSPI;
import hg.fx.spi.ProductInUseSPI;
import hg.fx.spi.ProductSPI;
import hg.fx.spi.qo.ImportHistorySQO;
import hg.fx.spi.qo.ProductInUseSQO;
import hg.fx.spi.qo.ProductSQO;
import hgfx.web.controller.sys.BaseController;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("mileOrder")
public class MileOrderController extends BaseController{
	
	@Autowired
	private MileOrderSPI mileOrderSPI;
	@Autowired
	private ImportHistorySPI importHistorySPI;
	@Autowired
	private ProductInUseSPI productInUseSPI;
	@Autowired
	private ProductSPI productSPI; 

	
	/** pageSize*/
	private final static String PAGE_SIZE = "9"; 
	
	/** 高到低*/
	private final static String HIGE_TO_LOW = "h2l";
	/** 低到高*/
	private final static String LOW_TO_HIGH = "l2h";
	/** 无排序*/
	private final static String NO_SORT = "ns";
	
	/** MAX_ROWS*/
	private final static Integer MAX_ROWS = 5000; 
	/**
	 * 
	 * @方法功能说明：页面跳转
	 * @修改者名字：cangs
	 * @修改时间：2016年6月6日下午1:57:42
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("newOrderView")
	public String newOrderView(HttpServletRequest request,HttpServletResponse response,Model model){
		ProductInUseSQO sqo = new ProductInUseSQO();
		DistributorUser distributorUser = getSessionUserInfo(request.getSession());
		sqo.setStatus(2);
		sqo.setDistributorId(distributorUser.getDistributor().getId());
		List<ProductInUse> productInUses = productInUseSPI.queryList(sqo);
		model.addAttribute("productInUses",productInUses);
		return "ordernew/order-manage-new.html";
	}
	
	/**
	 * 
	 * @方法功能说明：上传
	 * @修改者名字：cangs
	 * @修改时间：2016年6月6日下午1:57:49
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param file
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("upload")
	public String upload(HttpServletRequest request,HttpServletResponse response,Model model,@RequestParam("file") CommonsMultipartFile file,
			@RequestParam(value = "productID",defaultValue = "")String productID){
		Map<String, String> result_map = new HashMap<String, String>();
		DistributorUser distributorUser = getSessionUserInfo(request.getSession());
		//商品信息还给页面
		model.addAttribute("productID",productID);
		ProductInUseSQO sqo = new ProductInUseSQO();
		sqo.setStatus(2);
		sqo.setDistributorId(distributorUser.getDistributor().getId());
		List<ProductInUse> productInUses = productInUseSPI.queryList(sqo);
		model.addAttribute("productInUses",productInUses);
		// 1 验证图片上传格式
		String fileName = file.getFileItem().getName();
		if(!fileName.endsWith(".xlsx") && !fileName.endsWith(".XLSX") 
				&& !fileName.endsWith(".xls") && !fileName.endsWith(".XLS") 
				) {
			model.addAttribute("status","error");
			model.addAttribute("msg","请上传xlsx、xls或XLSX、XLS格式的文件！");
			model.addAttribute("miniWindow","close");
			return "ordernew/order-manage-new.html";
		}
		// 2 验证文件大小
		Long size = file.getFileItem().getSize();
		String maxSize="2048";
		if(size > Long.parseLong(maxSize) * 1024){
			model.addAttribute("status","error");
			model.addAttribute("msg","请上传【大小】小于等于" + maxSize + "KB的文件！");
			model.addAttribute("miniWindow","close");
			return "ordernew/order-manage-new.html";
		}
		try {
			InputStream in = file.getInputStream();
			FdfsFileUtil.init();
			FdfsFileInfo fileinfo = FdfsFileUtil.upload((FileInputStream) (in), file.getFileItem().getName().split("\\.")[1], result_map);
			//文件地址
			String url = "http://"+fileinfo.getSourceIpAddr()+"/"+fileinfo.getGroupName()+"/"+fileinfo.getRemoteFileName();
			//导入command
			ImportMileOrderCommand importMileOrderCommand = new ImportMileOrderCommand();
			importMileOrderCommand.setDistributorId(distributorUser.getDistributor().getId());
			List<CreateMileOrderCommand> createMileOrderCommands = new LinkedList<>();
			//开始操作excel
			InputStream  is = file.getInputStream();
			Workbook wb = Workbook.getWorkbook(is);
			Sheet se = wb.getSheet(0);
			//校验表头
			ProductSQO productSQO = new ProductSQO();
			productSQO.setProductID(productID);
			Product product = productSPI.queryProductByID(productSQO);
			String excelhead = "";
			for(int k=0;k<product.getExcelHeadJson().split(";").length;k++){
				if(k+1==product.getExcelHeadJson().split(";").length)
					excelhead+=se.getCell(k,0).getContents().trim();
				else
					excelhead+=se.getCell(k,0).getContents().trim()+";";
			}
			if(StringUtils.isBlank(productID)){
				model.addAttribute("status","error");
				model.addAttribute("msg","请选择产品后重新提交!");
				model.addAttribute("miniWindow","close");
				return "ordernew/order-manage-new.html";
			}
			if(!StringUtils.equals(excelhead,product.getExcelHeadJson())){
				model.addAttribute("status","error");
				model.addAttribute("msg","表头格式不正确!");
				model.addAttribute("miniWindow","close");
				model.addAttribute("productID",productID);
				return "ordernew/order-manage-new.html";
			}
			//校验excel条数
			if(se.getRows()-1>MAX_ROWS){
				model.addAttribute("status","error");
				model.addAttribute("msg","最多上传"+MAX_ROWS+"条记录!");
				model.addAttribute("miniWindow","close");
				model.addAttribute("productID",productID);
				return "ordernew/order-manage-new.html";
			}
			int i = 1;
			while(i<=(se.getRows()-1)){
				try{
					if(StringUtils.isNotBlank(se.getCell(0,i).getContents())||StringUtils.isNotBlank(se.getCell(1,i).getContents())||StringUtils.isNotBlank(se.getCell(2,i).getContents())){
						CreateMileOrderCommand createMileOrderCommand = new CreateMileOrderCommand();
						createMileOrderCommand.setCsairCard(se.getCell(0,i).getContents().trim());
						createMileOrderCommand.setCsairName(se.getCell(1,i).getContents().trim());
						if(StringUtils.isNotBlank(se.getCell(2,i).getContents().trim())){
							try{
								createMileOrderCommand.setNum(Integer.valueOf(se.getCell(2,i).getContents().trim()));
							}catch(Exception e){
							}
						}
						createMileOrderCommand.setImportUser(distributorUser.getName());
						createMileOrderCommand.setProduct(product);
						createMileOrderCommands.add(createMileOrderCommand);
					}
					i++;
				}catch(Exception e){
				}
			}
			importMileOrderCommand.setList(createMileOrderCommands);
			//提交到service
			importMileOrderCommand = mileOrderSPI.importBatch(importMileOrderCommand);
			importMileOrderCommand.setDistributorId(distributorUser.getDistributor().getId());
			List<CreateMileOrderCommand> newCreateMileOrderCommands = new LinkedList<>();
			createMileOrderCommands = importMileOrderCommand.getList();
			for(int j=0;j<Integer.parseInt(PAGE_SIZE);j++){
				if((1-1)*Integer.parseInt(PAGE_SIZE)+j<createMileOrderCommands.size()){
					newCreateMileOrderCommands.add(createMileOrderCommands.get((1-1)*Integer.parseInt(PAGE_SIZE)+j));
				}
			}
			//将参数放入页面
			ImportHistorySQO importHistorySQO = new ImportHistorySQO();
			SimpleDateFormat dateToStr = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateToStr.format(new Date());
			SimpleDateFormat strToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				importHistorySQO.setBeginImportDate(strToDate.parse(date+" 00:00:00"));
				importHistorySQO.setEndImportDate(strToDate.parse(date+" 23:59:59"));
			} catch (ParseException e) {
			}
			importHistorySQO.setDistributorID(distributorUser.getDistributor().getId());
			model.addAttribute("filecontentlist",importHistorySPI.queryList(importHistorySQO));
//			System.out.println("MD5"+JSON.toJSONString(createMileOrderCommands));
			model.addAttribute("filecontent",Md5Util.MD5(JSON.toJSONString(createMileOrderCommands)));
			model.addAttribute("importMileOrderCommands",newCreateMileOrderCommands);
			model.addAttribute("commands",JSON.toJSONString(importMileOrderCommand).replaceAll("\"", "|"));
			model.addAttribute("miniWindow","open");
			model.addAttribute("pageNum",1);
			int totalcount = importMileOrderCommand.getList().size();
			double totalPageNum = 1;
			if(totalcount>Integer.valueOf(PAGE_SIZE)){
				totalPageNum=Math.ceil(Double.parseDouble(Integer.toString(totalcount))/Double.parseDouble(PAGE_SIZE));
			}
			model.addAttribute("totalPageNum",totalPageNum);
			model.addAttribute("sort",NO_SORT);
			model.addAttribute("url",url);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			model.addAttribute("fileName",fileName.split("\\.")[0]+distributorUser.getDistributor().getCode()+simpleDateFormat.format(new Date())+"."+fileName.split("\\.")[1]);
			model.addAttribute("dataFalg",checkList(importMileOrderCommand.getList()));
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("status","error");
			model.addAttribute("msg","上传失败!");
			model.addAttribute("miniWindow","close");
		}
		return "ordernew/order-manage-new.html";
	}
	
	
	/**
	 * 
	 * @方法功能说明：excel导入提交
	 * @修改者名字：cangs
	 * @修改时间：2016年6月6日下午3:42:10
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param dstributorUserId
	 * @参数：@param url
	 * @参数：@param fileName
	 * @参数：@param commands
	 * @参数：@param dataFalg
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("submit")
	public String submit(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "url",defaultValue = "")String url,
			@RequestParam(value = "fileName",defaultValue = "")String fileName,
	        @RequestParam(value = "commands",defaultValue = "")String commands,
	        @RequestParam(value = "dataFalg",defaultValue = "")String dataFalg,
	        @RequestParam(value = "productID",defaultValue = "")String productID){
		long start = new Date().getTime();  
//		System.out.println("【性能】"+"收到请求");
//		System.out.println("【性能】"+"准备调用service");
		DistributorUser distributorUser = getSessionUserInfo(request.getSession());
		model.addAttribute("productID",productID);
		ProductInUseSQO sqo = new ProductInUseSQO();
		sqo.setStatus(2);
		sqo.setDistributorId(distributorUser.getDistributor().getId());
		List<ProductInUse> productInUses = productInUseSPI.queryList(sqo);
		model.addAttribute("productInUses",productInUses);
		try{
			final ImportMileOrderCommand importMileOrderCommand = JSON.parseObject(commands.replaceAll("\\|", "\""), ImportMileOrderCommand.class);
			if(StringUtils.isBlank(productID)){
				model.addAttribute("status","error");
				model.addAttribute("msg","请选择产品后重新提交!");
				model.addAttribute("miniWindow","close");
				return "ordernew/order-manage-new.html";
			}
			if(StringUtils.equals(dataFalg, "1")){
				model.addAttribute("status","error");
//				model.addAttribute("msg","请更改数据后重新提交!");
				model.addAttribute("miniWindow","close");
				return "ordernew/order-manage-new.html";
			}
			long end0 = new Date().getTime();  
			ProductSQO productSQO = new ProductSQO();
			productSQO.setProductID(productID);
			Product product =productSPI.queryProductByID(productSQO);
			for (CreateMileOrderCommand createMileOrderCommand : importMileOrderCommand.getList()) {
				createMileOrderCommand.setProduct(product);
			}
			long end1 = new Date().getTime();  
//			System.out.println("【性能】"+"调用for"+(end1-end0)/1000+"s");
//			System.out.println("【性能】"+"准备调用service"+(end1-start)/1000+"s");
			//提交
			new Thread(){
				@Override
				public void run(){
					
					//submitBatchOrder(importMileOrderCommand);
					submitBatchOrderOnebyOne(importMileOrderCommand);
				}


			}.start();
			long end2 = new Date().getTime();  
//			System.out.println("【性能】"+"service执行结束"+(end2-end1)/1000+"s");
			//创建记录
			CreateImportHistoryCommand createImportHistoryCommand = new CreateImportHistoryCommand();
			createImportHistoryCommand.setDstributorUserId(distributorUser.getId());
			createImportHistoryCommand.setFileName(fileName);
			createImportHistoryCommand.setFilePath(url);
			createImportHistoryCommand.setImportDate(new Date());
			createImportHistoryCommand.setNormalMileages(importMileOrderCommand.getOkMiles());
			createImportHistoryCommand.setNormalNum(importMileOrderCommand.getOkRows());
			createImportHistoryCommand.setContentMD5(Md5Util.MD5(JSON.toJSONString(importMileOrderCommand.getList())));
//			System.out.println("MD5"+JSON.toJSONString(importMileOrderCommand.getList()));
			long end3 = new Date().getTime();  
//			System.out.println("【性能】"+"准备开始上传记录数据持久化"+(end3-end2)/1000+"ms");
			importHistorySPI.create(createImportHistoryCommand);
			long end4 = new Date().getTime();  
//			System.out.println("【性能】"+"上传记录数据持久话结束"+(end4-end3)/1000+"ms"+"这是"+importMileOrderCommand.getList().size()+"条");
			model.addAttribute("status","success");
			model.addAttribute("miniWindow","close");
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("status","error");
			model.addAttribute("msg","提交失败!");
			model.addAttribute("miniWindow","close");
		}
		return "ordernew/order-manage-new.html";
	}
	
	/**
	 * @param importMileOrderCommand
	 */
	private void submitBatchOrder(
			final ImportMileOrderCommand importMileOrderCommand) {
	//	mileOrderSPI.submitBatch(importMileOrderCommand);
		//分小批次处理
		final List<CreateMileOrderCommand> list = importMileOrderCommand.getList();
		int batchSize = 1;// 200; 一条为一个事务
		int batch = 0;
		int toIndex = 0;
		ImportMileOrderCommand batchCmd = new ImportMileOrderCommand();
		batchCmd.setDistributorId(importMileOrderCommand.getDistributorId());
		batchCmd.setFromPlatform(importMileOrderCommand.getFromPlatform());
		batchCmd.setList(new ArrayList<CreateMileOrderCommand>());
		ImportMileOrderCommand ret = new ImportMileOrderCommand();
		ret.setDistributorId(importMileOrderCommand.getDistributorId());
		ret.setFromPlatform(importMileOrderCommand.getFromPlatform());
		ret.setList(new ArrayList<CreateMileOrderCommand>());
		while (toIndex != list.size()) {
			toIndex = (batch + 1) * batchSize;
			if (toIndex > list.size())
				toIndex = list.size();
			batchCmd.getList().clear();
			batchCmd.getList().addAll (list.subList(batch * batchSize, toIndex));
			ImportMileOrderCommand submitBatch = mileOrderSPI.submitBatch(batchCmd);
			ret.getList().addAll(submitBatch.getList());
			ret.setOkMiles(ret.getOkMiles() + submitBatch.getOkMiles());
			ret.setOkRows(ret.getOkRows() + submitBatch.getOkRows());
			batch++;
		}		
	}
	/**
	 * 逐条处理
	 * @param importMileOrderCommand
	 */
	private void submitBatchOrderOnebyOne(
			final ImportMileOrderCommand importMileOrderCommand) {
		//逐条处理
		final List<CreateMileOrderCommand> list = importMileOrderCommand.getList();
		 
		ImportMileOrderCommand batchCmd = new ImportMileOrderCommand();
		batchCmd.setDistributorId(importMileOrderCommand.getDistributorId());
		batchCmd.setFromPlatform(importMileOrderCommand.getFromPlatform());
		batchCmd.setList(new ArrayList<CreateMileOrderCommand>());
		ImportMileOrderCommand ret = new ImportMileOrderCommand();
		ret.setDistributorId(importMileOrderCommand.getDistributorId());
		ret.setFromPlatform(importMileOrderCommand.getFromPlatform());
		ret.setList(new ArrayList<CreateMileOrderCommand>());
		for (CreateMileOrderCommand cr:importMileOrderCommand.getList()) {
			batchCmd.getList().clear();
			batchCmd.getList().add(cr);
			ImportMileOrderCommand submitBatch = mileOrderSPI.submitBatch(batchCmd);
			ret.getList().addAll(submitBatch.getList());
			ret.setOkMiles(ret.getOkMiles() + submitBatch.getOkMiles());
			ret.setOkRows(ret.getOkRows() + submitBatch.getOkRows());
			
		}		
	}
	
	/**
	 * 
	 * @方法功能说明：排序
	 * @修改者名字：cangs
	 * @修改时间：2016年6月6日下午1:57:59
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param pageNum
	 * @参数：@param sort
	 * @参数：@param url
	 * @参数：@param commands
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("commandList")
	public String commandList(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
			@RequestParam(value = "sort",defaultValue = "")String sort,
			@RequestParam(value = "url",defaultValue = "")String url,
			@RequestParam(value = "fileName",defaultValue = "")String fileName,
			@RequestParam(value = "commands",defaultValue = "")String commands,
			@RequestParam(value = "productID",defaultValue = "")String productID,
			@RequestParam(value = "dataFalg",defaultValue = "")String dataFalg){
		ImportMileOrderCommand importMileOrderCommand = JSON.parseObject(commands.replaceAll("\\|", "\""), ImportMileOrderCommand.class);
		DistributorUser distributorUser = getSessionUserInfo(request.getSession());
		List<CreateMileOrderCommand> createMileOrderCommands = importMileOrderCommand.getList();
		//判断排序
		if(StringUtils.equals(dataFalg,"1")){
			//如果数据有异常 保持异常数据在最前面
			//异常数据
			List<CreateMileOrderCommand> errorlist = new LinkedList<>();
			//正常数据
			List<CreateMileOrderCommand> rightlist = new LinkedList<>();
			//分析元数据
			for (CreateMileOrderCommand createMileOrderCommand : createMileOrderCommands) {
				if(StringUtils.isNotBlank(createMileOrderCommand.getErrorTip())){
					errorlist.add(createMileOrderCommand);
				}else{
					rightlist.add(createMileOrderCommand);
				}
			}
			//进行排序
			if(StringUtils.equals(sort, HIGE_TO_LOW)){
				Collections.sort(rightlist, new Comparator<CreateMileOrderCommand>() {
					@Override
					public int compare(CreateMileOrderCommand o1, CreateMileOrderCommand o2) {
						return o2.getNum().compareTo(o1.getNum());
					}
				});
			}else if(StringUtils.equals(sort, LOW_TO_HIGH)){
				Collections.sort(rightlist, new Comparator<CreateMileOrderCommand>() {
					@Override
					public int compare(CreateMileOrderCommand o1, CreateMileOrderCommand o2) {
						return o1.getNum().compareTo(o2.getNum());
					}
				});
			}
			//合并排序结果
			createMileOrderCommands = new LinkedList<>();
			createMileOrderCommands.addAll(errorlist);
			createMileOrderCommands.addAll(rightlist);
		}else{
			//无异常数据 直接排序
			if(StringUtils.equals(sort, HIGE_TO_LOW)){
				Collections.sort(createMileOrderCommands, new Comparator<CreateMileOrderCommand>() {
					@Override
					public int compare(CreateMileOrderCommand o1, CreateMileOrderCommand o2) {
						return o2.getNum().compareTo(o1.getNum());
					}
				});
				
			}else if(StringUtils.equals(sort, LOW_TO_HIGH)){
				Collections.sort(createMileOrderCommands, new Comparator<CreateMileOrderCommand>() {
					@Override
					public int compare(CreateMileOrderCommand o1, CreateMileOrderCommand o2) {
						return o1.getNum().compareTo(o2.getNum());
					}
				});
			}
		}
		List<CreateMileOrderCommand> newCreateMileOrderCommands = new LinkedList<>();
		for(int i=0;i<Integer.parseInt(PAGE_SIZE);i++){
			if((pageNum-1)*Integer.parseInt(PAGE_SIZE)+i<createMileOrderCommands.size()){
				newCreateMileOrderCommands.add(createMileOrderCommands.get((pageNum-1)*Integer.parseInt(PAGE_SIZE)+i));
			}
		}
		model.addAttribute("importMileOrderCommands",newCreateMileOrderCommands);
		model.addAttribute("commands",JSON.toJSONString(importMileOrderCommand).replaceAll("\"", "|"));
		model.addAttribute("miniWindow","open");
		model.addAttribute("pageNum",pageNum);
		int totalcount = importMileOrderCommand.getList().size();
		double totalPageNum = 1;
		if(totalcount>Integer.valueOf(PAGE_SIZE)){
			totalPageNum=Math.ceil(Double.parseDouble(Integer.toString(totalcount))/Double.parseDouble(PAGE_SIZE));
		}
		model.addAttribute("totalPageNum",totalPageNum);
		model.addAttribute("sort",sort);
		model.addAttribute("fileName",fileName);
		model.addAttribute("url",url);
		model.addAttribute("dataFalg",dataFalg);
		model.addAttribute("productID",productID);
		ProductInUseSQO sqo = new ProductInUseSQO();
		sqo.setStatus(2);
		sqo.setDistributorId(distributorUser.getDistributor().getId());
		List<ProductInUse> productInUses = productInUseSPI.queryList(sqo);
		model.addAttribute("productInUses",productInUses);
		return "ordernew/order-manage-new.html";
	}

	
	/**
	 * 
	 * @方法功能说明：批量提交
	 * @修改者名字：cangs
	 * @修改时间：2016年6月6日下午3:41:07
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param csairCard
	 * @参数：@param csairName
	 * @参数：@param num
	 * @参数：@param dataFalg
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("batchSubmit")
	public String batchSubmit(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "csairCard",defaultValue = "")String csairCard,
			@RequestParam(value = "csairName",defaultValue = "")String csairName,
			@RequestParam(value = "num",defaultValue = "")String num,
			@RequestParam(value = "dataFalg",defaultValue = "")String dataFalg,
			@RequestParam(value = "productID",defaultValue = "")String productID,
			@RequestParam(value = "size",defaultValue = "")int size){
		ImportMileOrderCommand importMileOrderCommand = new ImportMileOrderCommand();
		DistributorUser distributorUser = getSessionUserInfo(request.getSession());
		ProductInUseSQO sqo = new ProductInUseSQO();
		sqo.setStatus(2);
		sqo.setDistributorId(distributorUser.getDistributor().getId());
		List<ProductInUse> productInUses = productInUseSPI.queryList(sqo);
		model.addAttribute("productInUses",productInUses);
		model.addAttribute("productID",productID);
		try{
			String[] csairCards = csairCard.split(",");
			String[] csairNames = csairName.split(",");
			String[] nums = num.split(",");
			List<CreateMileOrderCommand> createMileOrderCommands = new LinkedList<>();
			ProductSQO productSQO = new ProductSQO();
			productSQO.setProductID(productID);
			Product product = productSPI.queryProductByID(productSQO);
			for (int i = 0; i < size; i++) {
				CreateMileOrderCommand createMileOrderCommand = new CreateMileOrderCommand();
				try{
					createMileOrderCommand.setCsairCard(csairCards[i]);
				}catch(Exception e){
				}
				try{
					createMileOrderCommand.setCsairName(csairNames[i]);
				}catch(Exception e){
				}
				createMileOrderCommand.setProduct(product);
				try{
					if (StringUtils.isNotBlank(nums[i])) {
						createMileOrderCommand.setNum(Integer.parseInt(nums[i]));
					}
				}catch(Exception e){
				}
				createMileOrderCommand.setImportUser(distributorUser.getName());
				createMileOrderCommands.add(createMileOrderCommand);
			}
			importMileOrderCommand.setDistributorId(distributorUser.getDistributor().getId());
			importMileOrderCommand.setList(createMileOrderCommands);
			if(StringUtils.isBlank(productID)){
				model.addAttribute("status","error");
				model.addAttribute("msg","请选择产品后重新提交!");
				model.addAttribute("miniWindow","close");
				model.addAttribute("errorommands",createMileOrderCommands);
				model.addAttribute("dataFalg",checkList(createMileOrderCommands));
				return "ordernew/order-manage-new.html";
			}
			if(size>20){
				model.addAttribute("status","error");
				model.addAttribute("msg","一次最多添加20条，您可以提交后继续添加!");
				model.addAttribute("miniWindow","close");
				model.addAttribute("errorommands",createMileOrderCommands);
				model.addAttribute("dataFalg",checkList(createMileOrderCommands));
				return "ordernew/order-manage-new.html";
			}
			importMileOrderCommand = mileOrderSPI.importBatch(importMileOrderCommand);
			if(StringUtils.equals(String.valueOf(checkList(importMileOrderCommand.getList())), "1")){
				model.addAttribute("status","error");
//				model.addAttribute("msg","请更改数据后重新提交!");
				model.addAttribute("miniWindow","close");
				model.addAttribute("errorommands",importMileOrderCommand.getList());
				model.addAttribute("dataFalg",checkList(importMileOrderCommand.getList()));
				return "ordernew/order-manage-new.html";
			}
			importMileOrderCommand.setDistributorId(distributorUser.getDistributor().getId());
			//提交
			final ImportMileOrderCommand anotherImportMileOrderCommand = importMileOrderCommand;
			new Thread(){
				@Override
				public void run(){
//					mileOrderSPI.submitBatch(anotherImportMileOrderCommand);
					submitBatchOrderOnebyOne(anotherImportMileOrderCommand);
				}
			}.start();
			model.addAttribute("status","success");
			model.addAttribute("miniWindow","close");
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("status","error");
			model.addAttribute("msg","提交失败!");
			model.addAttribute("miniWindow","close");
			model.addAttribute("errorommands",importMileOrderCommand.getList());
			model.addAttribute("dataFalg",checkList(importMileOrderCommand.getList()));
		}
		return "ordernew/order-manage-new.html";
	}
	
	/**
	 * 
	 * @方法功能说明：验证list方法
	 * @修改者名字：cangs
	 * @修改时间：2016年6月14日上午9:22:15
	 * @修改内容：
	 * @参数：@param list
	 * @参数：@return
	 * @return:int
	 * @throws
	 */
	public static int checkList(List<CreateMileOrderCommand> list){
		int i=0;
		for (CreateMileOrderCommand createMileOrderCommand : list) {
			if(StringUtils.isNotBlank(createMileOrderCommand.getErrorTip())){
				i=1;
				break;
			}
		}
		return i;
	}
}
