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
package io.lhyz.android.todolist.data.source.remote;

import android.support.annotation.NonNull;

import io.lhyz.android.todolist.data.Task;
import io.lhyz.android.todolist.data.source.TasksDataSource;

/**
 * hello,android
 * Created by lhyz on 2016/8/3.
 */
public class TasksRemoteDataSource implements TasksDataSource {

    private static TasksRemoteDataSource INSTANCE;
    private static APIManager sAPIManager;

    private TasksRemoteDataSource() {
    }

    public static TasksRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TasksRemoteDataSource();
            sAPIManager = APIManager.getInstance();
        }
        return INSTANCE;
    }

    @Override
    public void getAllTasks(@NonNull final LoadTasksCallback callback) {
        sAPIManager.getAllTasks(new DefaultSubscriber<APIManager.Result>() {
            @Override
            public void onSuccess(APIManager.Result result) {
                callback.onTasksLoaded(result.result);
            }

            @Override
            public void onError(Throwable e) {
                callback.onDataNotAvailable();
                e.printStackTrace();
            }
        });
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull final GetTaskCallback callback) {
        sAPIManager.getOneTask(taskId, new DefaultSubscriber<APIManager.Result>() {
            @Override
            public void onSuccess(APIManager.Result result) {
                callback.onTaskLoaded(result.result.get(0));
            }

            @Override
            public void onError(Throwable e) {
                callback.onDataNotAvailable();
                e.printStackTrace();
            }
        });
    }

    @Override
    public void saveTask(@NonNull Task task) {
        sAPIManager.addOneTask(task, new DefaultSubscriber<APIManager.Result>() {
            @Override
            public void onSuccess(APIManager.Result result) {
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }

    @Override
    public void updateTask(@NonNull Task task) {
        sAPIManager.updateOneTask(task, new DefaultSubscriber<APIManager.Result>() {
            @Override
            public void onSuccess(APIManager.Result result) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        sAPIManager.completeTask(taskId, new DefaultSubscriber<APIManager.Result>() {
            @Override
            public void onSuccess(APIManager.Result result) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        sAPIManager.activeTask(taskId, new DefaultSubscriber<APIManager.Result>() {
            @Override
            public void onSuccess(APIManager.Result result) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void clearCompletedTasks() {
        sAPIManager.deleteAllCompleted(new DefaultSubscriber<APIManager.Result>() {
            @Override
            public void onSuccess(APIManager.Result result) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void refreshTasks() {
        //No Need For Remote Service
    }

    @Override
    public void deleteAllTasks() {
        //Not Recommended ! ! !
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        sAPIManager.deleteOneTask(taskId, new DefaultSubscriber<APIManager.Result>() {
            @Override
            public void onSuccess(APIManager.Result result) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
