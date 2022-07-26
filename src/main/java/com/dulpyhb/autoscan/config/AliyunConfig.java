package com.dulpyhb.autoscan.config;

public class AliyunConfig {

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    public static String ENDPOINT = "https://oss-cn-shanghai.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    public static String ACCESSKEYID = "LTAI5tPBtTfbfWgdEt7urh4z";
    public static String ACCESSKEYSECRET = "9UPEHMA0ENddcwqOh05rbzYbfaBhmr";
    // 填写Bucket名称，例如examplebucket。
    public static String BUCKETNAME = "food-database";

    public static String DOWNLOADDIR = "generate";
}
