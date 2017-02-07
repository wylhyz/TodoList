package com.lhyz.android.todolist.data.source

import com.lhyz.android.todolist.data.Task
import com.lhyz.android.todolist.data.source.local.TasksLocalDataSource
import com.lhyz.android.todolist.data.source.remote.TasksRemoteDataSource
import java.util.*


/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
class TasksRepository private constructor(tasksRemoteDataSource: TasksRemoteDataSource,
                                          tasksLocalDataSource: TasksLocalDataSource) : TasksDataSource {

    val tasksRemoteDataSource: TasksDataSource
    val tasksLocalDataSource: TasksDataSource

    init {
        this.tasksLocalDataSource = tasksLocalDataSource
        this.tasksRemoteDataSource = tasksRemoteDataSource
    }

    val cachedTasks = LinkedHashMap<String, Task>()

    var cachedIsDirty = false

    companion object {

        fun getInstance(tasksRemoteDataSource: TasksRemoteDataSource,
                        tasksLocalDataSource: TasksLocalDataSource): TasksRepository {
            return TasksRepository(tasksRemoteDataSource, tasksLocalDataSource)
        }
    }

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        if (!cachedIsDirty) {
            callback.onTasksLoaded(ArrayList(cachedTasks.values))
            return
        }

        if (cachedIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getTasksFromRemoteDataSource(callback)
        } else {
            // Query the local storage if available. If not, query the network.
            tasksLocalDataSource.getTasks(object : TasksDataSource.LoadTasksCallback {
                override fun onTasksLoaded(tasks: List<Task>) {
                    refreshCache(tasks)
                    callback.onTasksLoaded(ArrayList(cachedTasks.values))
                }

                override fun onDataNotAvailable() {
                    getTasksFromRemoteDataSource(callback)
                }
            })
        }
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        val cachedTask = getTaskWithId(taskId)

        // Respond immediately with cache if available
        if (cachedTask != null) {
            callback.onTaskLoaded(cachedTask)
            return
        }

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        tasksLocalDataSource.getTask(taskId, object : TasksDataSource.GetTaskCallback {
            override fun onTaskLoaded(task: Task?) {
                cachedTasks.put(task!!.id, task)
                callback.onTaskLoaded(task)
            }

            override fun onDataNotAvailable() {
                tasksRemoteDataSource.getTask(taskId, object : TasksDataSource.GetTaskCallback {
                    override fun onTaskLoaded(task: Task?) {
                        cachedTasks.put(task!!.id, task)
                        callback.onTaskLoaded(task)
                    }

                    override fun onDataNotAvailable() {
                        callback.onDataNotAvailable()
                    }
                })
            }
        })
    }

    override fun saveTask(task: Task) {
        tasksRemoteDataSource.saveTask(task)
        tasksLocalDataSource.saveTask(task)

        cachedTasks.put(task.id, task)
    }

    override fun completeTask(task: Task) {
        tasksRemoteDataSource.completeTask(task)
        tasksLocalDataSource.completeTask(task)

        val completedTask = Task(task.title, task.description, task.id, true)
        cachedTasks.put(task.id, completedTask)
    }

    override fun completeTask(taskId: String) {
        val task = getTaskWithId(taskId)
        if (task != null)
            completeTask(task)
    }

    override fun activateTask(task: Task) {
        tasksRemoteDataSource.activateTask(task)
        tasksLocalDataSource.activateTask(task)

        val activeTask = Task(task.title, task.description, task.id)

        cachedTasks.put(task.id, activeTask)
    }

    override fun activateTask(taskId: String) {
        val task = getTaskWithId(taskId)
        if (task != null)
            activateTask(task)
    }

    override fun clearCompletedTasks() {
        tasksRemoteDataSource.clearCompletedTasks()
        tasksLocalDataSource.clearCompletedTasks()

        val it = cachedTasks.entries.iterator()
        while (it.hasNext()) {
            val entry = it.next()
            if (entry.value.completed) {
                it.remove()
            }
        }
    }

    override fun refreshTasks() {
        cachedIsDirty = true
    }

    override fun deleteAllTasks() {
        tasksRemoteDataSource.deleteAllTasks()
        tasksLocalDataSource.deleteAllTasks()

        cachedTasks.clear()
    }

    override fun deleteTask(taskId: String) {
        tasksRemoteDataSource.deleteTask(checkNotNull(taskId))
        tasksLocalDataSource.deleteTask(checkNotNull(taskId))

        cachedTasks.remove(taskId)
    }

    private fun getTasksFromRemoteDataSource(callback: TasksDataSource.LoadTasksCallback) {
        tasksRemoteDataSource.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                refreshCache(tasks)
                refreshLocalDataSource(tasks)
                callback.onTasksLoaded(ArrayList(cachedTasks.values))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshCache(tasks: List<Task>) {
        cachedTasks.clear()
        for (task in tasks) {
            cachedTasks.put(task.id, task)
        }
        cachedIsDirty = false
    }

    private fun refreshLocalDataSource(tasks: List<Task>) {
        tasksLocalDataSource.deleteAllTasks()
        for (task in tasks) {
            tasksLocalDataSource.saveTask(task)
        }
    }

    private fun getTaskWithId(id: String): Task? {
        if (cachedTasks.isEmpty()) {
            return null
        } else {
            return cachedTasks[id]
        }
    }
}