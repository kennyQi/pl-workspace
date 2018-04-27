package lxs.app.service.line;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;

import java.util.Date;
import java.util.List;

import lxs.app.dao.line.LxsLineDAO;
import lxs.app.dao.line.LxsLineSelectiveDAO;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineSelective;
import lxs.pojo.command.line.CreateLineSelectiveCommand;
import lxs.pojo.command.line.DeleteLineSelectiveCommand;
import lxs.pojo.qo.line.LineSelectiveQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LineSelectiveService extends
		BaseServiceImpl<LineSelective, LineSelectiveQO, LxsLineSelectiveDAO> {

	@Autowired
	private LxsLineSelectiveDAO lineSelectiveDAO;
	@Autowired
	private LxsLineDAO lxsLineDAO;

	public void createLineSelective(CreateLineSelectiveCommand command){
		LineSelectiveQO lineSelectiveQO= new LineSelectiveQO();
		lineSelectiveQO.setForSale(1);
		LineSelective lineSelective = new LineSelective();
		lineSelective.setId(UUIDGenerator.getUUID());
		lineSelective.setSort(lineSelectiveDAO.maxProperty("sort", lineSelectiveQO)+1);
		lineSelective.setCreateDate(new Date());
		lineSelective.setType(command.getType());
		lineSelective.setLine(lxsLineDAO.get(command.getLineId()));
		lineSelective.setName(command.getName());
		lineSelectiveDAO.save(lineSelective);
	}
	
	public void deleteLineSelective(DeleteLineSelectiveCommand command){
			LineSelective lineSelective=lineSelectiveDAO.get(command.getId());
			Line line = lineSelective.getLine();
			line.setSelectiveLine(null);
			getDao().delete(lineSelective);
	}
	
	/**
	 * 
	 * @方法功能说明：删除无关联精选
	 * @修改者名字：cangs
	 * @修改时间：2016年3月23日下午1:37:41
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void delSelectiveByNullScenicSpot() {
		List<LineSelective> lineSelectives = lineSelectiveDAO.queryList(new LineSelectiveQO());
		for (LineSelective lineSelective : lineSelectives) {
			Line line = lxsLineDAO.get(lineSelective.getLine().getId());
			if(line==null||line.getForSale()==Line.NOT_SALE){
				lineSelectiveDAO.delete(lineSelective);
			}
		}
	}
	public int getMaxSort(){
		LineSelectiveQO lineSelectiveQO= new LineSelectiveQO();
		lineSelectiveQO.setForSale(1);
		return lineSelectiveDAO.maxProperty("sort", lineSelectiveQO);
	}
	@Override
	protected LxsLineSelectiveDAO getDao() {
		return lineSelectiveDAO;
	}

}
