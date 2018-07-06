package com.example.binh.todomvvmdatabindingp1.tasks.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.example.binh.todomvvmdatabindingp1.R;
import com.example.binh.todomvvmdatabindingp1.data.source.TasksRepository;
import com.example.binh.todomvvmdatabindingp1.tasks.TasksFilterType;

/**
 * Exposes the data to be used in the task list screen.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class TasksViewModel extends BaseObservable implements ITasksViewModel{

    public final ObservableField<String> currentFilteringLabel = new ObservableField<>();
    public final ObservableField<String> noTasksLabel = new ObservableField<>();
    public final ObservableField<Drawable> noTaskIconRes = new ObservableField<>();
    public final ObservableBoolean tasksAddViewVisible = new ObservableBoolean();


    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;

    private Context mContext; // To avoid leaks, this must be an Application Context.

    private final TasksRepository mTasksRepository;

    public TasksViewModel(TasksRepository repository, Context context) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        mTasksRepository = repository;

        // Set initial state
        setFiltering(TasksFilterType.ALL_TASKS);
    }

    @Override
    public void setFiltering(TasksFilterType requestType) {
        mCurrentFiltering = requestType;
        // Depending on the filter type, set the filtering label, icon drawables, etc.
        switch (requestType) {
            case ALL_TASKS:
                setResponse(R.string.label_all, R.string.no_tasks_all,
                        R.drawable.ic_assignment_turned_in_24dp, true);
                break;
            case ACTIVE_TASKS:
                setResponse(R.string.label_active, R.string.no_tasks_active,
                        R.drawable.ic_check_circle_24dp, false);
                break;
            case COMPLETED_TASKS:
                setResponse(R.string.label_completed, R.string.no_tasks_completed,
                        R.drawable.ic_verified_user_24dp, false);
                break;
        }
    }

    private void setResponse(@StringRes int label_all, @StringRes int no_task,
                             @DrawableRes int drawable, boolean shouldAddViewVisible) {
        currentFilteringLabel.set(mContext.getString(label_all));
        noTasksLabel.set(mContext.getResources().getString(no_task));
        noTaskIconRes.set(mContext.getResources().getDrawable(drawable));
        tasksAddViewVisible.set(shouldAddViewVisible);
    }
}
