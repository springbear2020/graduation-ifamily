import Vue from 'vue'
import App from './App.vue'
import router from '@/router'
import './assets/iconfont/iconfont.css'
import Vant from 'vant'
import 'vant/lib/index.css'

Vue.use(Vant)


import logoPattern from './components/logo-pattern'
import peopleTag from './components/people-tag'
import descTag from './components/desc-tag'
import socialMoments from './components/social-moments'

Vue.component('logo-pattern', logoPattern)
Vue.component('people-tag', peopleTag)
Vue.component('desc-tag', descTag)
Vue.component('social-moments', socialMoments)

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
    router
}).$mount('#app')
