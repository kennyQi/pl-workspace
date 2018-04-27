package lxs.admin.controller.ad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *               
 * @类功能说明：广告控制器
 * @类修改者：
 * @修改日期：2014年12月12日上午11:17:23
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhuxy
 * @创建时间：2014年12月12日上午11:17:23
 *
 */
@Controller
@RequestMapping(value="/advertisement")
public class AdvertiseController {
	/*
	@Autowired
	private LxsAdService lxsAdService;
	@Autowired
	private ImageService_1 spiImageServiceImpl_1;
	
	@ResponseBody
	@RequestMapping(value="/addAd")
	public String addAd(Model model, LxsCreateAdCommand command){
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		
		
		CreateImageCommand createImageCommand = new CreateImageCommand();
		createImageCommand.setTitle(command.getTitle());
		//利旧URL存储file的json
		createImageCommand.setFileInfo(command.getImageInfo());
		createImageCommand.setFileName(command.getFileName());
		createImageCommand.setRemark("旅行社APP广告");
		createImageCommand.setUseTypeId(SysProperties.getInstance().get("imageUseTypeId"));
		createImageCommand.setFromProjectEnvName(SysProperties.getInstance().get("imageEnvName"));
		createImageCommand.setFromProjectId(SysProperties.getInstance().get("imageProjectId"));
		createImageCommand.setAlbumId(SysProperties.getInstance().get("imageUseTypeId"));
		try {
			String imageID=spiImageServiceImpl_1.createImage_1(createImageCommand);
			HgLogger.getInstance().info("lxs_dev", "【addAd】"+"新增广告图片在图片服务中的ID：" + imageID);
			ImageQo imageQo = new ImageQo();
			imageQo.setId(imageID);
			ImageDTO imageDTO=spiImageServiceImpl_1.queryUniqueImage_1(imageQo);
			HgLogger.getInstance().info("lxs_dev", "【addAd】"+"查询图片服务里得到结果：" + JSON.toJSONString(imageDTO));
			Map<String, String> map = new HashMap<String, String>();
			for (String key : imageDTO.getSpecImageMap().keySet()) {
				FdfsFileInfo fdfsFileInfo = JSON.parseObject(imageDTO.getSpecImageMap().get(key).getFileInfo(), FdfsFileInfo.class);
				map.put(key, SysProperties.getInstance().get("fileUploadPath")+fdfsFileInfo.getUri());
			}
			command.setImagePath(map.get(SysProperties.getInstance().get("adImageType")));
		} catch (ImageException e) {
			HgLogger.getInstance().info("lxs_dev", "【addAd】"+"上传广告，图片服务报错"+HgLogger.getStackTrace(e));
			e.printStackTrace();
		} catch (IOException e) {
			HgLogger.getInstance().info("lxs_dev", "【addAd】"+"上传广告，图片服务IO报错"+HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		try {
			LxsAdQO lxsAdQO = new LxsAdQO();
			LxsAdPositionQO lxsAdPositionQO = new LxsAdPositionQO();
			lxsAdPositionQO.setProjectId(SysProperties.getInstance().get("adProjectId"));
			lxsAdPositionQO.setIsCommon(true);
			lxsAdPositionQO.setClientType(Integer.valueOf(SysProperties.getInstance().get("adAppClientType")));
			List<AdPositionDTO> adPositionDTOs = lxsAdService.getPositionList(lxsAdPositionQO);
			if(StringUtils.isBlank(lxsAdQO.getPositionId()) && adPositionDTOs.size() > 0){
				lxsAdQO.setPositionId(adPositionDTOs.get(0).getId());
			}
			Pagination pagination = new Pagination();
			pagination.setCondition(lxsAdQO);
			pagination=lxsAdService.queryPagination(pagination);
			if(pagination.getList().size()==0){
				command.setPriority(1);
			}else{
				AdDTO adDTO=(AdDTO)pagination.getList().get(0);
				HgLogger.getInstance().info("lxs_dev", "【addAd】"+"广告新增command：" + JSON.toJSONString(command) );
				command.setPriority(adDTO.getAdBaseInfo().getPriority()+1);
			}
			lxsAdService.createAd(command);
		} catch (Exception e) {
			HgLogger.getInstance().info("lxs_dev", "【addAd】"+"广告新增报错"+HgLogger.getStackTrace(e));
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "adList");
	}
	
	@RequestMapping(value="/adList")
	public String adList(@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model,LxsAdQO lxsAdQO){
		if(pageNo==null){
			pageNo=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		LxsAdPositionQO lxsAdPositionQO = new LxsAdPositionQO();
		lxsAdPositionQO.setProjectId(SysProperties.getInstance().get("adProjectId"));
		lxsAdPositionQO.setIsCommon(true);
		lxsAdPositionQO.setClientType(Integer.valueOf(SysProperties.getInstance().get("adAppClientType")));
		List<AdPositionDTO> adPositionDTOs = lxsAdService.getPositionList(lxsAdPositionQO);
		if(StringUtils.isBlank(lxsAdQO.getPositionId()) && adPositionDTOs.size() > 0){
			lxsAdQO.setPositionId(adPositionDTOs.get(0).getId());
		}
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(lxsAdQO);
		pagination= lxsAdService.queryPagination(pagination);
		model.addAttribute("adQO", lxsAdQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("adPosition", adPositionDTOs.get(0));
		return "/advertisement/adList.html";
	}
	
	@RequestMapping(value="/addAdView")
	public String addAdView(Model model){
		LxsAdPositionQO lxsAdPositionQO = new LxsAdPositionQO();
		lxsAdPositionQO.setProjectId(SysProperties.getInstance().get("adProjectId"));
		lxsAdPositionQO.setIsCommon(true);
		lxsAdPositionQO.setClientType(Integer.valueOf(SysProperties.getInstance().get("adAppClientType")));
		List<AdPositionDTO> adPositionDTOs = lxsAdService.getPositionList(lxsAdPositionQO);
		model.addAttribute("position", adPositionDTOs.get(0));
		return "advertisement/addAd.html";
	}
	
	@RequestMapping(value="/editAd")
	public String editAd(HttpServletRequest request,
					   Model model, @RequestParam(value="id", required=false)String id){
		LxsAdQO qo = new LxsAdQO();
		qo.setId(id);
		model.addAttribute("ad", lxsAdService.queryAd(qo));
		return "advertisement/editAd.html";
	}
	
	@ResponseBody
	@RequestMapping(value="/modifyAd")
	public String modifyAd(LxsUpdateAdCommand command){
		// 1. 默认返回值
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try {
			LxsAdQO qo = new LxsAdQO();
			qo.setId(command.getId());
			AdDTO adDTO=lxsAdService.queryAd(qo);
			LxsCreateAdCommand command2 = new LxsCreateAdCommand();
			//前台可更新
			command2.setTitle(command.getTitle());
			command2.setImageInfo(command.getImageInfo());
			CreateImageCommand createImageCommand = new CreateImageCommand();
			createImageCommand.setTitle(command.getTitle());
			//利旧URL存储file的json
			createImageCommand.setFileInfo(command.getImageInfo());
			createImageCommand.setFileName(command.getTitle());
			createImageCommand.setRemark("旅行社APP广告");
			createImageCommand.setUseTypeId(SysProperties.getInstance().get("imageUseTypeId"));
			createImageCommand.setFromProjectEnvName(SysProperties.getInstance().get("imageEnvName"));
			createImageCommand.setFromProjectId(SysProperties.getInstance().get("imageProjectId"));
			createImageCommand.setAlbumId(SysProperties.getInstance().get("imageUseTypeId"));
			try {
				if(command.getImageInfo().length()!=1){
					String imageID=spiImageServiceImpl_1.createImage_1(createImageCommand);
					ImageQo imageQo = new ImageQo();
					imageQo.setId(imageID);
					ImageDTO imageDTO=spiImageServiceImpl_1.queryUniqueImage_1(imageQo);
					Map<String, String> map = new HashMap<String, String>();
					for (String key : imageDTO.getSpecImageMap().keySet()) {
						FdfsFileInfo fdfsFileInfo = JSON.parseObject(imageDTO.getSpecImageMap().get(key).getFileInfo(), FdfsFileInfo.class);
						map.put(key, SysProperties.getInstance().get("fileUploadPath")+fdfsFileInfo.getUri());
					}
					command.setImagePath(map.get(SysProperties.getInstance().get("adImageType")));
				}
			} catch (ImageException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			command2.setImagePath(command.getImagePath());
			command2.setUrl(command.getUrl());
			//原广告获取
			command2.setPriority(adDTO.getAdBaseInfo().getPriority());
			command2.setIsShow(adDTO.getAdStatus().getIsShow());
			command2.setPositionId(adDTO.getPosition().getId());
			command2.setFileName(adDTO.getAdBaseInfo().getFileName());
			//删除
			LxsDeleteAdCommand command3= new LxsDeleteAdCommand();
			command3.setAdId(adDTO.getId());
			command3.setPositionId(adDTO.getPosition().getId());
			command3.setImageId(adDTO.getAdBaseInfo().getImageId());
			lxsAdService.deletAd(command3);
			//添加
			lxsAdService.createAd(command2);
		} catch (Exception e) {
			HgLogger.getInstance().info("lxs_dev", "【modifyAd】"+"异常，"+HgLogger.getStackTrace(e));
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = e.getMessage();
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message, callbackType, "adList");
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteAd")
	public String deleteAd(LxsDeleteAdCommand command){
		try{
			lxsAdService.deletAd(command);
		}catch(Exception e){
			HgLogger.getInstance().info("lxs_dev", "【deleteAd】"+"异常，"+HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "广告删除失败", null, "");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "广告删除成功", null, "");
	}
	
	@ResponseBody
	@RequestMapping(value="/changePriority")
	public String changePriority(@RequestParam(value = "id") String adID,@RequestParam(value = "type") String type){
		//查询当前广告
		LxsAdQO lxsAdQO = new LxsAdQO();
		lxsAdQO.setId(adID);
		AdDTO adDTO= lxsAdService.queryAd(lxsAdQO);
		//获取所有广告
		lxsAdQO = new LxsAdQO();
		LxsAdPositionQO lxsAdPositionQO = new LxsAdPositionQO();
		lxsAdPositionQO.setProjectId(SysProperties.getInstance().get("adProjectId"));
		lxsAdPositionQO.setIsCommon(true);
		lxsAdPositionQO.setClientType(Integer.valueOf(SysProperties.getInstance().get("adAppClientType")));
		List<AdPositionDTO> adPositionDTOs = lxsAdService.getPositionList(lxsAdPositionQO);
		lxsAdQO.setPositionId(adPositionDTOs.get(0).getId());
		Pagination pagination = new Pagination();
		pagination.setCondition(lxsAdQO);
		pagination=lxsAdService.queryPagination(pagination);
		List<AdDTO> adDTOs= (List<AdDTO>) pagination.getList();
		int i=0;
		for(AdDTO adDTO2:adDTOs){
			if(adDTO2.getAdBaseInfo().getPriority()==adDTO.getAdBaseInfo().getPriority()){
				if(i==0&&StringUtils.equals("up", type)){
					return DwzJsonResultUtil.createSimpleJsonString("500", "该广告已在最第一位");
				}else if((i+1)==adDTOs.size()&&StringUtils.equals("down", type)){
					return DwzJsonResultUtil.createSimpleJsonString("500", "该广告已在最末尾");
				}else{
					if(StringUtils.equals("down", type)){
						LxsUpdateAdCommand lxsUpdateAdCommand = new LxsUpdateAdCommand();
						lxsUpdateAdCommand.setId(adDTOs.get(i).getId());
						lxsUpdateAdCommand.setPriority(adDTOs.get(i+1).getAdBaseInfo().getPriority());
						lxsAdService.modifyAd(lxsUpdateAdCommand);
						lxsUpdateAdCommand = new LxsUpdateAdCommand();
						lxsUpdateAdCommand.setId(adDTOs.get(i+1).getId());
						lxsUpdateAdCommand.setPriority(adDTO.getAdBaseInfo().getPriority());
						lxsAdService.modifyAd(lxsUpdateAdCommand);
						return DwzJsonResultUtil.createSimpleJsonString("200", "下移成功");
					}else{
						LxsUpdateAdCommand lxsUpdateAdCommand = new LxsUpdateAdCommand();
						lxsUpdateAdCommand.setId(adDTOs.get(i).getId());
						lxsUpdateAdCommand.setPriority(adDTOs.get(i-1).getAdBaseInfo().getPriority());
						lxsAdService.modifyAd(lxsUpdateAdCommand);
						lxsUpdateAdCommand = new LxsUpdateAdCommand();
						lxsUpdateAdCommand.setId(adDTOs.get(i-1).getId());
						lxsUpdateAdCommand.setPriority(adDTO.getAdBaseInfo().getPriority());
						lxsAdService.modifyAd(lxsUpdateAdCommand);						
						return DwzJsonResultUtil.createSimpleJsonString("200", "上移成功");
					}
				}
			}else{
				i++;
			}
		}
		return DwzJsonResultUtil.createSimpleJsonString("500", "移动失败");
	}
		
*/
}
