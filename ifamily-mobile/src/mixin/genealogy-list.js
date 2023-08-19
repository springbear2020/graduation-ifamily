export const genealogyList = {
    data() {
        return {
            hasGenealogy: false
        }
    },
    mounted() {
        // 未查询过家族列表信息则进行查询
        const list = this.$store.state.genealogy.genealogyList
        if (!list || list.length <= 0) {
            this.$store.dispatch('genealogy/listGenealogyList').then(() => {
                this.hasGenealogy = true
            }).catch(() => {
                this.hasGenealogy = false
            })
        }
    }
}
