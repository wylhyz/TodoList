package com.lhyz.android.todolist.data

import java.util.*

/**
 * hello,android
 * @author lhyz on 2017/2/5
 *
 * 声明一个数据类
 */

data class Task(val title: String?,
                val description: String?,
                val id: String,
                val completed: Boolean) {

    constructor(title: String?, description: String?) : this(title, description, UUID.randomUUID().toString(), false)

    constructor(title: String?, description: String?, id: String) : this(title, description, id, false)

    constructor(title: String?, description: String?, completed: Boolean) : this(title, description, UUID.randomUUID().toString(), completed)

    fun getTitleForList(): String? {
        if (title != null && !title.isEmpty()) {
            return title
        }
        return description
    }

    fun isActive(): Boolean {
        return !completed
    }

    fun isEmpty(): Boolean {
        return (title == null || title.isEmpty())
                && (description == null || description.isEmpty())
    }
}