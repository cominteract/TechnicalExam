package net.decenternet.technicalexam.data;

import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.decenternet.technicalexam.domain.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class TaskLocalServiceProvider implements TaskLocalService {

    private final SharedPreferences sharedPreferences;
    private Collection<Task> localTasks;
    private final String tasksKey = "tasks";
    public TaskLocalServiceProvider(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        localTasks = new Gson()
                .fromJson(
                        sharedPreferences.getString(tasksKey, "[]"),
                        new TypeToken<List<Task>>(){}.getType()
                );
    }

    @Override
    public Optional<Task> taskSelected(final Integer id) {
        return localTasks.stream().filter(task-> task.getId().equals(id)).findFirst();
    }

    @Override
    public void delete(Integer id) {
        taskSelected(id).ifPresent( task -> {
            localTasks.remove(task);
            saveToSharedPref();
        });
    }

    private void saveToSharedPref(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(tasksKey, new Gson().toJson(localTasks));
        editor.commit();
    }

    @Override
    public void complete(Boolean completed, Integer id) {
        ArrayList<Task> tasks = new ArrayList<>(localTasks);
        taskSelected(id).ifPresent( task -> {
            task.setCompleted(completed);
            int index = tasks.indexOf(task);
            tasks.set(index, task);
            localTasks = tasks;
            saveToSharedPref();
        });
    }

    @Override
    public void add(Task task) {
        ArrayList<Task> tasks = new ArrayList<>(localTasks);
        task.setId(getNextId());
        tasks.add(task);
        localTasks = tasks;
        saveToSharedPref();
    }

    @Override
    public void save(Task task) {
        ArrayList<Task> tasks = new ArrayList<>(localTasks);
        taskSelected(task.getId()).ifPresent( ptask -> {
            int index = tasks.indexOf(ptask);
            tasks.set(index, task);
            localTasks = tasks;
            saveToSharedPref();
        });
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(localTasks);
    }

    private Integer getNextId() {
        return localTasks.size() + 1;
    }
}
