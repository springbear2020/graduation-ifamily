import Vue from 'vue'
import App from '@/App.vue'
import router from '@/router'
import store from '@/store'

import Vant from 'vant'
import 'vant/lib/index.css'
// 解决 [Violation] Added non-passive event listener...
import 'default-passive-events'

import api from '@/api'
import '@/assets/iconfont/iconfont.css'
import logoPattern from '@/components/layout/logo-pattern.vue'

Vue.use(Vant)
Vue.component('logo-pattern', logoPattern)

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
    router,
    store,
    beforeCreate() {
        Vue.prototype.$api = api
    }
}).$mount('#app')
