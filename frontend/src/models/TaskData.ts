import {State} from "./TaskState";

export type TaskData = {
    id: string
    description: string
    status: State
}