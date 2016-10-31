/*
 * Copyright (c) 2016 lhyz Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.lhyz.android.todolist.data.source;

import android.support.annotation.NonNull;

import io.lhyz.android.todolist.data.Task;

/**
 * hello,android
 * Created by lhyz on 2016/8/3.
 */
public class TasksRepository implements TasksDataSource {
    //本地存储
    private TasksDataSource mLocalDataSource;
    //云端存储
    private TasksDataSource mRemoteDataSource;

    private static TasksRepository INSTANCE;

    private TasksRepository(TasksDataSource localDataSource,
                            TasksDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static TasksRepository getInstance(TasksDataSource localDataSource,
                                              TasksDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getAllTasks(@NonNull LoadTasksCallback callback) {
        mLocalDataSource.getAllTasks(callback);
        mRemoteDataSource.getAllTasks(callback);
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {
        mLocalDataSource.getTask(taskId, callback);
        mRemoteDataSource.getTask(taskId, callback);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        mLocalDataSource.saveTask(task);
        mRemoteDataSource.saveTask(task);
    }

    @Override
    public void updateTask(@NonNull Task task) {
        mLocalDataSource.updateTask(task);
        mRemoteDataSource.updateTask(task);
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        mLocalDataSource.completeTask(taskId);
        mRemoteDataSource.completeTask(taskId);
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        mLocalDataSource.activateTask(taskId);
        mRemoteDataSource.activateTask(taskId);
    }

    @Override
    public void clearCompletedTasks() {
        mLocalDataSource.clearCompletedTasks();
        mRemoteDataSource.clearCompletedTasks();
    }

    @Override
    public void refreshTasks() {
        mLocalDataSource.refreshTasks();
        mRemoteDataSource.refreshTasks();
    }

    @Override
    public void deleteAllTasks() {
        mLocalDataSource.deleteAllTasks();
        mRemoteDataSource.deleteAllTasks();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        mLocalDataSource.deleteTask(taskId);
        mRemoteDataSource.deleteTask(taskId);
    }
}
