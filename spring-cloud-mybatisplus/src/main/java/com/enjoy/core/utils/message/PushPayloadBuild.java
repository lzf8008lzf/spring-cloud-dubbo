package com.enjoy.core.utils.message;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/3.
 */
public class PushPayloadBuild {
    /**
     * 所有平台，所有设备，内容为 ALERT 的通知
     *
     * @param alert 通知内容
     * @return
     */
    public static PushPayload buildPushObject_all_all_alert(String alert) {
        return PushPayload.alertAll(alert);
    }


    /**
     * 所有平台 通过昵称alias推送
     *
     * @param alias
     * @param alert
     * @return
     */
    public static PushPayload buildPushObject_all_alias_alert(String alias, String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(alert))
                .build();
    }

    /**
     * 推送tag人群
     *
     * @param tag
     * @param alert
     * @return
     */
    public static PushPayload buildPushObject_android_tag_alert(String tag, String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.alert(alert))
                .build();
    }


    public static PushPayload buildPushObject_android_tag_alertWithTitle(String tag, String alert, String title) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.android(alert, title, null))
                .build();
    }

    /**
     * 极光推送根据UserID推送
     *
     * @param alert
     * @param title
     * @return
     */
    public static PushPayload buildPushObjecAllByUserId(String alert, String title, Map<String, String> map) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alert))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("from", "enjoyment")
                                .setAlert(title)
                                .addExtras(map)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(title)
                                .addExtras(map)
                                .autoBadge()
                                .setSound("happy")
                                .addExtra("from", "enjoyment")
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(1000)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * 极光推送根据UserID推送
     *
     * @param alert
     * @param title
     * @return
     */
    public static PushPayload buildPushNoticeAllByUserId(String alert, String title, Map<String, String> map) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alert))
                .setMessage(Message.content(title))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(title)
                                .addExtras(map)
                                .autoBadge()
                                .setSound("happy")
                                .addExtra("from", "enjoyment")
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("from", "enjoyment")
                                .setAlert(title)
                                .addExtras(map)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(1000)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * 极光推送根据UserID推送(不到达制定页面)
     *
     * @param alert
     * @param title
     * @return
     */
    public static PushPayload buildPushObjecAllByUserId(String alert, String title) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alert))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("from", "enjoyment")
                                .setAlert(title)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(title)
                                .autoBadge()
                                .setSound("happy")
                                .addExtra("from", "enjoyment")
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(1000)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * 极光推送根据TAG推送
     *
     * @param tag
     * @param alert
     * @return
     */
    public static PushPayload buildPushObjecAllByTAG(String tag, String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("from", "enjoyment")
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .autoBadge()
                                .setSound("happy")
                                .addExtra("from", "enjoyment")
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(1000)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * IOS 别称推送
     *
     * @param alias
     * @param alert
     * @param msg_content
     * @return
     */
    public static PushPayload buildPushObject_ios_aliasAnd_alertWithExtrasAndMessage(String alias, String alert, String msg_content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .setBadge(2)
                                .setSound("happy")
                                .addExtra("from", "enjoyment")
                                .build())
                        .build())
                .setMessage(Message.content(msg_content))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    /**
     * IOS 标签推送
     *
     * @param tag
     * @param alert
     * @param msg_content
     * @return
     */
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String tag, String alert, String msg_content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .setBadge(1)
                                .setSound("happy")
                                .addExtra("from", "enjoyment")
                                .build())
                        .build())
                .setMessage(Message.content(msg_content))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String tag, String msg_content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag(tag))
//                .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .addExtra("from", "enjoyment")
                        .build())
                .build();
    }

}
