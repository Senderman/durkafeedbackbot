package com.senderman.durkafeedbackbot.repository;

import com.senderman.durkafeedbackbot.model.Feedback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

    Optional<Feedback> findFirstByOrderByIdDesc();

    long deleteByIdBetween(int from, int to);

    List<Feedback> findByType(String type);

    List<Feedback> findByTypeInOrIdIn(Collection<String> types, Collection<Integer> ids);

}
