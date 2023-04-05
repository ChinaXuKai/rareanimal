package com.guangyou.rareanimal.controller;

import cn.hutool.core.io.resource.ClassPathResource;
import com.guangyou.rareanimal.common.lang.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * @author xukai
 * @create 2023-03-14 21:20
 */
@Slf4j
@RestController
@RequestMapping("/transmit")
@Api(tags = "glb传输的接口（都不需要传jwt）")
public class TransmitController {

    @Value("${animal-model-glb.URL-location}")
    private String glbFile;

    @ApiOperation(value = "通过动物id传输对应的glb文件",notes = "通过动物id传输对应的glb文件")
    @GetMapping("/getGlbFile/{animalId}")
    public Result getGlbFile(@PathVariable("animalId") Integer animalId) throws IOException {
        String glbModePath = glbFile + "/" + animalId + ".glb";

        System.out.println("\n\n glbModePath:" + glbModePath + "\n\n");

        try (InputStream inputStream = new FileInputStream(glbModePath);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            byte[] glbData = outputStream.toByteArray();
            return Result.succ(200, "glb模型", ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(glbData));
        } catch (IOException e) {
            return Result.fail(e.getMessage());
        }

//        File glbFile = ResourceUtils.getFile(glbModePath);
//        ClassPathResource classPathResource = new ClassPathResource(glbModePath);
//        File glbFile = classPathResource.getFile();
//
//        byte[] data = Files.readAllBytes(glbFile.toPath());
//        return Result.succ(200, "glb模型", ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(data));
    }


}
