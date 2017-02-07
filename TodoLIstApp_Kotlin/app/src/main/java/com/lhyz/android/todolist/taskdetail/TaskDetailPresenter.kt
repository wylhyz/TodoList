package com.lhyz.android.todolist.taskdetail

import com.lhyz.android.todolist.data.Task
import com.lhyz.android.todolist.data.source.TasksDataSource
import com.lhyz.android.todolist.data.source.TasksRepository

/**
 * hello,android
 * @author lhyz on 2017/2/7
 */
class TaskDetailPresenter(taskId: String?,
                          tasksRepository: TasksRepository,
                          tasksDetailView: TaskDetailContract.View)
    : TaskDetailContract.Presenter {

    val mTasksRepository = tasksRepository
    val mTaskDetailView = tasksDetailView
    var mTaskId: String? = taskId

    init {
        mTaskDetailView.setPresenter(this)
    }

    override fun editTask() {
        if (mTaskId.isNullOrEmpty()) {
            mTaskDetailView.showMissingTask()
            return
        }
        mTaskDetailView.showEditTask(mTaskId!!)
    }

    override fun deleteTask() {
        if (mTaskId.isNullOrEmpty()) {
            mTaskDetailView.showMissingTask()
        }
        mTasksRepository.deleteTask(mTaskId!!)
        mTaskDetailView.showTaskDeleted()
    }

    override fun completeTask() {
        if (mTaskId.isNullOrEmpty()) {
            mTaskDetailView.showMissingTask()
            return
        }
        mTasksRepository.completeTask(mTaskId!!)
        mTaskDetailView.showTaskMarkedComplete()
    }

    override fun activateTask() {
        if (mTaskId.isNullOrEmpty()) {
            mTaskDetailView.showMissingTask()
            return
        }
        mTasksRepository.activateTask(mTaskId!!)
        mTaskDetailView.showTaskMarkedActive()
    }

    override fun start() {
        openTask()
    }

    fun openTask() {
        if (mTaskId.isNullOrEmpty()) {
            mTaskDetailView.showMissingTask()
            return
        }

        mTaskDetailView.setLoadingIndicator(true)
        mTasksRepository.getTask(mTaskId!!, object : TasksDataSource.GetTaskCallback {
            override fun onTaskLoaded(task: Task?) {
                if (!mTaskDetailView.isActive()) {
                    return
                }
                mTaskDetailView.setLoadingIndicator(false)
                if (null == task) {
                    mTaskDetailView.showMissingTask()
                } else {
                    showTask(task)
                }
            }

            override fun onDataNotAvailable() {
                if (!mTaskDetailView.isActive()) {
                    return
                }
                mTaskDetailView.showMissingTask()
            }
        })
    }

    fun showTask(task: Task) {
        val title = task.title
        val description = task.description

        if (title.isNullOrEmpty()) {
            mTaskDetailView.hideTitle()
        } else {
            mTaskDetailView.showTitle(title!!)
        }

        if (description.isNullOrEmpty()) {
            mTaskDetailView.hideDescription()
        } else {
            mTaskDetailView.showDescription(description!!)
        }

        mTaskDetailView.showCompletionStatus(task.completed)
    }
}