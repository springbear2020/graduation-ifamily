<template>
  <van-cell :title="title" :border="false">
    <template #label>
      <p>父亲：
        <people-tag :name="'冯学慧'" :sex="3" :right="true" :bottom="true" @click.native="$toast.success('查看人员信息')"/>
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '1')" v-if="addButton">添加
        </van-button>
      </p>
      <p>母亲：
        <people-tag :name="'刘纯洲'" :sex="1" :right="true" :bottom="true" @click.native="$toast.success('查看人员信息')"/>
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '2')" v-if="addButton">添加
        </van-button>
      </p>
      <p>配偶：
        <people-tag :name="'刘纯洲'" :sex="1" :right="true" :bottom="true" @click.native="$toast.success('查看人员信息')"/>
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '3')" v-if="addButton">添加
        </van-button>
      </p>
      <p>子女：
        <people-tag :name="'冯学慧'" :sex="3" :right="true" :bottom="true" @click.native="$toast.success('查看人员信息')"/>
        <people-tag :name="'刘纯洲'" :sex="1" :right="true" :bottom="true" @click.native="$toast.success('查看人员信息')"/>
        <people-tag :name="'冯学良'" :sex="0" :right="true" :bottom="true" @click.native="$toast.success('查看人员信息')"/>
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '4')" v-if="addButton">添加
        </van-button>
      </p>
      <p>同胞：
        <people-tag :name="'冯世元'" :sex="0" :right="true" @click.native="$toast.success('查看人员信息')"/>
        <van-button type="primary" plain size="mini" icon="add-o" @click="$emit('add-type', '5')" v-if="addButton">添加
        </van-button>
      </p>
    </template>
  </van-cell>
</template>

<script>
export default {
  name: "family-relationship",
  props: {
    addButton: {
      type: Boolean,
      default: true
    },
    title: {
      type: String,
      default: '家庭关系'
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