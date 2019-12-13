package Commands;
//Nolan Amblard did this
import Bot.Commands;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Mute implements Commands {

    private int counter = 0;

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (message.length == 1) {
            event.getChannel().sendMessage("Please include the person you want to mute's tag.").queue();
        }
        if (message.length == 2) {
            //String username = message[1];
            Member target = event.getMessage().getMentionedMembers().get(0);
            Role muted = target.getGuild().getRolesByName("Muted", true).get(0);
            event.getGuild().addRoleToMember(target, muted).queue();
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getEffectiveName() + " has been muted.").queue();
        }

        if (message.length == 3) {
            //String username = message[1];
            //String duration = message[2];
            Member target = event.getMessage().getMentionedMembers().get(0);
            tempmute(target, parseTime(message[2]), parseTimeUnit(message[2]));
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getEffectiveName() + " has been muted.").queue();
        }

        if (message.length == 4) {
            //String username = message[1];
            //String duration = message[2];
            String reason = message[3];
            Member target = event.getMessage().getMentionedMembers().get(0);
            tempmute(target, parseTime(message[2]), parseTimeUnit(message[2]));
            event.getChannel().sendMessage("User " + event.getMessage().getMentionedMembers().get(0).getEffectiveName() + " has been muted. Reason: " + reason).queue();
        }
    }

    private int parseTime(String time) {
        TimeUnit unit = TimeUnit.SECONDS;
        char[] t = time.toCharArray();
        int breakPoint = 0;
        String number = "";
        int parsedNumber = 0;
        for(int i = 0; i < t.length; i ++) {
            if(t[i] == 's' || t[i] == 'S'){
                unit = TimeUnit.SECONDS;
                breakPoint = i;
                break;
            }
            else if(t[i] == 'm' || t[i] == 'M'){
                unit = TimeUnit.MINUTES;
                breakPoint = i;
                break;
            }
            else if(t[i] == 'h' || t[i] == 'H'){
                unit = TimeUnit.HOURS;
                breakPoint = i;
                break;
            }
            else if(t[i] == 'd' || t[i] == 'D'){
                unit = TimeUnit.DAYS;
                breakPoint = i;
                break;
            }
        }

        for(int j = 0; j < breakPoint; j++){
            number += t[j];
        }
        parsedNumber = Integer.parseInt(number);
        return parsedNumber;
    }

    private TimeUnit parseTimeUnit(String time) {
        TimeUnit unit = TimeUnit.SECONDS;
        char[] t = time.toCharArray();
        int breakPoint = 0;
        StringBuilder number = new StringBuilder();
        int parsedNumber = 0;
        for(int i = 0; i < t.length; i ++) {
            if(t[i] == 's' || t[i] == 'S'){
                unit = TimeUnit.SECONDS;
                breakPoint = i;
                break;
            }
            else if(t[i] == 'm' || t[i] == 'M'){
                unit = TimeUnit.MINUTES;
                breakPoint = i;
                break;
            }
            else if(t[i] == 'h' || t[i] == 'H'){
                unit = TimeUnit.HOURS;
                breakPoint = i;
                break;
            }
            else if(t[i] == 'd' || t[i] == 'D'){
                unit = TimeUnit.DAYS;
                breakPoint = i;
                break;
            }
        }
        return unit;
    }

    private void tempmute(Member target, int time, TimeUnit unit){
        Role muted = target.getGuild().getRolesByName("Muted", true).get(0);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                counter++;
                target.getGuild().addRoleToMember(target, muted).queue();
                if (counter == 2) {
                    target.getGuild().removeRoleFromMember(target, muted).queue();
                    this.cancel();
                }

            }
        };
        switch(unit) {
            case SECONDS:
                timer.schedule(task, 0, time * 1000);
                break;

            case MINUTES:
                timer.schedule(task, 0, (time * 1000) * 60);
                break;

            case HOURS:
                timer.schedule(task, 0, (time * 1000) * 3600);
                break;

            case DAYS:
                timer.schedule(task, 0, (time * 1000) * 86400);
                break;
        }

    }

    @Override
    public String getKeyword() {
        return "Mute";
    }
}
