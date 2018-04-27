package plfx.admin.controller.traveline;
//package plfx.admin.controller.traveline;
//
//import hg.common.util.DwzJsonResultUtil;
//import hg.common.util.Md5FileUtil;
//import hg.common.util.file.FdfsFileInfo;
//import hg.common.util.file.FdfsFileUtil;
//import hg.common.util.file.SimpleFileUtil;
//import hg.log.util.HgLogger;
//import hg.service.image.pojo.exception.ImageException;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import slfx.admin.common.AdminConfig;
//import slfx.admin.controller.BaseController;
//import slfx.xl.pojo.command.line.BatchCreateLineImageCommand;
//import slfx.xl.pojo.command.line.BatchCreateLinePictureCommand;
//import slfx.xl.pojo.command.line.BatchDeleteLinePictureCommand;
//import slfx.xl.pojo.command.line.CreateLineImageCommand;
//import slfx.xl.pojo.command.line.CreateLinePictureCommand;
//import slfx.xl.pojo.command.line.CreateLinePictureFolderCommand;
//import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
//import slfx.xl.pojo.dto.line.LineDTO;
//import slfx.xl.pojo.dto.line.LinePictureFolderDTO;
//import slfx.xl.pojo.exception.SlfxXlException;
//import slfx.xl.pojo.qo.LinePictureFolderQO;
//import slfx.xl.pojo.qo.LinePictureQO;
//import slfx.xl.pojo.qo.LineQO;
//import slfx.xl.pojo.system.LineMessageConstants;
//import slfx.xl.spi.inter.LinePictureFolderService;
//import slfx.xl.spi.inter.LineService;
//import slfx.xl.spi.inter.LineSnapshotService;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//
///**
// * @类功能说明：图片文件夹Controller
// * @公司名称：浙江汇购科技有限公司
// * @部门：技术部
// * @作者：chenyanshuo
// * @创建时间：2014年12月18日上午9:40:29
// * @版本：V1.0
// */
//@Controller
//@RequestMapping("/traveline-folder")
//public class LinePictureFolderController extends BaseController {
//	public final static String DZPW_SCENIC_USETYPE = "景区图片";//电子票务的景区相册用途
//	public final static double FILE_MAX_SIZE = 2;//图片大小上限(2M)
//	/*
//	@Autowired
//	private HgLogger hgLogger;//MongoDB日志
//	*/
//	@Autowired
//	private LinePictureFolderService picfolSer;
//	@Autowired
//	private LineService lineService;
//	@Autowired
//	private LineSnapshotService lineSnapshotService;
//	/**
//	 * @方法功能说明：线路图片页面
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午3:47:48
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/show")
//	public String show(@ModelAttribute LineQO lineQO,Model model){
//		try {
//			if(StringUtils.isBlank(lineQO.getId())){
//				throw new SlfxXlException(SlfxXlException.LINE_ID_IS_NULL,"线路ID不能为空！");				
//			}
//			
//			LinePictureFolderQO folderQO = this.factoryFolderQO(null,null,lineQO);
//			folderQO.setCreateDateSort(1);
//			List<LinePictureFolderDTO> folderList = picfolSer.queryListFolder(folderQO);
//			
//			//数据对象返回
//			model.addAttribute("folderList",folderList);
//			model.addAttribute("lineId",lineQO.getId());
//		} catch (Exception e) {
//			HgLogger.getInstance().error("chenyanshou","[show] 线路图片页面："+HgLogger.getStackTrace(e));
//		}
//		return "/traveline/line/line_pictureFolder.html";
//	}
//	
//	/**
//	 * @方法功能说明：图片文件夹保存提交
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午3:48:48
//	 * @param navTabid
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/saveFolderSubmit", method = { RequestMethod.POST })
//	public String saveFolderSubmit(@RequestParam(value="lineId",required=false) String lineId){
//		
//		Map<String,Object> remap = new HashMap<String, Object>(3);
//		try {
//			if(StringUtils.isBlank(lineId)){
//				throw new SlfxXlException(SlfxXlException.LINE_ID_IS_NULL,"线路ID不能为空！");				
//			}
//			
//			//添加数据
//			CreateLinePictureFolderCommand createFolder = new CreateLinePictureFolderCommand();
//			createFolder.setLineId(lineId);
//			LinePictureFolderDTO folderDto = picfolSer.createFolder(createFolder);
//			
//			// 返回json数据
//			remap.put("rs", 1);
//			remap.put("name",folderDto.getName());
//			remap.put("folderId",folderDto.getId());
//		} catch (Exception e) {
//			String showMsg = "未知异常";
//			if(e instanceof SlfxXlException){
//				e = (SlfxXlException)e;
//				showMsg = e.getMessage();
//			}
//			remap.put("rs",0);
//			remap.put("msg",showMsg);
//			HgLogger.getInstance().error("chenyanshou","[saveFolderSubmit] 图片文件夹保存提交："+HgLogger.getStackTrace(e));
//		}
//		return JSON.toJSONString(remap,SerializerFeature.DisableCheckSpecialChar);
//	}
//	
//	/**
//	 * @方法功能说明：图片上传
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午9:27:52
//	 * @param file
//	 * @param title
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/imgUpload")
//	public String imgUpload(@RequestParam(value="file",required = false) CommonsMultipartFile file){
//		
//		Map<String,Object> remap = new HashMap<String, Object>(3);
//		try {
//			if(null == file || null == file.getFileItem()){
//				throw new SlfxXlException(SlfxXlException.PICTURE_IMAGE_NOT_EXISTS,"图片不能为空！");				
//			}
//			
//			//获取上传图片文件，拷贝至自定义临时目录下
//			String imgName = this.imageHandler(file);
//			
//			// 返回json数据
//			remap.put("rs", 1);
//			remap.put("imgName",imgName);
//		} catch (Exception e) {
//			String showMsg = "未知异常";
//			if(e instanceof SlfxXlException){
//				e = (SlfxXlException)e;
//				showMsg = e.getMessage();
//			}
//			remap.put("rs",0);
//			remap.put("msg",showMsg);
//			HgLogger.getInstance().error("chenyanshou","[imgUpload] 线路图片-图片上传失败："+HgLogger.getStackTrace(e));
//		}finally{
//			//上传成功后，将临时文件删除
//			if(file != null && file.getFileItem() != null){
//				file.getFileItem().delete();
//			}
//		}
//		return JSON.toJSONString(remap,SerializerFeature.DisableCheckSpecialChar);
//	}
//	
//	/**
//	 * @方法功能说明: 图片处理 
//	 * @param file
//	 * @return
//	 * @throws IOException
//	 * @throws SlfxXlException 
//	 */
//	public String imageHandler(CommonsMultipartFile file) throws IOException, SlfxXlException{
//		
//		if(null == file || null == file.getFileItem()){
//			return null;			
//		}
//		
//		//判断图片类型
//		String orginName = file.getFileItem().getName();
//		String fileExt = StringUtils.substringAfterLast(orginName, ".").toLowerCase();
//		if(!fileExt.endsWith("jpg") && !fileExt.endsWith("gif")
//				&& !fileExt.endsWith("png") && !fileExt.endsWith("bmp")){
//			throw new SlfxXlException(SlfxXlException.PICTURE_IMAGETYPE_NOT_REQUIRE,"不能上传jpg、gif、png或bmp格式以外的图片");
//		}
//		
//		//验证图片大小
//		double size = file.getSize()/1024.0/1024.0;
//		if(size > FILE_MAX_SIZE){
//			throw new SlfxXlException(SlfxXlException.PICTURE_IMAGESIZE_GT_SCOPE,"上传图片不能大于"+FILE_MAX_SIZE+"M");			
//		}
//		
//		//临时文件路径
//		String tempPath = SimpleFileUtil.getPathToRename(orginName);
//		tempPath = tempPath.replace(File.separator+"temp",File.separator+"webapps"+File.separator+"group0");
//		File tempFile = new File(tempPath);
//		
//		FileUtils.copyInputStreamToFile(file.getInputStream(),tempFile);
//		//返回文件名
//		return tempFile.getName();
//	}
//	
//	/**
//	 * @方法功能说明：线路图片保存提交
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-18下午3:49:03
//	 * @param navTabid
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/savePictureSubmit", method = { RequestMethod.POST })
//	public String savePictureSubmit(
//			@RequestParam(value="names",required=false) String[] names,
//			@RequestParam(value="imgNames",required=false) String[] imgNames,
//			@RequestParam(value="pictureIds",required=false) String[] pictureIds,
//			@RequestParam(value="imgFlags",required=false) String[] imgFlags,
//			@RequestParam(value="folderId",required=false) String folderId,
//			@RequestParam(value="navTabid",required=false) String navTabid){
//		
//		try {
//			if(names.length != imgNames.length && imgNames.length != pictureIds.length 
//					&& pictureIds.length != imgFlags.length){
//				throw new SlfxXlException(SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"线路图片参数错误！");
//			}
//			
//			List<Integer> creates = new ArrayList<Integer>(0);//新增列
//			List<String> deletes = new ArrayList<String>(0);//删除列
//			for(int i = 0,len = imgFlags.length;i < len;i++){
//				if(StringUtils.isBlank(imgFlags[i])){
//					continue;
//				}
//				
//				if("+".equals(imgFlags[i])){
//					if(StringUtils.isBlank(names[i]) || StringUtils.isBlank(imgNames[i])){
//						throw new SlfxXlException(SlfxXlException.PICTURE_WITHOUTPARAM_NOT_EXISTS,"线路图片或图片名称不能为空！");
//					}
//					creates.add(i);
//				}
//				if("-".equals(imgFlags[i])){
//					if(StringUtils.isBlank(pictureIds[i])){
//						throw new SlfxXlException(SlfxXlException.PICTURE_ID_IS_NULL,"线路图片ID不能为空！");
//					}
//					deletes.add(pictureIds[i]);
//				}
//			}
//			
//			//判断是否需要新增
//			if(creates.size() > 0){
//				if(StringUtils.isBlank(folderId)){
//					throw new SlfxXlException(SlfxXlException.FOLDER_ID_IS_NULL,"线路文件夹ID不能为空！");
//				}
//				List<CreateLinePictureCommand> createPics = new ArrayList<CreateLinePictureCommand>(0);
//				List<CreateLineImageCommand> createImgs = new ArrayList<CreateLineImageCommand>(0);
//				String uri = null;
//				//获取文件并上传至DFS
//				List<FdfsFileInfo> infoList = new ArrayList<FdfsFileInfo>(0);
//				String tempPath = SimpleFileUtil.getTempDirectoryPath();
//				tempPath = tempPath.replace(File.separator+"temp",File.separator+"webapps"+File.separator+"group0");
//				for(Integer index : creates){
//					File tempFile = new File(tempPath+imgNames[index]);
//					
//					if(!SimpleFileUtil.isFile(tempFile)){
//						throw new SlfxXlException(SlfxXlException.PICTURE_IMAGE_NOT_EXISTS,"图片不能为空！");
//					}
//					FdfsFileUtil.init();
//					FdfsFileInfo fileInfo = FdfsFileUtil.upload(tempFile,null);
//					//设置元数据集
//					Map<String, String> metaMap = new HashMap<String, String>(1);
//					metaMap.put("md5",Md5FileUtil.getMD5(tempFile));
//					fileInfo.setMetaMap(metaMap);
//					infoList.add(fileInfo);
//					//删除临时图片
//					tempFile.delete();
//					
//					//线路图片
//					uri = AdminConfig.imageHost+fileInfo.getUri();
//					createPics.add(new CreateLinePictureCommand(names[index],uri,folderId));
//					//图片服务
//					createImgs.add(new CreateLineImageCommand(imgNames[index],names[index],fileInfo,null,names[index]));
//				}
//				List<String> idList = picfolSer.createPicture(new BatchCreateLinePictureCommand(createPics));
//				/**
//				 * 这里可能会有偏差，即线路图片和图片服务的数据对应不上的问题，因为如果网络延迟或各种原因
//				 * 后期优化++++++++
//				 */
//				for(int i = 0,len = createPics.size();i < len;i++){
//					createImgs.get(i).setFromId(idList.get(i));
//				}
//				BatchCreateLineImageCommand batch = new BatchCreateLineImageCommand(createImgs);
//				picfolSer.createLineImage(batch);
//			}
//			
//			//判断是否需要删除
//			if(deletes.size() > 0){
//				picfolSer.deletePicture(new BatchDeleteLinePictureCommand(deletes));
//			}
//		} catch (Exception e) {
//			String showMsg = "未知异常";
//			if(e instanceof SlfxXlException){
//				e = (SlfxXlException)e;
//				showMsg = e.getMessage();
//			}
//			if(e instanceof ImageException){
//				e = (ImageException)e;
//				showMsg = e.getMessage();
//			}
//			
//			HgLogger.getInstance().error("chenyanshou","[savePictureSubmit] 线路图片保存提交："+HgLogger.getStackTrace(e));
//			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_500,"线路图片维护失败:"+showMsg);
//		}
//		LinePictureFolderQO qo = new LinePictureFolderQO();
//		qo.setId(folderId);
//		LinePictureFolderDTO linePictureFolder = picfolSer.queryUniqueFolder(qo);
//		if(linePictureFolder != null){
//			LineDTO line = linePictureFolder.getLine();
//			if(line != null && line.getStatusInfo().getStatus() == 4){
//				lineSnapshotService.createLineSnapshot(line);
//				XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
//				apiCommand.setLineId(line.getId());
//				apiCommand.setStatus(LineMessageConstants.UPDATE_PICTURE);
//				lineService.sendLineUpdateMessage(apiCommand);
//			}
//		}
//		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200,
//				"线路图片操作成功",null,navTabid);
//	}
//	
//	/**
//	 * @方法功能说明：线路图片文件夹Qo的简单工厂方法
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-21下午4:40:41
//	 * @param name
//	 * @param lineQO
//	 * @return
//	 */
//	public LinePictureFolderQO factoryFolderQO(String id,String name,LineQO lineQO){
//		LinePictureFolderQO qo = new LinePictureFolderQO(id);
//		qo.setName(name);
//		qo.setNameLike(true);
//		qo.setLineQO(lineQO);
//		qo.setLineFetchAble(true);
//		qo.setCreateDateSort(-1);
//		return qo;
//	}
//	/**
//	 * @方法功能说明：线路图片Qo的简单工厂方法
//	 * @修改者名字：chenyanshou
//	 * @修改时间：2014-12-21下午4:40:41
//	 * @param name
//	 * @param lineQO
//	 * @return
//	 */
//	public LinePictureQO factoryPictureQO(String id,String name,LinePictureFolderQO folderQO){
//		LinePictureQO qo = new LinePictureQO(id);
//		qo.setName(name);
//		qo.setNameLike(true);
//		qo.setFolderQO(folderQO);
//		qo.setFolderFetchAble(true);
//		qo.setCreateDateSort(-1);
//		return qo;
//	}
//}