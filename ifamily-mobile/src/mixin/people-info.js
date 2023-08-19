import {ImagePreview} from "vant";
import dayjs from "dayjs";

export const peopleInfo = {
    data() {
        return {
            type: '0',
            emptyShow: false,
            people: {},
            showForm: false
        }
    },
    computed: {
        me() {
            return this.people.me ? this.people.me : {}
        },
        defaultPortrait() {
            let portrait = this.me.portrait
            if (!portrait || portrait.length === 0) {
                // 根据性别决定展示的默认肖像
                portrait = this.me.gender === 0 ? 'img/male.jpg' : 'img/female.jpg'
            }
            return portrait
        },
        age() {
            if (!this.me.birthdate) {
                return ''
            }
            const date = this.alive ? dayjs() : dayjs(this.me.deathDate);
            return date.diff(dayjs(this.me.birthdate), 'year');
        },
        alive() {
            return !this.me.deathDate
        },
    },
    methods: {
        previewImage(url) {
            let images = []
            images.push(url)
            ImagePreview({images})
        },
        initPeople() {
            // 路由路径中读取 pid
            const pid = this.$route.query.pid
            if (!pid) {
                this.emptyShow = true
                return
            }
            this.getPeople(pid)
        },
        getPeople(pid) {
            // 根据 peopleId 查询人员信息
            this.$api.people.getGenealogyPeopleDetails(pid).then(people => {
                this.people = people
                this.emptyShow = false
            }).catch(err => {
                this.emptyShow = true
                this.$toast({message: err.data || err.desc, position: 'bottom'})
            })
        },
        viewFamilyMember(pid) {
            // 修改路由 pid 参数
            const {...query} = this.$route.query;
            this.$router.replace({query: {...query, pid}});
            this.getPeople(pid)
            this.showForm = false
        }
    }
}
