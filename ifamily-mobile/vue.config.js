const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    publicPath: './',
    transpileDependencies: true,
    lintOnSave: false,
    productionSourceMap: false,
    devServer: {
        port: 9528,
        proxy: {
            '/ifamily-gateway-api': {
                target: 'http://localhost:8820',
                pathRewrite: {'^/ifamily-gateway-api': ''}
            },
        },
    },
})
