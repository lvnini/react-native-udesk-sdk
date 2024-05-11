package com.wanmi.udesk;

import android.content.Context;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cn.udesk.UdeskSDKManager;
import cn.udesk.model.UdeskCommodityItem;
import cn.udesk.config.UdeskConfig;
import udesk.core.LocalManageUtil;
import udesk.core.UdeskConst;

public class ReactNativeUdeskModule extends ReactContextBaseJavaModule {

    private String sdkToken; // 用户唯一的标识

    public ReactNativeUdeskModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "ReactNativeUdesk";
    }

    @ReactMethod
    public void startChat(@Nullable ReadableMap userInfo, Promise promise) {
        try {
            init(userInfo);
            // 默认系统字段是Udesk已定义好的字段，开发者可以直接传入这些用户信息，供客服查看。
            Map<String, String> info = new HashMap<>();
            info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, sdkToken);
            //以下信息是可选
            if (userInfo != null) {
                info.put(UdeskConst.UdeskUserInfo.NICK_NAME, getReadableMapString(userInfo, "nickename", ""));
                info.put(UdeskConst.UdeskUserInfo.EMAIL, getReadableMapString(userInfo, "email", ""));
                info.put(UdeskConst.UdeskUserInfo.CELLPHONE, getReadableMapString(userInfo, "phone", ""));
                info.put(UdeskConst.UdeskUserInfo.DESCRIPTION, getReadableMapString(userInfo, "description", ""));
            }
            Map<String, String> map = new HashMap<>();
            //自定义标签
            if (userInfo != null) {
                map.put("TextField_5469", getReadableMapString(userInfo, "version", ""));               //版本号
                map.put("TextField_5470", getReadableMapString(userInfo, "source", ""));                //来源
                map.put("TextField_5483", getReadableMapString(userInfo, "province", "暂无省中心"));      //省中心
                map.put("TextField_5484", getReadableMapString(userInfo, "identity", ""));              //身份
                map.put("TextField_5527", getReadableMapString(userInfo, "userId", ""));                //用户id
            }
            // info.put(UdeskConst.UdeskUserInfo.CUSTOMER_TOKEN, custom_token);
            // 只设置用户基本信息的配置setDefualtUserInfo
            UdeskConfig.Builder builder = new UdeskConfig.Builder();
            builder.setUdeskTitlebarBgResId(R.color.white)
                .setDefaultUserInfo(info)
                .setDefinedUserTextField(map)
                .setUdeskIMAgentNickNameColorResId(R.color.black);
            //自定义头像
            if (getReadableMapString(userInfo, "activity", "") != "") {
                builder.setCustomerUrl(getReadableMapString(userInfo, "activity", ""));
            }
            LocalManageUtil.saveSelectLanguage(getApplicationContext(), Locale.CHINA);
            UdeskSDKManager.getInstance().entryChat(getApplicationContext(), builder.build(), sdkToken);
            promise.resolve(true);
        } catch (Exception e) {
            promise.resolve(false);
        }
    }

    @ReactMethod
    public void sendCommodityMessage(@Nullable ReadableMap userInfo, @Nullable ReadableMap itemInfo, Promise promise) {
        try {
            init(userInfo);
            // 默认系统字段是Udesk已定义好的字段，开发者可以直接传入这些用户信息，供客服查看。
            Map<String, String> info = new HashMap<>();
            info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, sdkToken);
            //以下信息是可选
            if (userInfo != null) {
                info.put(UdeskConst.UdeskUserInfo.NICK_NAME, getReadableMapString(userInfo, "nickename", ""));
                info.put(UdeskConst.UdeskUserInfo.EMAIL, getReadableMapString(userInfo, "email", ""));
                info.put(UdeskConst.UdeskUserInfo.CELLPHONE, getReadableMapString(userInfo, "phone", ""));
                info.put(UdeskConst.UdeskUserInfo.DESCRIPTION, getReadableMapString(userInfo, "description", ""));
            }
            Map<String, String> map = new HashMap<>();
            //自定义标签
            if (userInfo != null) {
                map.put("TextField_5469", getReadableMapString(userInfo, "version", ""));               //版本号
                map.put("TextField_5470", getReadableMapString(userInfo, "source", ""));                //来源
                map.put("TextField_5483", getReadableMapString(userInfo, "province", "暂无省中心"));      //省中心
                map.put("TextField_5484", getReadableMapString(userInfo, "identity", ""));              //身份
                map.put("TextField_5527", getReadableMapString(userInfo, "userId", ""));                //用户id
            }
            // info.put(UdeskConst.UdeskUserInfo.CUSTOMER_TOKEN, custom_token);
            // 只设置用户基本信息的配置
            UdeskConfig.Builder builder = new UdeskConfig.Builder();
            builder.setDefaultUserInfo(info);
            //创建咨询对象的实例
            UdeskCommodityItem item = new UdeskCommodityItem();
            // 咨询对象主标题
            item.setTitle(getReadableMapString(itemInfo, "title", ""));
            //咨询对象描述
            item.setSubTitle(getReadableMapString(itemInfo, "subTitle", ""));
            //左侧图片
            item.setThumbHttpUrl(getReadableMapString(itemInfo, "img", ""));
            // 咨询对象网络链接
            item.setCommodityUrl(getReadableMapString(itemInfo, "url", ""));

            builder.setDefinedUserTextField(map).setCommodity(item);
            LocalManageUtil.saveSelectLanguage(getApplicationContext(), Locale.CHINA);
            UdeskSDKManager.getInstance().entryChat(getApplicationContext(), builder.build(), sdkToken);
            promise.resolve(true);
        } catch (Exception e) {
            promise.resolve(false);
        }
    }

    private void init(@Nullable ReadableMap initParam) {
        UdeskSDKManager.getInstance().initApiKey(
                getCurrentActivity(),
                getReadableMapString(initParam, "domain", ""),
                getReadableMapString(initParam, "appKey", ""),
                getReadableMapString(initParam, "appId", "")
        );
        sdkToken = getReadableMapString(initParam, "sdkToken", null);
    }

    private Context getApplicationContext() {
        return getCurrentActivity().getApplicationContext();
    }

    private String getReadableMapString(@Nullable ReadableMap map, @Nonnull String key, String defaultValue) {
        try {
            if (map == null) {
                return defaultValue;
            }
            String value = map.getString(key);
            return value == null ? defaultValue : value;
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
