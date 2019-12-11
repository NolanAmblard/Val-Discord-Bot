//Lawrence Zhang
package Commands;

import Bot.Commands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.time.LocalDateTime;
import java.util.*;

public class SetTimer implements Commands {

    //Execution of the message
    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {

        Message m = event.getMessage();
        User author = m.getAuthor();
        String[] content = m.getContentRaw().toLowerCase().split(" ");
        MessageChannel channel = event.getChannel();
        JDA jda = event.getJDA();
        User val = jda.getSelfUser();

        try {
            if (content.length == 2) {
                String[] userTimes = content[1].split(":");

                Thread thread = new Thread();
                MessageHistory history = new MessageHistory(channel);
                TextChannel textChannel = event.getTextChannel();

                LocalDateTime time = LocalDateTime.now();

                String hours = Integer.toString(time.getHour());
                String minutes = Integer.toString(time.getMinute());
                String seconds = Integer.toString(time.getSecond());

                String[] currentTimes = new String[3];
                currentTimes[0] = hours;
                currentTimes[1] = minutes;
                currentTimes[2] = seconds;

                int startTime = getTotalTime(currentTimes);
                int endTime = startTime + getTotalTime(userTimes);
                int currentTime = endTime - startTime;

                //Troubleshooting
                System.out.println(startTime);
                System.out.println(endTime);
                System.out.println(currentTime);

                for (int i = endTime; i >= startTime; i--) {
                    currentTime = i - startTime;

                    //Troubleshooting
                    System.out.println(currentTime);

                    channel.sendMessage(convertTimeToString(currentTime)).queue();

                    thread.sleep(1000);

                    channel.deleteMessageById(channel.).queue();

                    if (i < endTime) {
                        String id;

                        //Troubleshooting
                        System.out.println("I've arrived here!");

                        List<Message> messages = history.retrievePast(10).complete();

                        for (int j = messages.size() - 1; j >= 0; j--) {
                            if (messages.get(j).getAuthor().getId().equals(val.getId())) {
                                id = messages.get(j).getId();

                                //Troubleshooting
                                System.out.println("Message Author ID: " + messages.get(j).getAuthor().getId());
                                System.out.println("Val ID: " + val.getId());
                                System.out.println("Message ID: " + id);

                                textChannel.deleteMessageById(id).queue();

                                break;
                            }
                        }
                    }
                }

                channel.sendMessage("Time's up!").queue();
            }
            else if (content.length > 2) {

            }
        }
        catch (InterruptedException e) {
            channel.sendMessage("Oops! Something went wrong.").queue();
            channel.sendMessage("The error received is: " + e.getMessage()).queue();
        }
    }

    @Override
    public String getKeyword() {
        return "SetTimer";
    }

    private int getTotalTime(String[] times) {
        int time = 0;

        int multiplier = (int) Math.pow(60, (times.length - 1));

        for (String s : times) {
            time += Double.parseDouble(s) * multiplier;
            multiplier /= 60;
        }

        return time;
    }

    private String convertTimeToString(int time) {
        String timeToString;

        if (time % 3600 == time) {
            if (time % 60 == time) {
                 timeToString = "00:00:" + time;
            }
            else {
                String minutes = Double.toString( (double) time / 60);

                String seconds = "";

                if (minutes.contains(".")) {
                    String[] temp = minutes.split("[.]");

                    seconds = Double.toString(Math.round(Double.parseDouble("." + temp[1]) * 60));

                    if (seconds.substring(0, 2).equals("60")) {
                        seconds = "00" + seconds.substring(2);
                        minutes = Double.toString(Double.parseDouble(minutes) + 1);
                    }

                    timeToString = "00:" + minutes.substring(0, 2) + ":" + seconds.substring(0, 2);
                }
                else {
                    if (minutes.substring(0, minutes.indexOf(".")).length() == 1) {
                        timeToString =  "00:0" + minutes.substring(0, 2) + ":00";
                    }
                    else {
                        timeToString = "00:" + minutes.substring(0, 2) + ":00";
                    }
                }
            }

            return timeToString;
        }

        String hours = Double.toString((double) time / 3600);

        //Troubleshooting
        System.out.println(hours);

        String minutes;
        String seconds;

        if (hours.contains(".")) {
            String[] temp1 = hours.split("[.]");

            //Troubleshooting
            System.out.println(temp1);

            minutes = Double.toString(Double.parseDouble("." + temp1[1]) * 60);

            if (minutes.substring(0, 2).equals("60")) {
                minutes = "00" + minutes.substring(2);
                hours = Double.toString(Double.parseDouble(hours) + 1);
            }

            if (minutes.contains(".")) {
                String[] temp2 = minutes.split("[.]");

                seconds = Double.toString(Math.round(Double.parseDouble("." + temp2[1]) * 60));

                if (seconds.substring(0, 2).equals("60")) {
                    seconds = "00" + seconds.substring(2);
                    minutes = Double.toString(Double.parseDouble(minutes) + 1);
                }
                if (minutes.substring(0, 2).equals("60")) {
                    minutes = "00" + minutes.substring(2);
                    hours = Double.toString(Double.parseDouble(hours) + 1);
                }

                timeToString = hours.substring(0, hours.indexOf(".")) + ":" + minutes.substring(0, 2) + ":" + seconds.substring(0, 2);
            }
            else {
                if (minutes.substring(0, minutes.indexOf(".")).length() == 1) {
                    timeToString = hours.substring(0, hours.indexOf(".")) + ":0" + minutes.substring(0, 2) + ":00";
                }
                else {
                    timeToString = hours.substring(0, hours.indexOf(".")) + ":" + minutes.substring(0, 2) + ":00";
                }
            }
        }
        else {
            if (hours.substring(0, hours.indexOf(".")).length() == 1) {
                timeToString = "0" + hours.substring(0, hours.indexOf(".")) + ":00:00";
            }
            else {
                timeToString = hours.substring(0, hours.indexOf(".")) + ":00:00";
            }
        }

        return timeToString;
    }
}