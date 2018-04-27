package hg.jxc.admin.common;

import java.util.Iterator;
import java.util.Set;

import hg.pojo.command.JxcCommand;
import hg.system.model.auth.AuthRole;
import hg.system.model.auth.AuthUser;

public class CommandUtil {
	public static void SetOperator(AuthUser authUser, JxcCommand command) {
		if (authUser == null) {
			return;
		}
		command.setOperatorAccount(authUser.getLoginName());
		command.setOperatorName(authUser.getDisplayName());
		
		Iterator<AuthRole> i = authUser.getAuthRoleSet().iterator();
		if (i.hasNext()) {
			command.setOperatorType(i.next().getRoleName());
		}
	}
}
