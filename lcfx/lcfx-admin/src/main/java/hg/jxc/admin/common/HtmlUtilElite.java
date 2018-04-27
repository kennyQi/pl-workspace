package hg.jxc.admin.common;

/**
 * @author wangkq
 */
public class HtmlUtilElite {
	
	private StringBuffer sb;
	private int debug = 0;

	public HtmlUtilElite(int debug,String content) {
		this.debug = debug;
		sb = new StringBuffer(content);
	}

	public String outputHtml() {
		if (debug == 1) {
			this.replaceMark("inputType", "text");
		} else {
			this.replaceMark("inputType", "hidden");
		}
		return sb.toString();
	}

	public void replaceMark(String markName, String content) {
		String mark = "@{" + markName + "}";
		int i = -1;
		i = sb.indexOf(mark);
		while (i >= 0) {
			if (content == null) {
				sb.delete(i, i + mark.length());
				return;
			}
			sb.replace(i, i + mark.length(), content);
			i = sb.indexOf(mark);
		}
	}



	// ><input name="" type="text" />
	// <table border="1">
	// <tr>
	// <td>&nbsp;</td>
	// <td>&nbsp;</td>
	//
	// </tr>
	// <tr>
	// <td><input name="" type="text" /></td>
	// <td><input name="" type="hidden" id="" /></td>
	// </tr>
	// </table>

}
