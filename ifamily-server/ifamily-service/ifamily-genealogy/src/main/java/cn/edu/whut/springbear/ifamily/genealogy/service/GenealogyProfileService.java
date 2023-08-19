package cn.edu.whut.springbear.ifamily.genealogy.service;

import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyProfileDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:02
 */
public interface GenealogyProfileService extends IService<GenealogyProfileDO> {

    /**
     * 保存家族成员默认概况信息：所有人数均为 0
     */
    boolean createDefault(Long genealogyId);

    /**
     * 通过家族 ID 查询家族成员概况信息
     */
    GenealogyProfileDO getByGenealogyId(Long genealogyId);

    /**
     * 将家族总人数 +1，根据性别将男性或女性人数 +1，根据生逝状态将健在或已逝人数 +1
     *
     * @param genealogyId 家族 ID
     * @param gender      性别：[0]男 [1]女
     * @param deathDate   逝世日期
     * @return [1]更新成功
     */
    boolean peopleIncreaseOne(Long genealogyId, Integer gender, Date deathDate);

    /**
     * 比较新、旧性别，若两者不一致则更新家族成员概况信息中的男、女人数，新性别对应的人数 +1，旧性别对应的人数 -1
     *
     * @param newGender   新性别
     * @param oldGender   旧性别
     * @param genealogyId 家族 ID
     * @return [true]更新成功
     */
    boolean changeByGender(Integer newGender, Integer oldGender, Long genealogyId);

    /**
     * 比较新、旧逝世日期，若两者不一致则更新家族成员概况信息中的生、逝人数
     *
     * @param newDeathDate 新逝世日期
     * @param oldDeathDate 旧逝世日期
     * @param genealogyId  家族 ID
     * @return [true]更新成功
     */
    boolean changeByDeath(Date newDeathDate, Date oldDeathDate, Long genealogyId);

    /**
     * 将家族总人数 -1，根据性别将男性或女性人数 -1，根据生逝状态将健在或已逝人数 -1
     *
     * @param genealogyId 家族 ID
     * @param gender      性别：[0]男 [1]女
     * @param deathDate   逝世日期
     * @return [1]更新成功
     */
    boolean peopleDecreaseOne(Long genealogyId, Integer gender, Date deathDate);

}
