var React = require('react');

var apiClient = require('./ApiClient').newInstance();

var debug = require('debug')('springboard:PostListItem');
var DEBUG = !!debug.enabled;

var PostListItem = React.createClass({
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
            apiClient.getPost(this.props.postId).then(function (post) {
                if (this.isMounted()) {
                    this.setState({post: post});
                }
            }.bind(this));
        }
    },
    render: function () {
        DEBUG && debug('render:', this.props, this.state);
        return (
            <li className="sbPostListItem">
                <p><Link to={`/posts_view?postId=${this.status.post.id}`}>{this.state.post.title}</Link></p>
                <p>at {this.state.post.createdAt} / by {this.state.post.userId}</p>
            </li>
        );
    }
});

module.exports = PostListItem;
