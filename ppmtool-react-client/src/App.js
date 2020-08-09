import React from 'react';
import Dashboard from "./components/Dashboard";
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import Header from "./components/layout/Header";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import AddProject from "./components/project/AddProject";
import {Provider} from "react-redux";
import store from "./store";
import UpdateProject from "./components/project/UpdateProject";
import ProjectBoard from "./components/projectBoard/ProjectBoard"
import AddProjectTask from "./components/projectBoard/ProjectTasks/AddProjectTask";
import UpdateProjectTask from "./components/projectBoard/ProjectTasks/UpdateProjectTask";
import Landing from "./components/layout/Landing";
import Register from "./components/userManagement/Register";
import Login from "./components/userManagement/Login";
import jwt_decode from "jwt-decode";
import setJWTToken from "./securityUtils/setJWTToken";
import {SET_CURRENT_USER} from "./actions/types";
import {logout} from "./actions/securityActions";
import SecuredRoute from "./securityUtils/SecureRoute";

const jtwToken = localStorage.jwtToken;

if(jtwToken){
    setJWTToken(jtwToken);
    const decodedJwtToken = jwt_decode(jtwToken);
    store.dispatch({
       type: SET_CURRENT_USER,
       payload: decodedJwtToken
    });

    const currentTime = Date.now()/1000;
    if(decodedJwtToken.exp < currentTime){
        store.dispatch(logout());//redux store
        window.location.href = "/";
    }

}


function App() {
    return (
        <Provider store={store}>
            <Router>
                <div className="App">

                    <Header />

                    {/*Public routes below*/}
                    <Route exact path="/" component={Landing} />
                    <Route exact path="/register" component={Register} />
                    <Route exact path="/login" component={Login} />


                    {/*Private routes below*/}
                    {/*Anywhere in the project if I click on a component that has the /dashboard path value,
                            <Route> will intercept it and redirect me to the Dashboard component */}
                    <Switch>
                        <SecuredRoute exact path="/dashboard" component={Dashboard} />
                        <SecuredRoute exact path="/addProject" component={AddProject} />
                        <SecuredRoute exact path="/updateProject/:id" component={UpdateProject} />{/*Passing ID as a parameter of this URL*/}
                        <SecuredRoute exact path="/projectBoard/:id" component={ProjectBoard} />{/*Passing ID as a parameter of this URL*/}
                        <SecuredRoute exact path="/addProjectTask/:id" component={AddProjectTask} />{/*Passing ID as a parameter of this URL*/}
                        <SecuredRoute exact path="/updateProjectTask/:backlog_id/:pt_id" component={UpdateProjectTask} />{/*Passing ID as a parameter of this URL*/}
                    </Switch>

                </div>
            </Router>
        </Provider>
    );
}

export default App;
