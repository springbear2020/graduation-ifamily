import Vue from 'vue'
import App from './App.vue'
import router from '@/router'
import Vant from 'vant'
import 'vant/lib/index.css'
import './assets/iconfont/iconfont.css'

Vue.use(Vant)

Vue.component('logo-pattern', () => import('@/components/logo-pattern'))
Vue.component('people-tag', () => import('@/components/people-tag'))
Vue.component('desc-tag', () => import('@/components/desc-tag'))
Vue.component('social-moments', () => import('@/components/social-moments'))
Vue.component('image-list', () => import('@/components/image-list'))
Vue.component('message-box', () => import('@/components/message-box'))
Vue.component('portrait-desc', () => import('@/components/portrait-desc'))
Vue.component('sex-tag', () => import('@/components/sex-tag'))

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
    router
}).$mount('#app')
