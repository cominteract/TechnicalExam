package net.decenternet.technicalexam.ui.tasks;

import net.decenternet.technicalexam.data.TaskLocalService;
import net.decenternet.technicalexam.domain.Task;
import java.util.List;

public class TasksPresenter implements TasksContract.Presenter {

    private final TaskLocalService taskLocalService;
    private final TasksContract.View view;

    public TasksPresenter(TaskLocalService taskLocalService, TasksContract.View view) {
        this.taskLocalService = taskLocalService;
        this.view = view;
        view.displayTasks(taskLocalService.findAll());
    }

    @Override
    public void onAddTaskClicked(Task task) {
        taskLocalService.add(task);
    }

    @Override
    public List<Task> findAll() { return taskLocalService.findAll(); }

    @Override
    public void onSaveTaskClicked(Task task) {
        taskLocalService.save(task);
    }

    @Override
    public void onTaskChecked(int taskId) {
        taskLocalService.complete(true, taskId);
    }

    @Override
    public void onTaskUnchecked(int taskId) {
        taskLocalService.complete(false, taskId);
    }

    @Override
    public void onDeleteTaskClicked(int taskId) {
        taskLocalService.delete(taskId);
    }
}
