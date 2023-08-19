import Vuex from 'vuex'
import Vue from "vue"
import user from "@/store/user";
import genealogy from "@/store/genealogy";
import loading from "@/store/loading";

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {user, genealogy, loading}
})
