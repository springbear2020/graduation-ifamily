import {listGenealogiesOfUser} from "@/api/genealogy";

export default {
    namespaced: true,
    state: {
        genealogyList: []
    },
    getters: {
        defaultGenealogy(state) {
            // 过滤出用户家族列表中的默认家族
            return state.genealogyList.find(item => item.defaultGenealogy === 1) || {}
        }
    },
    mutations: {
        SET_GENEALOGY_LIST(state, list) {
            state.genealogyList = list
        },
        REMOVE_GENEALOGY_LIST(state) {
            state.genealogyList = []
        }
    },
    actions: {
        listGenealogyList({commit}) {
            return new Promise((resolve, reject) => {
                listGenealogiesOfUser().then(list => {
                    commit('SET_GENEALOGY_LIST', list)
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        }
    }
}
