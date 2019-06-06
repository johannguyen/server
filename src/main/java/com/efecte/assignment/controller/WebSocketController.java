package com.efecte.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.efecte.assignment.model.Post;
import com.efecte.assignment.service.PostService;
import com.google.gson.Gson;

@Controller
@CrossOrigin("*")
public class WebSocketController {


	@Autowired
	private PostService postService;


	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
    

    @MessageMapping("/post")
    @SendTo("/topic/post")
	public String processMessageFromClient(@Payload String message) throws Exception {
    	Post postFromMessage = new Gson().fromJson(message, Post.class);
    	Post postRes = null;
    	if(postFromMessage.getId() != null) {
    		Post p = postService.getById(postFromMessage.getId());
			if(p != null) {
				p.setColor(postFromMessage.getColor());
				p.setMessage(postFromMessage.getMessage());
			}
			postService.updatePost(p);
    	}
    	else {
    		postRes = postService.createPost(postFromMessage);
    	}
    	String response = new Gson().toJson(postRes);
		return response;
	}
	
	@MessageExceptionHandler
    public String handleException(Throwable exception) {
        messagingTemplate.convertAndSend("/errors", exception.getMessage());
	    return exception.getMessage();
    }
}
