import "./Task.css"
import {TaskData} from "../models/TaskData";
import {State} from "../models/TaskState";

type TaskProps = {
    data: TaskData
    changeState: (id: string, status: State) => void
    nextState: State;
}

export default function Task(props: TaskProps) {
    const {data} = props;

    const changeState = () => {
        props.changeState(props.data.id, props.nextState);
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