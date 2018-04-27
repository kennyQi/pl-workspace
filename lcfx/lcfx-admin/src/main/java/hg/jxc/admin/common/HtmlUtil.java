package hg.jxc.admin.common;

import java.util.ArrayList;
import java.util.List;

import jxc.domain.model.product.SkuProduct;
import jxc.domain.model.product.SpecDetail;
import jxc.domain.model.system.Project;

public class HtmlUtil {
	static String getHtmlTableProductSku(List<Project> projList,List<SkuProduct> skuList){
		StringBuffer sb = new StringBuffer();

		sb.append("<select name=\"select\" id=\"select\">");
		for (Project p : projList) {
			sb.append("<option value=\"");
			sb.append(p.getId());
			sb.append("\">");
			sb.append(p.getName());
			sb.append("</option>");
		}
		sb.append("</select>");

		String projectSelectorHtml = sb.toString();
		sb.setLength(0);
		// create sku HTML table
		sb.append("<table border=\"1\">");
		sb.append("<tr>");
		sb.append("<td>项目</td>");
		for (SpecDetail specDetail : skuList.get(0).getSpecDetails()) {
			sb.append("<td>");
			sb.append(specDetail.getSpecification().getSpecName());
			sb.append("</td>");
		}
		sb.append("<td>sku编码</td>");
		sb.append("<td>平台编码</td>");
		sb.append("</tr>");
		for (int i = 0; i < skuList.size(); i++) {
			SkuProduct skuProduct = skuList.get(i);
			sb.append("<tr>");
			if (i == 0) {

				sb.append("<td rowspan=\"");
				sb.append(skuList.size() * skuProduct.getSpecDetails().size());
				sb.append("\">");
				sb.append(projectSelectorHtml);
				sb.append("</td>");

			}
			for (SpecDetail specDetail : skuProduct.getSpecDetails()) {
				sb.append("<td>");
				sb.append(specDetail.getSpecValue().getSpecValue());
				sb.append("</td>");
			}
			sb.append("<td>");
			sb.append(skuProduct.getId());
			sb.append("</td>");
			sb.append("<td>");
			sb.append("<input type=\"text\" >");
			sb.append("</td>");
			sb.append("<tr/>");
		}
		
		sb.append("</table>");
		return sb.toString();
	}
	
	static public void getHtmlTableProductSku2(List<SkuProduct> skuList){
		StringBuffer sb = new StringBuffer();
		// create sku HTML table
		int i = 0;// sku计数

		int tableH = skuList.size();
		int tableW = skuList.get(0).getSpecDetails().size();
		List<Integer> lineHList = new ArrayList<Integer>();
		for (int j = 0; j < tableW; j++) {
			int lineH = 0;
			String lastId = skuList.get(0).getSpecDetails().get(j).getSpecValue().getId();
			for (int j2 = 0; j2 < skuList.size(); j2++) {
				if (lastId.equals(skuList.get(j2).getSpecDetails().get(j).getSpecValue().getId())) {
					lineH++;
				} else {
					lineHList.add(new Integer(lineH));
					break;
				}

				if (tableH == lineH) {
					lineHList.add(new Integer(lineH));
				}
			}
		}

		sb.append("<table border=\"1\">");
		sb.append("<tr>");
		for (SpecDetail specDetail : skuList.get(0).getSpecDetails()) {
			sb.append("<td>");
			sb.append(specDetail.getSpecification().getSpecName());
			sb.append("</td>");
		}
		sb.append("<td>sku编码</td>");
		sb.append("</tr>");

		for (int ii = 0; ii < skuList.size(); ii++) {
			SkuProduct skuProduct = skuList.get(ii);
			sb.append("<tr>");

			for (int j = 0; j < skuProduct.getSpecDetails().size(); j++) {
				SpecDetail specDetail = skuProduct.getSpecDetails().get(j);
				if (ii % lineHList.get(j) == 0 || ii == 0) {
					sb.append("<td rowspan=\"").append(lineHList.get(j)).append("\">");
					sb.append(specDetail.getSpecValue().getSpecValue());
					sb.append("</td>");
				}
			}
			sb.append("<td>");
			sb.append(skuProduct.getId());
			sb.append("<input type=\"text\" name=\"skuList[");
			sb.append(i);
			i = i + 1;
			sb.append("]\" value=\"");
			sb.append(skuProduct.getId());
			sb.append("\"/>");
			sb.append("</td>");
			sb.append("</tr>");

		}
		sb.append("</table>");
		
	}



	public static void getHtmlTableSpecForProdEdit() {
		// TODO Auto-generated method stub
		
		
	}
	
	
}
