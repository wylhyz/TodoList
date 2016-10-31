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

import java.util.List;

import io.lhyz.android.todolist.data.Task;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * hello,android
 * Created by lhyz on 2016/8/3.
 */
public class APIManager {

    private static final String API_URL = "http://192.168.0.102:3000/api/v1/";

    /**
     * 结果
     */
    public static class Result {
        boolean error;
        String errMsg;
        List<Task> result;
    }

    /**
     * API
     */
    public interface TodoListService {
        @GET("task")
        Observable<Result> getAll();

        @GET("task/{id}")
        Observable<Result> getOne(@Path("id") String id);

        @POST("task")
        Observable<Result> addOne(@Body Task task);

        @DELETE("task/{id}")
        Observable<Result> deleteOne(@Path("id") String id);

        @DELETE("task/completed")
        Observable<Result> deleteCompleted();

        @PUT("task/{id}")
        Observable<Result> updateOne(@Path("id") String id, @Body Task task);

        @PUT("task/complete/{id}")
        Observable<Result> completeOne(@Path("id") String id, @Body Task task);

    }

    private TodoListService mService;

    public static final class HOLDER {
        private static final APIManager INSTANCE = new APIManager();
    }

    private APIManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mService = retrofit.create(TodoListService.class);
    }

    public static APIManager getInstance() {
        return HOLDER.INSTANCE;
    }

    public void getAllTasks(DefaultSubscriber<Result> result) {
        mService.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result);
    }

    public void getOneTask(String taskId, DefaultSubscriber<Result> result) {
        mService.getOne(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result);
    }

    public void addOneTask(Task newTask, DefaultSubscriber<Result> result) {
        mService.addOne(newTask)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result);
    }

    public void deleteOneTask(String taskId, DefaultSubscriber<Result> result) {
        mService.deleteOne(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result);
    }

    public void deleteAllCompleted(DefaultSubscriber<Result> result) {
        mService.deleteCompleted()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result);
    }

    public void updateOneTask(Task modifiedTask, DefaultSubscriber<Result> result) {
        mService.updateOne(modifiedTask.getId(), modifiedTask)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result);
    }

    public void completeTask(String taskId, DefaultSubscriber<Result> result) {
        mService.completeOne(taskId, new Task("", "", taskId, true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result);
    }

    public void activeTask(String taskId, DefaultSubscriber<Result> result) {
        mService.completeOne(taskId, new Task("", "", taskId, false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result);
    }

}
