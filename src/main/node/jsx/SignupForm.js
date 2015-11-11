var React = require('react');

var apiClient = require('./ApiClient').newInstance();

var debug = require('debug')('springboard:SignupForm');
var DEBUG = !!debug.enabled;

var SignupForm = React.createClass({
    getInitialState: function () {
        DEBUG && debug('getInitialState:', this.props, this.state);
        return {
            username: this.props.username,
            password: ''
        };
    },
    render: function () {
        DEBUG && debug('render:', this.props, this.state);
        return (
            <div className="sbSignupForm">
                <h3>signup</h3>
                <form onSubmit={this.onSubmit}>
                    <p>
                        <label for="sbSignupUsername">username:</label>
                        <input id="sbSignupUsername" type="text" name="username" value={this.state.username}
                               onChange={this.onChangeUsername}/>
                    </p>
                    <p>
                        <label for="sbSignupPassword">password:</label>
                        <input id="sbSignupPassword" type="password" name="password" value={this.state.password}
                               onChange={this.onChangePassword}/>
                    </p>
                    <p>
                        <input type="submit"/>
                        <input type="reset"/>
                    </p>
                </form>
            </div>
        );
    },
    onChangeUsername: function (e) {
        this.setState({username: e.target.value});
    },
    onChangePassword: function (e) {
        this.setState({password: e.target.value});
    },
    onSubmit: function (e) {
        e.preventDefault();
        var data = {
            username: this.state.username,
            password: this.state.password
        };
        DEBUG && debug('onSubmit:', data);
        apiClient.signup(data).then(function (res) {
            if (res) {
                DEBUG && debug('signup ok!', res);
            } else {
                DEBUG && debug('signup fail!', res);
            }
        }).catch(function (err) {
            DEBUG && debug('signup err!', err);
        });
    }
});

module.exports = SignupForm;
