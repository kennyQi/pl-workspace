package jxc.app.service.warehousing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hg.common.component.BaseModel;
import hg.common.component.BaseServiceImpl;
import hg.pojo.command.warehousingnotice.CreateWarehousingNoticeCommand;
import hg.pojo.command.warehousingnotice.ModifyWarehousingNoticeCommand;
import hg.pojo.command.warehousingnotice.RemoveWarehousingNoticeCommand;
import hg.pojo.dto.product.SkuProductDTO;
import hg.pojo.exception.ProductException;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.WarehousingNoticeQo;
import hg.pojo.qo.SkuProductQO;
import jxc.app.dao.warehousing.WarehousingNoticeDao;
import jxc.app.service.product.SkuProductService;
import jxc.app.service.system.CheckRecordService;
import jxc.app.service.warehouse.WarehouseService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.Constants;
import jxc.domain.model.product.Product;
import jxc.domain.model.product.SkuProduct;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.warehouseing.notice.WarehousingNotice;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
public class WarehousingNoticeService extends BaseServiceImpl<WarehousingNotice, WarehousingNoticeQo, WarehousingNoticeDao> {
	@Autowired
	private WarehousingNoticeDao warehousingNoticeDao;

	@Autowired
	private SkuProductService skuProductService;

	@Autowired
	private WarehousingNoticeDetailService warehousingNoticeDetailService;

	@Autowired
	CheckRecordService checkRecordService;

	@Override
	protected WarehousingNoticeDao getDao() {
		return warehousingNoticeDao;
	}

	@Override
	public WarehousingNotice queryUnique(WarehousingNoticeQo qo) {
		WarehousingNotice ret = super.queryUnique(qo);
		Hibernate.initialize(ret.getProject());
		Hibernate.initialize(ret.getSupplier());
		Hibernate.initialize(ret.getWarehouse());
//		Hibernate.initialize(ret.getPaymentMethod());
//		Hibernate.initialize(ret.getAttention());
		return ret;
	}

	@Autowired
	private JxcLogger logger;

	public WarehousingNotice queryUnique4Edit(WarehousingNoticeQo qo) {
		WarehousingNotice warehousingNotice = super.queryUnique(qo);

//		getProxyId(warehousingNotice.getAttention());
		getProxyId(warehousingNotice.getWarehouse());
		getProxyId(warehousingNotice.getProject());
	//	getProxyId(warehousingNotice.getPaymentMethod());
		//getProxyId(warehousingNotice.getAttention());
		getProxyId(warehousingNotice.getSupplier());

		return warehousingNotice;

	}

	public void getProxyId(BaseModel obj) {
		HibernateProxy proxy = (HibernateProxy) obj;
		obj.setId((String) proxy.getHibernateLazyInitializer().getIdentifier());
	}

	@Transactional(rollbackFor = Exception.class)
	public void createWarehousingNotice(CreateWarehousingNoticeCommand command) throws ProductException {

		WarehousingNotice warehousingNotice = new WarehousingNotice();

		warehousingNotice.create(command);
		save(warehousingNotice);
		warehousingNoticeDetailService.createDetail(command.getDetailList(), warehousingNotice);

		// logger.debug(this.getClass(), "wkq", "添加采购单:" + command.get,
		// command.getOperatorName(), command.getOperatorType(),
		// command.getOperatorAccount(), "");

	}

	public List<SkuProductDTO> queryWarehousingNoticeDetailList(String[] skuIdArr) {
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

			// warehousingNoticeDetailList.add(warehousingNoticeDetail);
		}

		return skuProductDtoList;

	}

	public void removeWarehousingNotice(RemoveWarehousingNoticeCommand command) {
			WarehousingNotice warehousingNotice = get(command.getId());
			warehousingNotice.setStatusRemoved(true);
	}

	public void updateWarehousingNotice(ModifyWarehousingNoticeCommand command) {
		WarehousingNoticeQo qo = new WarehousingNoticeQo();
		qo.setId(command.getId());
		WarehousingNotice warehousingNotice = queryUnique(qo);
		warehousingNotice.modify(command);
		update(warehousingNotice);
		warehousingNoticeDetailService.modifyDetail(command.getDetailList(), warehousingNotice);

	}


}
