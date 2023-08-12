import Vue from 'vue'
import App from './App.vue'
import router from '@/router'
import Vant from 'vant'
import 'vant/lib/index.css'
import './assets/iconfont/iconfont.css'
import logoPattern from './components/logo-pattern'
import peopleTag from './components/people-tag'
import descTag from './components/desc-tag'

Vue.use(Vant)

Vue.component('logo-pattern', logoPattern)
Vue.component('people-tag', peopleTag)
Vue.component('desc-tag', descTag)

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
    router
}).$mount('#app')
