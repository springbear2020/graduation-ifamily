import {getImageToken} from "@/api/manager";
import {qiniuUploader} from "@/utils/qiniu";

export const imageUploader = {
    data() {
        return {
            // 图片类型：[1]用户头像 [2]家族封面 [3]人物肖像 [4]家族相册 [5]大事配图 [6]动态图片
            imgType: undefined,
            imgUrl: undefined,
            fileSuffix: undefined
        }
    },
    methods: {
        beforeRead(file) {
            if (file.type.indexOf("image") === -1) {
                this.$toast({message: '请选择正确格式的图片文件', position: 'bottom'});
                return false;
            }

            // 设置当前选择文件后缀名
            this.fileSuffix = '.' + file.type.split('/')[1]
            return true;
        },
        afterRead(fileObj) {
            fileObj.status = 'uploading';
            fileObj.message = '上传中...';

            // 获取图片文件上传令牌
            getImageToken({suffix: this.fileSuffix, type: this.imgType}).then(map => {
                // 上传图片文件到七牛云服务器
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
        }
    }
}
