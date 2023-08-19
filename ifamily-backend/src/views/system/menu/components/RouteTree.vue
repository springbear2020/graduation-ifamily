<template>
  <div>
    <el-tree
        ref="tree"
        :data="treeRoutes"
        :props="defaultProps"
        show-checkbox
        node-key="path"
    />
  </div>
</template>

<script>
import path from "path";
import {findMatched} from "@/utils/ant-matcher";
import {asyncRoutes} from "@/router";

export default {
  name: "RouteTree",
  props: {
    patterns: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      // tree component
      treeRoutes: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      checkedNodes: []
    }
  },
  mounted() {
    this.treeRoutes = this.simplifyRoutes(asyncRoutes)
    this.checkedNodes = this.generateTreeCheckedArr(this.treeRoutes)
  },
  methods: {
    checkTreeNodes() {
      // checked some routes which disabled status is true
      this.$nextTick(() => {
        this.$refs.tree.setCheckedNodes(this.checkedNodes)
      })
    },

    // Reshape the routes structure so that it looks the same as the sidebar
    simplifyRoutes(routes, basePath = '/') {
      const resultList = []

      for (let route of routes) {
        // skip some route
        if (route.hidden) {
          continue
        }

        const onlyOneShowingChild = this.onlyOneShowingChild(route.children, route)

        if (route.children && onlyOneShowingChild && !route.alwaysShow) {
          route = onlyOneShowingChild
        }

        const data = {
          path: path.resolve(basePath, route.path),
          title: route.meta && route.meta.title + ' - ' + path.resolve(basePath, route.path)
        }
        // disabled the current tree node if matched successfully
        const matchedPatterns = findMatched(this.patterns, data.path)
        if (matchedPatterns && matchedPatterns.length > 0) {
          data.disabled = true
          data.title = data.title + '：' + `${matchedPatterns.join('、')}`
        }

        // recursive child routes
        if (route.children) {
          data.children = this.simplifyRoutes(route.children, data.path);
        }

        resultList.push(data)
      }

      return resultList
    },

    // reference: src/view/layout/components/Sidebar/SidebarItem.vue
    onlyOneShowingChild(children = [], parent) {
      let onlyOneChild = null
      const showingChildren = children.filter(item => !item.hidden)

      // When there is only one child route, the child route is displayed by default
      if (showingChildren.length === 1) {
        onlyOneChild = showingChildren[0]
        onlyOneChild.path = path.resolve(parent.path, onlyOneChild.path)
        return onlyOneChild
      }

      // Show parent if there are no child route to display
      if (showingChildren.length === 0) {
        onlyOneChild = {...parent, path: '', noShowingChildren: true}
        return onlyOneChild
      }

      return false
    },

    generateTreeCheckedArr(routes) {
      let checkedNodes = []
      routes.forEach(route => {
        if (route.disabled) {
          checkedNodes.push(route)

          if (route.children) {
            const temp = this.generateTreeCheckedArr(route.children)
            if (temp.length > 0) {
              checkedNodes = [...checkedNodes, ...temp]
            }
          }
        }
      })

      return checkedNodes
    }

  }
}
</script>
