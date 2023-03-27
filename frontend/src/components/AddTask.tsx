import "./AddTask.css"
import {useState} from "react";
import {NewTaskData} from "../models/TaskData";

type AddTaskProps = {
    addTask: (task: NewTaskData) => void
}
export default function AddTask(props: AddTaskProps) {
    const [description, setDescription] = useState("");

    function addTask(): void {
        const newTask: NewTaskData = {description: description, status: "OPEN"}
        props.addTask(newTask);
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