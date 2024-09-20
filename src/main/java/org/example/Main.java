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
        String pathToTest = "";

        ConfigurationService configuration = AiConnector.initateConfigurationService();
        AiService aiService1 = AiConnector.provideAi(configuration);
        ChatMessage message1 = new UserMessage("You are Java expert. I want you to help me with TDD development");
        ChatMessage message2 = new UserMessage("I would like you to read the content of this interface and its unit tests, and to give me back the implementation.");
        ChatMessage message3 = new UserMessage("Interface code below:");
        ChatMessage message4 = new UserMessage(Files.readString(Path.of(pathToInterface)));
        ChatMessage message5 = new UserMessage("I would like you to implement this interface so that it will pass ALL the unit tests available in this class");
        ChatMessage message6 = new UserMessage("Test class code below:");
        ChatMessage message7 = new UserMessage(Files.readString(Path.of(pathToTest)));
        ChatMessage message8 = new UserMessage("I need that the output content you give me is JUST THE CODE");
        ChatMessage message9 = new UserMessage("The beginning of your response should be package org.example; and the ending of your response should be a closing bracket");

        List<ChatMessage> chatWithAi = new ArrayList<>(Arrays.asList(message1, message2, message3, message4, message5, message6, message7, message8, message9));
        Response<AiMessage> response = aiService1.generate(chatWithAi).orElseThrow(()-> new NoSuchElementException("No response from AI"));

        Files.writeString(Path.of(pathToImplementation),
                response.content().text(), StandardCharsets.UTF_8
        );

    }

}