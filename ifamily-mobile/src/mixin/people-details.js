import dayjs from "dayjs";

export const peopleDetails = {
    data() {
        return {
            emptyShow: false,
            people: {},
            // Used by 添加亲人
            showForm: false
        }
    },
    computed: {
        me() {
            return this.people.me || {}
        },
        age() {
            if (!this.me.birthdate) {
                return '不明'
            }

            const date = this.alive ? dayjs() : dayjs(this.me.deathDate);
            return date.diff(dayjs(this.me.birthdate), 'year');
        },
        alive() {
            return !this.me.deathDate
        },
    },
    methods: {
        initPeople() {
            const pid = this.$route.query.pid
            if (!pid) {
                this.emptyShow = true
                return
            }

            this.getPeople(pid)
        },
        getPeople(pid) {
            this.$api.genealogy.getPeopleDetails({peopleId: pid}).then(people => {
                this.people = people
                this.emptyShow = false
            }).catch(msg => {
                this.emptyShow = true
                this.$toast({message: msg, position: 'bottom'})
            })
        },
        viewFamilyMember(pid) {
            // 修改路由路径 pid 参数的值
            const {...query} = this.$route.query;
            this.$router.replace({query: {...query, pid}}).then(() => {
                this.getPeople(pid)
                this.showForm = false
            }).catch(() => {
            })
        }
    }
}
