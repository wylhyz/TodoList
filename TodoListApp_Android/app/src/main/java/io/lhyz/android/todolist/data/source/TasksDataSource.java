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

import java.util.List;

import io.lhyz.android.todolist.data.Task;

/**
 * hello,android
 * Created by lhyz on 2016/8/3.
 */
public interface TasksDataSource {

    interface LoadTasksCallback {

        void onTasksLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

    interface GetTaskCallback {

        void onTaskLoaded(Task task);

        void onDataNotAvailable();
    }

    //获取所有tasks
    void getAllTasks(@NonNull LoadTasksCallback callback);

    //根据ID获取一个task
    void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback);

    //保存一个task
    void saveTask(@NonNull Task task);

    //更新一个Task
    void updateTask(@NonNull Task task);

    //active和complete不涉及对task的内容操作，所以只需要一个ID即可操作
    void completeTask(@NonNull String taskId);

    void activateTask(@NonNull String taskId);

    //清楚所有被标记为completed的task
    void clearCompletedTasks();

    //刷新task列表
    void refreshTasks();

    //删除所有task
    void deleteAllTasks();

    //删除一个task
    void deleteTask(@NonNull String taskId);
}
