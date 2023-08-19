const {defineConfig} = require('@vue/cli-service')
const defaultSettings = require('./src/settings')

const path = require('path')
const NodePolyfillPlugin = require("node-polyfill-webpack-plugin")

function resolve(dir) {
    return path.join(__dirname, dir)
}

module.exports = defineConfig({
    /**
     * You will need to set publicPath if you plan to deploy your site under a sub path,
     * for example GitHub Pages. If you plan to deploy your site to https://foo.github.io/bar/,
     * then publicPath should be set to "/bar/".
     * In most cases please use '/' !!!
     * Details: https://cli.vuejs.org/config/#publicpath
     */
    publicPath: '/',
    transpileDependencies: true,
    lintOnSave: false,
    productionSourceMap: false,
    devServer: {
        port: 8080,
        open: true
    },
    configureWebpack: {
        // provide the app's title in webpack's name field, so that it can be accessed in index.html to inject the correct title.
        name: defaultSettings.title || 'Vue Element Admin',
        resolve: {
            alias: {
                '@': resolve('src')
            },
            // Deal with `BREAKING CHANGE: webpack < 5 used to include polyfills for node.js core modules by default.`
            // `This is no longer the case. Verify if you need this module and configure a polyfill for it.`
            fallback: {
                fs: false,
                crypto: require.resolve("crypto-browserify")
            }
        },
        plugins: [new NodePolyfillPlugin()]
    },
    chainWebpack(config) {
        // // it can improve the speed of the first screen, it is recommended to turn on preload
        // config.plugin('preload').tap(() => [{
        //     rel: 'preload',
        //     // to ignore runtime.js, details see https://github.com/vuejs/vue-cli/blob/dev/packages/@vue/cli-service/lib/config/app.js#L171
        //     fileBlacklist: [/\.map$/, /hot-update\.js$/, /runtime\..*\.js$/],
        //     include: 'initial'
        // }])

        // when there are many pages, it will cause too many meaningless requests
        config.plugins.delete('prefetch')

        // set svg-sprite-loader
        config.module
            .rule('svg')
            .exclude.add(resolve('src/assets/icons'))
            .end()
        config.module
            .rule('icons')
            .test(/\.svg$/)
            .include.add(resolve('src/assets/icons'))
            .end()
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
            .options({
                symbolId: 'icon-[name]'
            }).end()
    }
})
