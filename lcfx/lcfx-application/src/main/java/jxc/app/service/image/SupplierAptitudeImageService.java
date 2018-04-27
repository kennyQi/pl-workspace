package jxc.app.service.image;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.UploadSupplierAptitudeImageCommand;
import hg.pojo.qo.SupplierAptitudeImageQO;
import hg.pojo.qo.SupplierQO;
import jxc.app.dao.image.SupplierAptitudeImageDao;
import jxc.app.service.supplier.SupplierService;
import jxc.domain.model.image.SupplierAptitudeImage;
import jxc.domain.model.supplier.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SupplierAptitudeImageService extends BaseServiceImpl<SupplierAptitudeImage,SupplierAptitudeImageQO,SupplierAptitudeImageDao>{
	
	@Autowired
	private SupplierAptitudeImageDao supplierAptitudeImageDao;
	
	@Autowired
	private SupplierService supplierService;
	
	@Override
	protected SupplierAptitudeImageDao getDao() {
		return supplierAptitudeImageDao;
	}

	public SupplierAptitudeImage uploadSupplierAptitudeImage(UploadSupplierAptitudeImageCommand command){
		SupplierAptitudeImage supplierAptitudeImage = new SupplierAptitudeImage();
		supplierAptitudeImage.uploadSupplierAptitudeImage(command);
		supplierAptitudeImage = save(supplierAptitudeImage);
		return supplierAptitudeImage;
	}
	
	public SupplierAptitudeImage updateSupplierAptitudeImage(UploadSupplierAptitudeImageCommand command){
		SupplierAptitudeImage supplierAptitudeImage = new SupplierAptitudeImage();
		SupplierQO qo = new SupplierQO();
		qo.setId(command.getSupplierId());
		Supplier supplier = supplierService.queryUnique(qo);
		if(supplier != null){
			supplierAptitudeImage.updateSupplierAptitudeImage(command,supplier);
			supplierAptitudeImage = update(supplierAptitudeImage);
		}
		return supplierAptitudeImage;
	}
}
