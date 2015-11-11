var apiClient = require('ApiClient').newInstance();

var forum = false;
var forums = [];
var post = false;
var posts = [];
var comment = false;
var comments = [];
var changeListeners = [];

var store = {
    getForum: function () {
        return forum;
    },
    getForums: function () {
        return forums;
    },
    getPost: function () {
        return post;
    },
    getPosts: function () {
        return posts;
    },
    getComment: function () {
        return comment;
    },
    getComments: function () {
        return comments;
    },
    emitChange: function () {
        changeListeners.forEach(function (listener) {
            listener();
        });
    },
    addChangeListener: function (listener) {
        changeListeners.push(listener);
    },
    removeChangeListener: function (listener) {
        changeListeners.filter(function (l) {
            return listener !== l;
        });
    }
};

module.exports = store;
