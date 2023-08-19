<template>
  <div class="chart">
    <table v-if="tree.name" class="single">
      <tr>
        <td :colspan="Array.isArray(tree.children) ? tree.children.length * 2 : 1"
            :class="{'extend': Array.isArray(tree.children) && tree.children.length && tree.extend}">
          <div :class="{'couple': true, 'mate': tree.mates}">
            <!-- 当前节点 -->
            <div class="person" @click="$emit('click-node', tree)"
                 :class="[Array.isArray(tree.class) ? tree.class : [], `pid-${tree.id}`, tree.gender === 0 ? 'male' : 'female']">
              <div class="avatar">
                <img :src="tree.portrait || defaultPortrait(tree.gender)" alt="portrait"/>
              </div>
              <div class="name">{{ tree.name }}</div>
            </div>

            <!-- 伴侣节点 -->
            <template v-if="Array.isArray(tree.mates) && tree.mates.length">
              <div class="person" v-for="(mate, mateIndex) in tree.mates" :key="tree.name+mateIndex"
                   :class="[Array.isArray(mate.class) ? mate.class : [],`pid-${mate.id}`, mate.gender === 0 ? 'male' : 'female']"
                   @click="$emit('click-node', mate)">
                <div class="avatar">
                  <img :src="mate.portrait || defaultPortrait(mate.gender)" alt="portrait"/>
                </div>
                <div class="name">{{ mate.name }}</div>
              </div>
            </template>
          </div>

          <!-- 展开/折叠箭头 -->
          <div class="arrow" v-if="Array.isArray(tree.children) && tree.children.length"
               @click="tree.extend = !tree.extend; $emit('center-node', $event.target.previousSibling)">
          </div>
        </td>
      </tr>

      <!-- 递归生成孩子节点 -->
      <tr v-if="Array.isArray(tree.children) && tree.children.length && tree.extend">
        <td v-for="(children, index) in tree.children" :key="index" colspan="2" class="child">
          <family-tree-branch :tree="children" :single="tree.children.length === 1"
                              @click-node="$emit('click-node', $event)"
                              @center-node="$emit('center-node', $event)"
          />
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import {defaultPortrait} from "@/mixin/common-utils";

export default {
  name: "family-tree-branch",
  props: ['tree', 'single'],
  mixins: [defaultPortrait]
}
</script>

<style scoped>
.chart {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
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
  border-color: #ebedf0 #ebedf0 transparent transparent;
  transform: rotateZ(135deg);
  transform-origin: 50% 50% 0;
  transition: transform ease 300ms;
}

.arrow:hover:before {
  border-color: #969799 #969799 transparent transparent;
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
  border-left: 2px solid #ebedf0;
  transform: translate3d(-1px, 0, 0)
}

.child::before {
  content: "";
  position: absolute;
  left: 50%;
  bottom: 100%;
  height: 15px;
  border-left: 2px solid #ebedf0;
  transform: translate3d(-1px, 0, 0)
}

.child::after {
  content: "";
  position: absolute;
  left: 0;
  right: 0;
  top: -15px;
  border-top: 2px solid #ebedf0;
}

.child:first-child:before, .child:last-child:before {
  display: none;
}

.child:first-child:after {
  left: 50%;
  height: 15px;
  border: 2px solid;
  border-color: #ebedf0 transparent transparent #ebedf0;
  border-radius: 6px 0 0 0;
  transform: translate3d(1px, 0, 0)
}

.child:last-child:after {
  right: 50%;
  height: 15px;
  border: 2px solid;
  border-color: #ebedf0 #ebedf0 transparent transparent;
  border-radius: 0 6px 0 0;
  transform: translate3d(-1px, 0, 0)
}

.child:first-child.child:last-child::after {
  left: auto;
  border-radius: 0;
  border-color: transparent #ebedf0 transparent transparent;
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
  transition: all 1s;
  cursor: pointer;
}

.couple .person .avatar {
  display: block;
  width: 4em;
  height: 4em;
  margin: auto;
  overflow: hidden;
  background: white;
  border: 2px solid #ebedf0;
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
  border-top: 2px solid #ebedf0;
  z-index: 1;
}

.couple.mate .person:last-child {
  margin-left: 0;
}

.single {
  margin: auto;
}

.male {
  color: #007bff;
}

.female {
  color: #e83e8c
}
</style>
