package com.guangyou.rareanimal.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.request.SessionScope;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author xukai
 * @create 2022-11-28 22:20
 */
@SpringBootTest
@Slf4j
class FtpUtilTest {

    private String host = "120.78.75.230";
    private String userName = "root";
    private String password = "2577715138qwer";
    private int port = 22;
    private int timeout = 50000;


    // 测试代码
    public static void main(String[] args){
        try{
            Session session = versouSshUtil("120.78.75.230", "root", "2577715138qwer", 22, 50000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 连接远程服务器
     * @param host ip地址
     * @param userName 登录名
     * @param password 密码
     * @param port 端口
     * @throws Exception
     */
    public static Session versouSshUtil(String host,String userName,String password,int port,int timeout) throws Exception{
        log.info("尝试连接到....host:" + host + ",username:" + userName + ",password:" + password + ",port:"
                + port);
        JSch jsch = new JSch(); // 创建JSch对象
        Session session = jsch.getSession(userName, host, port);// 根据用户名，主机ip，端口获取一个Session对象
        session.setPassword(password); // 设置密码
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        return session;
    }
    /**
     * 在远程服务器上执行命令
     * @param cmd 要执行的命令字符串
     * @param charset 编码
     * @throws Exception
     */
    public static void runCmd(String cmd,String charset,Session session) throws Exception{
        ChannelExec channelExec = (ChannelExec)session.openChannel("exec");
        channelExec.setCommand(cmd);
        channelExec.setInputStream(null);
        channelExec.setErrStream(System.err);
        channelExec.connect();
        InputStream in = channelExec.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));
        String buf = null;
        while ((buf = reader.readLine()) != null){
            System.out.println(buf);
        }
        reader.close();
        channelExec.disconnect();
    }

    @Test
    void putImages() {


    }
}