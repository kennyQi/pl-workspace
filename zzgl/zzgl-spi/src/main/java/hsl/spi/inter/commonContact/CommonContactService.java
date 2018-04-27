package hsl.spi.inter.commonContact;
import hsl.pojo.command.CommonContact.CreateCommonContactCommand;
import hsl.pojo.command.CommonContact.UpdateCommonContactCommand;
import hsl.pojo.dto.commonContact.CommonContactDTO;
import hsl.pojo.exception.CommonContactException;
import hsl.pojo.qo.CommonContact.CommonContactQO;
import hsl.spi.inter.BaseSpiService;
public interface CommonContactService  extends BaseSpiService<CommonContactDTO,CommonContactQO>{
	/**
	 * @方法功能说明：添加常用联系人
	 * @修改者名字：chenxy
	 * @修改时间：2015年9月7日上午10:43:10
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CommonContactDTO
	 * @throws
	 */
	public CommonContactDTO addCommonContact(CreateCommonContactCommand command) throws CommonContactException;
	/**
	 * @方法功能说明：修改常用联系人
	 * @修改者名字：chenxy
	 * @修改时间：2015年9月7日上午10:43:31
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CommonContactDTO
	 * @throws
	 */
	public CommonContactDTO updateCommonContact(UpdateCommonContactCommand command) throws CommonContactException;
	/**
	 * @方法功能说明：删除常用联系人
	 * @修改者名字：chenxy
	 * @修改时间：2015年9月7日上午10:44:21
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean delCommonContact(String id);
}
