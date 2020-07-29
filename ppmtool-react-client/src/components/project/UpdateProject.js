import React, {Component} from 'react';
import {createProject, getProject} from "../../actions/projectActions";
import PropTypes from "prop-types";
import {connect} from "react-redux"; //connecting react to redux store
import classnames from "classnames";

class UpdateProject extends Component {

    constructor() {
        super();

        this.state = {
            id: "",
            projectName: "",
            projectIdentifier: "",
            description: "",
            startDate: "",
            endDate: "",
            errors: {}
        };

        this.onSubmit = this.onSubmit.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        if(nextProps.errors){
            this.setState({errors: nextProps.errors});
        }

        const {id, projectName, projectIdentifier, description, startDate, endDate} = nextProps.project;
        this.setState({ id, projectName, projectIdentifier, description, startDate, endDate  });

    }

    //Function is executed when the component is loaded. It is part of the React component life cycle
    componentDidMount() {
        const {id} = this.props.match.params;

        //calling the action on projectActions.js
        this.props.getProject(id, this.props.history);
    }

    onSubmit(e){
        e.preventDefault();//prevent the form from reloading when submitted

        const updateProject = {
            id: this.state.id,
            projectName: this.state.projectName,
            projectIdentifier: this.state.projectIdentifier,
            description: this.state.description,
            startDate: this.state.startDate,
            endDate: this.state.endDate
        }

        this.props.createProject(updateProject, this.props.history);

    }

    render() {

        const {errors} = this.state;

        return (
            <div className="project">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h5 className="display-4 text-center">Update Project form</h5>
                            <hr/>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input type="text"
                                           className={classnames("form-control form-control-lg", {
                                                "is-invalid": errors.projectName
                                           })}
                                           placeholder="Project Name" name="projectName"
                                           value={this.state.projectName}
                                           onChange={e => this.setState({projectName: e.target.value})}
                                    />
                                    {errors.projectName && (
                                        <div className="invalid-feedback">{errors.projectName}</div>
                                    )}
                                </div>

                                <div className="form-group">
                                    <input type="text" className={classnames("form-control form-control-lg", {
                                                "is-invalid": errors.projectIdentifier
                                            })}
                                           placeholder="Unique Project ID" name="projectIdentifier"
                                           value={this.state.projectIdentifier}
                                           onChange={e => this.setState({projectIdentifier: e.target.value})}
                                           disabled
                                    />
                                    {errors.projectIdentifier && (
                                        <div className="invalid-feedback">{errors.projectIdentifier}</div>
                                    )}
                                </div>

                                <div className="form-group">
                                    <textarea className={classnames("form-control form-control-lg", {
                                                    "is-invalid": errors.description
                                                })}
                                              placeholder="Project Description" name="description"
                                              value={this.state.description}
                                              onChange={e => this.setState({description: e.target.value})}
                                    >
                                    </textarea>
                                    {errors.description && (
                                        <div className="invalid-feedback">{errors.description}</div>
                                    )}
                                </div>

                                <h6>Start Date</h6>
                                <div className="form-group">
                                    <input type="date" className="form-control form-control-lg" name="startDate"
                                           value={this.state.startDate}
                                           onChange={e => this.setState({startDate: e.target.value})}
                                    />
                                </div>

                                <h6>Estimated End Date</h6>
                                <div className="form-group">
                                    <input type="date" className="form-control form-control-lg" name="endDate"
                                           value={this.state.endDate}
                                           onChange={e => this.setState({endDate: e.target.value})}
                                    />
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

UpdateProject.propTypes = {
    getProject: PropTypes.func.isRequired,
    createProject: PropTypes.func.isRequired,
    project: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToPropsUpdateProject = state => ({
    project: state.project.project,
    errors: state.errors
});

//{getProject} is the action
export default connect(mapStateToPropsUpdateProject, {getProject, createProject}) (UpdateProject);