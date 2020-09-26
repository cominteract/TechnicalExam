package net.decenternet.technicalexam.ui.tasks;

import net.decenternet.technicalexam.domain.Task;
import java.util.List;

public interface TasksContract {
    
    public interface View {
        void displayTasks(List<Task> tasks);
        void addTaskToList(Task task);
        void removeTaskFromList(Task task);
        void updateTaskInList(Task task);
        void popupTaskAddingDialog();
        void popupTaskEditorDialog(Task task);
        void completedChanged(Task task);
    }
    
    public interface Presenter {
        List<Task> findAll();
        void onAddTaskClicked(Task task);
        void onSaveTaskClicked(Task task);
        void onTaskChecked(int taskId);
        void onTaskUnchecked(int taskId);
        void onDeleteTaskClicked(int taskId);
    }
    
}
