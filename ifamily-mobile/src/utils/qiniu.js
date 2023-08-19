import * as qiniu from 'qiniu-js'
import {Notify} from 'vant';

/**
 * 上传图片文件到七牛云服务器
 * @param file 图片文件对象
 * @param key 文件名
 * @param token 验证令牌
 */
export function upload(file, key, token) {
    return new Promise((resolve, reject) => {
        const config = {
            shouldUseQiniuFileName: false,
            region: qiniu.region.z2,
            forceDirect: true,
            useCdnDomain: true,
        }

        const putExtra = {
            fname: {key},
            params: {},
            mimeType: ['image/*']
        }

        const options = {
            quality: 0.92,
            noCompressIfLarger: true
        }

        // 压缩后将图片上传到七牛云服务器
        qiniu.compressImage(file, options).then(data => {
            qiniu.upload(data.dist, key, token, putExtra, config).subscribe({
                // 上传进度
                next(next) {
                    // let rate = next.total.percent + "";
                    // rate = rate.substring(0, rate.indexOf(".") + 3) + '%'
                    // console.log(rate);
                },
                // 上传失败
                error(err) {
                    Notify({
                        type: 'danger',
                        message: '图片上传失败，请稍后重试'
                    })
                    reject(err)
                },
                // 上传成功
                complete(res) {
                    resolve(res)
                }
            })
        })
    });
}
