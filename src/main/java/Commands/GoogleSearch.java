//Lawrence Zhang
package Commands;

import Bot.Commands;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class GoogleSearch implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        Message m = event.getMessage();
        String raw = m.getContentRaw();
        String content = raw.substring(raw.indexOf(" ") + 1);
        MessageChannel channel = event.getChannel();

        try {
            Elements links = Jsoup.connect("http://www.google.com/search?q=" + URLEncoder.encode(content, "UTF-8")).userAgent("Chrome").get().select("a > h3");

            int i = 0;

            for (Element link : links) {
                if (i == 5) {
                    break;
                }
                else {
                    i++;
                }
                String title = link.text();
                String url = link.parentNode().toString(); // URL FORMAT: "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>"
                url = URLDecoder.decode(url.substring(url.indexOf('=', url.indexOf('=') + 1) + 1, url.indexOf('&')), "UTF-8");

                if (!url.startsWith("http")) {
                    continue; // Ads/news/etc and other crap.
                }

                channel.sendMessage(title + " | " + url).queue();
            }
        }
        catch (Exception e) {
            channel.sendMessage("The operation includes invalid symbols.").queue();
            channel.sendMessage("The error was: " + e.getMessage()).queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Search";
    }
}
