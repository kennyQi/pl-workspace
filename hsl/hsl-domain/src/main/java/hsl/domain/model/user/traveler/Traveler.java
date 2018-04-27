package hsl.domain.model.user.traveler;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.domain.model.user.User;
import hsl.pojo.command.traveler.CreateTravelerCommand;
import hsl.pojo.command.traveler.ModifyTravelerCommand;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.util.HSLConstants;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * @类功能说明：游客信息
 * @类修改者：
 * @修改日期：2015-9-28下午5:04:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-9-28下午5:04:24
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_USER + "TRAVELER")
public class Traveler extends BaseModel {

	/**
	 * 来自的用户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FROM_USER_ID")
	private User fromUser;

	/**
	 * 游客基本信息
	 */
	@Embedded
	private TravelerBaseInfo baseInfo;

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public TravelerBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new TravelerBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(TravelerBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}
	
	// --------------------------------------------------------------------
	
	private transient Manager manager;
	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}
	public class Manager {

		/**
		 * @方法功能说明：创建游客
		 * @修改者名字：zhurz
		 * @修改时间：2015-10-9下午4:17:23
		 * @修改内容：
		 * @参数：@param command
		 * @参数：@param fromUser
		 * @return:void
		 * @throws
		 */
		public void createTraveler(CreateTravelerCommand command, User fromUser) {
			Date now = new Date();
			setId(UUIDGenerator.getUUID());
			setFromUser(fromUser);
			getBaseInfo().setName(command.getName());
			getBaseInfo().setMobile(command.getMobile());
			if (!(HSLConstants.Traveler.ID_TYPE_SFZ.equals(command.getIdType())
					&& (command.getIdNo().length() == 18 || command.getIdNo().length() == 15))) {
				throw new ShowMessageException("证件不合法");
			}
			getBaseInfo().setIdNo(command.getIdNo());
			getBaseInfo().setIdType(command.getIdType());
			getBaseInfo().setCreateDate(now);
			getBaseInfo().setModifyDate(now);
		}

		/**
		 * @方法功能说明：修改游客
		 * @修改者名字：zhurz
		 * @修改时间：2015-9-29下午3:10:52
		 * @修改内容：
		 * @参数：@param command
		 * @return:void
		 * @throws
		 */
		public void modifyTraveler(ModifyTravelerCommand command) {
			if (!StringUtils.equals(command.getFromUserId(), getFromUser().getId()))
				throw new ShowMessageException("无权操作");
			Date now = new Date();
			getBaseInfo().setName(command.getName());
			getBaseInfo().setMobile(command.getMobile());
			if (!(HSLConstants.Traveler.ID_TYPE_SFZ.equals(command.getIdType())
					&& (command.getIdNo().length() == 18 || command.getIdNo().length() == 15))) {
				throw new ShowMessageException("证件不合法");
			}
			getBaseInfo().setIdNo(command.getIdNo());
			getBaseInfo().setIdType(command.getIdType());
			getBaseInfo().setModifyDate(now);
		}
		
	}
}
