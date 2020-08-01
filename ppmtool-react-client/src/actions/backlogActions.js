import Axios from "axios";
import {DELETE_PROJECT_TASK, GET_BACKLOG, GET_ERRORS, GET_PROJECT_TASK} from "./types";

export const addProjectTask = (backlog_id, project_task, history) => async dispatch => {

    try {
        await Axios.post(`/api/backlog/${backlog_id}`, project_task);
        history.push(`/projectBoard/${backlog_id}`);

        // The code below cleans any error that happened in the past, as it was saved on my Redux storage and was not removed.
        // If I do not have this code, when I load the Update Project Task form, I receive these errors that happened in the past.
        dispatch({
            type:GET_ERRORS,
            payload: {}
        });
    }catch (err) {
        dispatch({
            type:GET_ERRORS,
            payload:err.response.data
        });
    }

}

export const getBacklog = backlog_id => async dispatch => {
    try{
        const res = await Axios.get(`/api/backlog/${backlog_id}`);
        dispatch({
            type: GET_BACKLOG,
            payload: res.data
        });
    }catch (err) {
        dispatch({
            type:GET_ERRORS,
            payload:err.response.data
        });
    }
}

export const getProjectTask = (backlog_id, pt_id, history) => async dispatch => {
    try{
        const res = await Axios.get(`/api/backlog/${backlog_id}/${pt_id}`);
        dispatch({
            type: GET_PROJECT_TASK,
            payload: res.data
        });
    }catch (err) {
        history.push("/dashboard");

        // dispatch({
        //     type:GET_ERRORS,
        //     payload:err.response.data
        // });
    }
}

export const updateProjectTask = (backlog_id, pt_id, project_task, history) => async dispatch => {
    try{
        await Axios.patch(`/api/backlog/${backlog_id}/${pt_id}`, project_task);
        history.push(`/projectBoard/${backlog_id}`);

        dispatch({
                type:GET_ERRORS,
                payload: {}
            });

    }catch (err) {
        dispatch({
            type:GET_ERRORS,
            payload:err.response.data
        });
    }
}

export const deleteProjectTask = (backlog_id, pt_id) => async dispatch => {
    if(window.confirm(`Do you want to delete the task ${pt_id}?`)){
        await Axios.delete(`/api/backlog/${backlog_id}/${pt_id}`);
        dispatch({
            type:DELETE_PROJECT_TASK,
            payload: pt_id
        });
    }
}


