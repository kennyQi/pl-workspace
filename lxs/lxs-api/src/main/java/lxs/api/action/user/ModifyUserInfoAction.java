package lxs.api.action.user;

import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.log.util.HgLogger;
import hg.service.image.command.image.CreateImageCommand;
import hg.service.image.command.image.DeleteImageCommand;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.spi.inter.ImageService_1;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;
import hg.system.service.impl.AreaServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.controller.util.sign.Base64;
import lxs.api.v1.request.command.user.ModifyUserInfoCommand;
import lxs.api.v1.response.user.ModifyUserInfoResponse;
import lxs.app.service.user.LxsUserService;
import lxs.app.util.user.LXSFdfsFileUtil;
import lxs.domain.model.user.Image;
import lxs.domain.model.user.LxsUser;
import lxs.pojo.exception.user.UserException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("ModifyUserInfoAction")
public class ModifyUserInfoAction implements LxsAction {

	@Autowired
	private LxsUserService userService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private AreaServiceImpl areaServiceImpl;
	@Autowired
	private HgLogger hgLogger;
	@Autowired
	private ImageService_1 spiImageServiceImpl_1;
	
	private static String IMAGE_TYPE="png";

	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "【ModifyUserInfoAction】"+ "进入action");
		ModifyUserInfoCommand apicommand = JSON.parseObject(apiRequest.getBody().getPayload(), ModifyUserInfoCommand.class);
		lxs.pojo.command.user.ModifyUserInfoCommand command = new lxs.pojo.command.user.ModifyUserInfoCommand();
		command.setNickName(apicommand.getUserDTO().getBaseInfo().getNickName());
		command.setGender(apicommand.getUserDTO().getBaseInfo().getGender());
		command.setBirthDay(apicommand.getUserDTO().getBaseInfo().getBirthday());
		command.setEmail(apicommand.getUserDTO().getContactInfo().getEmail());
		command.setProvinceId(apicommand.getUserDTO().getContactInfo().getProvinceId());
		command.setCityId(apicommand.getUserDTO().getContactInfo().getCityId());
//		command.setImg_title(apicommand.getUserDTO().getAuthInfo().getLoginName()+"_USER_HEAD_IMAGE");
//		command.setImg_image_bytearry(apicommand.getUserDTO().getTitleImage().getImg_image_bytearry());
		HgLogger.getInstance().info("lxs_dev", "【ModifyUserInfoAction】"+apicommand.getUserDTO().getAuthInfo().getUserId() + "修改个人资料开始");
		LxsUser user = new LxsUser();
		user=userService.get(apicommand.getUserDTO().getAuthInfo().getUserId());
		ModifyUserInfoResponse modifUserInfoResponse = new ModifyUserInfoResponse();
		try{
			if(user!=null){
				Province province = new Province();
				if(apicommand.getUserDTO().getContactInfo().getProvinceId()!= null){
					HgLogger.getInstance().info("lxs_dev","【ModifyUserInfoAction】"+ apicommand.getUserDTO().getAuthInfo().getUserId() + "修改省份开始");
					ProvinceQo provinceQo = new ProvinceQo();
					provinceQo.setId(apicommand.getUserDTO().getContactInfo().getProvinceId());
					HgLogger.getInstance().info("lxs_dev","【ModifyUserInfoAction】"+ apicommand.getUserDTO().getAuthInfo().getUserId() + "查询省份开始");
					Province p=provinceService.queryUnique(provinceQo);
					if(p!=null){
						province.setName(p.getName());
						province.setId(command.getProvinceId());
						HgLogger.getInstance().info("lxs_dev", "【ModifyUserInfoAction】"+apicommand.getUserDTO().getAuthInfo().getUserId() + "修改省份成功");
					}else{
						HgLogger.getInstance().info("lxs_dev", "【ModifyUserInfoAction】"+apicommand.getUserDTO().getAuthInfo().getUserId() + "修改省份失败");
						throw new UserException(UserException.USER_NOT_FOUND, "省份不存在");
					}
				}
				City city = new City();
				if(command.getCityId()!= null){
					HgLogger.getInstance().info("lxs_dev","【ModifyUserInfoAction】"+ apicommand.getUserDTO().getAuthInfo().getUserId() + "修改城市开始");
					CityQo cityQo = new CityQo();
					cityQo.setId(command.getCityId());
					HgLogger.getInstance().info("lxs_dev","【ModifyUserInfoAction】"+ apicommand.getUserDTO().getAuthInfo().getUserId() + "查询城市开始");
					City c= cityService.queryUnique(cityQo);
					if(c!=null){
						city.setName(c.getName());
						city.setId(command.getCityId());
						HgLogger.getInstance().info("lxs_dev","【ModifyUserInfoAction】"+ apicommand.getUserDTO().getAuthInfo().getUserId() + "修改城市成功");
					}else{
						HgLogger.getInstance().info("lxs_dev", "【ModifyUserInfoAction】"+apicommand.getUserDTO().getAuthInfo().getUserId() + "修改城市失败");
						throw new UserException(UserException.USER_NOT_FOUND, "城市不存在");
					}
				}
				Image image = new Image();
				if(apicommand.getUserDTO().getTitleImage()!=null){
					if(StringUtils.isNotBlank(apicommand.getUserDTO().getTitleImage().getImg_image_bytearry())){
						image=upload(command.getImg_title(),Base64.decode(apicommand.getUserDTO().getTitleImage().getImg_image_bytearry()));
						command.setImg_url(image.getUrl());
						if(StringUtils.isNotBlank(apicommand.getUserDTO().getTitleImage().getImageId())&&StringUtils.isNotBlank(apicommand.getUserDTO().getTitleImage().getTitle())){
							//传入空图片ID和图片标题，开始修改图片
							ImageQo imageQo= new ImageQo();
							imageQo.setTitle(apicommand.getUserDTO().getTitleImage().getTitle());
							if(spiImageServiceImpl_1.queryCountImage_1(imageQo)!=0){
								List<String> list = new ArrayList<String>();
								list.add(apicommand.getUserDTO().getTitleImage().getImageId());
								DeleteImageCommand deleteImageCommand = new DeleteImageCommand();
								deleteImageCommand.setImageIds(list);
								spiImageServiceImpl_1.deleteImage_1(deleteImageCommand);
							}
						}
						CreateImageCommand createImageCommand = new CreateImageCommand();
						createImageCommand.setTitle(user.getAuthInfo().getLoginName()+SysProperties.getInstance().get("userImageType")+"."+IMAGE_TYPE);
						//利旧URL存储file的json
						createImageCommand.setFileInfo(image.getUrl());
						createImageCommand.setFileName(image.getTitle());
						createImageCommand.setRemark("旅行社APP用户头像");
						createImageCommand.setUseTypeId(SysProperties.getInstance().get("userImageType"));
						createImageCommand.setFromProjectEnvName(SysProperties.getInstance().get("imageEnvName"));
						createImageCommand.setFromProjectId(SysProperties.getInstance().get("imageProjectId"));
						createImageCommand.setAlbumId(SysProperties.getInstance().get("userImageType"));
						try {
							String imageID=spiImageServiceImpl_1.createImage_1(createImageCommand);
							command.setImg_image_id(imageID);
							ImageQo imageQo = new ImageQo();
							imageQo.setId(imageID);
							ImageDTO imageDTO=spiImageServiceImpl_1.queryUniqueImage_1(imageQo);
							Map<String, String> map = new HashMap<String, String>();
							for (String key : imageDTO.getSpecImageMap().keySet()) {
								FdfsFileInfo fdfsFileInfo = JSON.parseObject(imageDTO.getSpecImageMap().get(key).getFileInfo(), FdfsFileInfo.class);
								map.put(key, SysProperties.getInstance().get("fileUploadPath")+fdfsFileInfo.getUri());
							}
							command.setImg_url(JSON.toJSONString(map));
							command.setImg_title(user.getAuthInfo().getLoginName()+SysProperties.getInstance().get("userImageType")+"."+IMAGE_TYPE);
						} catch (ImageException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}else{
					if(user.getTitleImage()!=null){
						if(user.getTitleImage().getImageId()!=null&&StringUtils.isNotBlank(user.getTitleImage().getImageId())){
							command.setImg_image_id(user.getTitleImage().getImageId());
						}
						if(user.getTitleImage().getTitle()!=null&&StringUtils.isNotBlank(user.getTitleImage().getTitle())){
							command.setImg_title(user.getTitleImage().getTitle());
						}
						if(user.getTitleImage().getUrl()!=null&&StringUtils.isNotBlank(user.getTitleImage().getUrl())){
							command.setImg_url(user.getTitleImage().getUrl());
						}
					}
				}
				userService.modifyUserInfo(command,user,province,city);
				user=userService.get(apicommand.getUserDTO().getAuthInfo().getUserId());
				modifUserInfoResponse.setUser(QueryUserAction.userToUserDTO(user));
				modifUserInfoResponse.setMessage("修改成功");
				modifUserInfoResponse.setResult(ApiResponse.RESULT_CODE_OK);
				HgLogger.getInstance().info("lxs_dev", "【ModifyUserInfoAction】"+apicommand.getUserDTO().getAuthInfo().getUserId() + "修改资料成功");
			}else{
				throw new UserException(UserException.USER_NOT_FOUND, "用户不存在");
			}
		}catch(UserException e){
			HgLogger.getInstance().info("lxs_dev","【ModifyUserInfoAction】"+ apicommand.getUserDTO().getAuthInfo().getUserId() + "修改个人资料失败，"+e.getMessage());
			modifUserInfoResponse.setMessage(e.getMessage());
			modifUserInfoResponse.setResult(e.getCode());
		}
		HgLogger.getInstance().info("lxs_dev", "【ModifyUserInfoAction】"+"修改资料结果"+JSON.toJSONString(modifUserInfoResponse));
		return JSON.toJSONString(modifUserInfoResponse);
	}

	/**
	 * 上传图片
	 * @param image_id
	 * @param bytearry
	 * @return
	 */
	public static Image upload(String imageName,byte[] bytearry){
		Image returnImage= new Image();
		FdfsFileInfo fileInfo = null;
		InputStream stream = new ByteArrayInputStream(bytearry,0,bytearry.length);
		try {
			java.awt.Image image = ImageIO.read(stream);
			String imageType= IMAGE_TYPE;
			Map<String,String> metaMap=new HashMap<String,String>();
			metaMap.put("title", imageName);
			metaMap.put("height", String.valueOf(image.getHeight(null)));
			metaMap.put("width", String.valueOf(image.getWidth(null)));
			LXSFdfsFileUtil.init();
			fileInfo= LXSFdfsFileUtil.upload(bytearry,imageType,metaMap);
			returnImage.setUrl(SysProperties.getInstance().get("fileUploadPath")+fileInfo.getUri());
			returnImage.setTitle(imageName+"."+imageType);
			returnImage.setUrl(JSON.toJSONString(fileInfo));
		} catch (IOException e) {
			HgLogger.getInstance().info("lxs_dev", "【Image】"+"头像上传失败"+e);
			e.printStackTrace();
		}
		return returnImage;
	}
}
