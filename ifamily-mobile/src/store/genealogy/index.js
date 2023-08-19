import {listGenealogiesOfUser} from "@/api/genealogy";
import {memberTreeOfGenealogy} from "@/api/genealogy/member";
import {currentUserPeople} from "@/api/genealogy/people";

export default {
    namespaced: true,
    state: {
        genealogyList: [],
        tree: {},
        userPeople: {}
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
            state.userPeople = {}
        },
        SET_GENEALOGY_TREE(state, tree) {
            state.tree = tree
        },
        SET_USER_PEOPLE(state, people) {
            state.userPeople = people
        },
        REMOVE_TREE(state) {
            state.tree = {}
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
        },
        getPeopleOfUser({commit}) {
            return new Promise((resolve, reject) => {
                currentUserPeople().then(people => {
                    commit('SET_USER_PEOPLE', people)
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        }
    }
}
