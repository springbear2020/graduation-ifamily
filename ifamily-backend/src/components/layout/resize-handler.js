import store from '@/store'

const {body} = document
// refer to Bootstrap's responsive design
const mobileWidth = 992

export default {
    watch: {
        $route() {
            if (this.device === 'mobile' && this.sidebar.opened) {
                store.dispatch('app/closeSideBar', {withoutAnimation: false})
            }
        }
    },
    beforeMount() {
        window.addEventListener('resize', this.$_resizeHandler)
    },
    beforeDestroy() {
        window.removeEventListener('resize', this.$_resizeHandler)
    },
    mounted() {
        const isMobile = this.$_isMobile()
        if (isMobile) {
            store.dispatch('app/toggleDevice', 'mobile')
            store.dispatch('app/closeSideBar', {withoutAnimation: true})
        }
    },
    methods: {
        // use $_ for mixins properties, details see https://vuejs.org/v2/style-guide/index.html#Private-property-names-essential
        $_isMobile() {
            const rect = body.getBoundingClientRect()
            return rect.width - 1 < mobileWidth
        },
        $_resizeHandler() {
            if (!document.hidden) {
                const isMobile = this.$_isMobile()
                store.dispatch('app/toggleDevice', isMobile ? 'mobile' : 'desktop')

                if (isMobile) {
                    store.dispatch('app/closeSideBar', {withoutAnimation: true})
                }
            }
        }
    }
}
