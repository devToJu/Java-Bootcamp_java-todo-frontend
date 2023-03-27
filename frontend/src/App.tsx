import axios from 'axios';
import React, {useEffect, useState} from 'react';
import './App.css';
import Header from "./components/Header";
import TaskBoard from "./components/TaskBoard";
import {NewTaskData, TaskData} from "./models/TaskData";
import AddTask from "./components/AddTask";
import {State} from "./models/TaskState";

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
            .then(() => getAllTasksFromApi())
            .catch(reason => console.error(reason));
    }

    function changeTaskState(id: string, status: State) {
        const oldTask = allTasks.find(task => task.id === id)
        if (oldTask === undefined) {
            return;
        }

        const updateTask: TaskData = {
            id: id,
            description: oldTask.description,
            status: status
        }

        axios.put("/api/todo/"+id, updateTask)
            .then(() => getAllTasksFromApi())
            .catch(reason => console.error(reason));
    }

    return (
        <div className="App">
            <Header/>
            <TaskBoard allTasks={allTasks} changeTaskState={changeTaskState}/>
            <AddTask addTask={addTaskToApi}/>
        </div>
    );
}

export default App;
