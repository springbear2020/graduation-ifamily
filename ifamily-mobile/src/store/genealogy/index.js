import {listGenealogies, getMemberTree, getCurrentUserPeople} from "@/api/genealogy";

export default {
    namespaced: true,
    state: {
        // 家族列表
        genealogyList: [],
        // 家族树谱
        tree: {},
        // 我的资料
        userPeople: {},
    },
    getters: {
        // 过滤出用户家族列表中的默认家族
        defaultGenealogy(state) {
            return state.genealogyList.find(item => item.defaultGenealogy === 1) || {}
        }
    },
    mutations: {
        CLEAR_STATE(state) {
            state.genealogyList = []
            state.tree = {}
            state.userPeople = {}
        },
        SET_GENEALOGY_LIST(state, list) {
            state.genealogyList = list
        },
        SET_GENEALOGY_TREE(state, tree) {
            state.tree = tree
        },
        SET_USER_PEOPLE(state, people) {
            state.userPeople = people
        }
    },
    actions: {
        updateGenealogyStore({dispatch, commit}) {
            return new Promise(() => {
                commit('CLEAR_STATE')
                // 查询最新的家族列表信息
                dispatch('genealogies')
            })
        },
        genealogies({commit}) {
            return new Promise((resolve, reject) => {
                listGenealogies().then(list => {
                    commit('SET_GENEALOGY_LIST', list)
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        },
        memberTree({commit}) {
            return new Promise((resolve, reject) => {
                getMemberTree().then(tree => {
                    // 为祖先节点添加 root-node 样式名称使得初始化页面时其居中展示
                    tree.class = ['root-node']
                    commit('SET_GENEALOGY_TREE', tree)
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        },
        genealogyUserPeople({commit}) {
            return new Promise((resolve, reject) => {
                getCurrentUserPeople().then(people => {
                    commit('SET_USER_PEOPLE', people)
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        }
    }
}
