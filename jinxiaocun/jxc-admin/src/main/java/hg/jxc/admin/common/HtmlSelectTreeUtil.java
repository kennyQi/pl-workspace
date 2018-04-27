package hg.jxc.admin.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
/**
 * 
 * @author wangkq 
 *
 */
public class HtmlSelectTreeUtil {
	private String mode;
	private int findUsing;
	// 0全部
	// 1只选上级
	// 2只选下级
	// ""或null=全部
	private List<Node> list;
	private String inputName;

	public HtmlSelectTreeUtil(String inputName, String modeString,int findUsing) {
		this.inputName = inputName;
		this.mode = modeString;
		this.findUsing = findUsing;
		list = new ArrayList<HtmlSelectTreeUtil.Node>();
	}

	public void addList(String pid, String id, String name,Boolean enable) {
		list.add(new Node(pid, id, name, enable));
	}

	public String getHTML() {
		StringBuffer sb = new StringBuffer();
		for (Node node : list) {
			if (StringUtils.isNotBlank(node.pid)) {
				for (Node node2 : list) {
					if (StringUtils.isNotBlank(node2.id) && node.pid.equals(node2.id)) {
						if (node2.list == null) {
							node2.list = new ArrayList<HtmlSelectTreeUtil.Node>();
						}
						node2.list.add(node);
					}
				}
			}
		}
		for (Node node : list) {
			if (StringUtils.isBlank(node.pid)) {
				node.appendNode2a(sb);
				node.addChild(sb, inputName);
				sb.append("</li>");
			}
		}
		return sb.toString();

	}

	private class Node {

		public Node(String pid, String id, String name,Boolean enable) {
			this.pid = pid;
			this.id = id;
			this.name = name;
			this.enable = enable;
		}

		Boolean enable;
		String pid;
		String id;
		String name;
		List<Node> list;

		private void addChild(StringBuffer sb, String inputName) {
			if (this.list == null || this.list.size() == 0) {
				return;
			}
			sb.append("<ul>");
			for (Node node : this.list) {
				node.appendNode2a(sb);
				node.addChild(sb, inputName);
				sb.append("</li>");
			}
			sb.append("</ul>");
		}

		private void appendNode2a(StringBuffer sb) {
			if (StringUtils.isBlank(mode) || "0".equals(mode)) {
				if(findUsing ==1 && this.enable == false){
					sb.append("<li><a style=\"color:#CCCCCC\" >");
				}else {
					sb.append("<li><a style=\"color:#0066CC\" href=\"javascript:\" onclick=\"$.bringBack({");
					sb.append(inputName);
					sb.append(":'");
					sb.append(this.id);
					sb.append("', categoryName:'");
					sb.append(this.name);
					sb.append("'})\">");
				}
				sb.append(this.name);
				if (this.enable==false) {
					sb.append("(禁用)");
				}

				sb.append("</a>");
			} else if ("1".equals(mode)) {
				if (this.list == null || this.list.size() == 0) {
					sb.append("<li><a style=\"color:#CCCCCC\">");
					sb.append(this.name);
					if (this.enable==false) {
						sb.append("(禁用)");
					}

					sb.append("</a>");
				} else {
					if(findUsing ==1 && this.enable == false){
						sb.append("<li><a style=\"color:#CCCCCC\">");
					}else {
						sb.append("<li><a style=\"color:#0066CC\" href=\"javascript:\" onclick=\"$.bringBack({");
						sb.append(inputName);
						sb.append(":'");
						sb.append(this.id);
						sb.append("', categoryName:'");
						sb.append(this.name);
						sb.append("'})\">");
					}
					sb.append(this.name);
					if (this.enable==false) {
						sb.append("(禁用)");
					}

					sb.append("</a>");
				}

			} else if ("2".equals(mode)) {
				if (this.list == null || this.list.size() == 0) {
					
					if(findUsing ==1 && this.enable == false){
						sb.append("<li><a style=\"color:#CCCCCC\">");
					}else {
						sb.append("<li><a style=\"color:#0066CC\" href=\"javascript:\" onclick=\"$.bringBack({");
						sb.append(inputName);
						sb.append(":'");
						sb.append(this.id);
						sb.append("', categoryName:'");
						sb.append(this.name);
						sb.append("'})\">");
					}
					sb.append(this.name);
					if (this.enable==false) {
						sb.append("(禁用)");
					}
					sb.append("</a>");

				} else {

					sb.append("<li><a style=\"color:#CCCCCC\" >");
					sb.append(this.name);
					if (this.enable==false) {
						sb.append("(禁用)");
					}
					sb.append("</a>");

				}

			}
			// 格式：
			// onclick="$.bringBack({categoryId:'11', category:'分类11'})">分类11</a></li>
		}
	}

}
