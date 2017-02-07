package com.lhyz.android.todolist.tasks

import android.app.Activity
import com.lhyz.android.todolist.addedittask.AddEditTaskActivity
import com.lhyz.android.todolist.data.Task
import com.lhyz.android.todolist.data.source.TasksDataSource
import com.lhyz.android.todolist.data.source.TasksRepository
import java.util.*


/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
class TasksPresenter(tasksRepository: TasksRepository, tasksView: TasksContract.View) : TasksContract.Presenter {

    val mTasksRepository: TasksRepository = tasksRepository
    val mTasksView: TasksContract.View = tasksView

    var mCurrentFiltering = TasksFilterType.ALL_TASKS
    var mFirstLoad = true

    init {
        mTasksView.setPresenter(this)
    }

    override fun result(requestCode: Int, resultCode: Int) {
        // If a task was successfully added, show snackbar
        if (AddEditTaskActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
            mTasksView.showSuccessfullySavedMessage()
        }
    }

    override fun loadTasks(forceUpdate: Boolean) {
        loadTasks(forceUpdate || mFirstLoad, true)
        mFirstLoad = false
    }

    private fun loadTasks(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            mTasksView.setLoadingIndicator(true)
        }
        if (forceUpdate) {
            mTasksRepository.refreshTasks()
        }

        mTasksRepository.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                val tasksToShow = ArrayList<Task>()

                // We filter the tasks based on the requestType
                for (task in tasks) {
                    when (mCurrentFiltering) {
                        TasksFilterType.ALL_TASKS -> tasksToShow.add(task)
                        TasksFilterType.ACTIVE_TASKS -> if (task.isActive()) {
                            tasksToShow.add(task)
                        }
                        TasksFilterType.COMPLETED_TASKS -> if (task.completed) {
                            tasksToShow.add(task)
                        }
                        else -> tasksToShow.add(task)
                    }
                }
                // The view may not be able to handle UI updates anymore
                if (!mTasksView.isActive()) {
                    return
                }
                if (showLoadingUI) {
                    mTasksView.setLoadingIndicator(false)
                }

                processTasks(tasksToShow)
            }

            override fun onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mTasksView.isActive()) {
                    return
                }
                mTasksView.showLoadingTasksError()
            }
        })
    }

    private fun processTasks(tasks: List<Task>) {
        if (tasks.isEmpty()) {
            processEmptyTasks()
        } else {
            mTasksView.showTasks(tasks)
            showFilterLabel()
        }
    }

    private fun showFilterLabel() {
        when (mCurrentFiltering) {
            TasksFilterType.ACTIVE_TASKS -> mTasksView.showActiveFilterLabel()
            TasksFilterType.COMPLETED_TASKS -> mTasksView.showCompletedFilterLabel()
            else -> mTasksView.showAllFilterLabel()
        }
    }

    private fun processEmptyTasks() {
        when (mCurrentFiltering) {
            TasksFilterType.ACTIVE_TASKS -> mTasksView.showNoActiveTasks()
            TasksFilterType.COMPLETED_TASKS -> mTasksView.showNoCompletedTasks()
            else -> mTasksView.showNoTasks()
        }
    }

    override fun addNewTask() {
        mTasksView.showAddTask()
    }

    override fun openTaskDetails(requestedTask: Task) {
        mTasksView.showTaskDetailsUi(requestedTask.id)
    }

    override fun completeTask(completedTask: Task) {
        mTasksRepository.completeTask(completedTask)
        mTasksView.showTaskMarkedComplete()
        loadTasks(false, false)
    }

    override fun activateTask(activeTask: Task) {
        mTasksRepository.activateTask(activeTask)
        mTasksView.showTaskMarkedActive()
        loadTasks(false, false)
    }

    override fun clearCompletedTasks() {
        mTasksRepository.clearCompletedTasks()
        mTasksView.showCompletedTasksCleared()
        loadTasks(false, false)
    }

    override fun setFiltering(requestType: TasksFilterType) {
        mCurrentFiltering = requestType
    }

    override fun getFiltering(): TasksFilterType {
        return mCurrentFiltering
    }

    override fun start() {
        loadTasks(false)
    }
}