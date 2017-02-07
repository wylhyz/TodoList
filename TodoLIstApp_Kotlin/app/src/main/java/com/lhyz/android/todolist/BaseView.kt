package com.lhyz.android.todolist

/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
interface BaseView<T> {
    fun setPresenter(presenter: T)
}