package com.lhyz.android.todolist

import android.content.Context
import com.lhyz.android.todolist.data.source.TasksRepository
import com.lhyz.android.todolist.data.source.local.TasksLocalDataSource
import com.lhyz.android.todolist.data.source.remote.TasksRemoteDataSource

/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
class Injection {
    companion object {
        fun provideTasksRepository(context: Context): TasksRepository {
            return TasksRepository.getInstance(
                    TasksRemoteDataSource.getInstance(),
                    TasksLocalDataSource.getInstance(context)
            )
        }
    }
}