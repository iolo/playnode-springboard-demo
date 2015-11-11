var React = require('react');

var apiClient = require('./ApiClient').newInstance();

var debug = require('debug')('springboard:CommentListItem');
var DEBUG = !!debug.enabled;

var CommentListItem = React.createClass({
    getInitialState: function () {
        DEBUG && debug('getInitialState:', this.props, this.state);
        return {
            commentId: this.props.commentId,
            comment: this.props.comment
        };
    },
    componentDidMount: function () {
        DEBUG && debug('componentDidMount:', this.props, this.state);
        if (this.state.commentId && !this.state.comment) {
            apiClient.getComment(this.state.commentId).then(function (comment) {
                if (this.isMounted()) {
                    this.setState({comment: comment});
                }
            }.bind(this));
        }
    },
    render: function () {
        DEBUG && debug('render:', this.props, this.state);
        return (
            <ul className="sbCommentListItem">
                <pre>{this.state.comment.content}</pre>
                <p>
                    <small>at {this.state.comment.createdAt} / by {this.state.comment.userId}</small>
                </p>
            </ul>
        );
    }
});

module.exports = CommentListItem;
