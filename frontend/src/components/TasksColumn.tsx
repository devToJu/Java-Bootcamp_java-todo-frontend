import "./TasksColumn.css"
import {TaskData} from "../models/TaskData";
import Task from "./Task";

type TaskColumnProps = {
    headline: string;
    tasks: TaskData[];
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
                    <Task key={task.id} task={task}/>)
            }
        </div>
    )
}