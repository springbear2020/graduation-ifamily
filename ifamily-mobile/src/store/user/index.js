import {login, getUser} from '@/api/user'
import {setToken, removeToken} from "@/utils/auth";

export default {
    namespaced: true,
    state: {
        token: '',
        user: {},
    },
    getters: {},
    mutations: {
        CLEAR_STATE(state) {
            state.token = ''
            state.user = {}
        },
        SET_TOKEN(state, token) {
            state.token = token
        },
        SET_USER(state, user) {
            state.user = user
        },
    },
    actions: {
        logout({commit}) {
            removeToken()
            commit('CLEAR_STATE')
        },
        login({commit}, params) {
            return new Promise((resolve, reject) => {
                login(params).then(token => {
                    setToken(token)
                    commit('SET_TOKEN', token);
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        },
        getUser({commit}) {
            return new Promise((resolve, reject) => {
                getUser().then(user => {
                    commit('SET_USER', user)
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        },
        signIn({commit}, token) {
            setToken(token)
            commit('SET_TOKEN', token)
        }
    }
}
