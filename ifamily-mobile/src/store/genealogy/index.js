import {listGenealogiesOfUser} from "@/api/genealogy";
import {memberTreeOfGenealogy, listGenerationMember} from "@/api/genealogy/member";
import {currentUserPeople} from "@/api/genealogy/people";

export default {
    namespaced: true,
    state: {
        // 家族列表
        genealogyList: [],
        // 家族树谱
        tree: {},
        // 我的资料
        userPeople: {},
        // 世代成员
        memberMap: {
            generations: [],
            members: {}
        }
    },
    getters: {
        defaultGenealogy(state) {
            // 过滤出用户家族列表中的默认家族
            return state.genealogyList.find(item => item.defaultGenealogy === 1) || {}
        }
    },
    mutations: {
        CLEAR_STATE(state) {
            state.genealogyList = []
            state.tree = {}
            state.userPeople = {}
            state.memberMap = {}
        },
        SET_GENEALOGY_LIST(state, list) {
            state.genealogyList = list
        },
        SET_GENEALOGY_TREE(state, tree) {
            state.tree = tree
        },
        SET_USER_PEOPLE(state, people) {
            state.userPeople = people
        },
        SET_GENERATION_MEMBER(state, memberMap) {
            state.memberMap = memberMap
        },
    },
    actions: {
        logout({commit}) {
            commit('CLEAR_STATE')
        },
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
        getGenealogyTree({commit}) {
            return new Promise((resolve, reject) => {
                memberTreeOfGenealogy().then(tree => {
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
                currentUserPeople().then(people => {
                    commit('SET_USER_PEOPLE', people)
                    resolve()
                }).catch(err => {
                    reject(err)
                })
            })
        },
        generationMembers({commit}, params) {
            return new Promise((resolve, reject) => {
                listGenerationMember(params).then(memberMap => {
                    commit("SET_GENERATION_MEMBER", memberMap)
                }).catch(err => {
                    reject(err)
                })
            })
        }
    }
}
