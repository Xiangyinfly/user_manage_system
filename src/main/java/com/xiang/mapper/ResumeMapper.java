package com.xiang.mapper;

import com.xiang.domain.entity.Resume;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chenwentao
* @description 针对表【sys_resume】的数据库操作Mapper
* @createDate 2023-12-20 14:16:59
* @Entity com.xiang.domain.entity.Resume
*/
@Mapper
public interface ResumeMapper extends BaseMapper<Resume> {

}




