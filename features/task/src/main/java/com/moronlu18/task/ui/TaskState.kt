package com.moronlu18.task.ui

sealed class TaskState {
    data object TitleIsMandatoryError : TaskState()
    data object CustomerUnspecifiedError: TaskState()
    data object IncorrectDateRangeError : TaskState()
    data object Success : TaskState()
}