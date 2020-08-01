import React, {Component} from 'react';
import {connect} from "react-redux";
import {getProjectTask, updateProjectTask} from "../../../actions/backlogActions";
import PropTypes from "prop-types";
import {Link} from "react-router-dom";
import classnames from "classnames";

class UpdateProjectTask extends Component {

    constructor() {
        super();

        this.state = {
            id: "",
            projectSequence: "",
            summary: "",
            acceptanceCriteria: "",
            status: "",
            priority: "",
            dueDate: "",
            projectIdentifier: "",
            createdAt: "",
            errors: {}
        }
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        const {backlog_id, pt_id} = this.props.match.params; //get backlog_id from the Route URL
        this.props.getProjectTask(backlog_id, pt_id, this.props.history);
    }

    componentWillReceiveProps(nextProps) {
        if(nextProps.errors){
            this.setState({errors: nextProps.errors});
        }

        const { id, projectSequence, summary, acceptanceCriteria, status, priority, dueDate, projectIdentifier, createdAt } = nextProps.project_task;
        this.setState({ id, projectSequence, summary, acceptanceCriteria, status, priority, dueDate, projectIdentifier, createdAt });
    }

    onSubmit(e){
        e.preventDefault();//prevent the form from reloading when submitted

        const UpdateProjectTask = {
            id: this.state.id,
            projectSequence: this.state.projectSequence,
            summary: this.state.summary,
            acceptanceCriteria: this.state.acceptanceCriteria,
            status: this.state.status,
            priority: this.state.priority,
            dueDate: this.state.dueDate,
            projectIdentifier: this.state.projectIdentifier,
            createdAt: this.state.createdAt
        }

        this.props.updateProjectTask(this.state.projectIdentifier, this.state.projectSequence, UpdateProjectTask, this.props.history);
        // console.log(UpdateProjectTask);
        //this.props.createProject(updateProject, this.props.history);

    }

    render() {

        const {errors} = this.state;

        return (
            <div className="add-PBI">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <Link to={`/projectBoard/${this.state.projectIdentifier}`} className="btn btn-light">
                                Back to Project Board
                            </Link>
                            <h4 className="display-4 text-center">Update Project Task</h4>
                            <p className="lead text-center">
                                Project Name: {this.state.projectIdentifier} + Project Task ID: {this.state.projectSequence}
                            </p>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input type="text"
                                           className={classnames("form-control form-control-lg", {
                                               "is-invalid": errors.summary
                                           })}
                                           placeholder="Project Task summary"
                                           name="summary"
                                           value={this.state.summary}
                                           onChange={e => this.setState({summary: e.target.value})}
                                    />
                                    {errors.summary && (
                                        <div className="invalid-feedback">{errors.summary}</div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <textarea className="form-control form-control-lg" placeholder="Acceptance Criteria"
                                              name="acceptanceCriteria"
                                              value={this.state.acceptanceCriteria}
                                              onChange={e => this.setState({acceptanceCriteria: e.target.value})}
                                    />
                                </div>
                                <h6>Due Date</h6>
                                <div className="form-group">
                                    <input type="date" className="form-control form-control-lg"
                                           name="dueDate"
                                           value={this.state.dueDate}
                                           onChange={e => this.setState({dueDate: e.target.value})}
                                    />
                                </div>
                                <div className="form-group">
                                    <select className="form-control form-control-lg"
                                            name="priority"
                                            value={this.state.priority}
                                            onChange={e => this.setState({priority: e.target.value})}
                                    >
                                        <option value={0}>Select Priority</option>
                                        <option value={1}>High</option>
                                        <option value={2}>Medium</option>
                                        <option value={3}>Low</option>
                                    </select>
                                </div>

                                <div className="form-group">
                                    <select className="form-control form-control-lg"
                                            name="status"
                                            value={this.state.status}
                                            onChange={e => this.setState({status: e.target.value})}
                                    >
                                        <option value="">Select Status</option>
                                        <option value="TO_DO">TO DO</option>
                                        <option value="IN_PROGRESS">IN PROGRESS</option>
                                        <option value="DONE">DONE</option>
                                    </select>
                                </div>

                                <input type="submit" className="btn btn-primary btn-block mt-4"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

UpdateProjectTask.propTypes = {
    getProjectTask: PropTypes.func.isRequired,
    project_task: PropTypes.object.isRequired,
    updateProjectTask: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    project_task: state.backlog.project_task,
    errors: state.errors //retrieving errors from state
});

export default connect(mapStateToProps, {getProjectTask, updateProjectTask}) (UpdateProjectTask);
