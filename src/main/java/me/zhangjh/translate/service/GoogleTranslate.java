package me.zhangjh.translate.service;

import com.google.cloud.translate.v3.*;
import me.zhangjh.translate.constant.GGLanguage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjh
 * @date 2022/10/5
 */
public class GoogleTranslate {

    private final String projectId;

    {
        projectId = System.getenv("GG_projectId");
    }

    // Translate text to target language.
    private TranslateTextResponse translateText(String projectId, String targetLanguage, String text)
            throws IOException {

        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (TranslationServiceClient client = TranslationServiceClient.create()) {
            // Supported Locations: `global`, [glossary location], or [model location]
            // Glossaries must be hosted in `us-central1`
            // Custom Models must use the same location as your model. (us-central1)
            LocationName parent = LocationName.of(projectId, "global");

            // Supported Mime Types: https://cloud.google.com/translate/docs/supported-formats
            TranslateTextRequest request =
                    TranslateTextRequest.newBuilder()
                            .setParent(parent.toString())
                            .setMimeType("text/plain")
                            .setTargetLanguageCode(targetLanguage)
                            .addContents(text)
                            .build();

            return client.translateText(request);
        }
    }

    public List<Map<String, String>> getLanguages() {
        return GGLanguage.getLanguages();
    }

    public TranslateTextResponse translateText(String to, String text) throws IOException {
        TranslateTextResponse response = this.translateText(projectId, to, text);

        // Display the translation for each input text provided
        for (Translation translation : response.getTranslationsList()) {
            System.out.printf("Translated text: %s\n", translation.getTranslatedText());
        }

        return response;
    }
}
