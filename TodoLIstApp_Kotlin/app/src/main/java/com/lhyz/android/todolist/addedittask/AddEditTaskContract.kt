package com.lhyz.android.todolist.addedittask

import com.lhyz.android.todolist.BasePresenter
import com.lhyz.android.todolist.BaseView


/**
 * hello,android
 * @author lhyz on 2017/2/7
 */
interface AddEditTaskContract {
    interface View : BaseView<Presenter> {

        fun showEmptyTaskError()

        fun showTasksList()

        fun setTitle(title: String)

        fun setDescription(description: String)

        fun isActive(): Boolean
    }

    interface Presenter : BasePresenter {

        fun saveTask(title: String, description: String)

        fun populateTask()

        fun isDataMissing(): Boolean
    }
}