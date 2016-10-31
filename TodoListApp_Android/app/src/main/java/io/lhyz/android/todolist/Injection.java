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
package io.lhyz.android.todolist;

import android.content.Context;
import android.support.annotation.NonNull;

import io.lhyz.android.todolist.data.source.TasksRepository;
import io.lhyz.android.todolist.data.source.local.TasksLocalDataSource;
import io.lhyz.android.todolist.data.source.remote.TasksRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * hello,android
 * Created by lhyz on 2016/8/3.
 */
public class Injection {

    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        return TasksRepository.getInstance(TasksLocalDataSource.getInstance(context), TasksRemoteDataSource.getInstance());
    }
}