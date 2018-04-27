package hg.dzpw.app.component.interceptor;

import hg.dzpw.app.component.manager.DealerCacheManager;
import hg.dzpw.app.component.manager.ScenicSpotCacheManager;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.scenicspot.ClientDevice;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据库插入更新拦截器
 */
public class EntityInterceptor extends EmptyInterceptor {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ScenicSpotCacheManager scenicSpotCacheManager;
	@Autowired
	private DealerCacheManager dealerCacheManager;
	
	private void handleEntity(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		if (entity instanceof ClientDevice) {
			ClientDevice device = (ClientDevice) entity;
			scenicSpotCacheManager.refreshClientDevice(device.getScenicSpot().getId(), device.getId());
		} else if (entity instanceof Dealer) {
			Dealer dealer = (Dealer) entity;
			if (dealer.getBaseInfo().getStatus() != null && dealer.getBaseInfo().getStatus() == 1){
				dealerCacheManager.setSecretKey(dealer.getId(), dealer.getClientInfo().getKey(), dealer.getClientInfo().getSecretKey());
				if (dealer.getClientInfo().getPublishAble() != null && dealer.getClientInfo().getPublishAble())
					dealerCacheManager.setDealerPushlishUrl(dealer.getClientInfo().getKey(), dealer.getClientInfo().getPublishUrl());
				else
					dealerCacheManager.removeDealerPushlishUrl(dealer.getClientInfo().getKey());
			}
			else
				dealerCacheManager.removeDealer(dealer.getClientInfo().getKey());
		} else if (entity instanceof ScenicSpot) {
			// 更新修改时间
			((ScenicSpot) entity).getBaseInfo().setModifyDate(new Date());
		}
	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		if (entity instanceof Dealer)
			dealerCacheManager.removeDealer(((Dealer) entity).getClientInfo().getKey());
	}

	/**
	 * 插入数据
	 */
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {

		handleEntity(entity, id, state, propertyNames, types);
			
		return true;
	}

	/**
	 * 更新数据
	 */
	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) throws CallbackException {

		handleEntity(entity, id, previousState, propertyNames, types);

		return true;
	}

}
