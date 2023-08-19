export const user = {
    data() {
        return {
            formData: {
                account: '',
                password: '',
                code: '',
                // [0]密码登录 [1]验证码登录
                loginType: '0',
            },
            agree: false,
            passwordFieldType: 'password',
            buttonText: '获取验证码',
            countdown: 0,
            // [0]邮箱账号 [1]手机账号
            accountType: undefined,
            error: ''
        }
    },
    computed: {
        rightIcon() {
            return this.passwordFieldType === 'password' ? 'closed-eye' : 'eye-o';
        }
    },
    methods: {
        // 验证手机号和邮箱格式
        validatePhoneOrEmail() {
            const account = this.formData.account
            const phoneRegExp = /^1[3456789]\d{9}$/
            const emailRegExp = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
            if (emailRegExp.test(account)) {
                this.accountType = '0'
            } else if (phoneRegExp.test(account)) {
                this.accountType = '1'
            } else {
                this.accountType = undefined
            }
            return phoneRegExp.test(account) || emailRegExp.test(account);
        },
        // 发送验证码请求
        handleSendCode() {
            if (this.countdown > 0) {
                return;
            }

            // 请求服务器发送验证码 accountType: [0]发送邮箱验证码 [1]发送手机验证码
            if (this.accountType === '0') {
                this.sendEmailVerifyCode();
            } else if (this.accountType === '1') {
                this.sendPhoneVerifyCode();
            }
        },
        // 发送邮箱验证码
        sendEmailVerifyCode() {
            this.lockButton();
            this.$api.manager.sendEmailCode({email: this.formData.account}).then(() => {
                this.$toast.success('发送成功');
            }).catch(err => {
                this.error = err.data || err.desc
            })
        },
        // 发送手机验证码
        sendPhoneVerifyCode() {
            this.$toast.fail('手机验证码服务暂不可用')
        },
        // 锁定获取验证码按钮
        lockButton() {
            this.countdown = 60;
            const timer = setInterval(() => {
                if (this.countdown <= 1) {
                    clearInterval(timer);
                    this.countdown = 0;
                    this.buttonText = "获取验证码";
                } else {
                    this.countdown--;
                    this.buttonText = `${this.countdown}s 后重试`;
                }
            }, 1000);
        }
    }
}
