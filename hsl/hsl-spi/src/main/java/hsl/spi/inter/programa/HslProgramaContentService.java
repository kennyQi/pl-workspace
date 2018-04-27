package hsl.spi.inter.programa;

import hsl.pojo.command.Programa.CreateProgramaContentCommand;
import hsl.pojo.command.Programa.UpdateProgramaContentCommand;
import hsl.pojo.dto.programa.ProgramaContentDTO;
import hsl.pojo.qo.programa.HslProgramaContentQO;
import hsl.spi.inter.BaseSpiService;


public interface HslProgramaContentService extends BaseSpiService<ProgramaContentDTO, HslProgramaContentQO>{
	/**
	 * @方法功能说明：修改栏目内容
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月23日下午2:32:35
	 * @修改内容：
	 * @参数：@param updateProgramaContentCommand
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean updateProgramaContent(UpdateProgramaContentCommand updateProgramaContentCommand);
	/**
	 * @方法功能说明：删除内容
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月23日下午2:34:15
	 * @修改内容：
	 * @参数：@param ids
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean deleteProgramaContent(String ids);
	/**
	 * @方法功能说明：新增栏目内容
	 * @修改者名字：zhaows
	 * @修改时间：2015年4月24日上午9:34:15
	 * @修改内容：
	 * @参数：@param createProgramaContentCommand
	 * @参数：@return
	 * @return:ProgramaContentDTO
	 * @throws
	 */
	public ProgramaContentDTO createProgramaContent(CreateProgramaContentCommand createProgramaContentCommand);
}
