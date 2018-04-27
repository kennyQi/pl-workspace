package hsl.pojo.exception;
@SuppressWarnings("serial")
public class LineIndexAdException extends BaseException {

	public LineIndexAdException(Integer code, String message) {
		super(code, message);
	}
	public  final static Integer RESULT_LINEINDEXAD_EXIST=1;
	public  final static Integer RESULT_LINEINDEXAD_NOEXIST=2;
}