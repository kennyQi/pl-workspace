package hg.jxc.admin.common;

import java.util.List;

import hg.system.model.auth.AuthPerm;
import hg.system.model.auth.AuthUser;
import hg.system.service.SecurityService;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;

public class PermUtil {
	public static void addPermListToModel(SecurityService ss, Model model, AuthUser a) {
		if (a == null) {
			return;
		}
		List<AuthPerm> permList = ss.findUserPerms2(a.getLoginName());
		for (int i = 0; i < permList.size(); i++) {
			if (StringUtils.isBlank(permList.get(i).getParentId())) {
				permList.set(i, null);
			}

		}
		model.addAttribute("permList", permList);

	}

	public static void addPermAttr4List(SecurityService ss, Model model, AuthUser a) {
		if (a == null) {
			return;
		}
		List<AuthPerm> permList = ss.findUserPerms2(a.getLoginName());
		for (int i = 0; i < permList.size(); i++) {
			AuthPerm p = permList.get(i);
			if (p.getParentId() == "" || p.getParentId() == null) {
				continue;
			}
			String uri = p.getUrl().replace("/", "");
			model.addAttribute(uri, 1);
		}
	}

	public static void addPermAttr4Side(SecurityService ss, Model model, AuthUser a) {
		if (a == null) {
			return;
		}
		List<AuthPerm> permList = ss.findUserPerms2(a.getLoginName());
		for (int i = 0; i < permList.size(); i++) {
			AuthPerm p = permList.get(i);
			String uri = p.getUrl().replace("/", "");
			model.addAttribute(uri, 1);
		}
	}
}
