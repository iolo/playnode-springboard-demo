var React = require('react');

var apiClient = require('./ApiClient').newInstance();

var debug = require('debug')('springboard:LoginForm');
var DEBUG = !!debug.enabled;

var LoginForm = React.createClass({
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
            <div className="sbLoginForm">
                <h3>login</h3>
                <form onSubmit={this.onSubmit}>
                    <p>
                        <label for="sbLoginUsername">username:</label>
                        <input id="sbLoginUsername" type="text" name="username" value={this.state.username}
                               onChange={this.onChangeUsername}/>
                    </p>
                    <p>
                        <label for="sbLoginPassword">password:</label>
                        <input id="sbLoginPassword" type="password" name="password" value={this.state.password}
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
        apiClient.login(data).then(function (res) {
            if (res) {
                DEBUG && debug('login ok!', res);
            } else {
                DEBUG && debug('login fail!', res);
            }
        }).catch(function (err) {
            DEBUG && debug('login err!', err);
        });
    }
});

module.exports = LoginForm;
