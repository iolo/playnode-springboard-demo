var React = require('react');

var ForumListItem = require('./ForumListItem');

var apiClient = require('./ApiClient').newInstance();

var debug = require('debug')('springboard:ForumList');
var DEBUG = !!debug.enabled;

var ForumList = React.createClass({
    getInitialState: function () {
        DEBUG && debug('getInitialState:', this.props, this.state);
        return {
            forums: this.props.forums
        };
    },
    componentWillMount: function () {
        DEBUG && debug('componentDidMount:', this.props, this.state);
        if (!this.state.forums) {
            apiClient.getForums().then(function (forums) {
                if (this.isMounted()) {
                    this.setState({forums: forums});
                }
            }.bind(this));
        }
    },
    render: function () {
        DEBUG && debug('render:', this.props, this.state);
        var forumList;
        if (this.state.forums) {
            forumList = (
                <ul>
                    {(this.state.forums).map(function (forum) {
                        return (
                            <ForumListItem key={forum.id} forum={forum}/>
                        );
                    })}
                </ul>
            );
        }
        return (
            <div className="sbForumList">
                <h3>springboard forum list</h3>
                {forumList}
            </div>
        );
    }
});

module.exports = ForumList;
