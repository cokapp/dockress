'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var replace = require('gulp-replace-task'),
    rename = require('gulp-rename'),
    minimist = require('minimist');

//配置文件处理
gulp.task('replace', function() {
    var opt = {
        string: 'env',
        default: { env: process.env.NODE_ENV || 'dev' }
    };
    var args = minimist(process.argv.slice(2), opt);

    var srcPath = path.join(conf.paths.src, '/app/scripts/app.config.js.tpl'),
        destPath = path.join(conf.paths.src, '/app/scripts');

    console.log('use ' + args.env + ' filter!');

    var replaceVariables = require('../_filter/' + args.env + '.json');
    //replaceVariables.env = replaceVariables.env || args.env;

    gulp.src(srcPath)
        .pipe(replace({
            variables: replaceVariables
        }))
        .pipe(rename('app.config.js'))
        .pipe(gulp.dest(destPath));
});
