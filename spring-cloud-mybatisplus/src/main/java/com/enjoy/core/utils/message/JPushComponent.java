package com.enjoy.core.utils.message;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class JPushComponent {

    private static Logger logger = LoggerFactory.getLogger(JPushComponent.class);
    private static final String APP_KEY = "314eea1b168bd27f69a75494";//必填，例如466f7032ac604e02fb7bda89
    private static final String MASTER_SECRET = "a038789292e4959af2fde16f";//必填，每个应用都对应一个masterSecret
    private JPushClient jpushClient = null;

    /**
     * #极光消息推送
     * #Message Push Environment[dev]
     * #pushEnvironment=dev
     * #Message Push Environment[beta]
     * #pushEnvironment=beta
     * #Message Push Environment[online]
     * pushEnvironment=online
     */

    @Value("${pushEnvironment:dev}")
    private String pushEnvironment;

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    public JPushComponent() {
        jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
    }

    private boolean sendPushMessage(PushPayload pushPayload)
    {
        boolean bRet = false;
        try {
            PushResult result = jpushClient.sendPush(pushPayload);
            bRet = result.isResultOK();
            logger.info("resultOK:"+result.isResultOK());
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Should review the error, and fix the request", e);
            logger.error("HTTP Status:{}, Error Code:{}, Error Message:{} " , e.getStatus(),e.getErrorCode(),e.getErrorMessage());
        }

        return bRet;
    }

    public String getUserIdByEnv(Long userId)
    {
        String envUserId = userId.toString();
        if(!"original".equals(pushEnvironment.trim()))
        {
            envUserId  = pushEnvironment.trim()+ envUserId;
        }

        return envUserId;
    }

    public void pushMessageListByUserId(List<Map<String,Object>> pushMessageList)
    {
        // 消息推送
        if(pushMessageList != null && (pushMessageList).size()> 0) {

            for (Map<String,Object> it:pushMessageList) {
                Long msgReceiveUserId = (Long) it.get("merchantId");
                String msgContent = (String) it.get("msgContent");
                Map<String,String> extraMap = (Map<String, String>) it.get("extraMap");
                pushMessageByUserId(msgReceiveUserId, msgContent, extraMap);
            }
        }
    }


    /**
     * 极光推送根据UserId推送
     * @param userId
     * @param msg
     * @return
     */
    public void pushMessageByUserId(Long userId,String msg,Map<String,String> map){
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                pushMessage(userId,msg,map);
            }
        });
    }

    public void pushMessage(Long userId,String msg,Map<String,String> map){

        sendPushMessage(PushPayloadBuild.buildPushNoticeAllByUserId(getUserIdByEnv(userId),msg,map));

    }

    /**
     * 极光推送根据TAG推送
     * @param tag
     * @param msg
     * @return
     */
    public void pushMessageByTag(String tag,String msg){

        sendPushMessage(PushPayloadBuild.buildPushObjecAllByTAG(tag,msg));
    }

    public void pushMessageByUserIdAll(Long userId,String msg){

        sendPushMessage(PushPayloadBuild.buildPushObject_all_alias_alert(userId.toString(),msg));

    }
    
     public void pushMessageByTagAll(String tag,String msg){

        sendPushMessage(PushPayloadBuild.buildPushObject_android_tag_alert(tag,msg));

    }
}
