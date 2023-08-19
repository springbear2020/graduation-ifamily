package cn.edu.whut.springbear.ifamily.genealogy.service.impl;

import cn.edu.whut.springbear.ifamily.genealogy.mapper.PeopleMapper;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PeopleDO;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 20:02
 */
@Service
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper, PeopleDO> implements PeopleService {
}
