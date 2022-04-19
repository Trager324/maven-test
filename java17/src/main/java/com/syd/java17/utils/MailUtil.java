package com.syd.java17.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author SYD
 * @description 邮件发送工具
 * @date 2021/7/31
 */
public class MailUtil {

    private static final String MAIL_USER = "nsxl@ncec.edu.cn";
    private static final String MAIL_PWD = "Nsdn@1234";
//    private static final String MAIL_AUTH = "pYhhYYxHJSf5Rokk";
    private static final String MAIL_HOST = "smtp.exmail.qq.com";
    //创建一个配置文件保存并读取信息
    private static final Properties properties = new Properties();

    static {
        //设置qq邮件服务器
        properties.setProperty("mail.host", MAIL_HOST);
        //设置发送的协议
        properties.setProperty("mail.transport.protocol", "smtp");
        //设置用户是否需要验证
        properties.setProperty("mail.smtp.auth", "true");
        //=================================只有QQ存在的一个特性，需要建立一个安全的链接
        // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
    }

    /**
     * @description 获取session
     * @author SYD
     * @date 2021/7/31
     */
    private static Session getSession() {
        //1.创建一个session会话对象；
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("nsxl@ncec.edu.cn", MAIL_PWD);
            }
        });
        //可以通过session开启Dubug模式，查看所有的过程
        session.setDebug(false);
        return session;
    }

    /**
     * @description String或String[]类型转换为String
     * @author SYD
     * @date 2021/7/31
     */
    private static String[] cast2Strings(Object val) {
        String[] res;
        if (val instanceof String) {
            res = new String[]{(String) val};
        } else if (val instanceof String[]) {
            res = (String[]) val;
        } else {
            throw new RuntimeException("Illegal type");
        }
        return res;
    }

    /**
     * @description 接收String或String[]类型的参数，进行类型转换
     * receivers，urls，originNames可以为String或String[]类型
     * urls的长度要和originNames相等
     * @author SYD
     * @date 2021/7/31
     */
    public static void sendMail(
            Object receivers, Object urls, Object originNames,
            String subject, String content) throws MessagingException, UnsupportedEncodingException {
        String[] receiversVal = cast2Strings(receivers);
        String[] urlsVal = cast2Strings(urls);
        String[] originNamesVal = cast2Strings(originNames);
        if (originNamesVal.length != urlsVal.length) {
            throw new RuntimeException("Array length mismatch");
        }
        sendMail(receiversVal, urlsVal, originNamesVal, subject, content);
    }

    /**
     * @description 在上一个函数的基础上省略了原文件名，采用了文件路径的名称
     * @author SYD
     * @date 2021/7/31
     */
    public static void sendMail(
            Object receivers, Object urls, String subject,
            String content) throws MessagingException, UnsupportedEncodingException {
        String[] receiversVal = cast2Strings(receivers);
        String[] urlsVal = cast2Strings(urls);
        String[] originNamesVal = new String[urlsVal.length];
        for (int i = 0; i < urlsVal.length; i++) {
            String url = urlsVal[i];
            int idx = url.lastIndexOf("/");
            idx = idx == -1 ? url.lastIndexOf("\\") : idx;
            originNamesVal[i] = url.substring(idx + 1);
        }
        sendMail(receiversVal, urlsVal, originNamesVal, subject, content);
    }

    /**
     * receivers: 接收人，可以多个
     * urls: 附件地址，可多个
     * originNames: 文件原始名称，需要和urls对应
     * subject: 邮件主题
     * content: 邮件正文
     * @author SYD
     * @date 2021/7/31
     */
    public static void sendMail(
            String[] receivers, String[] urls, String[] originNames,
            String subject, String content) throws MessagingException, UnsupportedEncodingException {
        Session session = getSession();
        //2.获取连接对象，通过session对象获得Transport，需要捕获或者抛出异常；
        Transport tp = session.getTransport();
        //3.连接服务器,需要抛出异常；
        tp.connect(MAIL_HOST, MAIL_USER, MAIL_PWD);
        //4.连接上之后我们需要发送邮件；
        MimeMessage mimeMessage = setMessage(session, receivers, urls, originNames, subject, content);
        //5.发送邮件
        tp.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        //6.关闭连接
        tp.close();
    }

    public static MimeMessage setMessage(
            Session session, String[] receivers, String[] urls, String[] originNames,
            String subject, String content) throws MessagingException, UnsupportedEncodingException {
        //消息的固定信息
        MimeMessage mimeMessage = new MimeMessage(session);
        //邮件发送人
        mimeMessage.setFrom(new InternetAddress(MAIL_USER));

        //邮件接收人，可以同时发送给很多人；
        Address[] recipients = new Address[receivers.length];
        for (int i = 0; i < receivers.length; i++) {
            recipients[i] = new InternetAddress(receivers[i]);
        }
        mimeMessage.setRecipients(Message.RecipientType.TO, recipients);

        mimeMessage.setSubject(subject); //邮件主题

        //文本
        MimeBodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(content,"text/html;charset=utf-8");

//        //拼装邮件正文内容
//        MimeMultipart multipart = new MimeMultipart();
//        multipart.addBodyPart(contentPart);
//        multipart.setSubType("related"); //1.文本和图片内嵌成功！

//        //new MimeBodyPart().setContent(multipart); //将拼装好的正文内容设置为主体
//        MimeBodyPart contentText =  new MimeBodyPart();
//        contentText.setContent(multipart);


        //拼接附件
        MimeMultipart result = new MimeMultipart();

        for (int i = 0; i < urls.length; i++) {
            //附件
            MimeBodyPart file = new MimeBodyPart();
            file.setDataHandler(new DataHandler(new FileDataSource(urls[i])));
            file.setFileName(MimeUtility.encodeText(originNames[i])); //附件设置名字
            result.addBodyPart(file);
        }

        result.addBodyPart(contentPart);//正文
        result.setSubType("mixed"); //正文和附件都存在邮件中，所有类型设置为mixed；

        //放到Message消息中
        mimeMessage.setContent(result);
        mimeMessage.saveChanges();//保存修改

        return mimeMessage;
    }

    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
        sendMail(MAIL_USER, "C:\\Users\\asus\\Desktop\\1.txt",
                "测试邮件标题", "测试邮件内容");
//        sendMail(MAIL_USER, "C:\\Users\\asus\\Desktop\\1.txt", "测试文件.txt",
//                "测试邮件标题", "测试邮件内容");
    }
}
