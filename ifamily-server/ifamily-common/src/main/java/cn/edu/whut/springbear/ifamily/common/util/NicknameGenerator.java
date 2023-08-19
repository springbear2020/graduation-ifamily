package cn.edu.whut.springbear.ifamily.common.util;

import java.util.Random;

/**
 * @author Spring-_-Bear
 * @since 23/05/02 11:00
 */
public class NicknameGenerator {

    private static final String[] ADJECTIVES = {
            "快乐的", "幸福的", "聪明的", "可爱的", "温柔的", "美丽的", "帅气的", "勇敢的", "善良的", "真诚的"
    };

    private static final String[] NOUNS = {
            "小熊", "小鹿", "小鸟", "小鱼", "小虫", "小兔", "小猫", "小狗", "小象", "小猴"
    };

    private static final Random RANDOM = new Random();

    /**
     * 生成随机中文用户昵称
     */
    public static String generate() {
        String adjective = ADJECTIVES[RANDOM.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[RANDOM.nextInt(NOUNS.length)];
        return adjective + noun;
    }

}
