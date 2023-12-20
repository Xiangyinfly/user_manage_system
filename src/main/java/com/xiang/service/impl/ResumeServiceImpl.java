package com.xiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.entity.Resume;
import com.xiang.domain.entity.User;
import com.xiang.domain.vo.PageVo;
import com.xiang.domain.vo.ResumeInfoVo;
import com.xiang.domain.vo.UserInfoVo;
import com.xiang.enums.AppHttpCodeEnum;
import com.xiang.exception.SystemException;
import com.xiang.service.ResumeService;
import com.xiang.mapper.ResumeMapper;
import com.xiang.utils.BeanCopyUtils;
import com.xiang.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
* @author chenwentao
* @description 针对表【sys_resume】的数据库操作Service实现
* @createDate 2023-12-20 14:16:59
*/
@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume>
    implements ResumeService{

    @Override
    public ResponseResult getMyInfo() {
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<Resume> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Resume::getUserId,userId);
        Resume resume = getOne(queryWrapper);
        ResumeInfoVo resumeInfoVo = BeanCopyUtils.copyBean(resume, ResumeInfoVo.class);
        return ResponseResult.ok(resumeInfoVo);
    }

    @Override
    public ResponseResult getResumeList(Integer pageNum, Integer pageSize, String name) {
        LambdaQueryWrapper<Resume> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(name),Resume::getName,name);
        Page<Resume> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<ResumeInfoVo> resumeInfoVos = BeanCopyUtils.copyBeanList(page.getRecords(), ResumeInfoVo.class);
        PageVo pageVo = new PageVo(resumeInfoVos, page.getTotal());
        return ResponseResult.ok(pageVo);
    }

    @Override
    public ResponseResult addResume(Resume resume) {
        LambdaQueryWrapper<Resume> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Resume::getUserId,resume.getUserId());
        Resume resume1 = getOne(queryWrapper);
        if (resume1 != null) {
            throw new SystemException(AppHttpCodeEnum.RESUME_EXIST);
        }
        resume.setCreateTime(new Date());
        save(resume);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult updateResume(Resume resume) {
        LambdaQueryWrapper<Resume> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Resume::getUserId,resume.getUserId());
        Resume resume1 = getOne(queryWrapper);
        if (resume1 == null) {
            throw new SystemException(AppHttpCodeEnum.NO_SUCH_RESUME);
        }

        resume.setUpdateTime(new Date());
        LambdaUpdateWrapper<Resume> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Resume::getUserId,resume.getUserId());
        update(resume,updateWrapper);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult deleteResume(Long userId) {
        LambdaQueryWrapper<Resume> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Resume::getUserId,userId);
        Resume resume = getOne(queryWrapper);
        if (resume == null) {
            throw new SystemException(AppHttpCodeEnum.NO_SUCH_RESUME);
        }
        remove(queryWrapper);
        return ResponseResult.ok();
    }


}




