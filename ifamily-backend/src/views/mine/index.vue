<template>
  <div class="app-container">
    <el-card class="box-card-component">
      <div slot="header" class="box-card-header">
        <img src="/img/user-cover.png" alt="avatar">
      </div>

      <div style="position:relative;">
        <pan-thumb :image="userAvatar" class="panThumb"/>
        <mallki class-name="mallki-text" :text="userInfo.username"/>
        <div style="padding-top:35px;" class="progress-item">
          <span>UID：{{ userInfo.uid }}</span>
        </div>
        <div class="progress-item">
          <span>个性签名：{{ userInfo.signature }}</span>
        </div>
        <div class="progress-item">
          <span>用户昵称：{{ userInfo.nickname }}</span>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import Mallki from "@/views/mine/components/Mallki"
import PanThumb from "@/views/mine/components/PanThumb"
import {mapGetters} from "vuex";

export default {
  name: "UserProfile",
  components: {PanThumb, Mallki},
  computed: {
    ...mapGetters(['userInfo']),
    userAvatar() {
      return this.userInfo.avatar ? `${this.userInfo.avatar}?imageView2/1/w/80/h/80` : '/img/avatar.gif?imageView2/1/w/80/h/80'
    }
  }
}
</script>

<style lang="scss">
.box-card-component {
  .el-card__header {
    padding: 0 !important;
  }
}
</style>

<style lang="scss" scoped>
.box-card-component {
  width: 500px;
  position: absolute;
  top: 30%;
  left: 0;
  right: 0;
  margin: auto;

  .box-card-header {
    position: relative;
    height: 240px;

    img {
      width: 100%;
      height: 100%;
      transition: all 0.2s linear;

      &:hover {
        transform: scale(1.1, 1.1);
        filter: contrast(130%);
      }
    }
  }

  .mallki-text {
    position: absolute;
    top: 0;
    right: 0;
    font-size: 20px;
    font-weight: bold;
  }

  .panThumb {
    z-index: 100;
    height: 70px !important;
    width: 70px !important;
    position: absolute !important;
    top: -45px;
    left: 0;
    border: 5px solid #ffffff;
    background-color: #fff;
    margin: auto;
    box-shadow: none !important;

    ::v-deep .pan-info {
      box-shadow: none !important;
    }
  }

  .progress-item {
    margin-bottom: 10px;
    font-size: 14px;
  }

  @media only screen and (max-width: 1510px) {
    .mallki-text {
      display: none;
    }
  }
}
</style>
