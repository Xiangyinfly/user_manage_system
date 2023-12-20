package com.xiang.controller;

import com.xiang.annotation.SystemLog;
import com.xiang.domain.ResponseResult;
import com.xiang.domain.dto.ResumeDto;
import com.xiang.domain.entity.Resume;
import com.xiang.service.ResumeService;
import com.xiang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/resume")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    @SystemLog(businessName = "查询我的履历信息")
    @GetMapping("/myInfo")
    public ResponseResult getMyInfo() {
        return resumeService.getMyInfo();
    }

    @SystemLog(businessName = "查询履历列表")
    @GetMapping("/resumeList")
    public ResponseResult getResumeList(Integer pageNum,Integer pageSize,String name) {
        return resumeService.getResumeList(pageNum,pageSize,name);
    }
    @SystemLog(businessName = "添加履历")
    @PostMapping
    public ResponseResult addResume(@RequestBody ResumeDto resumeDto) {
        Resume resume = BeanCopyUtils.copyBean(resumeDto, Resume.class);
        return resumeService.addResume(resume);
    }
    @SystemLog(businessName = "修改履历")
    @PutMapping
    public ResponseResult updateResume(@RequestBody ResumeDto resumeDto) {
        Resume resume = BeanCopyUtils.copyBean(resumeDto, Resume.class);
        return resumeService.updateResume(resume);
    }
    @SystemLog(businessName = "删除履历")
    @DeleteMapping("/{userId}")
    public ResponseResult deleteResume(@PathVariable Long userId) {
        return resumeService.deleteResume(userId);
    }

}
