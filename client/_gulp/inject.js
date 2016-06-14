'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var $ = require('gulp-load-plugins')();
var browserSync = require('browser-sync');

//基于bower的依赖注入
var wiredep = require('wiredep').stream;
//一个js工具库，提供计算功能
var _ = require('lodash');

gulp.task('inject-reload', ['inject'], function() {
    browserSync.reload();
});


//注入生成的应用css，未合并的应用js，以及bower依赖css、js，输出到.tmp/server/
gulp.task('inject', ['scripts', 'styles'], function() {

    //appcss
    var appCss = gulp.src([
        path.join(conf.paths.tmp, '/serve/*.css')
    ], {
        read: false
    });

    //appjs
    var appJs = gulp.src([
        path.join(conf.paths.src, '/app/**/*.module.js'),
        path.join(conf.paths.src, '/app/**/*.js'),
        path.join('!' + conf.paths.src, '/app/**/*.spec.js'),
        path.join('!' + conf.paths.src, '/app/**/*.mock.js')
    ], {
        read: false
    });

    //libcss
    var libCss = gulp.src([
        path.join(conf.paths.src, '/libs/**/*.css')
    ], {
        read: false
    });

    //libjs
    var libJs = gulp.src([
        path.join(conf.paths.src, '/libs/**/*.js')
    ]);

    var appCssOptions = {
        starttag: '<!-- inject:appcss -->',
        ignorePath: [conf.paths.src, path.join(conf.paths.tmp, '/serve')],
        addRootSlash: false
    };

    var appJsOptions = {
        starttag: '<!-- inject:appjs -->',
        ignorePath: [conf.paths.src, path.join(conf.paths.tmp, '/serve')],
        addRootSlash: false
    };

    var libCssOptions = {
        starttag: '<!-- inject:libcss -->',
        ignorePath: [conf.paths.src, path.join(conf.paths.tmp, '/serve')],
        addRootSlash: false
    };

    var libJsOptions = {
        starttag: '<!-- inject:libjs -->',
        ignorePath: [conf.paths.src, path.join(conf.paths.tmp, '/serve')],
        addRootSlash: false
    };

    return gulp.src(path.join(conf.paths.src, '/index.html'))
        .pipe($.inject(appCss, appCssOptions))
        .pipe($.inject(appJs, appJsOptions))
        .pipe($.inject(libCss, libCssOptions))
        .pipe($.inject(libJs, libJsOptions))
        .pipe(wiredep(_.extend({}, conf.wiredep)))
        .pipe(gulp.dest(path.join(conf.paths.tmp, '/serve/')));

});