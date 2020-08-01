import React from 'react';
import Dashboard from "./components/Dashboard";
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import Header from "./components/layout/Header";
import {BrowserRouter as Router, Route} from "react-router-dom";
import AddProject from "./components/project/AddProject";
import {Provider} from "react-redux";
import store from "./store";
import UpdateProject from "./components/project/UpdateProject";
import ProjectBoard from "./components/projectBoard/ProjectBoard"
import AddProjectTask from "./components/projectBoard/ProjectTasks/AddProjectTask";
import UpdateProjectTask from "./components/projectBoard/ProjectTasks/UpdateProjectTask";

function App() {
    return (
        <Provider store={store}>
            <Router>
                <div className="App">
                    <Header />
                    {/*Anywhere in the project if I click on a component that has the /dashboard path value,
                            <Route> will intercept it and redirect me to the Dashboard component */}
                    <Route exact path="/dashboard" component={Dashboard} />
                    <Route exact path="/addProject" component={AddProject} />
                    <Route exact path="/updateProject/:id" component={UpdateProject} />{/*Passing ID as a parameter of this URL*/}
                    <Route exact path="/projectBoard/:id" component={ProjectBoard} />{/*Passing ID as a parameter of this URL*/}
                    <Route exact path="/addProjectTask/:id" component={AddProjectTask} />{/*Passing ID as a parameter of this URL*/}
                    <Route exact path="/updateProjectTask/:backlog_id/:pt_id" component={UpdateProjectTask} />{/*Passing ID as a parameter of this URL*/}
                </div>
            </Router>
        </Provider>
    );
}

export default App;
