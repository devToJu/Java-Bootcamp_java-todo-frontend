import {State} from "./TaskState";

export type TaskData = {
    id: string
    description: string
    status: State
}

export type NewTaskData = {
    description: string
    status: State
}