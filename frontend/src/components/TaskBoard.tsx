import "./TaskBoard.css"
import TasksColumn from "./TasksColumn";
import {TaskData} from "../models/TaskData";
import {State} from "../models/TaskState";

type TaskBoardProps = {
    allTasks: TaskData[]
    changeTaskState: (id: string, status: State) => void
}

export default function TaskBoard(props: TaskBoardProps) {
    const filterTasksByStatus = (status: State) => {
        return props.allTasks.filter(task => task.status === status);
    }

    return(
        <div className="board">
            <div className="flex-container">
                <TasksColumn headline={"Todo"}
                             tasks={filterTasksByStatus("OPEN")}
                             changeTaskState={props.changeTaskState}
                             nextState={"IN_PROGRESS"}
                />
                <TasksColumn headline={"Doing"}
                             tasks={filterTasksByStatus("IN_PROGRESS")}
                             changeTaskState={props.changeTaskState}
                             nextState={"DONE"}
                />
                <TasksColumn headline={"Done"}
                             tasks={filterTasksByStatus("DONE")}
                             changeTaskState={props.changeTaskState}
                             nextState={"DONE"}
                />
            </div>
        </div>
    )
}