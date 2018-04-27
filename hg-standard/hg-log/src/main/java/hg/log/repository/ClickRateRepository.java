package hg.log.repository;

import hg.log.po.clickrecord.ClickRate;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClickRateRepository extends MongoRepository<ClickRate,String>{

}
