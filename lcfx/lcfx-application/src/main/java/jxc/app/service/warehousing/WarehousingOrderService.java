package jxc.app.service.warehousing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hg.common.component.BaseModel;
import hg.common.component.BaseServiceImpl;
import hg.pojo.command.warehousingorder.CreateWarehousingOrderCommand;
import hg.pojo.command.warehousingorder.ModifyWarehousingOrderCommand;
import hg.pojo.command.warehousingorder.RemoveWarehousingOrderCommand;
import hg.pojo.dto.product.SkuProductDTO;
import hg.pojo.exception.ProductException;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.WarehousingOrderQo;
import hg.pojo.qo.SkuProductQO;
import jxc.app.dao.warehousing.WarehousingOrderDao;
import jxc.app.service.product.SkuProductService;
import jxc.app.service.system.CheckRecordService;
import jxc.app.service.warehouse.WarehouseService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.Constants;
import jxc.domain.model.product.Product;
import jxc.domain.model.product.SkuProduct;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.warehouseing.order.WarehousingOrder;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
public class WarehousingOrderService extends BaseServiceImpl<WarehousingOrder, WarehousingOrderQo, WarehousingOrderDao> {
	@Autowired
	private WarehousingOrderDao warehousingOrderDao;

	@Autowired
	private SkuProductService skuProductService;

	@Autowired
	private WarehousingOrderDetailService warehousingOrderDetailService;

	@Autowired
	CheckRecordService checkRecordService;

	@Override
	protected WarehousingOrderDao getDao() {
		return warehousingOrderDao;
	}

	@Override
	public WarehousingOrder queryUnique(WarehousingOrderQo qo) {
		WarehousingOrder ret = super.queryUnique(qo);
		Hibernate.initialize(ret.getProject());
		Hibernate.initialize(ret.getSupplier());
		Hibernate.initialize(ret.getWarehouse());
//		Hibernate.initialize(ret.getPaymentMethod());
//		Hibernate.initialize(ret.getAttention());
		return ret;
	}

	@Autowired
	private JxcLogger logger;

	public WarehousingOrder queryUnique4Edit(WarehousingOrderQo qo) {
		WarehousingOrder warehousingOrder = super.queryUnique(qo);

//		getProxyId(warehousingOrder.getAttention());
		getProxyId(warehousingOrder.getWarehouse());
		getProxyId(warehousingOrder.getProject());
//		getProxyId(warehousingOrder.getPaymentMethod());
//		getProxyId(warehousingOrder.getAttention());
		getProxyId(warehousingOrder.getSupplier());

		return warehousingOrder;

	}

	public void getProxyId(BaseModel obj) {
		HibernateProxy proxy = (HibernateProxy) obj;
		obj.setId((String) proxy.getHibernateLazyInitializer().getIdentifier());
	}

	@Transactional(rollbackFor = Exception.class)
	public void createWarehousingOrder(CreateWarehousingOrderCommand command) throws ProductException {

		WarehousingOrder warehousingOrder = new WarehousingOrder();

		warehousingOrder.create(command);
		save(warehousingOrder);
		warehousingOrderDetailService.createDetail(command.getDetailList(), warehousingOrder);

		// logger.debug(this.getClass(), "wkq", "添加采购单:" + command.get,
		// command.getOperatorName(), command.getOperatorType(),
		// command.getOperatorAccount(), "");

	}

	public List<SkuProductDTO> queryWarehousingOrderDetailList(String[] skuIdArr) {
		List<SkuProductDTO> skuProductDtoList = new ArrayList<SkuProductDTO>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < skuIdArr.length; i++) {
			SkuProductQO qo = new SkuProductQO();
			qo.setId(skuIdArr[i]);
			SkuProduct skuProd = skuProductService.queryUnique(qo);

			List<SpecDetail> specDetails = skuProd.getSpecDetails();
			for (SpecDetail specDetail : specDetails) {
				sb.append(specDetail.getSpecValue().getSpecValue());
				sb.append(" ");
			}
			SkuProductDTO skuProductDTO = new SkuProductDTO();
			skuProductDTO.setSkuCode(skuProd.getId());
			skuProductDTO.setProductCode(skuProd.getProduct().getProductCode());
			skuProductDTO.setProductName(skuProd.getProduct().getProductName());
			skuProductDTO.setSpecificationsName(sb.toString());
			sb.setLength(0);
			skuProductDTO.setUnit(skuProd.getProduct().getUnit().getUnitName());
			skuProductDtoList.add(skuProductDTO);

			// warehousingOrderDetailList.add(warehousingOrderDetail);
		}

		return skuProductDtoList;

	}

	public void removeWarehousingOrder(RemoveWarehousingOrderCommand command) {
		List<String> idList = command.getIdList();
		for (String id : idList) {
			WarehousingOrder warehousingOrder = get(id);
			warehousingOrder.setStatusRemoved(true);
			update(warehousingOrder);
		}

	}

	public void updateWarehousingOrder(ModifyWarehousingOrderCommand command) {
		WarehousingOrderQo qo = new WarehousingOrderQo();
		qo.setId(command.getId());
		WarehousingOrder warehousingOrder = queryUnique(qo);
		warehousingOrder.modify(command);
		update(warehousingOrder);
		warehousingOrderDetailService.modifyDetail(command.getDetailList(), warehousingOrder);

	}


}
