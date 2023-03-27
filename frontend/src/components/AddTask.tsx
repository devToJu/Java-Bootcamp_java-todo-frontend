import "./AddTask.css"
import {useContext, useState} from "react";
import {NewTaskData} from "../models/TaskData";
import {TaskFunctionsContext} from "../contexts/TaskFunctionsContext";

export default function AddTask() {
    const [description, setDescription] = useState("");
    const taskFunctionsContext = useContext(TaskFunctionsContext);

    function addTask(): void {
        const newTask: NewTaskData = {description: description, status: "OPEN"}
        taskFunctionsContext.addTask(newTask);
        setDescription("");
    }

    return (
        <div className="addTask">
            <label className="rightMargin">Add new Task:</label>
            <input className="rightMargin"
                   placeholder="description"
                   value={description}
                   onChange={(event) => setDescription(event.target.value)}/>
            <button onClick={addTask}>+</button>
        </div>
    )
}