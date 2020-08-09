import React, {Component} from "react";
import {Redirect, Route} from "react-router-dom";
import PropTypes from "prop-types";
import {connect} from "react-redux";

const SecuredRoute = ({component: Component, security, ...otherProps}) => (
  <Route {...otherProps}
         render={
             props => security.validToken === true ? (<Component {...props} />) : (<Redirect to="/login" />)
         }
  />
);

const mapStateToProps = state => ({
    security: state.security
});

SecuredRoute.propTypes = {
    security: PropTypes.object.isRequired
};

export default connect(mapStateToProps) (SecuredRoute);
