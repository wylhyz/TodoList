package com.lhyz.android.todolist.tasks

import com.lhyz.android.todolist.BasePresenter
import com.lhyz.android.todolist.BaseView
import com.lhyz.android.todolist.data.Task


/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
interface TasksContract {
    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showTasks(tasks: List<Task>)

        fun showAddTask()

        fun showTaskDetailsUi(taskId: String)

        fun showTaskMarkedComplete()

        fun showTaskMarkedActive()

        fun showCompletedTasksCleared()

        fun showLoadingTasksError()

        fun showNoTasks()

        fun showActiveFilterLabel()

        fun showCompletedFilterLabel()

        fun showAllFilterLabel()

        fun showNoActiveTasks()

        fun showNoCompletedTasks()

        fun showSuccessfullySavedMessage()

        fun isActive(): Boolean

        fun showFilteringPopUpMenu()
    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int, resultCode: Int)

        fun loadTasks(forceUpdate: Boolean)

        fun addNewTask()

        fun openTaskDetails(requestedTask: Task)

        fun completeTask(completedTask: Task)

        fun activateTask(activeTask: Task)

        fun clearCompletedTasks()

        fun setFiltering(requestType: TasksFilterType)

        fun getFiltering(): TasksFilterType
    }
}