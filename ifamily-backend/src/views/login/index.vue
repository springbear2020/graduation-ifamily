<template>
  <div class="login-container">
    <el-form ref="loginForm" class="login-form" autocomplete="on" label-position="left"
             :model="loginForm"
             :rules="loginRules"
    >

      <div class="title-container">
        <h3 class="title">管理员登录</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user"/>
        </span>

        <el-input ref="username" placeholder="用户名" tabindex="1" autocomplete="on"
                  v-model="loginForm.username"
        />
      </el-form-item>

      <el-tooltip content="大写锁定已开启" placement="right" manual v-model="capsTooltip">
        <el-form-item prop="password">
          <span class="svg-container">
            <svg-icon icon-class="password"/>
          </span>

          <el-input ref="password" placeholder="密码" tabindex="2" autocomplete="on"
                    :key="passwordType"
                    v-model="loginForm.password"
                    :type="passwordType"
                    @keyup.native="checkCapslock"
                    @blur="capsTooltip = false"
                    @keyup.enter.native="handleLogin"
          />

          <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
          </span>
        </el-form-item>
      </el-tooltip>

      <el-button type="primary" style="width: 100%" @click.native.prevent="handleLogin"
                 :loading="loading"
      >登录
      </el-button>
    </el-form>

  </div>
</template>

<script>
export default {
  name: "UserLogin",
  data() {
    return {
      loginForm: {
        username: undefined,
        password: undefined
      },
      loginRules: {
        username: [{required: true, message: '用户名不能为空'}],
        password: [{required: true, message: '密码不能为空'}]
      },
      passwordType: 'password',
      capsTooltip: false,
      loading: false,
      redirect: undefined,
      otherQuery: {}
    }
  },
  watch: {
    $route: {
      immediate: true,
      handler: function (route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      }
    }
  },
  mounted() {
    this.inputFocus()
  },
  methods: {
    inputFocus() {
      if (this.loginForm.username === '') {
        this.$refs.username.focus()
      } else if (this.loginForm.password === '') {
        this.$refs.password.focus()
      }
    },
    checkCapslock(e) {
      const {key} = e
      this.capsTooltip = key && key.length === 1 && (key >= 'A' && key <= 'Z')
    },
    showPwd() {
      this.passwordType = this.passwordType === 'password' ? 'text' : 'password'
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (!valid) {
          return false
        }

        this.loading = true
        this.$store.dispatch('user/login', this.loginForm).then(() => {
          this.$router.replace({path: this.redirect || '/', query: this.otherQuery})
          this.loading = false
        }).catch((msg) => {
          this.loading = false
          this.$notify.error(msg)
        })
      })
    }
  }
}
</script>

<style lang="scss">
/* Fix input background inconsistency and cursor color change。Details see https://github.com/PanJiaChen/vue-element-admin/pull/927 */
$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    //color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0;
      -webkit-appearance: none;
      border-radius: 0;
      padding: 12px 5px 12px 15px;
      //color: $light_gray;
      height: 47px;
      //caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  //color: $bg;
  background: url("~@/assets/img/background.jpg");
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $bg;
      margin: 0 auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
