import {ReactElement, useEffect, useState} from "react";
import {NewTaskData, TaskData} from "../models/TaskData";
import axios from "axios";
import {TaskFunctions, TaskFunctionsContext} from "./TaskFunctionsContext";

type Props = {
    children: ReactElement
}

export default function TaskFunctionContextProvider(props: Props) {
    const [allTasks, setAllTasks] = useState<TaskData[]>([]);

    useEffect(() => {
        getAllTasksFromApi();
    }, [])

    function getTasks(): TaskData[] {
        return allTasks;
    }

    function getAllTasksFromApi(): void {
        axios.get("/api/todo")
            .then(response => {
                setAllTasks(response.data);
            })
            .catch(reason => console.error(reason));
    }

    function addTaskToApi(newTask: NewTaskData): void {
        axios.post("/api/todo", newTask)
            .then(result => setAllTasks([...allTasks, result.data]))
            .catch(reason => console.error(reason));
    }

    function updateTaskInApi(task: TaskData) {
        axios.put("/api/todo/" + task.id, task)
            .then(response => updateTasksAfterTaskIsUpdated(response.data))
            .catch(reason => console.error(reason));
    }

    function updateTasksAfterTaskIsUpdated(updatedTask: TaskData): void {
        const updatedTasks = allTasks.map(task =>
            (task.id === updatedTask.id) ? updatedTask : task);
        setAllTasks(updatedTasks);
    }

    function deleteTaskInApi(id: string) {
        axios.delete(`/api/todo/${id}`)
            .then(response => updateTasksAfterTaskIsDeleted(response.data))
            .catch(reason => console.error(reason));
    }

    function updateTasksAfterTaskIsDeleted(deletedTask: TaskData): void {
        const updatedTasks = allTasks.filter(task => task.id !== deletedTask.id);
        setAllTasks(updatedTasks);
    }

    const taskFunctions: TaskFunctions = {
        deleteTask: deleteTaskInApi,
        updateTask: updateTaskInApi,
        getAllTasks: getTasks,
        addTask: addTaskToApi
    }

    return (
        <TaskFunctionsContext.Provider value={taskFunctions}>
            {props.children}
        </TaskFunctionsContext.Provider>
    )
}