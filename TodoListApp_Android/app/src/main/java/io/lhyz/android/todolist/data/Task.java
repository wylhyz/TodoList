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
package io.lhyz.android.todolist.data;

import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.UUID;

/**
 * hello,android
 * Created by lhyz on 2016/8/3.
 */
@DatabaseTable(tableName = "tasks")
public final class Task implements Serializable {

    private static final long serialVersionUID = -7050492019251241384L;

    //注解id=true声明为此id是每行的唯一标识 identity（主键）
    @DatabaseField(id = true)
    private String id;

    @Nullable
    @DatabaseField(canBeNull = false)
    private String title;

    @Nullable
    @DatabaseField(canBeNull = false)
    private String description;

    @DatabaseField
    private boolean completed;

    public Task() {
        //For OrmLite
    }

    /**
     * Use this constructor to create a new active Task.
     */
    public Task(@Nullable String title, @Nullable String description) {
        id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        completed = false;
    }

    /**
     * Use this constructor to create an active Task if the Task already has an id (copy of another
     * Task).
     */
    public Task(@Nullable String title, @Nullable String description, String id) {
        this.id = id;
        this.title = title;
        this.description = description;
        completed = false;
    }

    /**
     * Use this constructor to create a new completed Task.
     */
    public Task(@Nullable String title, @Nullable String description, boolean completed) {
        id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    /**
     * Use this constructor to specify a completed Task if the Task already has an id (copy of
     * another Task).
     */
    public Task(@Nullable String title, @Nullable String description, String id, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getTitleForList() {
        if (title != null && !title.equals("")) {
            return title;
        } else {
            return description;
        }
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isActive() {
        return !completed;
    }

    public boolean isEmpty() {
        return (title == null || "".equals(title)) &&
                (description == null || "".equals(description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Task task = (Task) o;
        return Objects.equal(id, task.id) &&
                Objects.equal(title, task.title) &&
                Objects.equal(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, description);
    }

    @Override
    public String toString() {
        return "Task with title " + title;
    }

}
