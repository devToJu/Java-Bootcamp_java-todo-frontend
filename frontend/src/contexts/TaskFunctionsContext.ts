import {createContext} from "react";
import {NewTaskData, TaskData} from "../models/TaskData";

export type TaskFunctions = {
    deleteTask: (id: string) => void;
    updateTask: (task: TaskData) => void;
    getAllTasks: () => TaskData[];
    addTask: (newTask: NewTaskData) => void;
}

export const TaskFunctionsContext = createContext<TaskFunctions>(
    {
        deleteTask: () => {},
        updateTask: () => {},
        getAllTasks: () => [],
        addTask: () => {}
    });