import {getAvatarToken} from "@/api/manager";
import {qiniuUploader} from "@/utils/qiniu";

export const imageUploader = {
    data() {
        return {
            // [1]用户头像 [2]家族封面
            imgType: '0',
            imgUrl: '',
            fileType: ''
        }
    },
    methods: {
        afterRead(fileObj) {
            fileObj.status = 'uploading';
            fileObj.message = '上传中...';

            // 获取文件上传 token
            getAvatarToken({suffix: this.fileType, type: this.imgType}).then(map => {
                // 将文件上传到七牛云服务器
                qiniuUploader(fileObj.file, map.key, map.token).then(() => {
                    fileObj.status = 'done'
                    this.imgUrl = map.cdn + map.key
                }).catch(() => {
                    fileObj.status = 'failed';
                    fileObj.message = '上传失败';
                })

            }).catch(() => {
                fileObj.status = 'failed';
                fileObj.message = '上传失败';
            })
        },
        beforeRead(file) {
            if (file.type.indexOf("image") === -1) {
                this.$toast('请上传正确格式的图片文件');
                return false;
            }
            // 获取图片文件格式后缀
            this.fileType = '.' + file.type.split('/')[1]
            return true;
        },
    }
}
