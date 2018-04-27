package hg.demo.member.common.spi.command.adminconfig;

import hg.framework.common.base.BaseSPICommand;

/**
 * 删除命令
 * @author Administrator
 * @date 2016-5-30
 * @since
 */
public class DeleteAdminConfigCommand extends BaseSPICommand{
public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

private String id;

}