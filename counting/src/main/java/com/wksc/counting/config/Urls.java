package com.wksc.counting.config;

/**
 * Created by Administrator on 2016/7/2.
 */
public class Urls {
    public static final String BASE_URL = "http://mbasys.1919.cn:8880/gw?cmd=";
//    public static final String BASE_URL = "http://bpb.1919.cn/ea/gw?cmd=";
//    public static final String BASE_URL = "http://118.123.209.13:8880/gw?cmd=";
    public static final String BASE_INFO = BASE_URL+"appGetBaseInfo";
    public static final String LOGIN = BASE_URL+"memberLogin";
    public static final String COREINDEX = BASE_URL+"appCoreIndex";
    public static final String COREDETAIL = BASE_URL+"appCoreDetails";
    public static final String TOPICINDEX = BASE_URL +"appTopicIndex";
    public static final String STORS = BASE_URL+"appGetShopByName";
    public static final String GET_MOBILE_VALID_CODE = BASE_URL+"getMobileValidCode";
    public static final String SET_NODE_RULE = BASE_URL+"appSetNodeRule";
    public static final String GET_ARTICLES = BASE_URL+"appArticleList";
    public static final String MODIFY_PASSWORD = BASE_URL+"updateLoginMemberPassword";
    public static final String UPDATEINFO = "http://mbasys.1919.cn:8880/app/update.json";

}
