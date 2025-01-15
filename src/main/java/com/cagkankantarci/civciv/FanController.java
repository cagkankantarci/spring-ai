package com.cagkankantarci.civciv;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class FanController {

    private ChatModel chatModel;
    public FanController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/fan")
    String findHighestPaidPersonByGenre(@RequestParam(defaultValue = "futbol") String genre) {

        String message =  "{genre} dalında en yüksek maaşa sahip olan kişiler ve maaşı şeklinde birlikte getir eğer cevabı bilmiyorsan " +
                "google amcaya sormamı iste.";

        PromptTemplate promptTemplate = new PromptTemplate(message);

        Prompt prompt = promptTemplate.create(Map.of("genre", genre));

        return chatModel.call(prompt).getResult().getOutput().getContent();
    }

    @GetMapping("/fan-output-parser")
    Map<String, Object> findHighestPaidPersonByGenreWithOutputParser(@RequestParam(defaultValue = "futbol") String genre) {

        String message =  "{genre} dalında en yüksek maaşa sahip olan kişiler ve maaşı şeklinde birlikte getir eğer cevabı bilmiyorsan " +
                "google amcaya sormamı iste. {format}";

        //ListOutputConverter outputConverter = new ListOutputConverter(new DefaultConversionService());
        MapOutputConverter outputConverter = new MapOutputConverter();
        String format = outputConverter.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(message,Map.of("genre", genre, "format", format));

        Prompt prompt = promptTemplate.create();

        String response = chatModel.call(prompt).getResult().getOutput().getContent();

        return outputConverter.convert(response);
    }

}
