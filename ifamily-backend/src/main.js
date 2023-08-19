import Vue from 'vue'
import App from './App.vue'
import router from '@/router'
import store from '@/store'

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

// a modern alternative to CSS resets
import 'normalize.css/normalize.css'
// cookie operation utils
import Cookies from 'js-cookie'
// solution for [Violation] Added non-passive event listener...
import 'default-passive-events'

// global css
import '@/styles/index.scss'

// customize js
import '@/assets/icons/index.js'
import './permission.js'
import './utils/error-log.js'
import api from '@/api/index.js'

Vue.use(Element, {size: Cookies.get('size') || 'medium'});

Vue.config.productionTip = false

new Vue({
    el: '#app',
    router,
    store,
    beforeCreate() {
        Vue.prototype.$api = api
    },
    render: h => h(App)
})
