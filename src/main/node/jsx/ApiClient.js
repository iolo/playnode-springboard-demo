'use strict';

require('es6-promise').polyfill();
require('isomorphic-fetch');

var debug = require('debug')('springboard:ApiClient');
var DEBUG = !!debug.enabled;

function handleJsonResponse(res) {
    if (res.status >= 200 && res.status < 300) {
        return res.json();
    }
    var err = new Error(res.statusText);
    err.statusCode = res.statusCode;
    err.response = response;
    throw err;
}

var ApiClient = function (config) {
    this.config = Object.assign({endpoint: 'http://localhost:3000'}, config);
};

ApiClient.prototype.getForums = function () {
    return fetch(this.config.endpoint + '/forums').then(handleJsonResponse);
};

ApiClient.prototype.getForum = function (forumId) {
    return fetch(this.config.endpoint + '/forums/' + forumId).then(handleJsonResponse);
};

ApiClient.prototype.getPosts = function (forumId) {
    return fetch(this.config.endpoint + '/posts/in/' + forumId).then(handleJsonResponse);
};

ApiClient.prototype.getPost = function (postId) {
    return fetch(this.config.endpoint + '/posts/' + postId).then(handleJsonResponse);
};

ApiClient.prototype.createPost = function (post) {
    var req = {
        method: 'post',
        data: post
    };
    return fetch(this.config.endpoint + '/posts', req).then(handleJsonResponse);
};

ApiClient.prototype.getComments = function (postId) {
    return fetch(this.config.endpoint + '/comments/on/' + postId).then(handleJsonResponse);
};

ApiClient.prototype.getComment = function (commentId) {
    return fetch(this.config.endpoint + '/comments/' + commentId).then(handleJsonResponse);
};

ApiClient.prototype.createComment = function (comment) {
    var req = {
        method: 'post',
        data: comment
    };
    return fetch(this.config.endpoint + '/comments', req).then(handleJsonResponse);
};

ApiClient.prototype.signup = function (data) {
    var req = {
        method: 'post',
        data: data
    };
    return fetch(this.config.endpoint + '/signup', req).then(handleJsonResponse).then(function (res) {
        DEBUG && debug('signup ok', res);
        return true;
    });
};

ApiClient.prototype.login = function (data) {
    var req = {
        method: 'post',
        data: data
    };
    return fetch(this.config.endpoint + '/login', req).then(handleJsonResponse).then(function (res) {
        DEBUG && debug('login ok', res);
        localStorage && localStorage.setItem('currentUser', JSON.stringify(res));
        return true;
    });
};

ApiClient.prototype.logout = function () {
    localStorage && localStorage.removeItem('currentUser');
};

ApiClient.prototype.getCurrentUser = function () {
    try {
        return localStorage && JSON.parse(localStorage.getItem('currentUser'));
    } catch (e) {
        return false;
    }
};

ApiClient.newInstance = function (config) {
    return new ApiClient(config);
};

module.exports = ApiClient;
