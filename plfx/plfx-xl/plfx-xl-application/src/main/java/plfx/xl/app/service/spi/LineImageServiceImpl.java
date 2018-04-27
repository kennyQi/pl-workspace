package plfx.xl.app.service.spi;

import hg.common.util.EntityConvertUtils;
import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.log.util.HgLogger;
import hg.service.image.command.image.CreateImageCommand;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.pojo.qo.AlbumQo;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.spi.inter.ImageService_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.xl.app.component.base.BaseXlSpiServiceImpl;
import plfx.xl.app.service.local.LineImageLocalService;
import plfx.xl.domain.model.line.LineImage;
import plfx.xl.pojo.command.line.CreateLineImageCommand;
import plfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import plfx.xl.pojo.dto.line.LineDTO;
import plfx.xl.pojo.dto.line.LineImageDTO;
import plfx.xl.pojo.message.SynchronizationLineMessage;
import plfx.xl.pojo.qo.LineImageQO;
import plfx.xl.pojo.system.LineMessageConstants;
import plfx.xl.spi.inter.LineImageService;
import plfx.xl.spi.inter.LineSnapshotService;

import com.alibaba.fastjson.JSON;


@Service("lineImageService")
public class LineImageServiceImpl extends BaseXlSpiServiceImpl<LineImageDTO, LineImageQO, LineImageLocalService>
		implements LineImageService{
	
	@Autowired
	private LineImageLocalService lineImageLocalService;
	@Autowired
	private ImageService_1 imageService;
	@Autowired
	private RabbitTemplate template;
	@Autowired
	private LineSnapshotService lineSnapshotService;
	
	@Override
	public List<LineImageDTO> getLineImages(LineImageQO lineImageQO) {
		List<LineImage> imageList = lineImageLocalService.queryList(lineImageQO);
		return EntityConvertUtils.convertEntityToDtoList(imageList, LineImageDTO.class);
	}

	@Override
	protected LineImageLocalService getService() {
		return lineImageLocalService;
	}

	@Override
	protected Class<LineImageDTO> getDTOClass() {
		return LineImageDTO.class;
	}

	@Override
	public void modifyLineImage(CreateLineImageCommand command) {
		String fdfsFileInfoJSON = command.getFdfsFileInfoJSON();
		if(null != fdfsFileInfoJSON){
			String[] infos = fdfsFileInfoJSON.split("&365");//如果遇到文件名称带这个字符串保存图片就会出错
//			HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->modifyLineImage->infos长度:" + infos.length);
				if(infos.length > 0){
					for(int i = 0; i < infos.length; i++){
						HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->modifyLineImage->修改图片:" + infos[i].toString());
						if(infos[i].split("=")[0].equals("imageId")){
							//删除图片
							HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->modifyLineImage->开始删除图片>>>>>");
							LineImageQO lineImageQO = new LineImageQO();
							lineImageQO.setImageId(infos[i].split("=")[1]);
							if(null != command.getDayRouteId()){
								lineImageQO.setDayRouteId(command.getDayRouteId());
							}
							LineImage lineImage =  lineImageLocalService.queryUnique(lineImageQO);
							HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->modifyLineImage->删除图片信息:" + JSON.toJSONString(lineImage));
							if(null != lineImage){
								lineImageLocalService.deleteById(lineImage.getId());
							}
							HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->modifyLineImage->删除图片结束:");
						}else{
							//新增图片
							HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->modifyLineImage->开始新增图片>>>>>");
							CreateLineImageCommand createLineImageCommand = new CreateLineImageCommand();
							createLineImageCommand.setLineId(command.getLineId());
							createLineImageCommand.setDayRouteId(command.getDayRouteId());
							createLineImageCommand.setFdfsFileInfoJSON(infos[i]);
							FdfsFileInfo fileInfo = createLineImageCommand.getLineImageFileInfo();
							createLineImageCommand.setName(fileInfo.getMetaMap().get("title"));
							HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->modifyLineImage->新增图片createLineImageCommand:" + JSON.toJSONString(createLineImageCommand));
							createLineImage(createLineImageCommand);
						}
						
					}
					LineDTO lineDTO = new LineDTO();
					lineDTO.setId(command.getLineId());
					lineSnapshotService.createLineSnapshot(lineDTO);
					XLUpdateLineMessageApiCommand xlUpdateLineMessageApiCommand = new XLUpdateLineMessageApiCommand();
					xlUpdateLineMessageApiCommand.setLineId(command.getLineId());
					sendModifyLineImageMessage(xlUpdateLineMessageApiCommand);
				}
		}else{
			HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->modifyLineImage->图片信息为空");
		}
	}
	
	/**
	 * 
	 * @方法功能说明：发送修改线路图片通知
	 * @修改者名字：yuqz
	 * @修改时间：2015年5月6日下午3:27:32
	 * @修改内容：
	 * @参数：@param modifyLineMinPriceCommand
	 * @return:void
	 * @throws
	 */
	private void sendModifyLineImageMessage(XLUpdateLineMessageApiCommand xlUpdateLineMessageApiCommand) {
		SynchronizationLineMessage baseAmqpMessage=new SynchronizationLineMessage();
		XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
		apiCommand.setLineId(xlUpdateLineMessageApiCommand.getLineId());
		apiCommand.setStatus(LineMessageConstants.UPDATE_LINE_IMAGE);
		baseAmqpMessage.setContent(apiCommand);
		baseAmqpMessage.setType(Integer.parseInt(LineMessageConstants.UPDATE_LINE_IMAGE));//发送修改线路图片通知
		baseAmqpMessage.setSendDate(new Date());
		baseAmqpMessage.setArgs(null);
		try{
			template.convertAndSend("plfx.xl.modifyLineMinPriceMessage",baseAmqpMessage);			
			
			HgLogger.getInstance().info("tandeng","LineServiceImpl->sendLineUpdateMessage-成功:"+ JSON.toJSONString(baseAmqpMessage));
		}catch(Exception e){
			HgLogger.getInstance().error("tandeng","LineServiceImpl->sendLineUpdateMessage-失败:"+ JSON.toJSONString(baseAmqpMessage));
		}
	}
	
	/**
	 * 
	 * @方法功能说明：向图片服务创建线路图片
	 * @修改者名字：yuqz
	 * @修改时间：2015年4月24日上午10:43:08
	 * @修改内容：
	 * @参数：@param tempFileInfo
	 * @参数：@param title
	 * @参数：@param remark
	 * @参数：@return
	 * @return:Image
	 * @throws
	 */
	private LineImage createLineImage(CreateLineImageCommand command) {
		HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->createLineImage:projectId_image=" + 
				SysProperties.getInstance().get("projectId_image") + ",envId_image=" + 
						SysProperties.getInstance().get("envId_image"));
		FdfsFileInfo tempFileInfo = command.getLineImageFileInfo();
		// 调用图片服务一定要有图片服务中所配的工程id和环境名，可能会和CC控制中心所配的有不同，因此在CC中要添加两个自定义属性
		CreateImageCommand createImageCommand = new CreateImageCommand(
				SysProperties.getInstance().get("projectId_image"),
				SysProperties.getInstance().get("envId_image"));
		createImageCommand.setAlbumId("SLFX_PRODUCT_XL");
		createImageCommand.setFileInfo(JSON.toJSONString(tempFileInfo));
		createImageCommand.setTitle(command.getName());
		createImageCommand.setRemark(command.getName());
		createImageCommand.setUseTypeId("SLFX_PRODUCT_XL"); // 用途id已提前在图片服务后台建好，并设定了裁剪副本数量和尺寸
		HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->createLineImage->向图片服务创建图片command：" + JSON.toJSONString(createImageCommand));
		String imageId = null;
		try {
			imageId = imageService.createImage_1(createImageCommand);
			HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->createLineImage->向图片服务创建图片返回的图片id：" + imageId);
		} catch (ImageException e) {
			HgLogger.getInstance().error("yuqz", "LineImageServiceImpl->createLineImage->error:" + HgLogger.getStackTrace(e));
		} catch (IOException e) {
			HgLogger.getInstance().error("yuqz", "LineImageServiceImpl->createLineImage->error:" + HgLogger.getStackTrace(e));
		}
		if(null == imageId){
			return null;
		}
		ImageQo imageQo = new ImageQo();
		imageQo.setId(imageId);
		AlbumQo albumQo=new AlbumQo();
		albumQo.setId("SLFX_PRODUCT_XL");
		imageQo.setAlbumQo(albumQo);
		ImageDTO imageDTO = imageService.queryUniqueImage_1(imageQo);
		HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->createLineImage->根据图片id查询imageDTO：" + JSON.toJSONString(imageDTO));
		command.setImageId(imageId);
		return convertLineImage(imageDTO,command);
	}
	
	/**
	 * 
	 * @方法功能说明：根据图片服务返回的ImageDTO组装成本地LineImage值对象
	 * @修改者名字：yuqz
	 * @修改时间：2015年4月24日上午10:46:44
	 * @修改内容：
	 * @参数：@param imageDTO
	 * @参数：@return
	 * @return:Image
	 * @throws
	 */
	private LineImage convertLineImage(ImageDTO imageDTO, CreateLineImageCommand command) {
		LineImage lineImage = new LineImage();
		lineImage.create(imageDTO,command);
		HgLogger.getInstance().info("yuqz", "LineImageServiceImpl->convertLineImage->本地保存图片信息：" + JSON.toJSONString(lineImage));
		lineImageLocalService.saveArray(lineImage);
		return lineImage;
	}

	@Override
	public List<LineImageDTO> queryAllImage(LineImageQO lineImageQO) {
		List<LineImage> lineImageList = new ArrayList<LineImage>();
		lineImageList = lineImageLocalService.queryList(lineImageQO);
		return EntityConvertUtils.convertEntityToDtoList(lineImageList, LineImageDTO.class);
	}

}

