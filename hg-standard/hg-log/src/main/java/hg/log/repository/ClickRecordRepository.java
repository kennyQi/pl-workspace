package hg.log.repository;

import hg.log.po.clickrecord.ClickRecord;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClickRecordRepository extends MongoRepository<ClickRecord,String>{

}
