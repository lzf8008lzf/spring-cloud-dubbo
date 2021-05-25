package com.enjoy.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.enjoy.vo.LoginRequest;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-05-24 15:57
 **/

public class WxAppLoginService {
    public void login(LoginRequest request) throws WxErrorException {
        final WxMaService wxService = getWxMaService();

        // 获取微信用户session
        WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(request.getCode());
        if (null == session) {
            throw new RuntimeException("login handler error");
        }

        // 解密用户信息
        WxMaUserInfo wxUserInfo = wxService.getUserService().getUserInfo(session.getSessionKey(),
                request.getEncryptedData(), request.getIv());
        if (null == wxUserInfo) {
            throw new RuntimeException("wxUser not exist");
        }

        // 解密手机号码信息
        WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxService.getUserService().getPhoneNoInfo(session.getSessionKey(),
                request.getEncryptedData(), request.getIv());
        if (Objects.isNull(wxMaPhoneNumberInfo) || StringUtils.isBlank(wxMaPhoneNumberInfo.getPhoneNumber())) {
            // 解密手机号码信息错误
        }

        System.out.printf("============用户登录注册获取微信用户信息===========> username=%s", wxUserInfo.getNickName());

    }

    private WxMaService getWxMaService() {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid("appId");
        config.setSecret("appSecret");
        config.setMsgDataFormat("JSON");
        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(config);
        return wxMaService;
    }
}
