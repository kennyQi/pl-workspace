package hg.jxc.admin.common;

import hg.common.model.DwzTreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * dwz树形HTML生成工具
 * 
 * @author zhurz
 */
public class JxcDwzTreeUtil {

	/**
	 * 将dwzTreeList转换成dwz树HTML代码 dwzTree节点parentId为null视为顶级节点
	 * 
	 * @param dwzTreeList
	 * @param cssClass
	 * @return
	 */
	public static String createDwzTreeListHtml(List<DwzTreeNode> dwzTreeList,
			String cssClass) {
		try {
			return createDwzTreeHtml(createDwzTree(dwzTreeList), cssClass);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 通过无关联的dwzTreeList转换为有关联的 dwzTree节点parentId为null视为顶级节点
	 * 
	 * @param dwzTreeList
	 * @return
	 */
	public static List<DwzTreeNode> createDwzTree(List<DwzTreeNode> dwzTreeList) {
		Map<String, List<DwzTreeNode>> map = new HashMap<String, List<DwzTreeNode>>();
		for (DwzTreeNode dwzTree : dwzTreeList) {
			String parentId = dwzTree.getParentId();
			if (!map.containsKey(parentId)) {
				map.put(parentId, new ArrayList<DwzTreeNode>());
			}
			map.get(parentId).add(dwzTree);
		}
		for (DwzTreeNode dwzTree : dwzTreeList) {
			dwzTree.setChildrenList(map.get(dwzTree.getId()));
		}
		return map.containsKey(null) ? map.get(null)
				: new ArrayList<DwzTreeNode>();
	}

	private static String createDwzTreeHtml(List<DwzTreeNode> dwzTreeList,
			String cssClass) {
		StringBuffer htmlBuf = new StringBuffer();
		htmlBuf.append("<ul class=\"");
		if (StringUtils.isNotBlank(cssClass)) {
			htmlBuf.append(cssClass);
		} else {
			htmlBuf.append("tree treeFolder");
		}
		htmlBuf.append("\">");
		createChildrenListhtml(dwzTreeList, htmlBuf);
		htmlBuf.append("</ul>");
		return htmlBuf.toString();
	}

	private static void createChildrenListhtml(List<DwzTreeNode> dwzTreeList,
			StringBuffer sb) {
		if (sb != null && dwzTreeList != null && dwzTreeList.size() > 0) {
			for (DwzTreeNode dwzTree : dwzTreeList) {
				sb.append("<li><a");
				Map<String, String> attrMap = dwzTree.getAttrMap();
				for (String attrKey : attrMap.keySet()) {
					String attrValue = attrMap.get(attrKey);
					if (StringUtils.isNotBlank(attrValue)) {
						sb.append(" ").append(attrKey).append("=\"")
								.append(attrValue).append("\"");
					}
				}

				sb.append(">");
				//
				sb.append("<span style=\"float:right; display: inline;; margin-right:70px\"><b style=\"padding-right:100px\">");
				
				if (dwzTree.isChecked()) {
					sb.append("启用");
				}else{
					sb.append("禁用");
				}
				sb.append		("</b><b onclick=\"edit('");

				sb.append(dwzTree.getId());
				sb.append("')\" style=\" display:inline\">编辑</b>&nbsp;&nbsp;&nbsp;&nbsp;<b onclick=\"del('");
				sb.append(dwzTree.getId());
				sb.append("')\" style=\" display:inline\">删除</b>&nbsp;&nbsp;&nbsp;&nbsp;");
				
				
				if (dwzTree.isChecked()) {
					sb.append("<b onclick=\"addChild('");
					sb.append(dwzTree.getId());
					sb.append("')\" style=\" display:inline\">添加子节点</b></span>");
				}else{
					sb.append("<b style=\"color:#CCCCCC\" >添加子节点</b></span>");
				}
				//
				sb.append(dwzTree.getDisplayName());

				sb.append("</a>");
				if (dwzTree.hasChildren()) {
					sb.append("<ul>");
					createChildrenListhtml(dwzTree.getChildrenList(), sb);
					sb.append("</ul>");
				}
				sb.append("</li>");
			}
		}
	}

}
