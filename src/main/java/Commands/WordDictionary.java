//Lawrence Zhang
package Commands;

import Bot.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import org.jsoup.Jsoup;
import com.google.gson.Gson;

import java.awt.*;
import java.net.URLEncoder;
import java.util.List;

public class WordDictionary implements Commands {

    private static final String[] languages = new String[] {"en", "hi", "es", "fr", "ja", "ru", "de", "it", "ko", "pt-BR", "ar", "tr"};

    static class Word {
        private String definition;
        private String word;
        private Meaning[] meanings;

        static class Meaning {
            private String partOfSpeech;
            private Definition[] definitions;

            static class Definition {
                private String definition;
                private String example;
                private String[] synonyms;
                private String[] antonyms;
            }
        }
    }

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        Message m = event.getMessage();
        String raw = m.getContentRaw();
        String content = raw.substring(raw.indexOf(" ") + 1);
        MessageChannel channel = event.getChannel();
        EmbedBuilder embedBuilder = new EmbedBuilder();

        try {
            String request;
            if (content.contains(";")) {
                String language = content.substring(content.indexOf(";") + 1).replaceAll("\\s", "");
                content = content.substring(0, content.indexOf(";"));
                request = Jsoup.connect("https://api.dictionaryapi.dev/api/v2/entries/" + language + "/" + URLEncoder.encode(content, "UTF-8")).userAgent("Chrome").ignoreContentType(true).execute().body();
            }
            else {
                request = Jsoup.connect("https://api.dictionaryapi.dev/api/v2/entries/en/" + URLEncoder.encode(content, "UTF-8")).userAgent("Chrome").ignoreContentType(true).execute().body();
            }
            request = request.substring(1, request.length() - 1);
            Word word = new Gson().fromJson(request, Word.class);

            embedBuilder.setTitle(word.word);

            for (int i = 0; i < word.meanings.length; i++) {

                StringBuilder stringBuilder = new StringBuilder();

                for (int j = 0; j < word.meanings[i].definitions.length; j++) {
                    stringBuilder.append(word.meanings[i].definitions[j].definition).append("\n");

                    if (word.meanings[i].definitions[j].example != null) {
                        stringBuilder.append("\"").append(word.meanings[i].definitions[j].example).append("\"");
                    }

                    if (word.meanings[i].definitions[j].synonyms != null && word.meanings[i].definitions[j].synonyms.length != 0) {
                        stringBuilder.append("\nSynonyms: ");
                        for (int k = 0; k < word.meanings[i].definitions[j].synonyms.length; k++) {
                            stringBuilder.append(word.meanings[i].definitions[j].synonyms[k]);
                            if (k != word.meanings[i].definitions[j].synonyms.length - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                    }

                    if (word.meanings[i].definitions[j].antonyms != null && word.meanings[i].definitions[j].antonyms.length != 0) {
                        stringBuilder.append("\nAntonyms: ");
                        for (int k = 0; k < word.meanings[i].definitions[j].antonyms.length; k++) {
                            stringBuilder.append(word.meanings[i].definitions[j].antonyms[k]);
                            if (k != word.meanings[i].definitions[j].antonyms.length - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                    }

                    if (j != word.meanings[i].definitions.length - 1) {
                        stringBuilder.append("\n\n");
                    }
                }

                embedBuilder.addField(word.meanings[i].partOfSpeech, stringBuilder.toString(), false);
            }

            embedBuilder.setColor(Color.CYAN);

            event.getChannel().sendMessage(embedBuilder.build()).queue();
        }
        catch (Exception e) {
            channel.sendMessage("Sorry, that's either not a word or an invalid input.").queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Define";
    }
}
