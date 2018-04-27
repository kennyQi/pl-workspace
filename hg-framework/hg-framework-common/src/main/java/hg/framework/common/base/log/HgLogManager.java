package hg.framework.common.base.log;

import hg.framework.common.model.Pagination;

import java.util.List;

/**
 * 汇购日志管理
 *
 * @author zhurz
 */
public class HgLogManager {

	/**
	 * 日志配置
	 */
	private HgLogConfig config;

	public HgLogManager(HgLogConfig config) {
		this.config = config;
	}

	/**
	 * 查询日志列表
	 *
	 * @param qo
	 * @return
	 */
	public List<HgLog> queryList(HgLogQO qo) {

		return null;
	}

	/**
	 * 分页查询日志
	 *
	 * @param qo
	 * @return
	 */
	public Pagination<HgLog> queryPagination(HgLogQO qo) {

		return null;
	}

}
