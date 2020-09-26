package net.decenternet.technicalexam.data;

import net.decenternet.technicalexam.domain.Task;
import java.util.List;
import java.util.Optional;

public interface TaskLocalService {

    void save(Task task);
    void add(Task task);
    void delete(Integer id);
    void complete(Boolean completed, Integer id);
    Optional<Task> taskSelected(Integer id);
    List<Task> findAll();
}
