package com.senderman.durkafeedbackbot.dbservice;

import com.senderman.durkafeedbackbot.model.Feedback;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FeedbackService {

    Optional<Feedback> findById(int id);

    List<Feedback> findByType(String type);

    List<Feedback> findByTypeInOrIdIn(Collection<String> types, Collection<Integer> ids);

    void deleteById(int id);

    long count();

    boolean existsById(int id);

    Iterable<Feedback> findAll();

    // id could be changed, so use the returned object
    Feedback insert(Feedback feedback);

    Feedback update(Feedback feedback);

    /**
     * Delete feedbacks in range
     *
     * @param from lower bound (inclusive)
     * @param to   upper bound (inclusive)
     * @return amount of deleted feedbacks
     */
    long deleteByIdBetween(int from, int to);

}
