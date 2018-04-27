package jxc.app.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.component.BaseQo;
import hg.common.component.BaseServiceImpl;
import jxc.app.dao.system.OperationFormDao;
import jxc.domain.model.system.OperationForm;

@Service
@Transactional
public class OperationFormService extends BaseServiceImpl<OperationForm,BaseQo,OperationFormDao>{
	
	@Autowired
	OperationFormDao operationFormDao;
	
	@Override
	protected OperationFormDao getDao() {
		return operationFormDao;
	}

	/**
	 * 新建运营形式
	 * @param name
	 * @return
	 */
	public OperationForm createOperationForm(String name){
		
		OperationForm operationForm = new OperationForm();
		operationForm.createOperationForm(name);
		this.save(operationForm);
		
		return operationForm;
	}
}
