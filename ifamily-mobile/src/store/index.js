import Vuex from 'vuex'
import Vue from "vue"
import user from "@/store/user";

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {user}
})
