/**
 *  Welcome to your gulpfile!
 *  The gulp tasks are splitted in several files in the gulp directory
 *  because putting all here was really too long
 */
'use strict';
var gulp = require('gulp');
//文件读取
var wrench = require('wrench'),
    minimist = require('minimist');

/**
 *  This will load all js or coffee files in the gulp directory
 *  in order to load all gulp tasks
 */
wrench.readdirSyncRecursive('./_gulp').filter(function(file) {
    return (/\.(js|coffee)$/i).test(file);
}).map(function(file) {
    require('./_gulp/' + file);
});


/**
 *  Default task clean temporaries directories and launch the
 *  main optimization build task
 */
gulp.task('default', ['clean'], function() {
    gulp.start('build');
});

var opt = {
	string: 'env',
	default: { env: process.env.NODE_ENV || 'dev' }
};
var args = minimist(process.argv.slice(2), opt);

process._args = args;
