import Vue from 'vue'
import App from './App.vue'
import router from '@/router'
import Vant from 'vant'
import 'vant/lib/index.css'
import './assets/iconfont/iconfont.css'
import footerTabSpan from './components/footer-tab-span'
import logoCharacter from './components/logo-character'
import familyPeopleTag from './components/family-people-tag'

Vue.use(Vant)
Vue.component('footer-tab-span', footerTabSpan)
Vue.component('logo-character', logoCharacter)
Vue.component('family-people-tag', familyPeopleTag)
Vue.config.productionTip = false

new Vue({
    render: h => h(App),
    router
}).$mount('#app')
