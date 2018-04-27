package hsl.app.dao.log;

import hsl.pojo.log.PLZXClickRecord;

import org.springframework.data.mongodb.repository.MongoRepository;
public interface PLZXClickRecordRepository extends MongoRepository<PLZXClickRecord,String>{

}
