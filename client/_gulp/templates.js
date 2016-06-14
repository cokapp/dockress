'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var $ = require('gulp-load-plugins')();

//angular模板缓存
gulp.task('templates', function() {
	return gulp.src([
			path.join(conf.paths.src, '/app/**/*.tpl.html')
		])
		.pipe($.angularTemplatecache(conf.pkg.name + '.tpl.js', {
			module: conf.pkg.name,
			root: 'app'
		}))
		.pipe(gulp.dest(path.join(conf.paths.tmp, '/templates/')));
});