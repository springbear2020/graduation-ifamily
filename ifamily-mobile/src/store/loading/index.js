export default {
    namespaced: true,
    state: {
        isLoading: false
    },
    getters: {},
    mutations: {
        LOADING(state) {
            state.isLoading = true
        },
        CANCEL(state) {
            state.isLoading = false
        }
    },
    actions: {}
}
