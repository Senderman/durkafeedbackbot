package com.senderman.durkafeedbackbot.repository;

import com.senderman.durkafeedbackbot.model.Feedback;
import io.micronaut.data.mongodb.annotation.MongoDeleteQuery;
import io.micronaut.data.mongodb.annotation.MongoFindOptions;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@MongoRepository
public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

    @MongoFindQuery(filter = "{}", sort = "{ _id: -1 }")
    @MongoFindOptions(limit = 1)
    Optional<Feedback> findFirstOrderByIdDesc();

    @MongoDeleteQuery("{ _id: { $gte: :from, $lte: :to } }")
    long deleteByIdBetween(int from, int to);

    List<Feedback> findByType(String type);

    @MongoFindQuery("{ $or: [ { type: { $in: :types } }, { _id: { $in: :ids } } ] }")
    List<Feedback> findByTypeInOrIdIn(Collection<String> types, Collection<Integer> ids);

}
