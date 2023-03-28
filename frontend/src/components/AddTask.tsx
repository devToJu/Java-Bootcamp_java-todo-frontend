import "./AddTask.css"
import {FormEvent, useContext, useState} from "react";
import {NewTaskData} from "../models/TaskData";
import {TaskFunctionsContext} from "../contexts/TaskFunctionsContext";

export default function AddTask() {
    const [description, setDescription] = useState("");
    const taskFunctionsContext = useContext(TaskFunctionsContext);

    function addTask(event: FormEvent<HTMLFormElement>): void {
        event.preventDefault();
        const newTask: NewTaskData = {description: description, status: "OPEN"}
        taskFunctionsContext.addTask(newTask);
        setDescription("");
    }

    return (
        <form className="addTask"
              onSubmit={addTask}
              onReset={() => setDescription("")}
        >
            <label className="rightMargin">Add new Task:</label>

            <input className="rightMargin"
                   placeholder="description"
                   value={description}
                   onChange={(event) => setDescription(event.target.value)}/>

            <button className="rightMargin" type="submit">Add</button>
            <button type="reset">Clear</button>
        </form>
    )
}