import org.json.JSONArray;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
public class JUnitTestRun {
	
	
//	@Test
	public void hello(){
		System.out.println("hello world");
	}
	
	public static void main(String[] args) {
		String jsonString="[\"13\",\"13\",\"请选择\"]";
		JSONArray jsonArray=new JSONArray(jsonString);
		for (int i = 0; i < jsonArray.length(); i++) {
			System.out.println(jsonArray.get(i));
		}
	}
}
