package hg.jxc.admin.common;

/**
 * @author wangkq
 */
public class HtmlCodeUtil {
	private StringBuffer sb;
	private int debug = 0;

	public String outputHtml() {
		return sb.toString();
	}

	public HtmlCodeUtil(int debugEnable) {
		this.debug = debugEnable;
		this.sb = new StringBuffer(); 
	}

	public void addTableL(String cssClass, String name, String id) {
		sb.append("<table ");
		this.addAttr("class", cssClass);
		this.addAttr("name", name);
		this.addAttr("id", id);
		sb.append(">");
	}

	public void addTableR() {
		sb.append("</table>");
	}

	public void addTrL() {
		sb.append("<tr>");
	}

	public void addTrR() {
		sb.append("</tr>");
	}

	public void addTdL() {
		sb.append("<td>");
	}

	public void addTdR() {
		sb.append("</td>");
	}

	public void addContent(String content) {
		this.sb.append(content);
	}

	public void addTdWithContent(String content) {
		this.addTdL();
		this.sb.append(content);
		this.addTdR();
	}

	public void addAttr(String attrName, String attrVal) {
		if (attrVal == null) {
			return;
		}
		this.sb.append(" ");
		this.sb.append(attrName);
		this.sb.append("=\"");
		this.sb.append(attrVal);
		this.sb.append("\" ");
	}

	public String genMark(int id) {
		return "@#" + id + "@#";
	}

	public void addMark(int id) {
		sb.append("@#");
		sb.append(id);
		sb.append("@#");
	}

	public void addElementLStart(String elementName) {
		sb.append("<");
		sb.append(elementName);
		sb.append(" ");
	}

	public void addElementLEnd() {
		sb.append(" >");
	}

	public void addElementR(String elementName) {
		sb.append("</");
		sb.append(elementName);
		sb.append(">");
	}

	public void replaceMark(int id, String content) {
		String mark = "@#" + id + "@#";
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

	public void addInputElement(boolean hidden, String name, String id, String value, String ExtraAttr) {
		sb.append("<input ");
		if (hidden == true && debug == 0) {
			this.addAttr("type", "hidden");
		} else {
			this.addAttr("type", "text");
		}
		this.addAttr("name", name);
		this.addAttr("id", id);
		this.addAttr("value", value);
		if (ExtraAttr != null) {
			sb.append(" ");
			sb.append(ExtraAttr);
			sb.append(" ");
		}
		sb.append(" />");

	}
	
	public void addSelctL(String name,String id,String ExtraAttr){
		sb.append("<select ");
		this.addAttr("name", name);
		this.addAttr("id", id);
		if (ExtraAttr != null) {
			sb.append(" ");
			sb.append(ExtraAttr);
			sb.append(" ");
		}
		sb.append(">");
	}
	
//	<select name="select" id="select">
//	  <option value="vv">aaa</option>
//	</select>
	public void addSelctR(){
		sb.append("</select>");
	}
	
	public void addSelctOption(String val,String displayVal){
		sb.append("<option value=\"");
		if (val!=null) {
			sb.append(val);
		}
		sb.append("\">");
		sb.append(displayVal);
		sb.append("</option>");
	}

	public static void main(String[] args) {

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
