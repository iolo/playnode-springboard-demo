var React = require('react');

var apiClient = require('./ApiClient').newInstance();

var debug = require('debug')('springboard:CommentForm');
var DEBUG = !!debug.enabled;

var CommentForm = React.createClass({
    getInitialState: function () {
        DEBUG && debug('getInitialState:', this.props, this.state);
        return {
            currentUser: apiClient.getCurrentUser(),
            postId: this.props.postId,
            content: ''
        };
    },
    render: function () {
        DEBUG && debug('render:', this.props, this.state);
        if (!this.state.currentUser) {
            return (
                <Link to={`/login`}>login to write new comment</Link>
            );
        }
        return (
            <div className="sbCommentForm">
                <form onSubmit={this.onSubmit}>
                    <textarea id="sbCommentContentEdit" value={this.state.content} onChange={this.onChangeContent}/>
                    <input type="submit"/>
                </form>
            </div>
        );
    },
    onChangeContent: function (e) {
        this.setState({content: e.target.value});
    },
    onSubmit: function (e) {
        e.preventDefault();
        var comment = {
            userId: this.state.currentUser.id,
            postId: this.state.postId,
            content: this.state.content
        };
        DEBUG && debug('onSubmit:', comment);
        apiClient.createComment(comment).then(function (res) {
            // TODO: refresh!
            DEBUG && debug('create comment ok!', res);
        }).catch(function (err) {
            DEBUG && debug('create comment err!', err);
        });
    }
});

module.exports = CommentForm;
