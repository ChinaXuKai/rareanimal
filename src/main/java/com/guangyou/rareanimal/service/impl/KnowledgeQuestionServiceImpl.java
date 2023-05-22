package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.mapper.CategoryMapper;
import com.guangyou.rareanimal.mapper.KnowledgeQuestionMapper;
import com.guangyou.rareanimal.mapper.KnowledgeQuestionTypeMapper;
import com.guangyou.rareanimal.pojo.KnowledgeQuestion;
import com.guangyou.rareanimal.pojo.KnowledgeQuestionType;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.dto.SubmitOptionDto;
import com.guangyou.rareanimal.pojo.vo.KnowledgeQuestionVo;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;
import com.guangyou.rareanimal.service.KnowledgeQuestionService;
import com.guangyou.rareanimal.utils.CopyUtils;
import com.guangyou.rareanimal.utils.ShiroUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author xukai
 * @create 2023-05-20 11:19
 */
@Service
public class KnowledgeQuestionServiceImpl implements KnowledgeQuestionService {


    @Autowired
    private KnowledgeQuestionMapper knowledgeQuestionMapper;
    @Autowired
    private CopyUtils copyUtils;

    @Override
    public Result getQuestionByPageAndType(PageDto pageDto, Long questionTypeId) {
        PageDataVo<KnowledgeQuestionVo> pageDataVo = new PageDataVo<>();
        //1 根据问题类型和分页条件 获取 知识问题集合
        List<KnowledgeQuestion> knowledgeQuestionList =
                knowledgeQuestionMapper.getQuestionByPageAndType(pageDto.getPageSize() * (pageDto.getPage() - 1), pageDto.getPageSize(), questionTypeId);
            //若 知识问题集合 元素个数为0，则说明当前问题类型还没有问题
        if (knowledgeQuestionList.isEmpty()) {
            return Result.succ("当前问题中类型还没有问题");
        }

        //2 转换为 知识问题vo集合
        List<KnowledgeQuestionVo> knowledgeQuestionVoList = copyUtils.knowledgeQuestionListCopy(knowledgeQuestionList);
        //3 赋值 pageDataVo
        pageDataVo.setPageData(knowledgeQuestionVoList);
            //设置 所属问题类型下的问题总数（total）、每页显示数量（size）、当前第几页（current）、总共有多少页数据（pages）
        pageDataVo.setCurrent(pageDto.getPage());
        pageDataVo.setSize(pageDto.getPageSize());
        int total = knowledgeQuestionMapper.selectCount(new LambdaQueryWrapper<KnowledgeQuestion>()
                .eq(KnowledgeQuestion::getQuestionTypeId,questionTypeId)).intValue();
        pageDataVo.setTotal(total);
        int isRemainZero = total%pageDto.getPageSize();
        if (isRemainZero != 0){
            pageDataVo.setPages( (total/pageDto.getPageSize()) + 1);
        }else {
            pageDataVo.setPages( total/pageDto.getPageSize() );
        }

        return Result.succ(pageDataVo);
    }


    @Autowired
    private KnowledgeQuestionTypeMapper knowledgeQuestionTypeMapper;

    @Override
    public Result getAllQuestionType() {
        return Result.succ(knowledgeQuestionTypeMapper.selectList(null));
    }


    @Override
    public Result submitQuestionOption(List<SubmitOptionDto> submitOptionDtoList) {
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            return Result.fail(Result.FORBIDDEN,"当前还没登录，请登录后再答题哦",null);
        }
        //for循环 单个问题检验
        for (SubmitOptionDto submitOptionDto : submitOptionDtoList) {
            //如果回答正确，则
            verifyQuestion(submitOptionDto, userId);
        }
        return null;
    }

    /**
     * 根据 提交问题的dto 判断 该用户是否回答正确
     * @param submitOptionDto 提交问题的dto
     * @param userId 用户id
     * @return 返回true表示回答正确，返回false表示回答错误
     */
    private void verifyQuestion(SubmitOptionDto submitOptionDto,Integer userId){
        //根据 知识问题id 查询该问题
        Long questionId = submitOptionDto.getKnowledgeQuestionId();
        KnowledgeQuestion knowledgeQuestion = knowledgeQuestionMapper.selectOne(new LambdaQueryWrapper<KnowledgeQuestion>()
                .eq(KnowledgeQuestion::getKnowledgeQuestionId, questionId));
        //若该问题的正确答案和用户提交的选项一致，即回答正确
        if (knowledgeQuestion.getCorrectOption().equals(submitOptionDto.getUserOption())) {
            //

        }
    }

}
