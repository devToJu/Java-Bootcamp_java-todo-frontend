import "./TasksColumn.css"
import {TaskData} from "../models/TaskData";
import Task from "./Task";
import {State} from "../models/TaskState";

type TaskColumnProps = {
    headline: string;
    tasks: TaskData[];
    changeTaskState: (id: string, status: State) => void;
    nextState: State;
}

export default function TasksColumn(props: TaskColumnProps) {
    return (
        <div className="column">
            <div className="headline">
                <h2>
                    {props.headline}
                </h2>
            </div>
            {
                props.tasks.map(task =>
                    <Task key={task.id}
                          data={task}
                          changeState={props.changeTaskState}
                          nextState={props.nextState}
                    />)
            }
        </div>
    )
}