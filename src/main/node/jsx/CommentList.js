var React = require('react');

var CommentListItem = require('./CommentListItem');

var apiClient = require('./ApiClient').newInstance();

var debug = require('debug')('springboard:CommentList');
var DEBUG = !!debug.enabled;

var CommentList = React.createClass({
    getInitialState: function () {
        DEBUG && debug('getInitialState:', this.props, this.state);
        return {
            postId: this.props.postId,
            comments: this.props.comments
        };
    },
    componentDidMount: function () {
        DEBUG && debug('componentDidMount:', this.props, this.state);
        if (this.state.postId && !this.state.comments) {
            apiClient.getComments(this.state.postId).then(function (comments) {
                if (this.isMounted()) {
                    this.setState({comments: comments});
                }
            }.bind(this));
        }
    },
    render: function () {
        DEBUG && debug('render:', this.props, this.state);
        return (
            <div className="sbCommentList">
                <ul>
                    {this.state.comments.map(function (comment) {
                        return (
                            <CommentListItem key={comment.id} comment={comment}/>
                        );
                    })}
                </ul>
            </div>
        );
    }
});

module.exports = CommentList;
