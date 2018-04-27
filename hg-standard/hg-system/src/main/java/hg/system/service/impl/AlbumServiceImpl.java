package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.command.album.AlbumCreateByUseTypeCommand;
import hg.system.command.album.AlbumCreateCommand;
import hg.system.command.album.AlbumDeleteCommand;
import hg.system.command.album.AlbumModifyCommand;
import hg.system.command.image.ImageDeleteCommand;
import hg.system.dao.AlbumDao;
import hg.system.exception.HGException;
import hg.system.model.image.Album;
import hg.system.model.image.Image;
import hg.system.qo.AlbumQo;
import hg.system.qo.ImageQo;
import hg.system.service.AlbumService;
import hg.system.service.ImageService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：相册_service实现
 * @类修改者：zzb
 * @修改日期：2014年9月3日下午3:25:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月3日下午3:25:24
 */
@Service
@Transactional
public class AlbumServiceImpl extends BaseServiceImpl<Album, AlbumQo, AlbumDao> implements AlbumService {
	
	/**
	 * 相册Dao
	 */
	@Autowired
	private AlbumDao dao;
	
	/**
	 * 图片service
	 */
	@Resource
	private ImageService imageService;
	
	@Override
	public Album albumCreate(AlbumCreateCommand command) {
		Album albumPar = null;
		if (StringUtils.isNotEmpty(command.getParentId()))
			albumPar = getDao().get(command.getParentId());

		Album album = new Album();
		album.albumCreate(command, albumPar);
		getDao().save(album);
		return album;
	}
	
	@Override
	public void checkIsChild(String albumId, String checkId) throws HGException {
		
		Album checkAlbum = getDao().get(checkId);
		
		if (checkAlbum.getParent() == null) 
			return;
		
		if (checkAlbum.getParent().getId().equals(albumId)) {
			throw new HGException(HGException.CODE_ALBUM_IS_CHILD, "被检查节点是检查节点的子节点");
		} else {
			checkIsChild(albumId, checkAlbum.getParent().getId());
		}

	}
	
	@Override
	public Album albumModify(AlbumModifyCommand command) throws HGException {
		
		// 1. 检查par是否是本身
		if(command.getAlbumId().equals(command.getParentId()))
			throw new HGException(HGException.CODE_ALBUM_PAR_IS_OWN, "上级相册不能为本身！");
		
		// 2. 检查设置的par是否是该记录原来的子节点
		try {
			checkIsChild(command.getAlbumId(), command.getParentId());
		} catch (HGException e) {
			throw new HGException(HGException.CODE_ALBUM_PAR_IS_OWN, "上级相册不能为该相册的子相册！");
		}

		// 3. 修改保存
		Album album = getDao().get(command.getAlbumId());

		Album albumPar = null;
		if (StringUtils.isNotEmpty(command.getParentId())) {
			albumPar = getDao().get(command.getParentId());
		}
		album.albumtModify(command, albumPar);
		getDao().update(album);
		return album;
			
	}
	
	@Override
	public void albumDeleteCheck(AlbumDeleteCommand command) throws HGException {
		
		// 1. 非空
		if(command == null)
			throw new HGException(HGException.CODE_ALBUM_NOT_EXIST, "请选择一条记录进行删除！");
		
		// 2. 将单个数据放到list中
		if (command.getAlbumIds() == null) {
			command.setAlbumIds(new ArrayList<String>());
		}
		if (StringUtils.isNotEmpty(command.getAlbumId())) {
			command.getAlbumIds().add(command.getAlbumId());
		}

		// 3. 检查是否有子节点
		for (String albumId : command.getAlbumIds()) {
			AlbumQo parentQo = new AlbumQo();
			parentQo.setId(albumId);
			
			AlbumQo qo = new AlbumQo();
			qo.setParent(parentQo);
			List<Album> albumChilds = this.queryList(qo);
			if (albumChilds != null && albumChilds.size() > 0)
				throw new HGException(HGException.CODE_ALBUM_HAS_CHILDS, "包含<span style='color:red;'>【子节点】</span>, 是否确定删除？");
		}

	}

	@Override
	public void albumDelete(AlbumDeleteCommand command) throws HGException {
		
		// 1. 非空
		if (command == null)
			throw new HGException(HGException.CODE_ALBUM_NOT_EXIST, "请选择一条记录进行删除！");

		// 2. 将单个数据放到list中
		if (command.getAlbumIds() == null) {
			command.setAlbumIds(new ArrayList<String>());
		}
		if (StringUtils.isNotEmpty(command.getAlbumId())) {
			command.getAlbumIds().add(command.getAlbumId());
		}
		if (StringUtils.isNotEmpty(command.getAlbumIdsStr())) {
			String[] ids = command.getAlbumIdsStr().split(",");
			for(String id : ids){
				command.getAlbumIds().add(id);
			}
		}

		// 3. 递归删除
		try {
			for (String albumId : command.getAlbumIds()) {
				albumDeleteAllById(albumId);
			}
		} catch (Exception e) {
			throw new HGException(HGException.CODE_ALBUM_DEL_ERR, "删除异常");
		}


	}
	
	@Override
	public void albumDeleteAllById(String albumId) throws HGException {
		
		try {
			// 1. 查询child节点 递归删除
			AlbumQo parentQo = new AlbumQo();
			parentQo.setId(albumId);
			
			AlbumQo qo = new AlbumQo();
			qo.setParent(parentQo);
			List<Album> albumChilds = this.queryList(qo);
			if (albumChilds != null && albumChilds.size() > 0) {
				for (Album child : albumChilds) {
					albumDeleteAllById(child.getId());
				}
			}
			
			// 2. 删除该节点
			// 2.1 删除图片和附件
			ImageQo imageQo = new ImageQo();
			imageQo.setAlbumQo(parentQo);
			List<Image> imageList = imageService.queryList(imageQo);
			List<String> imageIds = new ArrayList<String>();
			for (Iterator<Image> iterator = imageList.iterator(); iterator.hasNext();) {
				Image image = (Image) iterator.next();
				imageIds.add(image.getId());
			}
			ImageDeleteCommand imageDeleteCommand = new ImageDeleteCommand();
			imageDeleteCommand.setImageIds(imageIds);
			imageService.imageDelete(imageDeleteCommand);
			
			// 2.2 删除相册
			getDao().deleteById(albumId);
		} catch (Exception e) {
			throw new HGException(HGException.CODE_ALBUM_DEL_ERR, "删除异常");
		}
		
	}
	
	@Override
	public Album albumCreateByUseType(AlbumCreateByUseTypeCommand command)
			throws HGException {
		
		// 1. 检查字段
		if (command == null || !StringUtils.isNotBlank(command.getUseType()) 
				|| command.getUseTypeConfig() == null
				|| command.getReplaceMap() == null)
			throw new HGException(HGException.CODE_ALBUM_DATA_NOT_ENOUGH, "数据不完整！");
		
		// 2. 找到相册路径配置值
		JSONObject obj = command.getUseTypeConfig().get(command.getUseType());
		if (obj == null)
			throw new HGException(HGException.CODE_ALBUM_USETYPE_ERROR, "没有对应使用类型！");
		
		String albumPathConfig = obj.toJSONString();
		
		// 3. 找到 需替换的字符 集合
		String regex = "@[\\w]+@";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(albumPathConfig);
		
		Set<String> toBeReplaceStr = new HashSet<String>();
		while (matcher.find()) {
			toBeReplaceStr.add(matcher.group(0));
		}
		
		// 4. 检查, 替换字符
		JSONObject replaceMap = command.getReplaceMap();
		for (Iterator<String> iterator = toBeReplaceStr.iterator(); iterator.hasNext();) {
			
			String toBeReplace = (String) iterator.next();
			String replaceVal = replaceMap.getString(toBeReplace.replaceAll("@", ""));
			
			if(!StringUtils.isNotBlank(replaceVal))
				throw new HGException(HGException.CODE_ALBUM_USETYPE_ERROR, "传递的替换值不匹配！");
			
			albumPathConfig = albumPathConfig.replaceAll(toBeReplace, replaceVal);
			System.out.println(albumPathConfig);
		}
		
		// 5. 递归创建相册
		Album album = this.albumCreateCyc(JSONObject.parseObject(albumPathConfig).getJSONObject("album"));
		
		return album;
	}
	
	/**
	 * 
	 * @方法功能说明：递归创建相册
	 * @修改者名字：zzb
	 * @修改时间：2014年9月29日下午6:57:28
	 * @修改内容：
	 * @参数：@param curAlbum
	 * @参数：@return
	 * @return:Album
	 * @throws
	 */
	private Album albumCreateCyc(JSONObject curAlbum) {
		
		// 1. 查询该相册是否存在
		AlbumQo albumQo = new AlbumQo();
		String parId = curAlbum.getString("parentId");
		
		if(!StringUtils.isNotBlank(parId)) {
			albumQo.setRoot(true);
		} else {
			AlbumQo albumParQo = new AlbumQo();
			albumParQo.setId(parId);
			albumQo.setParent(albumParQo);
		}
		
		albumQo.setTitle(curAlbum.getString("title"));
		albumQo.setOwnerId(curAlbum.getString("ownerId"));
		albumQo.setOwnerItemId(curAlbum.getString("ownerItemId"));
		albumQo.setUseType(curAlbum.getInteger("useType"));
		
		Album album = this.queryUnique(albumQo);
		
		// 2. 不存在新建
		if (album == null) {
			
			AlbumCreateCommand createCommand = new AlbumCreateCommand();
			createCommand.setTitle(curAlbum.getString("title"));
			createCommand.setOwnerId(curAlbum.getString("ownerId"));
			createCommand.setOwnerItemId(curAlbum.getString("ownerItemId"));
			createCommand.setUseType(curAlbum.getInteger("useType"));
			createCommand.setParentId(StringUtils.isNotBlank(parId) ? parId : null);

			album = this.albumCreate(createCommand);
		}
		
		// 3. 遍历子节点, 继续创建
		String children = curAlbum.getString("children");
		if (StringUtils.isNotBlank(children)) {
			
			JSONObject childrenObj = JSONObject.parseObject(children);
			childrenObj.put("parentId", album.getId());
			return albumCreateCyc(childrenObj);
		} else {
			return album;
		}
	}

	@Override
	protected AlbumDao getDao() {
		return dao;
	}

	


}
