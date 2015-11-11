var React = require('react');

var CommentList = require('./CommentList');
var CommentForm = require('./CommentForm');

var apiClient = require('./ApiClient').newInstance();

var debug = require('debug')('springboard:PostView');
var DEBUG = !!debug.enabled;

var PostView = React.createClass({
    getInitialState: function () {
        DEBUG && debug('getInitialState:', this.props, this.state);
        return {
            postId: this.props.postId,
            post: this.props.post
        };
    },
    componentDidMount: function () {
        DEBUG && debug('componentDidMount:', this.props, this.state);
        if (this.state.postId && !this.state.post) {
            apiClient.getPost(this.state.postId).then(function (post) {
                if (this.isMounted()) {
                    this.setState({post: post});
                }
            }.bind(this));
        }
    },
    render: function () {
        DEBUG && debug('render:', this.props, this.state);
        return (
            <div className="sbPostView">
                <h3>{this.state.post.title}</h3>
                <p>at {this.state.post.createdAt}</p>
                <p>by {this.state.post.userId}</p>
                <pre>{this.state.post.content}</pre>
                <hr />
                <CommentList postId={this.state.post.id}/>
                <CommentForm postId={this.state.post.id}/>
            </div>
        );
    }
});

module.exports = PostView;
