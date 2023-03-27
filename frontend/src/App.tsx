import axios from 'axios';
import React, {useEffect, useState} from 'react';
import './App.css';
import Header from "./components/Header";
import TaskBoard from "./components/TaskBoard";
import {NewTaskData, TaskData} from "./models/TaskData";
import AddTask from "./components/AddTask";
import {TaskFunctionsContext} from "./contexts/TaskFunctionsContext";

function App() {
    const [allTasks, setAllTasks] = useState<TaskData[]>([]);

    useEffect(() => {
        getAllTasksFromApi();
    }, [])

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
            .then(response => updateTasksAfterUpdatingTask(response.data))
            .catch(reason => console.error(reason));
    }

    function deleteTaskInApi(id: string) {
        axios.delete(`/api/todo/${id}`)
            .then(response => updateTasksAfterDeletingTask(response.data))
            .catch(reason => console.error(reason));
    }

    function updateTasksAfterUpdatingTask(updatedTask: TaskData): void {
        const updatedTasks = allTasks.map(task =>
            (task.id === updatedTask.id) ? updatedTask : task);
        setAllTasks(updatedTasks);
    }

    function updateTasksAfterDeletingTask(deletedTask: TaskData): void {
        const updatedTasks = allTasks.filter(task => task.id !== deletedTask.id);
        setAllTasks(updatedTasks);
    }

    return (
        <div className="App">
            <Header/>
            <TaskFunctionsContext.Provider value={{
                deleteTask: deleteTaskInApi,
                updateTask: updateTaskInApi
            }}>
                <TaskBoard allTasks={allTasks}/>
            </TaskFunctionsContext.Provider>

            <AddTask addTask={addTaskToApi}/>
        </div>
    );
}

export default App;
