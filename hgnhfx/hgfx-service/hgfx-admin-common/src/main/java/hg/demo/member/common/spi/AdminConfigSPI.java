package hg.demo.member.common.spi;



import hg.demo.member.common.domain.model.adminconfig.AdminConfig;
import hg.demo.member.common.domain.model.adminconfig.GeneralResult;
import hg.demo.member.common.spi.command.adminconfig.ClearTestCommand;
import hg.demo.member.common.spi.command.adminconfig.CreateAdminAccountCommand;
import hg.demo.member.common.spi.command.adminconfig.CreateAdminConfigCommand;
import hg.demo.member.common.spi.command.adminconfig.DeleteAdminConfigCommand;
import hg.demo.member.common.spi.command.adminconfig.ModifyAdminConfigCommand;
import hg.demo.member.common.spi.qo.AdminConfigSQO;
import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;

import java.util.List;

public interface AdminConfigSPI extends BaseServiceProviderInterface {

AdminConfig create(CreateAdminConfigCommand command) throws Exception;

AdminConfig modify(ModifyAdminConfigCommand command);

void delete(DeleteAdminConfigCommand command);

Pagination<AdminConfig> queryAdminConfigPagination(AdminConfigSQO sqo);

List<AdminConfig> queryAdminConfigList(AdminConfigSQO sqo);

AdminConfig queryAdminConfig(AdminConfigSQO sqo);


GeneralResult adminIsCreated();


GeneralResult genDbConfig(String jdbcUrl, String userName, String pwd);

GeneralResult clearTest(ClearTestCommand command) throws Exception;

GeneralResult createAdminAccount(CreateAdminAccountCommand command) throws Exception;



}