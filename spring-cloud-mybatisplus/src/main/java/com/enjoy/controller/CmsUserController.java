package com.enjoy.controller;


import com.enjoy.core.annotation.VisitLimit;
import com.enjoy.entity.CmsUser;
import com.enjoy.service.CmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author LiZhaofu
 * @since 2020-06-01
 */
@RestController
@RequestMapping("/")
public class CmsUserController {
    @Autowired
    private CmsUserService cmsUserService;

    @VisitLimit(limit = 1, second = 1)
    @RequestMapping("getuser")
    public CmsUser getuser() {
        CmsUser cmsUser=cmsUserService.getById(1);

        return cmsUser;
    }
}

