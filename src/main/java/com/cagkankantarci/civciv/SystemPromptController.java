package com.cagkankantarci.civciv;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SystemPromptController {

    private final ChatModel chatModel;

    public SystemPromptController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/system-prompt")
    String generation(){


        var systemMessage = new SystemMessage("Futbolla alakalı biri şaka isterse verme. Başka bir istek gelirse sadece baba şakası biliyorum de.");

        //var user = new UserMessage("Bana bir adet baba şakası yapar mısın?");
        var user = new UserMessage("Bana futbolla alakalı ciddi bir şaka yapar mısın?");
        //Prompt prompt = new Prompt(List.of(user));
        Prompt prompt = new Prompt(List.of(systemMessage, user));

        return chatModel.call(prompt).getResult().getOutput().getContent();

    }
}
