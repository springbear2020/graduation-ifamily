<script>
export default {
  name: "SidebarItemLink",
  functional: true,
  props: {
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    }
  },
  render(h, context) {
    const {icon, title} = context.props
    const menuItems = []

    if (icon) {
      if (icon.includes('el-icon')) {
        menuItems.push(<i class={[icon, 'sub-el-icon']}/>)
      } else {
        menuItems.push(<svg-icon icon-class={icon}/>)
      }
    }

    if (title) {
      menuItems.push(<span slot='title'>{(title)}</span>)
    }

    return menuItems
  }
}
</script>

<style scoped>
.sub-el-icon {
  color: currentColor;
  width: 1em;
  height: 1em;
}
</style>
