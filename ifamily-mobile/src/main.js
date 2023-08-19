import Vue from 'vue'
import App from './App.vue'
import router from '@/router'
import Vant from 'vant'
import 'vant/lib/index.css'
import './assets/iconfont/iconfont.css'
import logoPattern from '@/components/layout/logo-pattern'
import sexTag from '@/components/tag/sex-tag'
import descTag from '@/components/tag/desc-tag'
import peopleTag from '@/components/tag/people-tag'
import imageList from '@/components/image-list'
import portraitDesc from '@/components/portrait-desc'
import socialMoments from '@/components/social-moments'

Vue.component('logo-pattern', logoPattern)
Vue.component('sex-tag', sexTag)
Vue.component('desc-tag', descTag)
Vue.component('people-tag', peopleTag)
Vue.component('image-list', imageList)
Vue.component('portrait-desc', portraitDesc)
Vue.component('social-moments', socialMoments)

Vue.use(Vant)

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
    router
}).$mount('#app')
