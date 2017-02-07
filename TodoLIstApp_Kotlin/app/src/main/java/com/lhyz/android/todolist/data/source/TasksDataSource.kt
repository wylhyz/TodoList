package com.lhyz.android.todolist.data.source

import com.lhyz.android.todolist.data.Task

/**
 * hello,android
 * @author lhyz on 2017/2/5
 *
 * 数据源接口
 */
interface TasksDataSource {

    interface LoadTasksCallback {
        fun onTasksLoaded(tasks: List<Task>)
        fun onDataNotAvailable()
    }

    interface GetTaskCallback {
        fun onTaskLoaded(task: Task?)
        fun onDataNotAvailable()
    }

    fun getTasks(callback: LoadTasksCallback)

    fun getTask(taskId: String, callback: GetTaskCallback)

    fun saveTask(task: Task)

    fun completeTask(task: Task)

    fun completeTask(taskId: String)

    fun activateTask(task: Task)

    fun activateTask(taskId: String)

    fun clearCompletedTasks()

    fun refreshTasks()

    fun deleteAllTasks()

    fun deleteTask(taskId: String)
}