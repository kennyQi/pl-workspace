package hg.pojo.exception;


@SuppressWarnings("serial")
public class ProjectException extends BaseException {

	public ProjectException(Integer code, String message){
		super(code, message);
	}
	
	/**
	 * 项目名称已存在
	 */
	public final static Integer RESLUT_PROJECT_NAME_REPEAT=1;
	
	/**
	 * 项目已使用
	 */
	public final static Integer RESULT_PROJECT_USE=2;
	
	
}
