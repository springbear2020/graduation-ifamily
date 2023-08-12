<template>
  <div>
    <van-nav-bar fixed :title="title" left-arrow @click-left="backFamilyTree"/>

    <!-- 人员信息头像和姓名 -->
    <van-grid :border="false" :column-num="1">
      <van-grid-item>
        <template #icon>
          <van-image round height="100" width="100" src="https://img01.yzcdn.cn/vant/cat.jpeg"/>
        </template>
        <template #text>
          <p class="name">冯勇贤</p>
        </template>
      </van-grid-item>
    </van-grid>

    <van-cell title="家庭关系" :border="false">
      <template #label>
        <p>父亲：
          <van-button type="primary" plain size="mini" icon="add-o" @click="addRelatives(1)">添加</van-button>
        </p>
        <p>母亲：
          <van-button type="primary" plain size="mini" icon="add-o" @click="addRelatives(2)">添加</van-button>
        </p>
        <p>配偶：
          <van-button type="primary" plain size="mini" icon="add-o" @click="addRelatives(3)">添加</van-button>
        </p>
        <p>子女：
          <people-tag :name="'冯学慧'" :sex="3" :right="true" :bottom="true" @click.native="handleViewPeople"/>
          <people-tag :name="'刘纯洲'" :sex="1" :right="true" :bottom="true" @click.native="handleViewPeople"/>
          <people-tag :name="'冯学良'" :sex="0" :right="true" :bottom="true" @click.native="handleViewPeople"/>
          <van-button type="primary" plain size="mini" icon="add-o" @click="addRelatives(4)">添加</van-button>
        </p>
        <p>同胞：
          <people-tag :name="'冯世元'" :sex="0" :right="true" @click.native="handleViewPeople"/>
          <van-button type="primary" plain size="mini" icon="add-o" @click="addRelatives(5)">添加</van-button>
        </p>
      </template>
    </van-cell>

    <!-- 信息表单 -->
    <van-form v-show="showForm">
      <!-- 头像、姓氏、姓名、字辈 -->
      <van-cell-group class="container">
        <van-uploader max-count="1" v-model="formData.portrait"/>
        <div>
          <van-field name="surname" v-model="formData.surname" label="姓氏" placeholder="姓氏" :border="false" required/>
          <van-field name="name" v-model="formData.name" label="名字" placeholder="名字" :border="false" required/>
          <van-field name="word" v-model="formData.word" label="字辈" placeholder="字辈" :border="false" required/>
        </div>
      </van-cell-group>

      <!-- 性别、世代、字辈、排行 -->
      <van-cell-group>
        <van-field name="sex" label="性别" :border="false" required>
          <template #input>
            <van-radio-group v-model="formData.sex" direction="horizontal">
              <van-radio name="0">男</van-radio>
              <van-radio name="1">女</van-radio>
            </van-radio-group>
          </template>
        </van-field>
        <van-field name="generation" label="世代" placeholder="世代" :border="false" required>
          <template #input>
            <van-stepper v-model="formData.generation"/>
          </template>
        </van-field>
        <van-field name="range" label="排行" placeholder="排行" :border="false" required>
          <template #input>
            <van-stepper v-model="formData.range"/>
          </template>
        </van-field>
      </van-cell-group>

      <!-- 手机、现居地 -->
      <van-cell-group>
        <van-field name="mobile" type="tel" v-model="formData.mobile" label="手机" placeholder="手机" :border="false"/>
        <van-field name="presentPlace" label="现居地" placeholder="点击选择省市区" readonly clickable
                   v-model="formData.presentPlace" @click="chooseArea('1')" :border="false"/>
      </van-cell-group>

      <!-- 生于、出生地 -->
      <van-cell-group>
        <van-field readonly clickable name="birthdate" label="生于" placeholder="点击选择出生日期"
                   v-model="formData.birthdate" @click="chooseDate('1')" :border="false"/>
        <van-field name="birthplace" readonly clickable label="出生地" placeholder="点击选择省市区"
                   v-model="formData.birthplace" @click="chooseArea('2')" :border="false"/>
      </van-cell-group>

      <!-- 逝于、埋葬地 -->
      <van-cell-group>
        <van-field name="alive" v-model="formData.birthplace" label="健在" input-align="right" :border="false">
          <template #input>
            <van-switch v-model="formData.alive" size="20"/>
          </template>
        </van-field>
        <div v-show="!formData.alive">
          <van-field readonly clickable name="dieDate" label="逝于" placeholder="点击选择逝世日期"
                     v-model="formData.dieDate" :border="false" @click="chooseDate('2')"/>
          <van-field name="burialPlace" readonly clickable label="埋葬地" placeholder="点击选择省市区"
                     @click="chooseArea('3')" v-model="formData.burialPlace" :border="false"/>
        </div>
      </van-cell-group>

      <div class="container">
        <van-button block @click="showForm = false">取消</van-button>
        <van-button block type="primary" @click="handleAddRelatives">保存</van-button>
      </div>
    </van-form>

    <!-- 日期选择 -->
    <van-action-sheet v-model="showDatetimePicker">
      <van-datetime-picker v-model="selectedDate" type="datetime" title="选择年月日小时分钟"
                           :min-date="minDate" :max-date="curDate" @cancel="showDatetimePicker = false"
                           @confirm="confirmDate"/>
    </van-action-sheet>

    <!-- 地址选择组件 -->
    <van-popup v-model="showAreaPopup" position="bottom">
      <van-area :area-list="areaList" @confirm="confirmArea" @cancel="showAreaPopup = false">
        <template #columns-bottom>
          <van-field rows="1" label="详细地址" type="textarea" maxlength="100" placeholder="详细地址" show-word-limit
                     v-model="fullAddress" class="full-address"/>
        </template>
      </van-area>
    </van-popup>
  </div>
</template>

<script>
import dayjs from 'dayjs'
import {areaList} from '@vant/area-data'

export default {
  name: "index",
  data() {
    return {
      title: '添加亲人',
      showForm: false,
      // TODO [Vant bug]当 minDate 的 year 小于 100 时 [minYear, maxYear] 间的年份不能正常渲染
      // [1]出生日期 [2]逝世日期
      dateType: '1',
      minDate: new Date(100, 0, 1),
      curDate: new Date(),
      selectedDate: new Date(),
      showDatetimePicker: false,
      // [1]现居地 [2]出生地 [3]埋葬地
      areaType: '1',
      areaList: [],
      showAreaPopup: false,
      fullAddress: '',
      formData: {
        portrait: [],
        surname: '',
        name: '',
        sex: '0',
        generation: 1,
        word: '',
        range: 1,
        mobile: '',
        presentPlace: '',
        birthdate: '',
        birthplace: '',
        alive: true,
        dieDate: '',
        burialPlace: ''
      }
    }
  },
  mounted() {
    this.areaList = areaList
  },
  methods: {
    backFamilyTree() {
      this.$router.replace('/family/tree')
    },
    handleViewPeople() {
      this.$toast.success('查看人员信息')
    },
    addRelatives(type) {
      // [1]父亲 [2]母亲 [3]配偶 [4]子女 [5]同胞
      switch (type) {
        case 1:
          this.title = '添加父亲';
          this.formData.sex = '0'
          break;
        case 2:
          this.title = '添加母亲';
          this.formData.sex = '1'
          break;
        case 3:
          this.title = '添加配偶';
          break;
        case 4:
          this.title = '添加子女';
          break;
        case 5:
          this.title = '添加同胞';
          break;
        default:
          this.title = '添加亲人'
      }
      this.showForm = true
    },
    handleAddRelatives() {
      console.log(this.formData);
      this.$toast.success('保存成功');
      this.showForm = false
    },
    chooseDate(type) {
      this.dateType = type
      this.showDatetimePicker = true
    },
    confirmDate() {
      const dateStr = dayjs(this.selectedDate).format('YYYY-MM-DD HH:mm:ss')
      if (this.dateType === '1') {
        this.formData.birthdate = dateStr
      } else {
        this.formData.dieDate = dateStr
      }
      this.showDatetimePicker = false
    },
    confirmArea(area) {
      area = area.filter((item) => !!item).map((item) => item.name).join('/')
      area = this.fullAddress.trim() ? area + "/" + this.fullAddress.trim() : area
      switch (this.areaType) {
        case "1":
          this.formData.presentPlace = area;
          break;
        case "2":
          this.formData.birthplace = area
          break;
        case "3":
          this.formData.burialPlace = area;
          break;
      }
      this.showAreaPopup = false
    },
    chooseArea(type) {
      this.areaType = type
      this.showAreaPopup = true
    }
  }
}
</script>

<style scoped>
.name {
  color: #323233;
  font-size: 16px;
  line-height: 24px;
  font-weight: bold;
}

.van-cell__title button {
  height: 20px;
  position: absolute;
}

/deep/ .van-grid-item__content {
  margin-top: 46px;
}

/deep/ .van-uploader__upload {
  margin: 0;
}

.van-cell__label p {
  height: 20px;
}

.container {
  display: flex;
}

.container button {
  margin-top: 16px;
}

/deep/ .van-uploader {
  display: flex;
}

/deep/ .van-uploader .van-uploader__wrapper {
  margin: auto;
  padding: 16px;
}

.full-address {
  background-color: #f7f8fa;
}
</style>