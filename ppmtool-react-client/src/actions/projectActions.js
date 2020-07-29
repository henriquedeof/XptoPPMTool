import Axios from "axios";
import {GET_ERRORS, GET_PROJECT, GET_PROJECTS, DELETE_PROJECT} from "./types";

/*
* The URL and port are located on the package.json file on the proxy attribute. Now, I just need to use the URI and also I have a centralized place for the URL.
* */

export const createProject = (project, history) => async dispatch => {
    try{
        //const res =  await Axios.post("/api/project", project);//If I do not write 'http://' the application does not work.
        await Axios.post("/api/project", project);//If I do not write 'http://' the application does not work.
        history.push("/dashboard")

        // The code below cleans any error that happened in the past, as it was saved on my Redux storage and was not removed.
        // If I do not have this code, when I load the Update Project form, I receive these errors that happened in the past.
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

export const getProjects = () => async dispatch =>{
    const res = await Axios.get("/api/project/all");
    dispatch({
       type: GET_PROJECTS,
       payload: res.data
    });
}

export const getProject = (id, history) => async dispatch =>{
    try{
        const res = await Axios.get(`/api/project/${id}`);
        dispatch({
            type: GET_PROJECT,
            payload: res.data
        });
    }catch (error) {
        history.push("/dashboard");
    }
};

export const deleteProject = id => async dispatch =>{
    if(window.confirm('Are you sure you want to delete this project?')){
        await Axios.delete(`/api/project/${id}`);//using backticks
        dispatch({
            type: DELETE_PROJECT,
            payload: id
        });
    }

};
