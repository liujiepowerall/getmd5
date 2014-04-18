var cordova = require('cordova'),
    exec = require('cordova/exec');

var GetMd5 = function() {

};
GetMd5.prototype.getmd5 = function(url)
{
    exec(null, null, 'GetMd5Plugin', 'getmd5', [url]);
};

var getmd5 = new GetMd5();

module.exports = getmd5;