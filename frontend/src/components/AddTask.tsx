import "./AddTask.css"
import {useState} from "react";

type AddTaskProps = {
    addTask: (value: string) => void
}
export default function AddTask(props: AddTaskProps) {
    const [description, setDescription] = useState("");

    function addTask() : void {
        props.addTask(description);
        setDescription("");
    }

    return(
        <div className="addTask">
            <label className="rightMargin">Add new Task:</label>
            <input className="rightMargin"
                   placeholder="description"
                   value={description}
                   onChange={(event) => setDescription(event.target.value)}/>
            <button onClick={addTask}> + </button>
        </div>
    )
}