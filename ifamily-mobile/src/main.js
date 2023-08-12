import Vue from 'vue'
import App from './App.vue'
import router from '@/router'
import './assets/iconfont/iconfont.css'
import Vant from 'vant'
import 'vant/lib/index.css'
import logoPattern from './components/logo-pattern'
import peopleTag from './components/people-tag'
import descTag from './components/desc-tag'
import socialMoments from './components/social-moments'
import imageList from './components/image-list'
import messageBox from './components/message-box'
import portraitDesc from './components/portrait-desc'

Vue.use(Vant)
Vue.component('logo-pattern', logoPattern)
Vue.component('people-tag', peopleTag)
Vue.component('desc-tag', descTag)
Vue.component('social-moments', socialMoments)
Vue.component('image-list', imageList)
Vue.component('message-box', messageBox)
Vue.component('portrait-desc', portraitDesc)

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
    router
}).$mount('#app')
