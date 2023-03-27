import axios from 'axios';
import React, {useEffect, useState} from 'react';
import './App.css';
import Header from "./components/Header";
import TaskBoard from "./components/TaskBoard";
import {NewTaskData, TaskData} from "./models/TaskData";
import AddTask from "./components/AddTask";

function App() {
    const [allTasks, setAllTasks] = useState<TaskData[]>([]);

    useEffect(() => {
        getAllTasksFromApi();
    }, [])

    function getAllTasksFromApi(): void{
        axios.get("/api/todo")
            .then(response => {
                setAllTasks(response.data);
            })
            .catch(reason => console.error(reason));
    }

    function addTaskToApi(newTask: NewTaskData) : void {
        axios.post("/api/todo", newTask)
            .then(result => setAllTasks([...allTasks, result.data]))
            .catch(reason => console.error(reason));
    }

    function updateTaskInApi(task: TaskData) {
        axios.put("/api/todo/"+task.id, task)
            .then(() => getAllTasksFromApi())
            .catch(reason => console.error(reason));
    }

    return (
        <div className="App">
            <Header/>
            <TaskBoard allTasks={allTasks} updateTask={updateTaskInApi}/>
            <AddTask addTask={addTaskToApi}/>
        </div>
    );
}

export default App;
