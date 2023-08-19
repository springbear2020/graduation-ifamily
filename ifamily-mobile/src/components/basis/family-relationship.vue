<template>
  <van-cell :title="title" :border="border">
    <template #label>
      <p><span class="relatives">生父：</span>
        <people-tag v-if="relation.father" :name="relation.father.name" :sex="relation.father.gender"
                    @click.native="$emit('view-family-member', relation.father.id)"/>
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '1')"
                    v-if="addButton && !relation.father && !hasHusband">添加
        </van-button>
      </p>

      <p><span class="relatives">生母：</span>
        <people-tag v-if="relation.mother" :name="relation.mother.name" :sex="relation.mother.gender"
                    @click.native="$emit('view-family-member', relation.mother.id)"/>
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '2')"
                    v-if="addButton && relation.father && !relation.mother && !hasHusband">添加
        </van-button>
      </p>

      <p><span class="relatives">配偶：</span>
        <people-tag v-if="relation.mates" v-for="item in relation.mates" :key="item.id"
                    :name="item.name" :sex="item.gender" @click.native="$emit('view-family-member', item.id)"
        />
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '3')"
                    v-if="addButton && !hasHusband">添加
        </van-button>
      </p>

      <p><span class="relatives">子女：</span>
        <people-tag v-if="relation.children" v-for="item in relation.children" :key="item.id"
                    :name="item.name" :sex="item.gender" @click.native="$emit('view-family-member', item.id)"/>
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '4')"
                    v-if="addButton && (!hasHusband && relation.mates.length >= 1 || hasHusband && relation.mates.length === 1)">
          添加
        </van-button>
      </p>

      <p><span class="relatives">同胞：</span>
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
    },
    border: {
      type: Boolean,
      default: true
    }
  }
}
</script>

<style scoped>
.van-cell__title button {
  height: 20px;
  position: absolute;
}

.van-cell__label p {
  margin: 8px 0;
}

.relatives {
  height: 20px;
  width: 40px;
}
</style>
