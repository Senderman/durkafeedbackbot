package com.senderman.durkafeedbackbot.dbservice;

import com.senderman.durkafeedbackbot.model.Feedback;
import com.senderman.durkafeedbackbot.repository.FeedbackRepository;
import jakarta.inject.Singleton;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Singleton
public class PgsqlFeedbackService implements FeedbackService {

    private final FeedbackRepository repo;

    public PgsqlFeedbackService(FeedbackRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Feedback> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public List<Feedback> findByType(String type) {
        return repo.findByType(type);
    }

    @Override
    public List<Feedback> findByTypeInOrIdIn(Collection<String> types, Collection<Integer> ids) {
        return repo.findByTypeInOrIdIn(types, ids);
    }

    @Override
    public void deleteById(int id) {
        repo.deleteById(id);
    }

    @Override
    public long count() {
        return repo.count();
    }

    @Override
    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    @Override
    public Iterable<Feedback> findAll() {
        return repo.findAll();
    }

    @Override
    public Feedback insert(Feedback feedback) {
        int id = repo.findFirstOrderByIdDesc()
                .map(f -> f.getId() + 1)
                .orElse(1);

        feedback.setId(id);
        return repo.save(feedback);
    }

    @Override
    public Feedback update(Feedback feedback) {
        return repo.update(feedback);
    }

    @Override
    public long deleteByIdBetween(int from, int to) {
        return repo.deleteByIdBetween(from, to);
    }
}
