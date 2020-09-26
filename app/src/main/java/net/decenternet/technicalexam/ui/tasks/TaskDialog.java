package net.decenternet.technicalexam.ui.tasks;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import net.decenternet.technicalexam.R;
import net.decenternet.technicalexam.domain.Task;

public class TaskDialog {
    public void showAddDialog(Activity activity, TasksContract.View view){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_task);
        EditText dialogText = (EditText) dialog.findViewById(R.id.et_task_details);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_save);
        dialogButton.setText(activity.getString(R.string.save));
        dialogButton.setOnClickListener(v -> {
            Task task = new Task();
            task.setDescription(dialogText.getText().toString());
            view.addTaskToList(task);
            dialog.dismiss();
        });
        dialog.show();
    }

    public void showEditDialog(Activity activity, TasksContract.View view, Task task){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_task);
        EditText dialogText = (EditText) dialog.findViewById(R.id.et_task_details);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_save);
        dialogButton.setText(activity.getString(R.string.update));
        dialogButton.setOnClickListener(v -> {
            task.setDescription(dialogText.getText().toString());
            view.updateTaskInList(task);
            dialog.dismiss();
        });
        dialog.show();
    }
}
