import "./Task.css"
import {TaskData} from "../models/TaskData";
import {TaskState} from "../models/TaskState";

type TaskProps = {
    data: TaskData
    changeState: (task: TaskData) => void
}

export default function Task(props: TaskProps) {
    const {data} = props;

    function getNextState() : TaskState {
        return  data.status === "OPEN" ? "IN_PROGRESS" : "DONE";
    }

    const changeState = () => {
        const taskToUpdate: TaskData = { ...data, status: getNextState() };
        props.changeState(taskToUpdate);
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
                    ? <button>Delete</button>
                    : <button onClick={changeState}>Advance</button>
            }
        </div>
    )
}