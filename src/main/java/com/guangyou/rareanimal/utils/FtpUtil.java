package com.guangyou.rareanimal.utils;


import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

/**
 * ftp实现文件上传 nginx服务器
 */
@Component
@Slf4j
public class FtpUtil {

    //ftp服务器ip地址
    @Value("${ftp.host}")
    private String host;
    //端口
    @Value("${ftp.port}")
    private Integer port;
    //用户名
    @Value("${ftp.userName}")
    private String userName;
    //密码
    @Value("${ftp.password}")
    private String password;
    //连接的超时时间
    @Value("${ftp.timeout}")
    private Integer timeout;

    /**
     * 存放图片的根目录
     */
    @Value("${ftp.rootPath}")
    private String rootPath;

    /**
     * 存放图片的路径：返回给前端图片地址
     */
    @Value("${ftp.img.url}")
    private String imgUrl;


    //获取链接
    private ChannelSftp getChannel() throws JSchException {
        //设置打印类
//        JSch.setLogger(new SftpLogger());

        JSch jSch = new JSch();
        //->ssh root@host:port
        Session sshSession = jSch.getSession(userName,host,port);
        //密码
        sshSession.setPassword(password);
        //配置jsch跳过 主机公钥确认 StrictHostKeyChecking
        Properties sshConfig =new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);

        sshSession.connect(timeout);

        Channel channel=sshSession.openChannel("sftp");
        channel.connect();

        return (ChannelSftp) channel;
    }

    //创建目录
    private void createDir(String path,ChannelSftp sftp) throws SftpException {
        String[] folders=path.split("/");
        sftp.cd("/");
        for(String folder:folders){
            if(folder.length()>0){
                try{
                    sftp.cd(folder);
                }catch (SftpException e){
                    sftp.mkdir(folder);
                    sftp.cd(folder);
                }
            }
        }
    }


    /**
     * ftp上传文件
     * @param inputStream
     * @param imagePath
     * @param imagesName
     * @return
     * @throws JSchException
     * @throws SftpException
     */
    public String putImages(InputStream inputStream,String imagePath,String imagesName){
        try {
            //调用getChannel() 获取连接
            ChannelSftp sftp = getChannel();
            String path = rootPath + imagePath + "/";
            //创建目录
            createDir(path, sftp);

            //上传文件
            sftp.put(inputStream, path + imagesName);
            sftp.quit();
            sftp.exit();

            //处理返回的路径
            String resultFile;
            resultFile = imgUrl + imagePath + imagesName;

            log.info("path={}",path);
            log.info("path+imagesName={}",path + imagesName);
            log.info("resultFile={}",resultFile);
            return resultFile;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }


}


