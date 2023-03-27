import './App.css';
import Header from "./components/Header";
import TaskBoard from "./components/TaskBoard";
import AddTask from "./components/AddTask";

function App() {
    return (
            <div className="App">
                <Header/>
                <TaskBoard/>
                <AddTask/>
            </div>
    );
}

export default App;
