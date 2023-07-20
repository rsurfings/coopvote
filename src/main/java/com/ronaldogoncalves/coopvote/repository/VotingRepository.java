package com.ronaldogoncalves.coopvote.repository;

import com.ronaldogoncalves.coopvote.entity.Voting;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotingRepository extends MongoRepository<Voting, ObjectId> {
    @Query(value = "{'agenda._id' : ?0}")
    List<Voting> findByAgendaId(ObjectId id);
}