package hsl.spi.inter.programa;
import hsl.pojo.command.Programa.CreateProgramaCommand;
import hsl.pojo.command.Programa.UpdateProgramaStatusCommand;
import hsl.pojo.dto.programa.ProgramaDTO;
import hsl.pojo.qo.programa.HslProgramaQO;
import hsl.spi.inter.BaseSpiService;

public interface HslProgramaService extends BaseSpiService<ProgramaDTO, HslProgramaQO>{
	/**
	 * @方法功能说明：创建栏目
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月24日上午9:59:35
	 * @参数：@param CreateProgramaCommand
	 * @参数：@return
	 * @return:ProgramaDTO
	 * @throws
	 */
	public ProgramaDTO createPrograma(CreateProgramaCommand command);
	/**
	 * @方法功能说明：修改栏目状态
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月24日上午9:59:35
	 * @参数：@param UpdateProgramaStatusCommand
	 * @return:boolean
	 * @throws
	 */
	public boolean updateProgramaStutas(UpdateProgramaStatusCommand updateProgramaStatusCommand);
	/**
	 * @方法功能说明：删除栏目 
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月24日上午9:59:35
	 * @参数：@param ids
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean deletePrograma(String ids);
	
}
