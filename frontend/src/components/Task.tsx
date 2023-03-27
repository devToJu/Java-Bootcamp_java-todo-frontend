import "./Task.css"
import {TaskData} from "../models/TaskData";
import {TaskState} from "../models/TaskState";
import {useContext} from "react";
import {TaskFunctionsContext} from "../contexts/TaskFunctionsContext";

type TaskProps = {
    data: TaskData
}

export default function Task(props: TaskProps) {
    const {data} = props;

    const taskFunctionsContext = useContext(TaskFunctionsContext);

    function getNextState() : TaskState {
        return  data.status === "OPEN" ? "IN_PROGRESS" : "DONE";
    }

    const changeState = () => {
        const taskToUpdate: TaskData = { ...data, status: getNextState() };
        taskFunctionsContext.updateTask(taskToUpdate);
    }

    return (
        <div className="task">
            <h3>{data.description}</h3>
            <div>
                <p>{data.status}</p>
                <p>{data.id}</p>
            </div>

            {
                data.status === "DONE"
                    ? <button onClick={() => taskFunctionsContext.deleteTask(data.id)}>Delete</button>
                    : <button onClick={changeState}>Advance</button>
            }
        </div>
    )
}