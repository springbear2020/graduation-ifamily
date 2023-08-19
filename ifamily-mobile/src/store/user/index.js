import {login} from '@/api/user'
import {setToken, removeToken} from "@/utils/auth";

export default {
    namespaced: true,
    state: {
        token: ''
    },
    getters: {},
    mutations: {
        SET_TOKEN(state, data) {
            state.token = data
        },
        REMOVE_TOKEN(state) {
            state.token = ''
        }
    },
    actions: {
        login({commit}, params) {
            return new Promise((resolve, reject) => {
                login(params).then(token => {
                    setToken(token)
                    commit('SET_TOKEN', token);
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },
        logout({commit}) {
            removeToken()
            commit('REMOVE_TOKEN')
        }
    },
}
