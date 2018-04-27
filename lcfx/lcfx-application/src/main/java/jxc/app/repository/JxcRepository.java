package jxc.app.repository;

import hg.pojo.dto.log.JxcLog;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JxcRepository extends MongoRepository<JxcLog, String>{

}
