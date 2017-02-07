package com.lhyz.android.todolist.tasks

/**
 * hello,android
 * @author lhyz on 2017/2/6
 */
enum class TasksFilterType {
    /**
     * Do not filter tasks.
     */
    ALL_TASKS,

    /**
     * Filters only the active (not completed yet) tasks.
     */
    ACTIVE_TASKS,

    /**
     * Filters only the completed tasks.
     */
    COMPLETED_TASKS
}