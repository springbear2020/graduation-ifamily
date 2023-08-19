import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'

import app from '@/store/modules/app'
import errorLog from '@/store/modules/error-log'
import permission from '@/store/modules/permission'
import settings from '@/store/modules/settings'
import tagsView from '@/store/modules/tags-view'
import user from '@/store/modules/user'

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {app, errorLog, permission, settings, tagsView, user},
    getters
})

export default store
