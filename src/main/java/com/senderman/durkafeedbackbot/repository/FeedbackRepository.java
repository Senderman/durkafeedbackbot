package com.senderman.durkafeedbackbot.repository;

import com.senderman.durkafeedbackbot.model.Feedback;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

    Optional<Feedback> findFirstOrderByIdDesc();

    long deleteByIdBetween(int from, int to);

    List<Feedback> findByType(String type);

    List<Feedback> findByTypeInOrIdIn(Collection<String> types, Collection<Integer> ids);

}
