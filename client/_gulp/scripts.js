'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var $ = require('gulp-load-plugins')();
var browserSync = require('browser-sync');

gulp.task('scripts-reload', function() {
    return buildScripts()
        .pipe(browserSync.stream());
});

gulp.task('scripts', ['replace'], function() {
    return buildScripts();
});

function buildScripts() {
    return gulp.src(path.join(conf.paths.src, '/app/**/*.js'))
        .pipe($.eslint())
        .pipe($.eslint.format())
        .pipe($.size())
};