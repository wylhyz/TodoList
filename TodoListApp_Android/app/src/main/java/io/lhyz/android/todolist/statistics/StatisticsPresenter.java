/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.lhyz.android.todolist.statistics;

import android.support.annotation.NonNull;

import java.util.List;

import io.lhyz.android.todolist.data.Task;
import io.lhyz.android.todolist.data.source.TasksDataSource;
import io.lhyz.android.todolist.data.source.TasksRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link StatisticsFragment}), retrieves the data and updates
 * the UI as required.
 */
public class StatisticsPresenter implements StatisticsContract.Presenter {

    private final TasksRepository mTasksRepository;

    private final StatisticsContract.View mStatisticsView;

    public StatisticsPresenter(@NonNull TasksRepository tasksRepository,
                               @NonNull StatisticsContract.View statisticsView) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mStatisticsView = checkNotNull(statisticsView, "StatisticsView cannot be null!");
    }

    @Override
    public void start() {
        loadStatistics();
    }

    @Override
    public void loadStatistics() {
        mStatisticsView.setProgressIndicator(true);

        mTasksRepository.getAllTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                mStatisticsView.setProgressIndicator(false);

                mStatisticsView.displayStatistics(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                mStatisticsView.showLoadingStatisticsError();
            }
        });
    }
}
