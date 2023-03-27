import "./TaskBoard.css"
import TasksColumn from "./TasksColumn";
import {TaskData} from "../models/TaskData";
import {TaskState} from "../models/TaskState";

type TaskBoardProps = {
    allTasks: TaskData[]
    updateTask: (task: TaskData) => void
}

export default function TaskBoard(props: TaskBoardProps) {
    const filterTasksByStatus = (status: TaskState) => {
        return props.allTasks.filter(task => task.status === status);
    }

    return(
        <div className="board">
            <div className="flex-container">
                <TasksColumn headline={"Todo"}
                             tasks={filterTasksByStatus("OPEN")}
                             updateTask={props.updateTask}
                />
                <TasksColumn headline={"Doing"}
                             tasks={filterTasksByStatus("IN_PROGRESS")}
                             updateTask={props.updateTask}
                />
                <TasksColumn headline={"Done"}
                             tasks={filterTasksByStatus("DONE")}
                             updateTask={props.updateTask}
                />
            </div>
        </div>
    )
}