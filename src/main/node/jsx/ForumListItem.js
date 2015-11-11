var React = require('react');
var Link = require('react-router').Link;

var apiClient = require('./ApiClient').newInstance();

var debug = require('debug')('springboard:ForumListItem');
var DEBUG = !!debug.enabled;

var ForumListItem = React.createClass({
    getInitialState: function () {
        DEBUG && debug('getInitialState:', this.props, this.state);
        return {
            forumId: this.props.forumId,
            forum: this.props.forum
        };
    },
    componentWillMount: function () {
        DEBUG && debug('componentDidMount:', this.props, this.state);
        if (this.state.forumId && !this.state.forum) {
            apiClient.getForum(this.state.forumId).then(function (forum) {
                if (this.isMounted()) {
                    this.setState({forum: forum});
                }
            }.bind(this));
        }
    },
    render: function () {
        DEBUG && debug('render:', this.props, this.state);
        return (
            <li className="sbForumListItem">
                <h5><Link to={`/post_list?forumId=${this.state.forum.id}`}>{this.state.forum.title}</Link></h5>
            </li>
        );
    }
});

module.exports = ForumListItem;
