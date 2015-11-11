'use strict';

var debug = require('debug')('springboard:api');
var DEBUG = !!debug.enabled;

//---------------------------------------------------------

var thrift = require('thrift');

var Springboard = require('./gen-nodejs/springboard');
var Types = require('./gen-nodejs/springboard_types');

var conn = thrift.createConnection('localhost', 9876, {debug: true, max_attempts: 5});

conn.on('error', function (err) {
    console.error('*** THRIFT CONNECTION ERROR:', err);
});

process.on('exit', function () {
    console.log('*** BYE ***');
    conn.end();
});

var springboard = thrift.createClient(Springboard, conn);

var redis = require('redis');
var redisClient = redis.createClient();

var cache_getOrSet;
if (process.env.NO_REDIS_CACHE) {
    cache_getOrSet = function (key, supplier, callback) {
        return supplier(callback);
    };
} else {
    var cacheTTL = 10;
    cache_getOrSet = function (key, supplier, callback) {
        redisClient.get(key, function (err, data) {
            //if (err) {
            //    return callback(err);
            //}
            if (data) {
                DEBUG && debug('***cache hit:', key);
                return callback(null, JSON.parse(data));
            }
            DEBUG && debug('***cache miss:', key);
            supplier(function (err, supplied) {
                if (err) {
                    return callback(err);
                }
                if (supplied) {
                    DEBUG && debug('***cache supplied:', supplied);
                    redisClient.setex(key, cacheTTL, JSON.stringify(supplied));
                } else {
                    redisClient.del(key);
                }
                return callback(null, supplied);
            });
        });
    }
}

function checkRolesFn(roles) {
    roles = roles ? roles.toLowerCase().split(',').reduce(function (roles, role) {
        roles[role.trim()] = true;
        return roles;
    }, {}) : false;
    return function checkRoles(req, res, next) {
        if (roles) {
            DEBUG && debug('check role: ', roles, req.session.currentUser);
            if (!req.session.currentUser) {
                return res.status(401).json({status: 401, message: 'unauthorized'});
            }
            // TODO: check your role...
            // return res.status(403).end();
        }
        return next();
    };
}

//---------------------------------------------------------

var express = require('express');

var app = express();

app.set('json replacer', function (key, value) {
    if (value instanceof thrift.Int64) {
        return value.toNumber(true);
    }
    return value;
});

app.set('views', __dirname + '/views');

app.use(require('cors')());
app.use(require('body-parser').json());

app.use(require('express-session')({
    secret: 'may the force be with you',
    resave: false,
    saveUninitialized: true
}));

app.get('/forums', function (req, res, next) {
    cache_getOrSet('[f', function (callback) {
        springboard.getForums(function (err, data) {
            if (err) {
                return next(err);
            }
            //res.json(data);
            callback(null, data);
        });
    }, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.get('/forums/:forumId', function (req, res, next) {
    var forumId = req.params.forumId;
    cache_getOrSet('f:' + forumId, function (callback) {
        springboard.getForum(forumId, function (err, data) {
            if (err) {
                return next(err);
            }
            //res.json(data);
            callback(null, data);
        });
    }, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.post('/forums', checkRolesFn('admin'), function (req, res, next) {
    var forum = new Types.Forum();
    forum.title = req.body.title;
    springboard.createForum(forum, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.put('/forums/:forumId', checkRolesFn('admin'), function (req, res, next) {
    var forum = new Types.Comment();
    forum.id = req.params.forumId;//req.body.id;
    forum.title = req.body.title;
    springboard.updateForum(forum, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.delete('/forums/:forumId', checkRolesFn('admin'), function (req, res, next) {
    var forumId = req.params.forumId;
    springboard.deleteForum(forumId, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.get(['/forums/:forumId/posts', '/posts/in/:forumId'], function (req, res, next) {
    var forumId = req.params.forumId;
    cache_getOrSet('[p:f:' + forumId, function (callback) {
        springboard.getPosts(forumId, function (err, data) {
            if (err) {
                return next(err);
            }
            //res.json(data);
            callback(null, data);
        });
    }, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.get('/posts/:postId', function (req, res, next) {
    var postId = req.params.postId;
    cache_getOrSet('p:' + postId, function (callback) {
        springboard.getPost(postId, function (err, data) {
            if (err) {
                return next(err);
            }
            //res.json(data);
            callback(null, data);
        });
    }, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.post('/posts', checkRolesFn('writer'), function (req, res, next) {
    var post = new Types.Post({
        //id: 0,
        userId: req.session.currentUser.id,//req.body.userId,
        forumId: req.body.forumId,
        title: req.body.title,
        content: req.body.content,
        //createdAt: ''
    });
    DEBUG && debug('create post:', post);
    springboard.createPost(post, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.put('/posts/:postId', checkRolesFn('owner'), function (req, res, next) {
    var post = new Types.Post({
        id: req.params.postId,//req.body.id,
        userId: req.session.currentUser.id,//req.body.userId,
        forumId: req.body.forumId,
        title: req.body.title,
        content: req.body.content
    });
    DEBUG && debug('update post:', post);
    springboard.updatePost(post, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.delete('/posts/:postId', checkRolesFn('owner'), function (req, res, next) {
    var postId = req.params.postId;
    springboard.deletePost(postId, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.get(['/posts/:postId/comments', '/comments/on/:postId'], function (req, res, next) {
    var postId = req.params.postId;
    cache_getOrSet('[c:p:' + postId, function (callback) {
        springboard.getComments(postId, function (err, data) {
            if (err) {
                return next(err);
            }
            //res.json(data);
            callback(null, data);
        });
    }, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.get('/comments/:commentId', function (req, res, next) {
    var commentId = req.params.commentId;
    cache_getOrSet('c:' + commentId, function (callback) {
        springboard.getComment(commentId, function (err, data) {
            if (err) {
                return next(err);
            }
            //res.json(data);
            callback(null, data);
        });
    }, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.post('/comments', checkRolesFn('writer'), function (req, res, next) {
    var comment = new Types.Comment();
    comment.userId = req.session.currentUser.id;//req.body.userId;
    comment.postId = req.body.postId;
    comment.content = req.body.content;
    springboard.createComment(comment, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.put('/comments/:commentId', checkRolesFn('owner'), function (req, res, next) {
    var comment = new Types.Comment();
    comment.id = req.params.commentId;//req.body.id;
    comment.userId = req.session.currentUser.id;//req.body.userId;
    comment.postId = req.body.postId;
    comment.content = req.body.content;
    springboard.updateComment(comment, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.delete('/comments/:commentId', checkRolesFn('owner'), function (req, res, next) {
    var commentId = req.params.commentId;
    springboard.deleteComment(commentId, function (err, data) {
        if (err) {
            return next(err);
        }
        res.json(data);
    });
});

app.post('/login', function (req, res, next) {
    var username = req.body.username;
    var password = req.body.password;
    springboard.getUserByUsernameAndPassword(username, password, function (err, data) {
        if (err) {
            return next(err);
        }
        req.session.currentUser = data;
        //var returnUrl = req.query.returnUrl || 'forum_list';
        //res.redirect(returnUrl);
        res.json(data);
    });
});

app.post('/signup', function (req, res, next) {
    var user = {
        username: req.body.username,
        password: req.body.password
    };
    springboard.createUser(user, function (err, data) {
        if (err) {
            return next(err);
        }
        //var returnUrl = req.query.returnUrl || 'forum_list';
        //res.redirect(returnUrl);
        res.json(data);
    });
});

//---------------------------------------------------------
// TODO: isomorphic rendering server

require("node-jsx").install({harmony: true});

var React = require('react');
var ReactDOMServer = require('react-dom/server');

app.get('/forum_list.html', function (req, res, next) {
    return springboard.getForums(function (err, forums) {
        if (err) {
            return next(err);
        }
        var ForumList = React.createFactory(require('./jsx/ForumList'));
        return res.render('index.ejs', {
            reactOutput: ReactDOMServer.renderToString(ForumList({forums: forums})),
            forceServerSideRendering: req.query.forceServerSideRendering
        });
    });
});
app.get('/post_list.html', function (req, res, next) {
    return springboard.getForum(req.query.forumId, function (err, forum) {
        if (err) {
            return next(err);
        }
        return springboard.getPosts(req.query.forumId, function (err, posts) {
            if (err) {
                return next(err);
            }
            var PostList = React.createFactory(require('./jsx/PostList'));
            return res.render('index.ejs', {
                reactOutput: ReactDOMServer.renderToString(PostList({forum: forum, posts: posts})),
                forceServerSideRendering: req.query.forceServerSideRendering
            });
        });
    });
});
app.get('/post_view.html', function (req, res, next) {
    return springboard.getPost(req.query.postId, function (err, post) {
        if (err) {
            return next(err);
        }
        return springboard.getComments(req.query.postId, function (err, comments) {
            if (err) {
                return next(err);
            }
            var PostList = React.createFactory(require('./jsx/PostView'));
            return res.render('index.ejs', {
                reactOutput: ReactDOMServer.renderToString(PostList({post: post, posts: comments})),
                forceServerSideRendering: req.query.forceServerSideRendering
            });
        });
    });
});

app.use(require('serve-static')(__dirname + '/public'));

app.listen(3000);
