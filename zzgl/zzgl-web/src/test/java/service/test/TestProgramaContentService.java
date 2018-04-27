package service.test;
import hsl.app.service.spi.programa.HslProgramaContentServiceImpl;



import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestProgramaContentService {
	
	@Resource
	private HslProgramaContentServiceImpl hslHslProgramaContentServiceImpl;
	
	
/*	@Test
	public void testCreatePrograma(){
		CreateProgramaCommand cmd=new CreateProgramaCommand();
		List<ProgramaContentDTO> list=new ArrayList<ProgramaContentDTO>();
		ProgramaContentDTO programaContentDTO=new ProgramaContentDTO();
		try {
			cmd.setName("2");
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
	}*/
	/*@Test
	public void updateProgramaContentCommand(){
		updateProgramaContentCommand command=new updateProgramaContentCommand();
		command.setId("2");
		command.setContent("test");
		//hslProgramaQO.setName("1");
		this.hslHslProgramaContentServiceImpl.updateProgramaContent(command);
		//System.out.println(programaDTO);
	}*/
	/*@Test
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
	}*/
	
}	
