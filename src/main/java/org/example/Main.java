package org.example;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.output.Response;
import io.github.adamw7.client.configuration.ConfigurationService;
import io.github.adamw7.client.services.AiConnector;
import io.github.adamw7.orchestrator.ai.AiService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        //pre-requisites: generate jar for project PrivateAiHandsOn3
        //clone: https://github.com/pkaczoro/aigroup3toimprove
        //modify the paths below with the absolute paths for interface PetStore in project aigroup3toimprove

        String pathToInterface = "";
        String pathToImplementation = "";

        ConfigurationService configuration = AiConnector.initateConfigurationService();
        AiService aiService1 = AiConnector.provideAi(configuration);
        ChatMessage message1 = new UserMessage("You are Java expert.");
        ChatMessage message2 = new UserMessage("I would like you to read the content of this interface, and to give me back the implementation.");
        ChatMessage message3 = new UserMessage("Name the resulting class GdanskPetStore and give me a list of animals that can be found in this pet store");
        ChatMessage message4 = new UserMessage("Do not explain the code.");
        ChatMessage message5 = new UserMessage("Code below:");
        ChatMessage message6 = new UserMessage(Files.readString(Path.of(pathToInterface)));
        ChatMessage message7 = new UserMessage("I need that the output content you give me is JUST THE CODE");
        ChatMessage message8 = new UserMessage("The beginning of your response should be package org.example; and the ending of your response should be a closing bracket");

        List<ChatMessage> chatWithAi = new ArrayList<>(Arrays.asList(message1, message2, message3, message4, message5, message6, message7, message8));
        Response<AiMessage> response = aiService1.generate(chatWithAi).orElseThrow(()-> new NoSuchElementException("No response from AI"));

        Files.writeString(Path.of(pathToImplementation),
                response.content().text(), StandardCharsets.UTF_8
        );

    }

}