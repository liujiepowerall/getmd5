var cordova = require('cordova'),
    exec = require('cordova/exec');

var GetMd5 = function() {

};
GetMd5.prototype.getmd5 = function(success,failed,url)
{
    exec(success, failed, 'GetMd5Plugin', 'getmd5', [url]);
};

var md5 = new GetMd5();

module.exports = getmd5;