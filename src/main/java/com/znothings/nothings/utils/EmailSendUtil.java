package com.znothings.nothings.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * 邮件发送工具类
 *   RFC882文档规定了如何编写一封简单的邮件(纯文本邮件)，一封简单的邮件包含邮件头和邮件体两个部分，邮件头和邮件体之间使用空行分隔。
 *   MIME协议是对RFC822文档的升级和补充，它描述了如何生产一封复杂的邮件。通常我们把MIME协议描述的邮件称之为MIME邮件。MIME协议描述的数据称之为MIME消息。
 *   对于一封复杂邮件，如果包含了多个不同的数据，MIME协议规定了要使用分隔线对多段数据进行分隔，并使用Content-Type头字段对数据的类型、以及多个数据之间的关系进行描述。
 *   @author zenghh
 *   @email 625111833@qq.com
 *   @date 2018年9月17日 11:39:17
 */
public class EmailSendUtil {
    /** 发送邮箱的主机*/
    public static final String HOST="smtp.qq.com";
    /** 发送邮箱协议
     * 1、SMTP协议：全称为 Simple Mail Transfer Protocol，简单邮件传输协议。它定义了邮件客户端软件和SMTP邮件服务器之间，以及两台SMTP邮件服务器之间的通信规则。
     * 2、POP3协议：全称为 Post Office Protocol，邮局协议。它定义了邮件客户端软件和POP3邮件服务器的通信规则。
     * 3、IMAP协议：全称为 Internet Message Access Protocol,Internet消息访问协议，它是对POP3协议的一种扩展，也是定义了邮件客户端软件和IMAP邮件服务器的通信规则。
     * */
    public static final String PROTOCOL="smtp";
    /** 是否需要验证账号密码*/
    public static final String AUTH="true";
    /** 发送方账号密码*/
    public static final String FROM="znothing@163.com";
    public static final String PASSWORD="com.znothings";
    /** 是否开启DEBUG模式*/
    private static Boolean DEBUG = true;

    public static boolean send() throws MessagingException {
        Properties properties = new Properties();
//        Properties properties = System.getProperties();
        properties.setProperty("mail.host",HOST);
        properties.setProperty("mail.transport.protocol",PROTOCOL);
        properties.setProperty("mail.smtp.auth",AUTH);

        //使用JavaMail发送邮件的5个步骤
        //1.创建session
        Session session =Session.getInstance(properties);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(DEBUG);
        //2.通过session获取transport对象
        Transport transport = session.getTransport();
        //3.连接上服务器
        transport.connect(HOST,FROM,PASSWORD);
        //4.创建邮件
        Message message = createMail(session);
        //整封邮件 = 邮件头+邮件体
        MimeMessage mimeMessage = new MimeMessage(session);
        //由多个MIME消息组合成的MIME消息
        MimeMultipart mimeMultipart = new MimeMultipart();
        //一个MIME消息
        MimeBodyPart mimeBodyPart=new MimeBodyPart();
        //5.发送邮件
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();

        return true;
    }

    /**
     * 创建一封邮件
     * subject字段  --用于说明邮件主题
     * from字段 　　--用于指明发件人
     * to字段 　　  --用于指明收件人
     * cc字段 　　  -- 抄送，将邮件发送给收件人的同时抄送给另一个收件人，收件人可以看到邮件抄送给了谁
     * bcc字段 　　 -- 密送，将邮件发送给收件人的同时将邮件秘密发送给另一个收件人，收件人无法看到邮件密送给了谁
     * @param session
     * @return
     * @throws MessagingException
     */
    public static Message createMail(Session session) throws MessagingException {
        //1.创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //2.指明发件人
        mimeMessage.setFrom(new InternetAddress(FROM));
        //3.指明收件人
//        mimeMessage.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(FROM)});
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(FROM));
        //4.邮件的标题
        mimeMessage.setSubject("简单的邮件发送");
        //5.邮件的内容
        mimeMessage.setContent("你好","text/html;charset=UTF-8");
        return mimeMessage;
    }
}
