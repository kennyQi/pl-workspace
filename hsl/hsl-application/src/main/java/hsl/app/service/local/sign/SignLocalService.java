package hsl.app.service.local.sign;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.app.dao.sign.SignDao;
import hsl.domain.model.sign.Sign;
import hsl.pojo.command.sign.DeleteActivitySignCommand;
import hsl.pojo.exception.UserException;
import hsl.pojo.qo.SignQo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class SignLocalService extends BaseServiceImpl<Sign,SignQo,SignDao>{

	@Autowired
	private SignDao  signDao;
	
	@Override
	protected SignDao getDao() {
		return signDao;
	}
	
	/**
	 * 
	 * @方法功能说明：删除签到活动
	 * @修改者名字：hgg
	 * @修改时间：2015年9月17日下午3:45:03
	 * @修改内容：
	 * @参数：@param deleteActivitySignCommand
	 * @return:void
	 * @throws
	 */
	public void delActivitySign(DeleteActivitySignCommand deleteActivitySignCommand) throws UserException{
		
		HgLogger.getInstance().info("hgg", "SignLocalService->delActivitySign->删除活动开始");
		
		if(StringUtils.isBlank(deleteActivitySignCommand.getSignId())){
			throw new UserException(UserException.SIGN_ID_IS_NULL,"活动签到ID为空");
		}
		
		HgLogger.getInstance().info("hgg", "SignLocalService->delActivitySign->签到ID:"+deleteActivitySignCommand.getSignId());
		
		SignQo signQo = new SignQo();
		signQo.setId(deleteActivitySignCommand.getSignId());
		Sign sign = signDao.queryUnique(signQo);
		if(sign == null){
			throw new UserException(UserException.SIGN_NOT_FOUND,"没有查询到相应的签到活动");
		}
		
		signDao.delete(sign);
		
		HgLogger.getInstance().info("hgg", "SignLocalService->delActivitySign->删除活动完成");
	}
	

	public Integer getData(MultipartFile file)throws UserException{
		
		StringBuilder errorMessage = new StringBuilder();
		//上传成功的数量
		Integer k = 0;
		try {
			InputStream is = file.getInputStream();
			Workbook workbook = Workbook.getWorkbook(is);
			Sheet sheet = workbook.getSheet(0);
			int rows = sheet.getRows();
		    for (int i = 1; i < rows; i++) {

	    	     String name = sheet.getCell(0, i).getContents().trim();
	    	     String phone = sheet.getCell(1, i).getContents().trim();
	    	     String sID = sheet.getCell(2, i).getContents().trim();
	    	     String department = sheet.getCell(3, i).getContents().trim();
	    	     String job = sheet.getCell(4, i).getContents().trim();
	    	     String activityName = sheet.getCell(5, i).getContents().trim();
	    	     
	    	     if(StringUtils.isBlank(name)){
	    	    	 errorMessage.append("第"+i+"行第1列【姓名为空】。");
	    	     }
	    	     if(StringUtils.isBlank(phone)){
	    	    	 errorMessage.append("第"+i+"行第2列【手机号为空】。");
	    	     }
	    	     if(StringUtils.isNotBlank(phone) && phone.length() != 11){
	    	    	 errorMessage.append("第"+i+"行第2列【手机号号码格式不正确】。");
	    	     }
	    	     if(StringUtils.isBlank(activityName)){
	    	    	 errorMessage.append("第"+i+"行第6列【活动为空】。");
	    	     }
	    	     
	    	     boolean need = quertSign(activityName, phone);
	    	     
	    	     if(!need&&StringUtils.isBlank(errorMessage.toString())){
	    	    	 k++;
	    	    	 Sign sign = new Sign();
	    	    	 sign.setActivityName(activityName);
	    	    	 sign.setDepartment(department);
	    	    	 sign.setJob(job);
	    	    	 sign.setMobile(phone);
	    	    	 sign.setName(name);
	    	    	 sign.setsID(sID);
	    	    	 sign.setSigned(false);
	    	    	 sign.setId(UUIDGenerator.getUUID());
	    	    	 signDao.save(sign);
	    	     }
	    	   }
		    if(StringUtils.isNotBlank(errorMessage.toString())){
				throw new UserException(UserException.UPLOAD_SIGN_DATA_NOT_COMPANT,errorMessage.toString());
			}
		      
		} catch (IOException e) {
			HgLogger.getInstance().info("hgg", "上传签到数据异常__原因:"+errorMessage.toString());
			throw new UserException(UserException.UPLOAD_SIGN_DATA_NOT_COMPANT,errorMessage.toString());
		} catch (BiffException e) {
			HgLogger.getInstance().info("hgg", "上传签到数据异常__原因:"+errorMessage.toString());
			throw new UserException(UserException.UPLOAD_SIGN_DATA_NOT_COMPANT,errorMessage.toString());
		}
		
		return k;
	}
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：hgg
	 * @修改时间：2015年9月18日上午7:59:36
	 * @修改内容：
	 * @参数：@param file
	 * @参数：@throws UserException
	 * @return:void
	 * @throws
	 */
	public void vaildExcel(MultipartFile file)throws UserException{
		
		if(file == null){
			throw new UserException(UserException.UPLOAD_SIGN_DATA_IS_NULL,"没有上传文件");
		}
		
		String fileName = file.getOriginalFilename();
		if(!fileName.endsWith(".xls")){
			throw new UserException(UserException.UPLOAD_SIGN_DATA_NOT_JXL,"请上传.xls格式的EXCEL表格");
		}
		
	}
	
	/**
	 * 
	 * @方法功能说明：查询是否重复
	 * @修改者名字：hgg
	 * @修改时间：2015年9月17日下午6:10:50
	 * @修改内容：
	 * @参数：@param activityName
	 * @参数：@param mobile
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	private boolean quertSign(String activityName,String mobile){
		
		SignQo signQo = new SignQo();
		signQo.setActivityName(activityName);
		signQo.setMobile(mobile);
		
		List<Sign> signList = signDao.queryList(signQo);
		if(CollectionUtils.isNotEmpty(signList)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @方法功能说明：用户签到
	 * @修改者名字：hgg
	 * @修改时间：2015年9月18日上午10:06:22
	 * @修改内容：
	 * @参数：@param signQo
	 * @return:void
	 * @throws
	 */
	public void sign(SignQo signQo) throws UserException{
		
		Sign sign = signDao.queryUnique(signQo);
		if(sign == null){
			throw new UserException(UserException.SIGN_NOT_FOUND,"没有查询到相应的签到活动");
 		}
		
		sign.setSignTime(new Date());
		sign.setSigned(true);
		signDao.update(sign);
	}
	
	/**
	 * 
	 * @方法功能说明：签到详情
	 * @修改者名字：hgg
	 * @修改时间：2015年9月18日上午10:22:47
	 * @修改内容：
	 * @参数：@param signQo
	 * @参数：@return
	 * @return:Map<String,String>
	 * @throws
	 */
	public Map<String,String> signDetail(String activityName){
		
		//结果Map
		Map<String,String>  detailMap = new HashMap<String, String>();
		//总数量
		Integer totalCount = 0;
		//已签到
		Integer alreadySignCount = 0;
		//未签到
		Integer noSignCount = 0;
		
		StringBuilder noSignStr = new StringBuilder();
		SignQo signQo = new SignQo();
		signQo.setActivityName(activityName);
		List<Sign> signList = signDao.queryList(signQo);
		if(CollectionUtils.isNotEmpty(signList)){
			
			totalCount = signList.size();
			
			for (Sign sign : signList) {
				if(sign.isSigned()){
					alreadySignCount ++;
				}else{
					noSignCount ++;
					if(StringUtils.isBlank(noSignStr.toString())){
						noSignStr.append(sign.getName());
					}else{
						noSignStr.append("、").append(sign.getName());
					}
				}
			}
			
		}
		
		detailMap.put("alreadySignCount", alreadySignCount+"");
		detailMap.put("noSignCount", noSignCount+"");
		detailMap.put("totalCount", totalCount+"");
		detailMap.put("noSignStr", noSignStr.toString());
		
		HgLogger.getInstance().info("hgg", "本次查询：应签到总数：【"+totalCount+"】,已签到人数【"+alreadySignCount+"】,未签到人数:【"+noSignCount+"】,未签到姓名：【"+noSignStr.toString()+"】");
		
		return detailMap;
	}
	
}
