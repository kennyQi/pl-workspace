package hg.dzpw.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.util.MD5HashUtil;
import hg.dzpw.app.dao.DealerDao;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.vo.DealerSessionUserVo;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.dealer.DealerClientInfo;
import hg.dzpw.pojo.command.merchant.dealer.DealerLoginCommand;
import hg.dzpw.pojo.command.merchant.dealer.ModifyLoginDealerPasswordCommand;
import hg.dzpw.pojo.command.platform.dealer.PlatformCreateDealerCommand;
import hg.dzpw.pojo.command.platform.dealer.PlatformModifyDealerCommand;
import hg.dzpw.pojo.exception.DZPWException;
import hg.system.dao.AreaDao;
import hg.system.dao.CityDao;
import hg.system.dao.ProvinceDao;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：经销商服务
 * @类修改者：
 * @修改日期：2015-3-24下午3:33:56
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-24下午3:33:56
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DealerLocalService extends BaseServiceImpl<Dealer, DealerQo, DealerDao>{
	
	@Autowired
	private DealerDao dealerDao;
	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private AreaDao areaDao;

	@Override
	protected DealerDao getDao() {
		return dealerDao;
	}
	
	/**
	 * @方法功能说明：创建经销商
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-13上午10:43:33
	 * @参数：@param dealer
	 * @return:void
	 */
	public void createDealer(Dealer dealer) {
		this.dealerDao.save(dealer);
	}
	/**
	 * 重载是否加载地址信息
	 * @param qo
	 * @param isInit
	 * @return
	 */
	@Transactional(readOnly = true)
	public Dealer queryUnique(DealerQo qo,boolean isInit) {
		Dealer dealer=super.queryUnique(qo);
		if (isInit) {
			if (dealer.getBaseInfo().getProvince()!=null) {
				Hibernate.initialize(dealer.getBaseInfo().getProvince());
			}
			if (dealer.getBaseInfo().getCity()!=null) {
				Hibernate.initialize(dealer.getBaseInfo().getCity());
			}
			if (dealer.getBaseInfo().getArea()!=null) {
				Hibernate.initialize(dealer.getBaseInfo().getArea());
			}
		}
		return dealer;
	}

	/**
	 * @throws DZPWException 
	 * @方法功能说明：运营后台创建经销商
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-24下午3:31:01
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void platformCreateDealer(PlatformCreateDealerCommand command) throws DZPWException {
		
		if(StringUtils.isBlank(command.getProvinceId()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少所在省份");
		if(StringUtils.isBlank(command.getCityId()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少所在城市");
		if(StringUtils.isBlank(command.getAreaId()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少所在区");

		Province province = provinceDao.get(command.getProvinceId());
		City city = cityDao.get(command.getCityId());
		Area area = areaDao.get(command.getAreaId());
		Dealer dealer = new Dealer();
		//添加经销商编码
		dealer.getClientInfo().setKey(this.getNextKey());
		dealer.platformCreateDealer(command, province, city, area);
		
		getDao().save(dealer);
	}
	
	/**
	 * @throws DZPWException 
	 * @方法功能说明：运营后台修改经销商
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-24下午3:33:09
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void platformModifyDealer(PlatformModifyDealerCommand command) throws DZPWException {

		if(StringUtils.isBlank(command.getDealerId()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少经销商ID");
		if(StringUtils.isBlank(command.getProvinceId()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少所在省份");
		if(StringUtils.isBlank(command.getCityId()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少所在城市");
		if(StringUtils.isBlank(command.getAreaId()))
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "缺少所在区");

		Dealer dealer = getDao().get(command.getDealerId());
		if (dealer == null)
			throw new DZPWException(DZPWException.MESSAGE_ONLY, "经销商不存在或已被删除");

		Province province = provinceDao.get(command.getProvinceId());
		City city = cityDao.get(command.getCityId());
		Area area = areaDao.get(command.getAreaId());
		
		dealer.platformModifyDealer(command, province, city, area);
		getDao().update(dealer);
	}

	/**
	 * @方法功能说明：启用经销商
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-13上午10:48:01
	 * @参数：@param ids
	 * @return:void
	 */
	public void usableDealer(String[] ids)throws DZPWException{
		for(String id : ids){
			Dealer d = this.dealerDao.get(id);
			d.active();//启用
			this.dealerDao.update(d);
		}
	}
	
	/**
	 * @方法功能说明： 禁用经销商
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-13上午10:47:50
	 * @参数：@param ids
	 * @return:void
	 */
	public void disableDealer(String[] ids)throws DZPWException{
		for (String id : ids) {
			Dealer d = this.dealerDao.get(id);
			d.fobidden();// 禁用
			this.dealerDao.update(d);
		}
	}
	
	/**
	 * @方法功能说明：逻辑删除经销商
	 * @修改者名字：yangkang
	 * @修改时间：2014-11-13上午11:16:13
	 * @参数：@param ids
	 * @参数：@throws DZPWException
	 * @return:void
	 */
	public void removeDealer(String[] ids) throws DZPWException {
		for (String id : ids) {
			Dealer d = this.dealerDao.get(id);
			d.remove();// 设置经销商状态为-1逻辑删除
			this.dealerDao.update(d);
		}
	}
	
	/**
	 * @方法功能说明：检查经销商是否存在
	 * @修改者名字：yangkang
	 * @修改时间：2014-12-3下午3:17:08
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:Integer
	 * @throws
	 */
	public Integer isExistDealer(DealerQo qo) {
		List<Dealer> list = this.queryList(qo);
		if (list != null && list.size() > 0) {
			return list.size();
		} else {
			return 0;
		}
	}
	/**
	 * 
	 * @描述：景区端登录验证 
	 * @author: guotx 
	 * @param command
	 * @version: 2015-12-4 下午4:47:09
	 */
	public DealerSessionUserVo loginCheck(DealerLoginCommand command)
			throws UnknownAccountException, IncorrectCredentialsException,
			LockedAccountException, AuthenticationException {

		if (command == null || StringUtils.isBlank(command.getLoginName()) || StringUtils.isBlank(command.getPassword()))
			throw new AuthenticationException();

		DealerQo qo = new DealerQo();
		qo.setAdminLoginName(command.getLoginName());
		qo.setAdminLoginNameLike(false);
		qo.setRemoved(null);

		Dealer dealer = getDao().queryUnique(qo);

		// 景区不存在
		if (dealer == null)
			throw new UnknownAccountException();

		// 景区管理员密码不匹配
		if (!StringUtils.equalsIgnoreCase(DigestUtils.md5Hex(command.getPassword()), dealer.getClientInfo().getAdminPassword()))
			throw new IncorrectCredentialsException();

		// 景区未激活或已被删除
		if (dealer.getBaseInfo().getStatus()==0 || dealer.getBaseInfo().getRemoved())
			throw new LockedAccountException();

		return new DealerSessionUserVo(dealer);
	}

	public void modifyLoginDealerPassword(
			ModifyLoginDealerPasswordCommand command) throws Exception {
		if(StringUtils.isBlank(command.getNewPwd())||StringUtils.isBlank(command.getReNewPwd())){
			throw new Exception("新密码不能为空");
		}else if(StringUtils.isBlank(command.getOldpwd())){
			throw new Exception("原密码不能为空");
		}
		if (!command.getNewPwd().equals(command.getReNewPwd())) {
			throw new Exception("两次输入新密码不相同");
		}
		Dealer dealer=this.get(command.getDealerId());
		if (!MD5HashUtil.toMD5(command.getOldpwd()).equals(dealer.getClientInfo().getAdminPassword())) {
			throw new Exception("原密码不正确");
		}
		dealer.getClientInfo().setAdminPassword(MD5HashUtil.toMD5(command.getNewPwd()));
		update(dealer);
	}
	
	/**
	 * 
	 * @描述： 获取下一个景区编码
	 * @author: guotx 
	 * @version: 2015-12-14 下午4:35:47
	 */
	public String getNextKey(){
		String hql="select dealer.clientInfo.key as key FROM Dealer as dealer where dealer.clientInfo.key like ? order by dealer.clientInfo.key desc";
		List<Object> params=new ArrayList<Object>();
		params.add(DealerClientInfo.DEALER_KEY_PREFIX+"%");
		List<DealerClientInfo>dealers=null;
		dealers=getDao().hqlQueryList(DealerClientInfo.class, hql, params);
		if (dealers!=null&&dealers.size()>0) {
			String maxCode=dealers.get(0).getKey();
			maxCode=maxCode.substring(DealerClientInfo.DEALER_KEY_PREFIX.length());
			Integer currentCodeNum=Integer.parseInt(maxCode);
			return String.format(DealerClientInfo.DEALER_KEY_PREFIX+"%04d", currentCodeNum+1);
		}else{
			//没有则返回第一个起始code
			return DealerClientInfo.DEALER_KEY_PREFIX+"0001";
		}
	}
}
