package zzpl.api.action.user;

import hg.common.util.BeanMapperUtils;
import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.log.util.HgLogger;
import hg.service.image.command.image.CreateImageCommand;
import hg.service.image.command.image.DeleteImageCommand;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.dto.ImageSpecDTO;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.spi.inter.ImageService_1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.request.user.ModifyUserCommand;
import zzpl.api.client.response.user.ModifyUserResponse;
import zzpl.api.util.ZZPLFdfsFileUtil;
import zzpl.app.service.local.user.UserService;

import com.alibaba.fastjson.JSON;

@Component("ModifyUserAction")
public class ModifyUserAction implements CommonAction {

	@Autowired
	private UserService userService;
	@Autowired
	private ImageService_1 imageService_1;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		ModifyUserResponse modifyUserResponse = new ModifyUserResponse();
		try{
			ModifyUserCommand modifyUserCommand  = JSON.parseObject(apiRequest.getBody().getPayload(),ModifyUserCommand.class);
			HgLogger.getInstance().info("cs", "【ModifyUserAction】"+"modifyUserCommand:"+JSON.toJSONString(modifyUserCommand));
			zzpl.pojo.command.user.ModifyUserCommand zzplModifyUserCommand = BeanMapperUtils.getMapper().map(modifyUserCommand, zzpl.pojo.command.user.ModifyUserCommand.class);
			if(modifyUserCommand.getImageInfo()!=null&&StringUtils.isNotBlank(modifyUserCommand.getImageInfo())){
				//上传到文件服务
				FdfsFileInfo fdfsFileInfo = upload(modifyUserCommand.getUserID(), Base64.decode(modifyUserCommand.getImageInfo()));
				//获取当前用户
				String imageID=userService.get(modifyUserCommand.getUserID()).getImageID();
				//判断当前用户是否有头像
				if(StringUtils.isNotBlank(imageID)){
					//如果有删除掉当前用户在图片服务中的头像
					DeleteImageCommand deleteImageCommand = new DeleteImageCommand();
					List<String> ids= new LinkedList<String>();
					ids.add(imageID);
					deleteImageCommand.setImageIds(ids);
					imageService_1.deleteImage_1(deleteImageCommand);
				}
				//定义图片服务command
				CreateImageCommand createImageCommand = new CreateImageCommand();
				createImageCommand.setTitle(modifyUserCommand.getUserID());
				createImageCommand.setFileInfo(JSON.toJSONString(fdfsFileInfo));
				createImageCommand.setFileName(modifyUserCommand.getUserID());
				createImageCommand.setUseTypeId(SysProperties.getInstance().get("userHead"));
				createImageCommand.setFromProjectEnvName(SysProperties.getInstance().get("imageProjectID"));
				createImageCommand.setFromProjectId(SysProperties.getInstance().get("imageProjectID"));
				createImageCommand.setAlbumId(SysProperties.getInstance().get("userHead"));
				imageID = imageService_1.createImage_1(createImageCommand);
				ImageQo imageQo = new ImageQo();
				imageQo.setId(imageID);
				ImageDTO imageDTO=imageService_1.queryUniqueImage_1(imageQo);
				Map<String, ImageSpecDTO> map =imageDTO.getSpecImageMap();
				ImageSpecDTO imageSpecDTO = map.get(SysProperties.getInstance().get("userHeadSize"));
				FdfsFileInfo fdfsFileInfo1 = JSON.parseObject(imageSpecDTO.getFileInfo(), FdfsFileInfo.class);
				zzplModifyUserCommand.setImage(fdfsFileInfo1.getUri());
				zzplModifyUserCommand.setImageID(imageID);
			}
			userService.modfiyUser(zzplModifyUserCommand);
			modifyUserResponse.setMessage("修改成功");
			modifyUserResponse.setResult(ApiResponse.RESULT_CODE_OK);
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【ModifyUserAction】"+"异常，"+HgLogger.getStackTrace(e));
			modifyUserResponse.setMessage("修改失败");
			modifyUserResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
		}
		HgLogger.getInstance().info("cs", "【ModifyUserAction】"+"modifyUserResponse:"+JSON.toJSONString(modifyUserResponse));
		return JSON.toJSONString(modifyUserResponse);
	}

	/**
	 * 上传图片
	 * @param image_id
	 * @param bytearry
	 * @return
	 */
	public static FdfsFileInfo upload(String imageName,byte[] bytearry){
		FdfsFileInfo fileInfo = null;
		try {
			InputStream stream = new ByteArrayInputStream(bytearry,0,bytearry.length);
			java.awt.Image image = ImageIO.read(stream);
			Map<String,String> metaMap=new HashMap<String,String>();
			metaMap.put("title", imageName);
			metaMap.put("height", String.valueOf(image.getHeight(null)));
			metaMap.put("width", String.valueOf(image.getWidth(null)));
			ZZPLFdfsFileUtil.init();
			fileInfo= ZZPLFdfsFileUtil.upload(bytearry,SysProperties.getInstance().get("imageType"),metaMap);
		} catch (IOException e) {
			HgLogger.getInstance().info("cs", "【upload】"+"头像上传失败"+HgLogger.getStackTrace(e));
		}
		return fileInfo;
	}
}
