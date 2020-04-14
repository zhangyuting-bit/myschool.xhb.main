package com.zb.feign;

import com.zb.pojo.Answer;
import com.zb.pojo.Comment;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "COMMENTSERVER")
public interface CommentFeginClient {

    @RequestMapping(value = "/addcomment")
    public int addcomment(@RequestBody Comment comment);

    @RequestMapping(value = "/somcomments")
    public List<Comment> somecomments(@RequestParam("recordId") String recordId,
                                      @RequestParam("recordType") Integer recordType);

    @RequestMapping(value = "/deletecomment")
    public int deletecomment(@RequestParam("id") String id);


    @RequestMapping(value = "/addAnswer")
    public int addAnswer(@RequestBody Answer answer);

    @RequestMapping(value = "/listCommentsAnswer")
    public List<Answer> listCommentsAnswer(@RequestParam("commentId") String commentId);


    @RequestMapping(value = "/deleteAnswer")
    public int deleteAnswer(@RequestParam("id") Integer id);

}
