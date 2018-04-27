package service.test;
import java.util.ArrayList;
import java.util.List;

import hg.common.page.Pagination;
import hsl.domain.model.programa.Programa;
import hsl.pojo.command.Programa.CreateProgramaCommand;
import hsl.pojo.command.Programa.UpdateProgramaStatusCommand;
import hsl.pojo.dto.programa.ProgramaContentDTO;
import hsl.pojo.dto.programa.ProgramaDTO;
import hsl.pojo.qo.programa.HslProgramaQO;
import hsl.spi.inter.programa.HslProgramaService;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestProgramaService {
	
	@Resource
	private HslProgramaService hslProgramaService;
	
	
	@Test
	public void testCreatePrograma(){
		CreateProgramaCommand cmd=new CreateProgramaCommand();
		List<ProgramaContentDTO> list=new ArrayList<ProgramaContentDTO>();
		ProgramaContentDTO programaContentDTO=new ProgramaContentDTO();
		try {
			cmd.setName("1");
			cmd.setLocation(0);
			cmd.setStatus(1);
			programaContentDTO.setContent("111");
			list.add(programaContentDTO);
			cmd.setProgramaContentList(list);
			ProgramaDTO lineOrderDto=this.hslProgramaService.createPrograma(cmd);
			
			System.out.println(lineOrderDto);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	@Test
	public void queryPagination(){
		Pagination pagination=new Pagination();
		pagination.setPageNo(1);
		pagination.setPageSize(10);
		pagination.setCheckPassLastPageNo(false);
		//hslProgramaQO.setName("1");
		//hslProgramaQO.setId("24df401113eb40bebd313a2fe7a72de5");
		Pagination programaDTO=this.hslProgramaService.queryPagination(pagination);
		System.out.println(programaDTO);
	}
	@Test
	public void updateProgramaStutas(){
		UpdateProgramaStatusCommand updateProgramaStatusCommand=new UpdateProgramaStatusCommand();
		updateProgramaStatusCommand.setId("a0b612cbce21468193143e3be4af8d89");
		updateProgramaStatusCommand.setStatus(2);
		this.hslProgramaService.updateProgramaStutas(updateProgramaStatusCommand);
	}
	@Test
	public void deletePrograma(){
		String id="b08cb10286674e6292916cfbf66e492d,b08cb10286674e6292916cfbf66e492d";
		this.hslProgramaService.deletePrograma(id);
	}
	
}	
