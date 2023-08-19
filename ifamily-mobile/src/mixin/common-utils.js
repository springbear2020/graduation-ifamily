import {ImagePreview} from "vant";

/**
 * 单张图片预览
 */
export const previewImage = {
    methods: {
        previewImage(url) {
            let images = []
            images.push(url)
            ImagePreview({images})
        }
    }
}

/**
 * 根据性别展示人物肖像
 */
export const defaultPortrait = {
    methods: {
        defaultPortrait(gender) {
            return gender === 0 ? 'img/male.jpg' : 'img/female.jpg'
        }
    }
}
