var React = require('react');

var apiClient = require('./ApiClient').newInstance();

var debug = require('debug')('springboard:Header');
var DEBUG = !!debug.enabled;

var Header = React.createClass({
    getInitialState: function () {
        DEBUG && debug('getInitialState:', this.props, this.state);
        return {
            currentUser: apiClient.getCurrentUser()
        };
    },
    render: function () {
        DEBUG && debug('render:', this.props, this.state);
        if (this.state.currentUser) {
            return (
                <div className="sbHeader">
                    <span><Link to={`/login`}>login</Link></span>
                    <span>|</span>
                    <span><Link to={`/signup`}>signup</Link></span>
                </div>
            );
        } else {
            return (
                <div className="sbHeader">
                    <span>{this.state.currentUser.username}</span>
                    <span>|</span>
                    <span onClick={this.onClickLogout}>logout</span>
                </div>
            );
        }
    },
    onClickLogout: function () {
        DEBUG && debug('logout!');
        apiClient.logout();
    }
});

module.exports = Header;
