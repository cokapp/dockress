'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var $ = require('gulp-load-plugins')({
    pattern: ['gulp-*', 'main-bower-files', 'del']
});

//html处理
gulp.task('html', ['inject', 'templates'], function() {

    var tplJs = gulp.src(path.join(conf.paths.tmp, '/templates/*.js'), {
        read: false
    });
    var tplJsOptions = {
        starttag: '<!-- inject:tpljs -->',
        ignorePath: path.join(conf.paths.tmp, '/templates'),
        addRootSlash: false
    };

    var htmlFilter = $.filter('*.html', {
        restore: true
    });
    var jsFilter = $.filter('**/*.js', {
        restore: true
    });
    var cssFilter = $.filter('**/*.css', {
        restore: true
    });
    var assets;

    var enableSourcemaps = process._args.srcmap == 'enable';

    var pipe = gulp.src(path.join(conf.paths.tmp, '/serve/*.html'))
        .pipe($.inject(tplJs, tplJsOptions))
        .pipe(assets = $.useref.assets())
        .pipe($.rev())
        .pipe(jsFilter);

    if (enableSourcemaps) {
        pipe = pipe.pipe($.sourcemaps.init());
    }

    pipe = pipe.pipe($.ngAnnotate())
        .pipe($.uglify({
            preserveComments: $.uglifySaveLicense
        })).on('error', conf.errorHandler('Uglify'));

    if (enableSourcemaps) {
        pipe = pipe.pipe($.sourcemaps.write('maps'));
    }

    pipe = pipe.pipe(jsFilter.restore)
        .pipe(cssFilter);

    if (enableSourcemaps) {
        pipe = pipe.pipe($.sourcemaps.init());
    }

    //1.libs/semantic
    pipe = pipe.pipe($.replace('themes/default/assets/fonts/icons.', '../libs/semantic/themes/default/assets/fonts/icons.'))
        //2.libs/iconfont
        .pipe($.replace('iconfont.', '../libs/iconfont/iconfont.'))
        .pipe($.cssnano({
            processImport: false
        }));

    if (enableSourcemaps) {
        pipe = pipe.pipe($.sourcemaps.write('maps'));
    }

    pipe = pipe.pipe(cssFilter.restore)
        .pipe(assets.restore())
        .pipe($.useref())
        .pipe($.revReplace())
        .pipe(htmlFilter)
        .pipe($.htmlmin({
            empty: true,
            spare: true,
            quotes: true,
            conditionals: true
        }))
        .pipe(htmlFilter.restore)
        .pipe(gulp.dest(path.join(conf.paths.dist, '/')))
        .pipe($.size({
            title: path.join(conf.paths.dist, '/'),
            showFiles: true
        }));

    return pipe;
});


// Only applies for fonts from bower dependencies
// Custom fonts are handled by the "other" task
gulp.task('fonts', function() {
    return gulp.src($.mainBowerFiles())
        .pipe($.filter('**/*.{eot,svg,ttf,woff,woff2}'))
        .pipe($.flatten())
        .pipe(gulp.dest(path.join(conf.paths.dist, '/assets/fonts/')));
});

//复制其它文件
gulp.task('other', function() {
    var fileFilter = $.filter(function(file) {
        return file.stat.isFile();
    });
    return gulp.src([
            path.join(conf.paths.src, '/**/*'),
            path.join('!' + conf.paths.src, '/_semantic/**'),
            path.join('!' + conf.paths.src, '/**/*.{html,tpl,css,js,less}')
        ])
        .pipe(fileFilter)
        .pipe(gulp.dest(path.join(conf.paths.dist, '/')));
});

gulp.task('clean', function() {
    return $.del([path.join(conf.paths.dist, '/**'), path.join(conf.paths.tmp, '/')]);
});

gulp.task('build', ['clean', 'html', 'fonts', 'other']);
