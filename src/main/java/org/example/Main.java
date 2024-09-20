package org.example;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.output.Response;
import io.github.adamw7.client.configuration.ConfigurationService;
import io.github.adamw7.client.services.AiConnector;
import io.github.adamw7.orchestrator.ai.AiService;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        ConfigurationService configuration = AiConnector.initateConfigurationService();
        AiService aiService1 = AiConnector.provideAi(configuration);
        ChatMessage message1 = new UserMessage("What do you think about this code?");
        ChatMessage message5 = new UserMessage(Files.readString(Path.of("PATH_TO_PROJECT_FILE https://github.com/pkaczoro/aigroup3toimprove")));

        List<ChatMessage> chatWithAi = new ArrayList<>(Arrays.asList(message1, message5));
        Response<AiMessage> response = aiService1.generate(chatWithAi).orElseThrow(()-> new NoSuchElementException("No response from AI"));System.out.println(response.content().text());
    }

}