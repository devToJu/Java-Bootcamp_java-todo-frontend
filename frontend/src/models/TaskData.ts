import {TaskState} from "./TaskState";

export type TaskData = {
    id: string
    description: string
    status: TaskState
}

export type NewTaskData = {
    description: string
    status: TaskState
}