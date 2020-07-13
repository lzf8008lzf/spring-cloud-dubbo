package com.enjoy.core.utils.message;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.message.MessageBody;
import cn.jmessage.api.message.SendMessageResult;
import cn.jmessage.api.user.UserListResult;
import cn.jmessage.api.user.UserStateListResult;
import com.enjoy.core.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JMessageUtil {
    private static Logger logger = LoggerFactory.getLogger(JMessageUtil.class);
    private static final String APP_KEY = "314eea1b168bd27f69a75494";//必填，例如466f7032ac604e02fb7bda89
    private static final String MASTER_SECRET = "a038789292e4959af2fde16f";//必填，每个应用都对应一个masterSecret

    private static JMessageClient jMessageClient;

    static
    {
        jMessageClient = new JMessageClient(APP_KEY, MASTER_SECRET);
    }

    public static String signature(Date timestamp,String randomStr)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("appkey=").append(APP_KEY)
                .append("&timestamp=").append(timestamp.getTime())
                .append("&random_str=").append(randomStr)
                .append("&key=").append(MASTER_SECRET);
        String signature = MD5.getMD5Code(sb.toString());
        logger.info("key:{},signature:{}",sb.toString(),signature);
        return signature;
    }

    public static void sendSingleTextByAdmin(String sendUser,String toUser,String msgContent) {

        try {
            MessageBody body = MessageBody.text(msgContent);
            SendMessageResult result = jMessageClient.sendSingleTextByAdmin(toUser, sendUser, body);
            logger.info("msgId:{}",result.getMsg_id());
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Message: " + e.getMessage());
        }
    }

    public static void getAdminListByAppkey(int count) {
        try {
            if(count<=0)
                count=10;
            UserListResult res = jMessageClient.getAdminListByAppkey(0, count);
            logger.info(res.getOriginalContent());
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Message: " + e.getMessage());
        }
    }

    public static UserStateListResult[] getUsersState(List<String> userList) {
        try {
            String[] arrStr=userList.toArray(new String[userList.size()]);
            UserStateListResult[] results = jMessageClient.getUsersState(arrStr);
            for (UserStateListResult result : results) {
                logger.info(result.toString());
            }

            return results;
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Message: " + e.getMessage());
        }

        return null;
    }

    public static boolean registerUsers(String userName,String password) {
        boolean bRet = false;

        try {

            List<RegisterInfo> users = new ArrayList<RegisterInfo>();

            RegisterInfo user = RegisterInfo.newBuilder()
                    .setUsername(userName)
                    .setNickname(userName)
                    .setPassword(password)
                    .build();

            users.add(user);

            RegisterInfo[] regUsers = new RegisterInfo[users.size()];

            String retStr = jMessageClient.registerUsers(users.toArray(regUsers));
            logger.info(retStr);
            bRet = true;
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Message: " + e.getMessage());
        }

        return bRet;
    }
}
