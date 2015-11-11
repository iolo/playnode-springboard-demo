var debug = require('debug')('springboard:PostList');
var DEBUG = !!debug.enabled;

var apiClient = require('./ApiClient').newInstance();

var React = require('react');
var PostListItem = require('./PostListItem');

var PostList = React.createClass({
    getInitialState: function () {
        DEBUG && debug('getInitialState:', this.props, this.state);
        return {
            forumId: this.props.forumId || this.props.location.query.forumId,
            forum: this.props.forum,
            posts: this.props.posts
        };
    },
    componentWillMount: function () {
        DEBUG && debug('componentDidMount:', this.props, this.state);
        if (this.state.forumId && (!this.state.forum || !this.state.posts)) {
            Promise.all([
                apiClient.getForum(this.state.forumId),
                apiClient.getPosts(this.state.forumId)
            ]).then(function (res) {
                if (this.isMounted()) {
                    this.setState({forum: res[0], posts: res[1]});
                }
            }.bind(this));
        }
    },
    render: function () {
        DEBUG && debug('render:', this.props, this.state);
        var postList;
        if (this.state.posts) {
            postList = (
                <ul>
                    {this.state.posts.map(function (post) {
                        return (
                            <PostListItem key={post.id} post={post}/>
                        );
                    })}
                </ul>
            );
        }
        var postWriteLink;
        if (this.state.currentUser) {
            postWriteLink = (
                <div>
                    <Link to={`/post_form?forumId=${this.state.forum.id}`}>write new post</Link>
                </div>
            );
        } else {
            postWriteLink = (
                <div>
                    <Link to={`/login`}>login to write new post</Link>
                </div>
            );
        }
        return (
            <div className="sbPostList">
                <h3>{this.state.forum.title}</h3>
                {postList}
                {postWriteLink}
            </div>
        );
    }
});

module.exports = PostList;
