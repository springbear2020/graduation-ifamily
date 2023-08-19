export const verifyCode = {
    data() {
        return {
            countdown: 0,
            buttonText: '获取验证码',
            // [0]邮箱账号 [1]手机账号
            accountType: undefined,
        }
    },
    methods: {
        validatePhoneOrEmail() {
            const account = this.formData.account
            const phoneRegExp = /^1[3456789]\d{9}$/
            const emailRegExp = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/

            // 根据正则校验结果设置当前账户类型
            if (emailRegExp.test(account)) {
                this.accountType = '0'
            } else if (phoneRegExp.test(account)) {
                this.accountType = '1'
            } else {
                this.accountType = undefined
            }

            // 返回校验结果
            return phoneRegExp.test(account) || emailRegExp.test(account);
        },
        handleSendCode() {
            // 校验账号格式
            const result = this.validatePhoneOrEmail();
            if (!result) {
                const msg = this.type === '0' ? '邮箱格式不正确，请重新输入' : '手机格式不正确，请重新输入'
                this.$toast({message: msg, position: 'bottom'})
                return
            }

            // 获取验证码按钮倒计时未结束
            if (this.countdown > 0) {
                return
            }

            if (this.accountType === '0') {
                this.lockButton();
                this.sendEmailVerifyCode();
            } else if (this.accountType === '1') {
                this.lockButton();
                this.sendPhoneVerifyCode();
            }
        },
        sendEmailVerifyCode() {
            this.$api.manager.sendEmailCode({email: this.formData.account}).then(() => {
                this.$toast.success('发送成功');
            }).catch(msg => {
                this.$toast({message: msg, position: 'bottom'})
            })
        },
        sendPhoneVerifyCode() {
            this.$api.manager.sendPhoneCode({phone: this.formData.account}).then(() => {
                this.$toast.success('发送成功');
            }).catch(msg => {
                this.$toast({message: msg, position: 'bottom'})
            })
        },
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
