var Dispatcher = require('flux').Dispatcher;

var dispatcher = new Dispatcher();

var store = require('store');

dispatcher.register(function (payload) {
    DEBUG && debug('payload:', payload);
    return true;
});

module.exports = dispatcher;
