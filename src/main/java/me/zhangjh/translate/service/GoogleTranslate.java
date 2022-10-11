package me.zhangjh.translate.service;

import com.google.cloud.translate.v3.*;
import me.zhangjh.translate.constant.GGLanguage;
import me.zhangjh.translate.dto.TranslateRequest;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhangjh
 * @date 2022/10/5
 */
public class GoogleTranslate extends TranslateEngine<TranslateTextResponse> {

    private final String projectId;

    {
        projectId = System.getenv("GG_projectId");
    }

    // Translate text to target language.
    private TranslateTextResponse translateText(String projectId, String from, String targetLanguage, String text)
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
                            .setSourceLanguageCode(from)
                            .setTargetLanguageCode(targetLanguage)
                            .addContents(text)
                            .build();

            return client.translateText(request);
        }
    }

    public Map<String, String> getLanguages() {
        return GGLanguage.getLanguages();
    }

    @Override
    public TranslateTextResponse translateText(TranslateRequest request) throws IOException {
        TranslateTextResponse response = this.translateText(projectId, request.getFrom(), request.getTo(),
                request.getText());

        // Display the translation for each input text provided
        for (Translation translation : response.getTranslationsList()) {
            System.out.printf("Translated text: %s\n", translation.getTranslatedText());
        }

        return response;
    }
}
