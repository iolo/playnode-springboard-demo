var React = require('react');
var ReactDOM = require('react-dom');

var Router = require('react-router').Router;

var debug = require('debug')('springboard');
var DEBUG = !!debug.enabled;

//---------------------------------------------------------

var mountPoint = document.getElementById('springboard');

var routes = require('./routes');
ReactDOM.render(routes, mountPoint);

/*
// TODO: override via data-* attrs and/or global variable.
var params = location.search ? location.search.substring(1).split('&').reduce(function (params, param) {
    var kv = param.split('=');
    var key = decodeURIComponent(kv[0].trim());
    var value = kv.length > 1 ? decodeURIComponent(kv[1].trim()) : key;
    params[key] = value;
    return params;
}, {}) : {};

DEBUG && debug('params:', params);

var view = params.view;
var el = <h1>springboard not loaded</h1>;
if (!view) {
    if (params.postId) {
        view = 'post_view';
    } else if (params.forumId) {
        view = 'post_list';
    } else {
        view = 'forum_list';
    }
}

switch (view) {
    case 'forum_list':
        el = (
            <ForumList />
        );
        break;
    case 'post_list':
        if (!params.forumId) {
            DEBUG && debug('forumId required');
            break;
        }
        el = (
            <PostList forumId={params.forumId}/>
        );
        break;
    case 'post_view':
        if (!params.postId) {
            DEBUG && debug('postId required');
            break;
        }
        el = (
            <PostView postId={params.postId}/>
        );
        break;
    case 'post_form':
        if (!params.forumId) {
            DEBUG && debug('forumId required');
            break;
        }
        el = (
            <PostForm forumId={params.forumId}/>
        );
        break;
    case 'signup':
        el = (
            <SignupForm />
        );
        break;
    case 'login':
        el = (
            <LoginForm />
        );
        break;
}

ReactDOM.render(el, mountPoint);
*/

DEBUG && debug('ready for battle!');

