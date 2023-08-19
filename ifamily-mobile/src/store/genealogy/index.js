import {listGenealogiesOfUser, memberTreeOfGenealogy} from "@/api/genealogy";

export default {
    namespaced: true,
    state: {
        genealogyList: [],
        tree: {}
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
        CLEAR_STATE(state) {
            state.genealogyList = []
            state.tree = {}
        },
        SET_GENEALOGY_TREE(state, tree) {
            state.tree = tree
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
        },
        logout({commit}) {
            commit('CLEAR_STATE')
        },
        getGenealogyTree({commit}) {
            return new Promise((resolve, reject) => {
                memberTreeOfGenealogy().then(tree => {
                    // 为祖先节点添加 root-node 样式名称使得其居中展示
                    tree.class = ['root-node']
                    commit('SET_GENEALOGY_TREE', tree)
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        }
    }
}
