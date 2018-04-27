package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.domain.CZFile;
import hg.fx.domain.MileOrder;
import hg.fx.spi.qo.CZFileSQO;

import java.io.IOException;
import java.util.List;


public interface CzFileSPI extends BaseServiceProviderInterface {

	//处理成功
	public static final int CZ_FILE_SUCCEED = 100;
	//文件异常
	public static final int CZ_FILE_ERROR = 101;

	/**
	 * 生成文件 日结完毕后，生成上日的
	 * 调用方式：/czfile/gen?domain=xx&path=/main/apps/jfPath/cz/&date
	 * =<2015-06-07,date 为交易日期，可选项>
	 * 
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年6月11日上午9:54:43
	 * @version：
	 * @修改内容：
	 * @参数：@param path
	 * @参数：@param domain
	 * @参数：@param date 交易日期, ru 2015-06-07。可选。
	 * @参数：@return
	 * @参数：@throws IOException
	 * @return:String
	 * @throws
	 */
	public abstract List<MileOrder> genFile(String path, String domain,
			String date) throws IOException;

	/**
	 * 处理反馈文件，如失败的积分要回滚
	 * 
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年6月11日上午9:56:44
	 * @version：
	 * @修改内容：
	 * @参数：@param file
	 * @参数：@return
	 * @参数：@throws IOException
	 * @参数：@throws Exception
	 * @return:String
	 * @throws
	 */
	public abstract int handleFile(String file) throws IOException, Exception;

	/**
	 * 
	 * @方法功能说明：jfflow记录发送状态
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-7-14下午3:40:35
	 * @version：
	 * @修改内容：
	 * @参数：@param file
	 * @参数：@throws IOException
	 * @参数：@throws Exception
	 * @return:void
	 * @throws
	 */
	/*
	 * public void NHFileToSendList(String file) throws IOException, Exception {
	 * NHFile nhfile = new NHFile(); nhfile.parseFile(file); String sendFile =
	 * file.replace("_CZ", ""); Map<String, String> fileIdMap =
	 * getIdMap(sendFile); for (ACRow r : nhfile.acList) { String id =
	 * fileIdMap.get(r.AC记录流水号.value.toString()); JfFlow flow =
	 * flowService.get(id); // flow.setMerchandiseStatus(JfFlow.SEND);
	 * flow.setSendTime(new Date()); flow.setSendStatus(JfFlow.SEND);
	 * flowService.update(flow); }
	 * 
	 * }
	 * 
	 * /**
	 * 
	 * @方法功能说明：保存文件信息
	 * 
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * 
	 * @修改时间：2015-6-12下午2:25:12
	 * 
	 * @version：
	 * 
	 * @修改内容：
	 * 
	 * @参数：@param cz
	 * 
	 * @return:void
	 * 
	 * @throws
	 */

	public abstract void saveCzFile(CZFile cz);

	/**
	 * 
	 * @方法功能说明：获取已发送的文件
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-6-12下午2:25:39
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:List<CZFile>
	 * @throws
	 */
	public abstract List<CZFile> getSendCzFile();

	/**
	 * 
	 * @方法功能说明：获取待发送的文件
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-7-7下午4:27:06
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:List<CZFile>
	 * @throws
	 */
	public abstract List<CZFile> getToSendCzFile();

	/**
	 * 
	 * @方法功能说明：获取所有南航文件信息
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * @修改时间：2015-6-17下午2:45:02
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:List<CZFile>
	 * @throws
	 */
	 public Pagination<CZFile> getCzFile(CZFileSQO sqo);
	 /* private CZFileQo daoToQo(CzFileDto dto) { CZFileQo qo = new CZFileQo();
	 * qo.setId(dto.getId()); qo.setStatus(dto.getStatus());
	 * qo.setFileName(dto.getFileName()); qo.setStartDate(dto.getStartDate());
	 * qo.setEndDate(dto.getEndDate()); qo.setType(dto.getType());
	 * qo.setTimestamp(dto.getTimestamp());
	 * qo.setFeedbacktime(dto.getFeedbacktime()); return qo;
	 * 
	 * }
	 * 
	 * /**
	 * 
	 * @方法功能说明：更新南航文件信息
	 * 
	 * @修改者名字：<a href=gaoce@hgtech365.com>gaoce</a>
	 * 
	 * @修改时间：2015-6-12下午2:26:04
	 * 
	 * @version：
	 * 
	 * @修改内容：
	 * 
	 * @参数：@param cz
	 * 
	 * @return:void
	 * 
	 * @throws
	 */
	public abstract void updateCzFile(CZFile cz);

	public abstract CZFile update(CZFile entity);

}