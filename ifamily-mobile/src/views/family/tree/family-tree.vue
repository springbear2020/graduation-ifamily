<template>
  <div class="chart">
    <table v-if="jsonData.name" class="single">
      <tr>
        <!-- 当前父节点存在孩子节点则合并单元格个数为孩子个数的 2 倍，否则为 1 -->
        <td :colspan="Array.isArray(jsonData.children) ? jsonData.children.length * 2 : 1"
            :class="{'extend': Array.isArray(jsonData.children) && jsonData.children.length && jsonData.extend}">
          <div :class="{'couple': true, 'mate': jsonData.mate}">
            <!-- 当前节点 -->
            <div class="person" :class="Array.isArray(jsonData.class) ? jsonData.class : []"
                 @click="$emit('click-node', jsonData)">
              <div class="avatar">
                <img :src="jsonData.imgUrl" alt="couple img"/>
              </div>
              <div class="name">{{ jsonData.name }}</div>
            </div>

            <!-- 当前节点的伴侣节点 -->
            <template v-if="Array.isArray(jsonData.mate) && jsonData.mate.length">
              <div class="person" v-for="(mate, mateIndex) in jsonData.mate" :key="jsonData.name+mateIndex"
                   :class="Array.isArray(mate.class) ? mate.class : []" @click="$emit('click-node', mate)">
                <div class="avatar">
                  <img :src="mate.imgUrl" alt="mate img"/>
                </div>
                <div class="name">{{ mate.name }}</div>
              </div>
            </template>
          </div>

          <!-- 子节点展开/折叠箭头 -->
          <div class="arrow" v-if="Array.isArray(jsonData.children) && jsonData.children.length"
               @click="jsonData.extend = !jsonData.extend;"></div>
        </td>
      </tr>

      <!-- 如果当前父节点存在孩子节点且允许展开就生成下一代节点 -->
      <tr v-if="Array.isArray(jsonData.children) && jsonData.children.length && jsonData.extend">
        <td v-for="(children, index) in jsonData.children" :key="index" colspan="2" class="child">
          <!-- 循环递归生成子代节点 -->
          <FamilyTreeChart :jsonData="children" @click-node="$emit('click-node', $event)"
                           :single="jsonData.children.length === 1"/>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: "FamilyTreeChart",
  props: ["jsonData", "single"],
}
</script>

<style scoped>
.chart {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

table {
  border-collapse: separate !important;
  border-spacing: 0 !important;
}

td {
  position: relative;
  vertical-align: top;
  padding: 0 0 50px 0;
  text-align: center;
}

.arrow {
  position: absolute;
  left: 50%;
  bottom: 30px;
  width: 10px;
  height: 10px;
  padding: 10px;
  transform: translate3d(-15px, 0, 0);
  cursor: pointer;
}

.arrow:before {
  content: '';
  display: block;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  border: 2px solid;
  border-color: #ccc #ccc transparent transparent;
  transform: rotateZ(135deg);
  transform-origin: 50% 50% 0;
  transition: transform ease 300ms;
}

.arrow:hover:before {
  border-color: #333 #333 transparent transparent;
}

.extend .arrow:before {
  transform: rotateZ(-45deg);
}

.extend::after {
  content: "";
  position: absolute;
  left: 50%;
  bottom: 15px;
  height: 15px;
  border-left: 2px solid #ccc;
  transform: translate3d(-1px, 0, 0)
}

.child::before {
  content: "";
  position: absolute;
  left: 50%;
  bottom: 100%;
  height: 15px;
  border-left: 2px solid #ccc;
  transform: translate3d(-1px, 0, 0)
}

.child::after {
  content: "";
  position: absolute;
  left: 0;
  right: 0;
  top: -15px;
  border-top: 2px solid #ccc;
}

.child:first-child:before, .child:last-child:before {
  display: none;
}

.child:first-child:after {
  left: 50%;
  height: 15px;
  border: 2px solid;
  border-color: #ccc transparent transparent #ccc;
  border-radius: 6px 0 0 0;
  transform: translate3d(1px, 0, 0)
}

.child:last-child:after {
  right: 50%;
  height: 15px;
  border: 2px solid;
  border-color: #ccc #ccc transparent transparent;
  border-radius: 0 6px 0 0;
  transform: translate3d(-1px, 0, 0)
}

.child:first-child.child:last-child::after {
  left: auto;
  border-radius: 0;
  border-color: transparent #ccc transparent transparent;
  transform: translate3d(1px, 0, 0)
}

.couple {
  position: relative;
  display: inline-block;
  margin: 0 1em;
  box-sizing: border-box;
  text-align: center;
  white-space: nowrap;
}

.couple .person {
  position: relative;
  display: inline-block;
  z-index: 2;
  width: 6em;
  overflow: hidden;
  white-space: normal;
}

.couple .person .avatar {
  display: block;
  width: 4em;
  height: 4em;
  margin: auto;
  overflow: hidden;
  background: #fff;
  border: 2px solid #ccc;
  box-sizing: border-box;
  border-radius: 2em;
}

.couple .person .avatar img {
  width: 100%;
  height: 100%;
}

.couple .person .name {
  height: 2em;
  line-height: 2em;
  overflow: hidden;
  width: 100%;
}

.couple.mate::after {
  content: "";
  position: absolute;
  left: 2em;
  right: 2em;
  top: 2em;
  border-top: 2px solid #ccc;
  z-index: 1;
}

.couple.mate .person:last-child {
  margin-left: 0;
}

.single {
  margin: auto;
}

.root-node {
  margin-top: 50px;
}
</style>
