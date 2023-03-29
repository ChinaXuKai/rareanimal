//package com.guangyou.rareanimal.controller;
//
//import cn.hutool.core.io.resource.ClassPathResource;
//import com.guangyou.rareanimal.common.lang.Result;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.IOUtils;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ResourceUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.util.List;
//
///**
// * @author xukai
// * @create 2023-03-14 21:20
// */
//@Slf4j
//@RestController
//@RequestMapping("/download")
//@Api(tags = "用于下载的接口")
//public class DownloadController {
//
//
//    @ApiOperation(value = "通过动物id传输对应的glb文件",notes = "通过动物id传输对应的glb文件")
//    @GetMapping("/glb/{id}")
//    public Result glb(@PathVariable("id") Integer animalId) throws IOException {
//        String glbModePath = "./static/" + animalId + ".glb";
//        ClassPathResource classPathResource = new ClassPathResource(glbModePath);
//        File file = classPathResource.getFile();
//
//        byte[] data = Files.readAllBytes(file.toPath());
//        return Result.succ(200, "glb模型", ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(data));
//    }
//
//
//}
