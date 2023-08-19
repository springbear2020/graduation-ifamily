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
            passwordFieldType: 'password'
        }
    },
    computed: {
        rightIcon() {
            return this.passwordFieldType === 'password' ? 'closed-eye' : 'eye-o';
        }
    },
}
