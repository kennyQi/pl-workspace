package hg.log.repository;

import hg.log.po.normal.HgMail;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @类功能说明：Mail邮件Repository
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年10月26日 上午9:11:26
 */
public interface MailRepository extends MongoRepository<HgMail,String> {
	
}