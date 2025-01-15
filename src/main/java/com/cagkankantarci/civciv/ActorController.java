package com.cagkankantarci.civciv;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ActorController {

    private ChatModel chatModel;

    public ActorController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/actor/{actor}")
    Actor getActorsWithMovies(@PathVariable String actor){

        String promptMessage = "{actor} için 5 filmlik bir filmografi oluştur. {format}";

        var outputConverter = new BeanOutputConverter(Actor.class);
        String format = outputConverter.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(promptMessage, Map.of("actor", actor, "format", format));

        Prompt prompt = promptTemplate.create();

        Generation result = chatModel.call(prompt).getResult();

        return (Actor) outputConverter.convert(result.getOutput().getContent());
    }
}
