package hg.log.repository;

import hg.log.po.domainevent.DomainEvent;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DomainEventRepository extends MongoRepository<DomainEvent, String> {
	
	
}
