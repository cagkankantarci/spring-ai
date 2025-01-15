package com.cagkankantarci.civciv;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimplePromptController {

    private final ChatModel chatModel;

    public SimplePromptController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/simple-prompt")
    String generation(String userInput){

        return chatModel.call(new Prompt(userInput)).getResult().getOutput().getContent();
    }

}
