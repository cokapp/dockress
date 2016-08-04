'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var $ = require('gulp-load-plugins')();
var browserSync = require('browser-sync');

var wiredep = require('wiredep').stream;
var _ = require('lodash');

gulp.task('styles-reload', function() {
    return buildStyles()
        .pipe(browserSync.stream());
});
gulp.task('styles', function() {
    return buildStyles();
});

var buildStyles = function() {
    var appLess = path.join(conf.paths.src, '/app/less/_app.less');

    var lessOptions = {
        options: [
            path.join(conf.paths.src, '/app')
        ]
    };

    var injectFiles = gulp.src([
        path.join(conf.paths.src, '/app/**/*.less'),
        '!' + appLess
    ], {
        read: false
    });

    var injectOptions = {
        transform: function(filePath) {
            filePath = filePath.replace(conf.paths.src + '/app/less/', '');
            return '@import "' + filePath + '";';
        },
        starttag: '// injector',
        endtag: '// endinjector',
        addRootSlash: false
    };

    return gulp.src([appLess])
        .pipe($.inject(injectFiles, injectOptions))
        .pipe($.rename({
            basename: conf.pkg.name
        }))
        .pipe(wiredep(_.extend({}, conf.wiredep)))
        .pipe($.less(lessOptions)).on('error', conf.errorHandler('Less'))
        .pipe($.autoprefixer()).on('error', conf.errorHandler('Autoprefixer'))
        .pipe(gulp.dest(path.join(conf.paths.tmp, '/serve/')));
}