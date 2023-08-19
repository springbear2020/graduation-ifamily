import Vue from 'vue'
import App from './App.vue'
import router from '@/router'
import store from '@/store'

import Cookies from 'js-cookie'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'normalize.css/normalize.css'

import '@/styles/index.scss'
import '@/assets/icons'
import './permission'
import './utils/error-log'

Vue.use(Element, {size: Cookies.get('size') || 'medium'});

Vue.config.productionTip = false

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
})
