var React = require('react');
var Router = require('react-router').Router;
var Route = require('react-router').Route;

var ForumList = require('./ForumList');
var PostList = require('./PostList');
var PostView = require('./PostView');
var PostForm = require('./PostForm');
var SignupForm = require('./SignupForm');
var LoginForm = require('./LoginForm');

module.exports = (
    <Router>
        <Route path="/forum_list" component={ForumList}/>
        <Route path="/post_list" component={PostList}/>
        <Route path="/post_view" component={PostView}/>
        <Route path="/post_form" component={PostForm}/>
        <Route path="/signup" component={SignupForm}/>
        <Route path="/login" component={LoginForm}/>
        <Route path="*" component={ForumList}/>
    </Router>
);
