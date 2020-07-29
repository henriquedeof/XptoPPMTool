import React, {Component} from 'react';
import {Link} from "react-router-dom";
import Backlog from "./Backlog";
import {connect, Connect} from "react-redux";
import classnames from "classnames";
import PropTypes from "prop-types";
import {getBacklog} from "../../actions/backlogActions";


class ProjectBoard extends Component {

    componentDidMount() {
        const {id} = this.props.match.params;//get ID from the Rout URL
        this.props.getBacklog(id);
    }

    render() {

        const {id} = this.props.match.params;//Gettind ID that comes from the URL set on the App.js.
        const {project_tasks} = this.props.backlog;

        return (
            <div className="container">
                <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
                    <i className="fas fa-plus-circle"> Create Project Task</i>
                </Link>
                <br/>
                <hr/>
                <Backlog project_tasks_prop={project_tasks}/>
            </div>

        );
    }
}

ProjectBoard.propTypes = {
    backlog: PropTypes.object.isRequired,
    getBacklog: PropTypes.func.isRequired
    //errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    backlog: state.backlog
    //errors: state.errors
});

export default connect(mapStateToProps, {getBacklog}) (ProjectBoard);