import {combineReducers} from "redux";
import errorReducer from "./errorReducer";
import projectReducer from "./projectReducer";
import backlogReducer from "./backlogReducer";
import securityReducer from "./securityReducer";

//This is the REDUCER and below are its specifications
export default combineReducers({
    errors:errorReducer,
    project: projectReducer,
    backlog: backlogReducer,
    security: securityReducer
});