import React, {Component} from 'react';
import ProjectItem from "./project/ProjectItem";
import CreateProjectButton from "./project/CreateProjectButton";
import {connect} from "react-redux"; //connecting react to redux store
import {getProjects} from "../actions/projectActions";
import PropTypes from "prop-types";

class Dashboard extends Component {

    //lifecycle hook
    componentDidMount() {
        this.props.getProjects();//Invoking the getProjects() action on projectAction.js
    }

    render() {

        const {projects} = this.props.project;

        return (
            <div className="projects">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12">
                            <h1 className="display-4 text-center">Projects</h1>
                            <br/>
                            <CreateProjectButton />
                            <br/>
                            <hr/>

                            {projects.map(project => (
                                <ProjectItem key={project.id} project={project} />
                            ))
                            }

                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

Dashboard.propTypes = {
    project: PropTypes.object.isRequired,
    getProjects: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
    project: state.project //The 'project:' attribute is linking with 'project' on the index.js
});

export default connect(mapStateToProps, {getProjects}) (Dashboard);