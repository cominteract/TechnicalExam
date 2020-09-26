package net.decenternet.technicalexam.ui.tasks;

import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import net.decenternet.technicalexam.R;
import net.decenternet.technicalexam.data.TaskLocalService;
import net.decenternet.technicalexam.data.TaskLocalServiceProvider;
import net.decenternet.technicalexam.domain.Task;
import java.util.List;

public class TasksActivity extends AppCompatActivity implements TasksContract.View {

    private TasksContract.Presenter presenter;
    private TaskAdapter adapter;
    private TaskLocalService taskLocalService;
    private TaskDialog taskDialog = new TaskDialog();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        taskLocalService = new TaskLocalServiceProvider(PreferenceManager
                .getDefaultSharedPreferences(this));
        presenter = new TasksPresenter(taskLocalService,this);
        findViewById(R.id.iv_add_task).setOnClickListener(view -> popupTaskAddingDialog());
        /**
         * Finish this simple task recording app.
         * The following are the remaining todos for this project:
         * 1. Make sure all defects are fixed.
         * 2. Showing a dialog to add/edit the task.
         * 3. Allowing the user to toggle it as completed.
         * 4. Allowing the user to delete a task.
         *
         */
    }

    private void refreshAll(){
        if(adapter != null && !((RecyclerView)findViewById(R.id.rv_tasks)).isComputingLayout()){
            adapter.updateTasks(presenter.findAll());
        }
    }

    private void refreshSingleTask(Task task){
        if(adapter != null && !((RecyclerView)findViewById(R.id.rv_tasks)).isComputingLayout()) {
            adapter.updatedTask(task.getId(), presenter.findAll());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void displayTasks(List<Task> tasks) {
        if(adapter == null){
            adapter = new TaskAdapter(tasks, this);
            ((RecyclerView)findViewById(R.id.rv_tasks)).setAdapter(adapter);
        }else{
            adapter.updateTasks(tasks);
        }
    }

    @Override
    public void addTaskToList(Task task) {
        presenter.onAddTaskClicked(task);
        refreshAll();
    }

    @Override
    public void removeTaskFromList(Task task) {
        presenter.onDeleteTaskClicked(task.getId());
        refreshAll();
    }

    @Override
    public void updateTaskInList(Task task) {
        presenter.onSaveTaskClicked(task);
        refreshSingleTask(task);
    }

    @Override
    public void completedChanged(Task task) {
        if(task.getCompleted()){
            presenter.onTaskChecked(task.getId());
        }else{
            presenter.onTaskUnchecked(task.getId());
        }
        refreshSingleTask(task);
    }

    @Override
    public void popupTaskAddingDialog() {
        taskDialog.showAddDialog(this, this);
    }

    @Override
    public void popupTaskEditorDialog(Task task) {
        taskDialog.showEditDialog(this, this, task);
    }
}
