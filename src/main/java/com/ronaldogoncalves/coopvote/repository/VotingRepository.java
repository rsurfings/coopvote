package com.ronaldogoncalves.coopvote.repository;

import com.ronaldogoncalves.coopvote.entity.Voting;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingRepository extends MongoRepository<Voting, ObjectId> {
}