import org.aspectj.lang.ProceedingJoinPoint;


public class TestServiceAspect {

	
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("---->>");
		return pjp.proceed();
	}


}
