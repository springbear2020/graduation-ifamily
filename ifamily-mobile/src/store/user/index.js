import {reqLogin, reqGetCurrentUser, reqRefreshToken} from '@/api/user'
import {setToken, removeToken, setRefreshToken, removeRefreshToken, getRefreshToken} from "@/utils/auth";

export default {
    namespaced: true,
    state: {
        token: '',
        user: {}
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
        }
    },
    actions: {
        logout({commit}) {
            removeToken()
            removeRefreshToken()
            commit('CLEAR_STATE')
        },
        signIn({commit}, res) {
            setToken(res.accessToken)
            setRefreshToken(res.refreshToken)
            commit('SET_TOKEN', res.accessToken)
        },
        login({commit}, params) {
            return new Promise((resolve, reject) => {
                reqLogin(params).then(res => {
                    setToken(res.accessToken)
                    setRefreshToken(res.refreshToken)
                    commit('SET_TOKEN', res.accessToken);
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        },
        getUser({commit}) {
            return new Promise((resolve, reject) => {
                reqGetCurrentUser().then(user => {
                    commit('SET_USER', user)
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        },
        refreshUserToken({commit}) {
            return new Promise((resolve, reject) => {
                const token = getRefreshToken()
                if (token) {
                    // 请求认证服务器刷新用户令牌
                    reqRefreshToken(token).then(res => {
                        setToken(res.accessToken)
                        setRefreshToken(res.refreshToken)
                        commit('SET_TOKEN', res.accessToken)
                        resolve()
                    }).catch(err => {
                        reject(err)
                    })
                } else {
                    reject('empty refresh token')
                }
            })
        }
    }
}
