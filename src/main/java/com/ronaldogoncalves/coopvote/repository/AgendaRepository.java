package com.ronaldogoncalves.coopvote.repository;

import com.ronaldogoncalves.coopvote.entity.Agenda;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends MongoRepository<Agenda, ObjectId> {
}