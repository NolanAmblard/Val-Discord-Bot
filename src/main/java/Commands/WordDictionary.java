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
            String request = Jsoup.connect("https://api.dictionaryapi.dev/api/v2/entries/en/" + URLEncoder.encode(content, "UTF-8")).userAgent("Chrome").ignoreContentType(true).execute().body();
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
