import {getToken, setToken, removeToken, setRefreshToken, getRefreshToken, removeRefreshToken} from '@/utils/auth'
import {login, getCurrentAdminInfo, refreshUserToken} from "@/api/user"
import {resetRouter} from "@/router";

const state = {
    token: getToken(),
    userInfo: {
        uid: '',
        username: '',
        nickname: '',
        avatar: '',
        signature: ''
    },
    menus: []
}

const mutations = {
    SET_TOKEN: (state, token) => {
        state.token = token
    },
    SET_USER_INFO: (state, userInfo) => {
        Object.assign(state.userInfo, userInfo)
    },
    SET_MENUS: (state, menus) => {
        state.menus = menus
    }
}

const actions = {
    // user login
    login({commit}, formData) {
        return new Promise((resolve, reject) => {
            login(formData).then(res => {
                const {accessToken, refreshToken} = res
                commit('SET_TOKEN', accessToken)
                setToken(accessToken)
                setRefreshToken(refreshToken)
                resolve()
            }).catch(err => {
                reject(err)
            })
        })
    },

    // get user info
    getInfo({commit}) {
        return new Promise((resolve, reject) => {
            getCurrentAdminInfo().then(res => {
                const {id, username, avatar, nickname, signature, menus} = res
                commit('SET_USER_INFO', {uid: id, username, avatar, nickname, signature})
                commit('SET_MENUS', menus)
                resolve(res)
            }).catch(err => {
                // invalid user token, remove it
                commit('SET_TOKEN', '')
                removeToken()
                reject(err)
            })
        })
    },

    // user logout
    logout({commit, dispatch}) {
        return new Promise((resolve) => {
            commit('SET_TOKEN', '')
            removeToken()
            removeRefreshToken()
            // reset the routes exists
            resetRouter()
            // reset visited views and cached views
            dispatch('tagsView/delAllViews', null, {root: true})
            resolve()
        })
    },

    // refresh the user token though the `refreshToken` saved in the local storage
    refreshUserToken({commit}) {
        return new Promise((resolve, reject) => {
            const refreshToken = getRefreshToken()
            if (refreshToken) {
                refreshUserToken(refreshToken).then(res => {
                    const {accessToken, refreshToken} = res
                    commit('SET_TOKEN', accessToken)
                    setToken(accessToken)
                    setRefreshToken(refreshToken)
                    resolve()
                }).catch(err => {
                    // invalid refresh token, remove it
                    removeRefreshToken()
                    reject(err)
                })
            } else {
                reject('empty refresh token')
            }
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
