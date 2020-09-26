package net.decenternet.technicalexam.ui.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import net.decenternet.technicalexam.R;
import net.decenternet.technicalexam.domain.Task;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskDataHolder> {
    private List<Task> taskList;
    private TasksContract.View view;
    TaskAdapter(List<Task> taskList, TasksContract.View view){
        this.view = view;
        this.taskList = taskList;
    }

    void updateTasks(List<Task> tasks){
        taskList = tasks;
        notifyDataSetChanged();
    }

    void updatedTask(int id,List<Task> tasks){
        taskList = tasks;
        taskList.stream().filter(task -> task.getId().equals(id)).findFirst().ifPresent(task -> {
                    int index = taskList.indexOf(task);
                    if(index >= 0){
                        notifyItemChanged(taskList.indexOf(task));
                    }
                }
        );
    }

    @NonNull
    @Override
    public TaskDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskDataHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskDataHolder holder, int position) {
        holder.bind(taskList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskDataHolder extends RecyclerView.ViewHolder {

        public TaskDataHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Task task){
            ((TextView)itemView.findViewById(R.id.tv_task_details)).setText(task.getDescription());
            ((CheckBox)itemView.findViewById(R.id.cb_task)).setChecked(task.getCompleted());
            ((CheckBox)itemView.findViewById(R.id.cb_task)).setOnCheckedChangeListener((compoundButton, b) -> {
                task.setCompleted(b);
                view.completedChanged(task);
            });
            itemView.findViewById(R.id.iv_delete_task).setOnClickListener(imageView -> {
                view.removeTaskFromList(task);
            });
            itemView.setOnClickListener(itemView -> {
                view.popupTaskEditorDialog(task);
            });
        }
    }
}
