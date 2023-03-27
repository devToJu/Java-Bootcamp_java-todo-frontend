import {createContext} from "react";
import {TaskData} from "../models/TaskData";

export type TaskFunctions = {
    deleteTask: (id: string) => void;
    updateTask: (task: TaskData) => void;
}

export const TaskFunctionsContext = createContext<TaskFunctions>(
    {
        deleteTask: () => {},
        updateTask: () => {}
    });