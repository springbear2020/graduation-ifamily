<template>
  <van-cell :title="title" :border="false">
    <template #label>
      <p>生父：
        <people-tag v-if="relation.father" :name="relation.father.name" :sex="relation.father.gender"
                    @click.native="$emit('view-family-member', relation.father.id)"/>
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '1')"
                    v-if="addButton && !relation.father && !hasHusband">添加
        </van-button>
      </p>

      <p>生母：
        <people-tag v-if="relation.mother" :name="relation.mother.name" :sex="relation.mother.gender"
                    @click.native="$emit('view-family-member', relation.mother.id)"/>
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '2')"
                    v-if="addButton && relation.father && !relation.mother && !hasHusband">添加
        </van-button>
      </p>

      <p>配偶：
        <people-tag v-if="relation.mates" v-for="item in relation.mates" :key="item.id"
                    :name="item.name" :sex="item.gender" @click.native="$emit('view-family-member', item.id)"
        />
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '3')"
                    v-if="addButton && !hasHusband">添加
        </van-button>
      </p>

      <p>子女：
        <people-tag v-if="relation.children" v-for="item in relation.children" :key="item.id"
                    :name="item.name" :sex="item.gender" @click.native="$emit('view-family-member', item.id)"/>
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '4')"
                    v-if="addButton && (!hasHusband && relation.mates.length >= 1 || hasHusband && relation.mates.length === 1)">
          添加
        </van-button>
      </p>

      <p>同胞：
        <people-tag v-if="relation.compatriots" v-for="item in relation.compatriots" :key="item.id"
                    :name="item.name" :sex="item.gender" @click.native="$emit('view-family-member', item.id)"/>
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '5')"
                    v-if="addButton && relation.father && relation.mother && !hasHusband">添加
        </van-button>
      </p>
    </template>
  </van-cell>
</template>

<script>
import PeopleTag from '@/components/tag/people-tag'

export default {
  name: "family-relationship",
  components: {PeopleTag},
  props: {
    addButton: {
      type: Boolean,
      default: true
    },
    title: {
      type: String,
      default: '家庭关系'
    },
    relation: {
      type: Object,
      required: true
    },
    hasHusband: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style scoped>
.van-cell__label p {
  height: 20px;
}

.van-cell__title button {
  height: 20px;
  position: absolute;
}
</style>
