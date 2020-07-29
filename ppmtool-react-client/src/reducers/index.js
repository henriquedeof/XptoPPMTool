import {combineReducers} from "redux";
import errorReducer from "./errorReducer";
import projectReducer from "./projectReducer";
import backlogReducer from "./backlogReducer";

//This is the REDUCER and below are its specifications
export default combineReducers({
    errors:errorReducer,
    project: projectReducer,
    backlog: backlogReducer
});