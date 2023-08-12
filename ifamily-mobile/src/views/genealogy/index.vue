<template>
  <div>
    <van-nav-bar
        title="家族"
    >
      <template #right>
        <van-icon name="apps-o" size="20"/>
      </template>
    </van-nav-bar>

    <FamilyTreeChart :jsonData="jsonData" @click-node="clickNode"/>
  </div>
</template>

<script>
import FamilyTreeChart from '@/views/genealogy/tree/family-tree-chart';

export default {
  name: "index",
  components: {FamilyTreeChart},
  data() {
    return {
      jsonData: {
        name: '贾太公',
        children: [
          {
            name: '贾演',
            children: [
              {
                name: '贾代化',
                children: [
                  {name: '贾敷'},
                  {
                    name: '贾敬',
                    children: [
                      {
                        name: '贾珍',
                        imgUrl: "https://img01.yzcdn.cn/vant/cat.jpeg",
                        children: [
                          {
                            name: '贾蓉',
                            mate: [
                              {name: '秦可卿'}
                            ]
                          }
                        ],
                        mate: [
                          {name: '尤氏'},
                        ],
                      },
                      {name: '贾惜春'},
                    ]
                  },
                ]
              },
            ]
          },
          {
            name: '贾源',
            children: [
              {
                name: '贾代善',
                imgUrl: "https://img01.yzcdn.cn/vant/cat.jpeg",
                mate: [
                  {name: '史太君'}
                ],
                children: [
                  {
                    name: '贾赦',
                    children: [
                      {
                        name: '贾琏',
                        imgUrl: "https://img01.yzcdn.cn/vant/cat.jpeg",
                        mate: [
                          {name: '王熙凤'},
                          {name: '尤二姐'}
                        ],
                        children: [
                          {name: '巧姐'},
                        ]
                      },
                      {name: '贾迎春'},
                    ]
                  },
                  {
                    name: '贾政',
                    imgUrl: "https://img01.yzcdn.cn/vant/cat.jpeg",
                    children: [
                      {
                        name: '贾宝玉',
                        imgUrl: "https://img01.yzcdn.cn/vant/cat.jpeg",
                        mate: [
                          {name: '薛宝钗'}
                        ],
                      },
                      {name: '贾元春'},
                      {name: '贾珠'},
                      {name: '贾探春'},
                      {name: '贾环'},
                    ],
                    mate: [
                      {name: '王夫人',},
                      {name: '赵姨娘',},
                    ],
                  },
                  {
                    name: '贾敏',
                    imgUrl: "https://img01.yzcdn.cn/vant/cat.jpeg",
                    children: [
                      {name: '林黛玉'},
                    ],
                    mate: [{name: '林如海',}],
                  }
                ]
              }
            ]
          }
        ]
      }
    }
  },
  methods: {
    clickNode(node) {
      console.log(node)
    }
  }
}
</script>