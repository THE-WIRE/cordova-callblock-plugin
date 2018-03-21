
var exec = require('cordova/exec');

var PLUGIN_NAME = 'CallBlock';

var MyCordovaPlugin = {
  startWatch: function(phrase, cb) {
    exec(cb, null, PLUGIN_NAME, 'startWatch', [phrase]);
  },
  stopWatch: function(phrase, cb) {
    exec(cb, null, PLUGIN_NAME, 'stopWatch', [phrase]);
  }
};

module.exports = MyCordovaPlugin;
