package com.jiangying.utils;
public class ContentUtil {

    // 默认欢迎语不再使用 static 变量
    private static final String DEFAULT_CONTENT = "欢迎使用AI小助手，请输入您要咨询的问题";

    public static String getContent(String fromUserName, String toUserName, String content) {
        content = (content == null || content.isEmpty()) ? DEFAULT_CONTENT : content;
        return "<xml>\n" +
                "  <ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[" + toUserName + "]]></FromUserName>\n" +
                "  <CreateTime>12345678</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[" + content + "]]></Content>\n" +
                "</xml>";
    }
}
