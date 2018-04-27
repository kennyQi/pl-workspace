import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import dzpw.test.TestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:application-just-test.xml" })
public class JUnitTestRun2 {
	
	@Autowired
	private TestService testService;
	
	@Test
	public void hello() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		@SuppressWarnings("unused")
		Enhancer enhancer = null;
		System.out.println("hello world");
		System.out.println(testService.getClass());
		System.out.println(testService.getClass().getSuperclass());
		Method method = TestService.class.getDeclaredMethod("test", String.class);
//		Method[] methods = testService.getClass().getDeclaredMethods();
//		testService.test("nima");
		method.invoke(testService, "nima...");
//		for (Method m : methods) {
//			System.out.println(m.getName());
//			System.out.println(m.getAnnotation(Testannotation.class));
//		}
	}
	
	public static void main(String[] args) {
		
	}
}
