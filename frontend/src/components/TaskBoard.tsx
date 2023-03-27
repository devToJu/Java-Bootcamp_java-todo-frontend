import "./TaskBoard.css"
import TasksColumn from "./TasksColumn";
import {TaskState} from "../models/TaskState";
import {useContext} from "react";
import {TaskFunctionsContext} from "../contexts/TaskFunctionsContext";

export default function TaskBoard() {
    const {getAllTasks} = useContext(TaskFunctionsContext);

    const filterTasksByStatus = (status: TaskState) => {
        return getAllTasks().filter(task => task.status === status);
    }

    return (
        <div className="board">
            <div className="flex-container">
                <TasksColumn headline={"Todo"}
                             tasks={filterTasksByStatus("OPEN")}/>
                <TasksColumn headline={"Doing"}
                             tasks={filterTasksByStatus("IN_PROGRESS")}/>
                <TasksColumn headline={"Done"}
                             tasks={filterTasksByStatus("DONE")}/>
            </div>
        </div>
    )
}