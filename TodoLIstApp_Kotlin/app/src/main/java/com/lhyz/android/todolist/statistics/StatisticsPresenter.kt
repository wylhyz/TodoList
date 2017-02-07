package com.lhyz.android.todolist.statistics

import com.lhyz.android.todolist.data.Task
import com.lhyz.android.todolist.data.source.TasksDataSource
import com.lhyz.android.todolist.data.source.TasksRepository

/**
 * hello,android
 * @author lhyz on 2017/2/7
 */
class StatisticsPresenter(tasksRepository: TasksRepository,
                          statisticsView: StatisticsContract.View)
    : StatisticsContract.Presenter {

    val mTasksRepository = tasksRepository
    val mStatisticsView = statisticsView

    init {
        mStatisticsView.setPresenter(this)
    }

    override fun start() {
        loadStatistics()
    }

    fun loadStatistics() {
        mStatisticsView.setProgressIndicator(true)

        mTasksRepository.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                var activeTasks = 0
                var completedTasks = 0

                for ((title, description, id, completed) in tasks) {
                    if (completed) {
                        completedTasks += 1
                    } else {
                        activeTasks += 1
                    }
                }

                if (!mStatisticsView.isActive()) {
                    return
                }

                mStatisticsView.setProgressIndicator(false)
                mStatisticsView.showStatistics(activeTasks, completedTasks)
            }

            override fun onDataNotAvailable() {
                if (!mStatisticsView.isActive()) {
                    return
                }
                mStatisticsView.showLoadingStatisticsError()
            }
        })
    }
}