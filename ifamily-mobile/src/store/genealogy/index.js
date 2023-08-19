import {reqListGenealogies} from "@/api/genealogy";
import {reqMemberTreeOfGenealogy} from "@/api/genealogy/member";
import {reqCurrentUserPeople} from "@/api/genealogy/people";

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
        updateGenealogyStore({commit}) {
            // FIXME Used by 添加亲人、编辑成员、移除成员
            return new Promise((resolve, reject) => {
                commit('CLEAR_STATE')
                // 重新查询家族列表信息
                reqListGenealogies().then(list => {
                    commit('SET_GENEALOGY_LIST', list)
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        },
        listGenealogyList({commit}) {
            return new Promise((resolve, reject) => {
                reqListGenealogies().then(list => {
                    commit('SET_GENEALOGY_LIST', list)
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        },
        getGenealogyTree({commit}) {
            return new Promise((resolve, reject) => {
                reqMemberTreeOfGenealogy().then(tree => {
                    // 为祖先节点添加 root-node 样式名称使得初始化页面时其居中展示
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
                reqCurrentUserPeople().then(people => {
                    commit('SET_USER_PEOPLE', people)
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        }
    }
}
