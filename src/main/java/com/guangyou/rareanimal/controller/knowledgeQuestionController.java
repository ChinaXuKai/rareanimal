package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.dto.SubmitOptionDto;
import com.guangyou.rareanimal.service.KnowledgeQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xukai
 * @create 2023-05-20 11:13
 */
@Slf4j
@RestController
@RequestMapping("/knowledgeQuestion")
@Api(tags = "知识答题相关接口")
public class knowledgeQuestionController {

    @Autowired
    private KnowledgeQuestionService knowledgeQuestionService;

    @ApiOperation(value = "根据问题类型和分页条件获取知识问题")
    @GetMapping("/getQuestionByPageAndType")
    public Result getQuestionByPageAndType(PageDto pageDto, Long questionTypeId){
        return knowledgeQuestionService.getQuestionByPageAndType(pageDto, questionTypeId);
    }


    @ApiOperation(value = "获取所有的问题类型")
    @GetMapping("/getAllQuestionType")
    public Result getAllQuestionType(){
        return knowledgeQuestionService.getAllQuestionType();
    }


    @ApiOperation(value = "用户提交问题选项")
    @PostMapping("/submitQuestionOption")
    public Result submitQuestionOption(List<SubmitOptionDto> submitOptionDtoList){
        return knowledgeQuestionService.submitQuestionOption(submitOptionDtoList);
    }


}
