var React = require('react');

var apiClient = require('./ApiClient').newInstance();

var debug = require('debug')('springboard:PostForm');
var DEBUG = !!debug.enabled;

var PostForm = React.createClass({
    getInitialState: function () {
        DEBUG && debug('getInitialState:', this.props, this.state);
        return {
            currentUser: apiClient.getCurrentUser(),
            forumId: this.props.forumId,
            title: this.props.title,
            content: this.props.content
        };
    },
    render: function () {
        DEBUG && debug('render:', this.props, this.state);
        if (!this.state.currentUser) {
            return (
                <Link to={`/login`}>login to write new post</Link>
            );
        }
        return (
            <div className="sbPostForm">
                <h3>springboard post form</h3>
                <form onSubmit={this.onSubmit}>
                    <p>
                        <label htmlFor="sbCommentTitleEdit">title:</label>
                        <input id="sbCommentTitleEdit" name="title" value={this.state.title}
                               onChange={this.onChangeTitle}/>
                    </p>
                    <p>
                        <label htmlFor="sbCommentContentEdit">content:</label>
                        <textarea id="sbCommentContentEdit" name="content" value={this.state.content}
                                  onChange={this.onChangeContent}/>
                    </p>
                    <p>
                        <input type="submit"/>
                        <input type="reset"/>
                    </p>
                </form>
            </div>
        );
    },
    onChangeTitle: function (e) {
        this.setState({title: e.target.value});
    },
    onChangeContent: function (e) {
        this.setState({content: e.target.value});
    },
    onSubmit: function (e) {
        e.preventDefault();
        var data = {
            forumId: this.state.forumId,
            //userId: this.state.currentUser.id,
            title: this.state.title,
            content: this.state.content
        };
        DEBUG && debug('onSubmit:', data);
        apiClient.createPost(data).then(function (res) {
            DEBUG && debug('create post ok!', res);
        }).catch(function (err) {
            DEBUG && debug('create post err!', err);
        });
    }
});

module.exports = PostForm;
