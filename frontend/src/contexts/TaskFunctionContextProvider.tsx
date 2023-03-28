import {ReactElement, useEffect, useState} from "react";
import {NewTaskData, TaskData} from "../models/TaskData";
import axios from "axios";
import {TaskFunctions, TaskFunctionsContext} from "./TaskFunctionsContext";
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

type Props = {
    children: ReactElement
}

export default function TaskFunctionContextProvider(props: Props) {
    const [allTasks, setAllTasks] = useState<TaskData[]>([]);

    const notifyError = (reason: any) => {
        if (reason.type === undefined) {
            return;
        }

        const msg = `ERROR ${reason.response.status}:   
                ${reason.response.statusText} \n
                ${reason.request.responseURL}`;

        toast.error(msg, {theme: "dark"});
    }

    useEffect(() => getAllTasksFromApi());

    function getTasks(): TaskData[] {
        return allTasks;
    }

    function getAllTasksFromApi(): void {
        axios.get("/api/todo")
            .then(response => setAllTasks(response.data))
            .catch(reason => notifyError(reason));
    }

    function addTaskToApi(newTask: NewTaskData): void {
        axios.post("/api/todo", newTask)
            .then(result => setAllTasks([...allTasks, result.data]))
            .catch(reason => notifyError(reason));
    }

    function updateTaskInApi(task: TaskData) {
        axios.put("/api/todo/" + task.id, task)
            .then(response => updateTasksAfterTaskIsUpdated(response.data))
            .catch(reason => notifyError(reason));
    }

    function updateTasksAfterTaskIsUpdated(updatedTask: TaskData): void {
        const updatedTasks = allTasks.map(task =>
            (task.id === updatedTask.id) ? updatedTask : task);
        setAllTasks(updatedTasks);
    }

    function deleteTaskInApi(id: string) {
        axios.delete(`/api/todo/${id}`)
            .then(response => updateTasksAfterTaskIsDeleted(response.data))
            .catch(reason => notifyError(reason));
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
            <ToastContainer/>
        </TaskFunctionsContext.Provider>
    )
}