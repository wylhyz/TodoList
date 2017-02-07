package com.lhyz.android.todolist.addedittask

import com.lhyz.android.todolist.data.Task
import com.lhyz.android.todolist.data.source.TasksDataSource
import com.lhyz.android.todolist.data.source.TasksRepository

/**
 * hello,android
 * @author lhyz on 2017/2/7
 */
class AddEditTaskPresenter(taskId: String?,
                           tasksRepository: TasksRepository,
                           addTaskView: AddEditTaskContract.View,
                           shouldLoadDataFromRepo: Boolean)
    : AddEditTaskContract.Presenter, TasksDataSource.GetTaskCallback {

    val mTasksRepository: TasksRepository = tasksRepository
    val mAddTaskView: AddEditTaskContract.View = addTaskView

    var mTaskId = taskId
    var mIsDataMissing: Boolean = shouldLoadDataFromRepo

    override fun saveTask(title: String, description: String) {
        if (isNewTask()) {
            createTask(title, description)
        } else {
            updateTask(title, description)
        }
    }

    override fun populateTask() {
        if (isNewTask()) {
            throw RuntimeException("populateTask() was called but task is new.");
        }
        mTasksRepository.getTask(mTaskId!!, this)
    }

    override fun isDataMissing(): Boolean {
        return mIsDataMissing
    }

    override fun start() {
        if (!isNewTask() && mIsDataMissing) {
            populateTask()
        }
    }

    override fun onTaskLoaded(task: Task?) {
        // The view may not be able to handle UI updates anymore
        if (mAddTaskView.isActive()) {
            mAddTaskView.setTitle(task!!.title!!)
            mAddTaskView.setDescription(task.description!!)
        }
        mIsDataMissing = false
    }

    override fun onDataNotAvailable() {
        // The view may not be able to handle UI updates anymore
        if (mAddTaskView.isActive()) {
            mAddTaskView.showEmptyTaskError()
        }
    }

    private fun isNewTask(): Boolean {
        return mTaskId == null
    }

    private fun createTask(title: String?, description: String?) {
        val newTask = Task(title, description)
        if (newTask.isEmpty()) {
            mAddTaskView.showEmptyTaskError()
        } else {
            mTasksRepository.saveTask(newTask)
            mAddTaskView.showTasksList()
        }
    }

    private fun updateTask(title: String?, description: String?) {
        if (isNewTask()) {
            throw RuntimeException("updateTask() was called but task is new.");
        }
        mTasksRepository.saveTask(Task(title, description, mTaskId!!))
        mAddTaskView.showTasksList()
    }
}