import "./Task.css"
import {TaskData} from "../models/TaskData";
import {TaskState} from "../models/TaskState";
import {useContext} from "react";
import {TaskFunctionsContext} from "../contexts/TaskFunctionsContext";

type TaskProps = {
    task: TaskData
}

export default function Task(props: TaskProps) {
    const {task} = props;
    const taskFunctionsContext = useContext(TaskFunctionsContext);

    function getNextState() : TaskState {
        return  task.status === "OPEN" ? "IN_PROGRESS" : "DONE";
    }

    const changeState = () => {
        const taskToUpdate: TaskData = { ...task, status: getNextState() };
        taskFunctionsContext.updateTask(taskToUpdate);
    }

    return (
        <div className="task">
            <h3>{task.description}</h3>
            <div>
                <p>{task.status}</p>
                <p>{task.id}</p>
            </div>

            {
                task.status === "DONE"
                    ? <button onClick={() => taskFunctionsContext.deleteTask(task.id)}>Delete</button>
                    : <button onClick={changeState}>Advance</button>
            }
        </div>
    )
}