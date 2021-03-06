import React, {Component} from 'react';
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {login} from "../../actions/securityActions";
import classnames from "classnames";

class Login extends Component {

    constructor() {
        super();

        this.state = {
            username: "",
            password: "",
            errors: {}
        }
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        if(nextProps.security.validToken){
            this.props.history.push("/dashboard");
        }

        if(nextProps.errors){
            this.setState({errors: nextProps.errors});
        }
    }

    onSubmit(e){
        e.preventDefault();

        const loginRequest = {
            username: this.state.username,
            password: this.state.password
        }

        this.props.login(loginRequest);
    }

    componentDidMount() {
        if(this.props.security.validToken){
            this.props.history.push("/dashboard");
        }
    }

    render() {
        const {errors} = this.state;

        return (
            <div className="login">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h1 className="display-4 text-center">Log In</h1>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input type="text"
                                           className={classnames("form-control form-control-lg", {
                                               "is-invalid": errors.username
                                           })}
                                        // className="form-control form-control-lg"
                                           placeholder="Email Address (username)"
                                           name="username"
                                           value={this.state.username}
                                           onChange={e => this.setState({username: e.target.value})}
                                    />
                                    {errors.username && (
                                        <div className="invalid-feedback">{errors.username}</div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <input type="password"
                                           className={classnames("form-control form-control-lg", {
                                               "is-invalid": errors.password
                                           })}
                                           // className="form-control form-control-lg"
                                           placeholder="Password"
                                           name="password"
                                           value={this.state.password}
                                           onChange={e => this.setState({password: e.target.value})}
                                    />
                                    {errors.password && (
                                        <div className="invalid-feedback">{errors.password}</div>
                                    )}
                                </div>
                                <input type="submit" className="btn btn-info btn-block mt-4"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

Login.propTypes = {
    login: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    security: PropTypes.object.isRequired
};

//attributes from index.js
const mapStateToProps = state => ({
    security: state.security,
    errors: state.errors
});

export default connect(
    mapStateToProps,
    { login }
)(Login);
