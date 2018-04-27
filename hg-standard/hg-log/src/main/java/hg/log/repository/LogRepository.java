package hg.log.repository;

import hg.log.po.normal.HgLog;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<HgLog, String> {
	
}
