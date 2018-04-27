package hg.service.image.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.service.image.app.common.Assert;
import hg.service.image.app.dao.AlbumDao;
import hg.service.image.app.dao.ImageDao;
import hg.service.image.app.dao.ImageUseTypeDao;
import hg.service.image.app.dao.ProjectDao;
import hg.service.image.command.album.CreateAlbumCommand;
import hg.service.image.command.album.DeleteAlbumCommand;
import hg.service.image.command.album.ModifyAlbumCommand;
import hg.service.image.command.album.RemoveAlbumCommand;
import hg.service.image.command.album.RestoreAlbumCommand;
import hg.service.image.domain.model.Album;
import hg.service.image.domain.model.Project;
import hg.service.image.domain.qo.AlbumLocalQo;
import hg.service.image.domain.qo.ProjectLocalQo;
import hg.service.image.pojo.exception.ImageException;
import hg.system.model.staff.Staff;
import hg.system.qo.AuthStaffQo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：相册本地Service类
 * @修改者名字：yuxx
 * @修改时间：2014年12月15日 下午6:22:47
 * @修改内容：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月3日 下午10:58:26
 */
@Service
@Transactional
public class AlbumLocalService extends
		BaseServiceImpl<Album, AlbumLocalQo, AlbumDao> {
	@Autowired
	private AlbumDao albumDao;
	@Autowired
	private ImageUseTypeDao imageUseTypeDao;
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private DomainEventRepository domainEvent;

	@Override
	protected AlbumDao getDao() {
		return albumDao;
	}

	/**
	 * @方法功能说明：创建相册
	 * @修改者名字：yuxx
	 * @修改时间：2014年11月4日 下午5:17:01
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public Album createAlbum(CreateAlbumCommand command) throws ImageException {
		
		Assert.notCommand(command, ImageException.NEED_ALBUM_WITHOUTPARAM,
				"创建相册指令");
		Assert.notPorjectandEnv(command.getFromProjectId(),
				command.getFromProjectEnvName());

		// 检查是否存在根目录
		checkAndCreateRootAlbum(command);

		// 检查相册是否已存在，不存在则创建
		Album album = null;
		if (!checkPathExist(command.getFromProjectId(),
				command.getFromProjectEnvName(), command.getAlbumId())) {

			album = new Album(command);
			albumDao.save(album);
		}else{
			album = queryExistAlbum(command.getFromProjectId(),
					command.getFromProjectEnvName(), command.getPath());
		}

		// 领域日志
		DomainEvent event = new DomainEvent(
				"hg.service.image.domain.model.Album", "create",
				JSON.toJSONString(command));
		domainEvent.save(event);

		// 返回相册
		return album;
	}

	/**
	 * @方法功能说明：修改相册
	 * @修改者名字：yuxx
	 * @修改时间：2014年11月4日 下午6:16:07
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public void modifyAlbum(ModifyAlbumCommand command) throws ImageException {
		Assert.notCommand(command, ImageException.NEED_ALBUM_WITHOUTPARAM,
				"修改相册指令");
		Assert.notPorjectandEnv(command.getFromProjectId(),
				command.getFromProjectEnvName());

		// 相册可以由相册全名或者相册id确定，所以这里加个判断
		if (StringUtils.isBlank(command.getAlbumId())) {
			throw new ImageException(ImageException.NEED_ALBUM_WITHOUTPARAM,
					"相册ID");
		}

		Album album = albumDao.load(command.getAlbumId());
		// 更新相册
		album.modify(command);
		albumDao.update(album);

		// 领域日志
		DomainEvent event = new DomainEvent(
				"hg.service.image.domain.model.Album", "modify",
				JSON.toJSONString(command));
		domainEvent.save(event);
	}

	/**
	 * @方法功能说明：删除
	 * @修改者名字：yuxx
	 * @修改时间：2014年11月4日 下午5:17:01
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void removeAlbum(RemoveAlbumCommand command) throws ImageException,
			IOException {
		Assert.notCommand(command, ImageException.NEED_ALBUM_WITHOUTPARAM,
				"删除相册指令");
		Assert.notList(command.getAlbumIds(),
				ImageException.NEED_ALBUM_WITHOUTPARAM, "相册id");

		for (String albumId : command.getAlbumIds()) {
			Album album = albumDao.load(albumId);
			album.delete();
			albumDao.update(album);

			if (command.getRemoveSubAlbum()) {
				AlbumLocalQo qo = new AlbumLocalQo();
				qo.setProjectId(command.getFromProjectId());
				qo.setEnvName(command.getFromProjectEnvName());
				qo.setPath(album.getPath());
				qo.setPathLike(true);

				List<Album> albums = albumDao.queryList(qo);
				for (Album subAlbum : albums) {
					if (subAlbum.getPath().startsWith(album.getPath())) {
						subAlbum.remove();
						albumDao.update(subAlbum);
					}
				}
			}

		}

		// 领域日志
		DomainEvent event = new DomainEvent(
				"hg.service.image.domain.model.Album", "remove",
				JSON.toJSONString(command));
		domainEvent.save(event);
	}

	/**
	 * @方法功能说明：彻底删除
	 * @修改者名字：yuxx
	 * @修改时间：2014年11月4日 下午5:17:01
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void deleteAlbum(DeleteAlbumCommand command) throws ImageException,
			IOException {
		Assert.notCommand(command, ImageException.NEED_ALBUM_WITHOUTPARAM,
				"彻底删除相册指令");
		Assert.notList(command.getAlbumIds(),
				ImageException.NEED_ALBUM_WITHOUTPARAM, "相册id");

		for (String albumId : command.getAlbumIds()) {
			Album album = albumDao.load(albumId);
			album.delete();
			albumDao.update(album);

			if (command.getDeleteSubAlbum()) {
				AlbumLocalQo qo = new AlbumLocalQo();
				qo.setProjectId(command.getFromProjectId());
				qo.setEnvName(command.getFromProjectEnvName());
				qo.setPath(album.getPath());
				qo.setPathLike(true);

				List<Album> albums = albumDao.queryList(qo);
				for (Album subAlbum : albums) {
					if (subAlbum.getPath().startsWith(album.getPath())) {
						subAlbum.delete();
						albumDao.update(subAlbum);
					}
				}
			}

		}

		// 领域日志
		DomainEvent event = new DomainEvent(
				"hg.service.image.domain.model.Album", "delete",
				JSON.toJSONString(command));
		domainEvent.save(event);
	}

	/**
	 * @方法功能说明：还原
	 * @修改者名字：yuxx
	 * @修改时间：2014年11月4日 下午5:17:01
	 * @修改内容：
	 * @param command
	 * @throws ImageException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void restoreAlbum(RestoreAlbumCommand command)
			throws ImageException, IOException {
		Assert.notCommand(command, ImageException.NEED_ALBUM_WITHOUTPARAM,
				"还原相册指令");
		Assert.notList(command.getAlbumIds(),
				ImageException.NEED_ALBUM_WITHOUTPARAM, "相册id");

		for (String albumId : command.getAlbumIds()) {
			Album album = albumDao.load(albumId);
			album.delete();
			albumDao.update(album);

			AlbumLocalQo qo = new AlbumLocalQo();
			qo.setProjectId(command.getFromProjectId());
			qo.setEnvName(command.getFromProjectEnvName());
			qo.setPath(album.getPath());
			qo.setPathLike(true);

			List<Album> albums = albumDao.queryList(qo);
			for (Album subAlbum : albums) {
				if (subAlbum.getPath().startsWith(album.getPath())) {
					subAlbum.restore();;
					albumDao.update(subAlbum);
				}
			}

		}

		// 领域日志
		DomainEvent event = new DomainEvent(
				"hg.service.image.domain.model.Album", "delete",
				JSON.toJSONString(command));
		domainEvent.save(event);
	}

	/**
	 * 
	 * @方法功能说明：检查根相册是否存在，不存在则创建
	 * @修改者名字：yuxx
	 * @修改时间：2014年12月30日上午10:44:41
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws ImageException
	 * @return:void
	 * @throws
	 */
	private void checkAndCreateRootAlbum(CreateAlbumCommand command)
			throws ImageException {

		// 判断根目录相册是否存在，不存在则创建
		AlbumLocalQo albumQo = new AlbumLocalQo(command.getFromProjectId(),
				command.getFromProjectEnvName());
		albumQo.setPath(Album.getRootPath(command.getFromProjectId(),
				command.getFromProjectEnvName()));

		if (albumDao.queryCount(albumQo) == 0) {
			ProjectLocalQo projectQo = new ProjectLocalQo(
					command.getFromProjectId());
			projectQo.setEnvName(command.getFromProjectEnvName());
			projectQo.setStaffQo(new AuthStaffQo());
			Project project = projectDao.queryUnique(projectQo);

			Assert.notNull(project, ImageException.RESULT_PROJECT_NOTFOUND,
					"工程");
			Assert.notNull(project.getStaff(),
					ImageException.RESULT_PROJECT_NOTFOUND, "工程管理员");

			Staff staff = project.getStaff();
			CreateAlbumCommand createAlbumCommand = new CreateAlbumCommand(
					command.getFromProjectId(), command.getFromProjectEnvName());

			createAlbumCommand.setTitle(staff.getAuthUser().getLoginName());
			createAlbumCommand
					.setPath(Album.getRootPath(command.getFromProjectId(),
							command.getFromProjectEnvName()));

			Album rootAlbum = new Album(createAlbumCommand);
			albumDao.save(rootAlbum);
		}
		// 创建根相册结束
	}

	/**
	 * @方法功能说明：检查相册路径
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午6:22:47
	 * @param command
	 * @throws ImageException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private Boolean checkPathExist(String projectId, String envName, String albumId)
			throws ImageException {

		// 判断根目录相册是否存在，不存在则创建
		AlbumLocalQo albumQo = new AlbumLocalQo(projectId, envName);
		albumQo.setId(albumId);;

		if (albumDao.queryCount(albumQo) == 0) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * @方法功能说明：查相册路径
	 * @修改者名字：tandeng
	 * @修改时间：2015年02月13日 下午6:22:47
	 */
	private Album queryExistAlbum(String projectId, String envName, String path)
			throws ImageException {
		
		// 判断根目录相册是否存在，不存在则创建
		AlbumLocalQo albumQo = new AlbumLocalQo(projectId, envName);
		albumQo.setPath(path);
		
		Album album = albumDao.queryUnique(albumQo);
		return album;
	}
}