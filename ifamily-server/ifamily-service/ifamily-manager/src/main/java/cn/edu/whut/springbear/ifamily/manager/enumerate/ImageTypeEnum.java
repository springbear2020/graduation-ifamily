package cn.edu.whut.springbear.ifamily.manager.enumerate;

import lombok.Getter;

/**
 * @author Spring-_-Bear
 * @since 23/04/14 11:30
 */
@Getter
public enum ImageTypeEnum {

    /**
     * 用户头像
     */
    USER_AVATAR(1, "用户头像", "avatar"),
    /**
     * 家族封面
     */
    CLAN_COVER(2, "家族封面", "cover"),
    /**
     * 人物肖像
     */
    PEOPLE_PORTRAIT(3, "人物肖像", "portrait"),
    /**
     * 家族相册
     */
    FAMILY_ALBUM(4, "家族相册", "album"),
    /**
     * 大事配图
     */
    MEMORABILIA(5, "大事配图", "memorabilia");

    private final Integer code;
    private final String desc;
    private final String directory;

    ImageTypeEnum(Integer code, String desc, String directory) {
        this.code = code;
        this.desc = desc;
        this.directory = directory;
    }

}
