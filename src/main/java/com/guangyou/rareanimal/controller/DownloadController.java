package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author xukai
 * @create 2023-03-14 21:20
 */
@Slf4j
@RestController
@RequestMapping("/download")
@Api(tags = "用于下载的接口")
public class DownloadController {


//    @GetMapping("/glb/{id}")
//    public Result glb(@PathVariable("id") Integer animalId) throws IOException {
//        String glbModePath = "./models/" + animalId + ".glb";
//        File file = new File(glbModePath);
//
//        byte[] data = Files.readAllBytes(file.toPath());
//        return Result.succ(200, "glb模型", ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(data));
//    }


}
