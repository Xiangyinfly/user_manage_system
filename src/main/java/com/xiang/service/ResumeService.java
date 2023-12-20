package com.xiang.service;

import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.Resume;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chenwentao
* @description 针对表【sys_resume】的数据库操作Service
* @createDate 2023-12-20 14:16:59
*/
public interface ResumeService extends IService<Resume> {

    ResponseResult getMyInfo();

    ResponseResult getResumeList(Integer pageNum, Integer pageSize, String name);

    ResponseResult addResume(Resume resume);

    ResponseResult updateResume(Resume resume);

    ResponseResult deleteResume(Long userId);
}
