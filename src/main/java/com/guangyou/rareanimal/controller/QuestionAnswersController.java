package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.PublishQuestionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xukai
 * @create 2023-03-30 19:30
 */
@Slf4j
@RestController
@RequestMapping("/questionAnswers")
@Api(tags = "问答系统")
public class QuestionAnswersController {


    @ApiOperation(value = "用户提出问答",notes = "用户登录后可提出问答（需要传jwt）")
    @PostMapping("/publishQuestion")
    public Result publishQuestion(@RequestBody PublishQuestionDto publishQuestionDto){
        return null;
    }


}
