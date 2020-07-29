import React from "react";
import {Link} from "react-router-dom";

const CreateProjectButton = () => {
    return (
        //I believe I do not need <React.Fragment>. It was used for an older version of react that did not recognize <Link> as just one tag.
        //<React.Fragment> was used to substitute the <div> that was responsible for wrapping this component and I do not have an extra tag <div> using <React.Fragment>.
        <React.Fragment>
            <Link to="/addProject" className="btn btn-lg btn-info">
                Create a Project
            </Link>
        </React.Fragment>
    );
}

export default CreateProjectButton;