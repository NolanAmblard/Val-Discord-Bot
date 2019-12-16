//Lawrence Zhang
package Commands;

import Bot.Commands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.time.LocalDateTime;
import java.util.*;

public class SetTimer implements Commands {

    private int currentTime;

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
            String hours;
            String minutes;
            String seconds;
            String[] currentTimes;
            int startTime;
            int endTime;
            
            if (content.length == 2) {
                try {
                    String message = content[1];
                    Integer.parseInt(message.substring(0, 2));
                    Integer.parseInt(message.substring(3, 5));
                    Integer.parseInt(message.substring(6, 8));

                    if (message.charAt(2) != ':' && message.charAt(5) != ':') {
                        channel.sendMessage("The message contains invalid characters.").queue();

                        return;
                    }
                }
                catch (NumberFormatException | NullPointerException e) {
                    channel.sendMessage("The message contains invalid characters.").queue();

                    return;
                }

                String[] userTimes = content[1].split(":");

                LocalDateTime time = LocalDateTime.now();

                hours = Integer.toString(time.getHour());
                minutes = Integer.toString(time.getMinute());
                seconds = Integer.toString(time.getSecond());

                currentTimes = new String[3];
                currentTimes[0] = hours;
                currentTimes[1] = minutes;
                currentTimes[2] = seconds;

                startTime = getTotalTime(currentTimes);
                endTime = startTime + getTotalTime(userTimes);
                currentTime = endTime - startTime;

                //Troubleshooting
//                System.out.println(startTime);
//                System.out.println(endTime);
//                System.out.println(currentTime);

                String timer = createTimer(author, channel, val, startTime, endTime);

                if (timer.equalsIgnoreCase("start")) {
                    return;
                }
                else if (timer.equalsIgnoreCase("stop")) {
                    return;
                }
            }
            else if (content.length > 2) {

                LocalDateTime time = LocalDateTime.now();

                hours = Integer.toString(time.getHour());
                minutes = Integer.toString(time.getMinute());
                seconds = Integer.toString(time.getSecond());

                currentTimes = new String[3];
                currentTimes[0] = hours;
                currentTimes[1] = minutes;
                currentTimes[2] = seconds;

                startTime = getTotalTime(currentTimes);
                
                ArrayList<String> userTimes = new ArrayList<>();

                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 2; i < content.length; i += 2) {
                    String timeType = content[i];

                    if (Arrays.asList("hour", "hours", "hr", "hrs", "h", "minute", "minutes", "min", "mins", "m", "second", "seconds", "sec", "secs", "s").contains(timeType)) {
                        try {
                            userTimes.add(content[i - 1]);

                            stringBuilder.append(timeType);
                            stringBuilder.append(" ");
                        }
                        catch (NumberFormatException | NullPointerException e) {
                            channel.sendMessage("The message contains invalid characters.").queue();
                        }
                    }
                }

                String times = stringBuilder.toString();

                boolean hasMinutes = times.contains("minute") || times.contains("min") || times.contains("m");
                boolean hasSeconds = times.contains("second") || times.contains("sec") || times.contains("s");

                if (times.contains("hour") || times.contains("hr") || times.equalsIgnoreCase("h")) {
                    if (hasMinutes && hasSeconds) {
                        //Do nothing
                    }
                    else if (hasMinutes) {
                        userTimes.add("0");
                    }
                    else if (hasSeconds) {
                        userTimes.add(1, "0");
                    }
                    else {
                        userTimes.add("0");
                        userTimes.add("0");
                    }
                }
                else if (hasMinutes) {
                    userTimes.add(0, "0");

                    if (hasSeconds) {
                        //Do nothing
                    }
                    else {
                        userTimes.add("0");
                    }
                }
                else if (hasSeconds) {
                    userTimes.add(0, "0");
                    userTimes.add(0, "0");
                }

                String[] temp = new String[userTimes.size()];
                temp = userTimes.toArray(temp);

                endTime = startTime + getTotalTime(temp);
                currentTime = endTime - startTime;

                //Troubleshooting
                System.out.println("" + (endTime - startTime));

                String timer = createTimer(author, channel, val, startTime, endTime);

                if (timer.equalsIgnoreCase("start")) {
                    return;
                }
                else if (timer.equalsIgnoreCase("stop")) {
                    return;
                }
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

    private String createTimer(User author, MessageChannel channel, User self, int startTime, int endTime) throws InterruptedException {

        for (int i = endTime; i >= startTime; i--) {
            currentTime = i - startTime;

            //Troubleshooting
            System.out.println(currentTime);

            Thread.sleep(1000);

            if (i == endTime) {
                channel.sendMessage(convertTimeToString(currentTime)).queue();
            }
            else if (i < endTime) {

                //Troubleshooting
//                System.out.println("I've arrived here!");

                MessageHistory history = new MessageHistory(channel);
                List<Message> temp = history.retrievePast(10).complete();

                String functions = timerFunctions(author, channel, self, history, temp, currentTime);

                if (functions.equalsIgnoreCase("start")) {
                    return functions;
                }
                else if (functions.equalsIgnoreCase("stop")) {
                    return functions;
                }
            }
        }

        channel.sendMessage("Time's up!").queue();

        return "";
    }
    
    private String createTimer(User author, MessageChannel channel, User self, int currentTime) throws InterruptedException {
        
        int startTime = currentTime;
        
        for (int i = startTime; i >= 0; i--) {
            currentTime = startTime - (startTime - i);

            //Troubleshooting
            System.out.println(currentTime);

            Thread.sleep(1000);

            if (i == startTime) {
                channel.sendMessage(convertTimeToString(currentTime)).queue();
            }
            else if (i < startTime) {

                //Troubleshooting
//                System.out.println("I've arrived here!");

                MessageHistory history = new MessageHistory(channel);
                List<Message> temp = history.retrievePast(10).complete();

                String functions = timerFunctions(author, channel, self, history, temp, currentTime);

                if (functions.equalsIgnoreCase("start")) {
                    return functions;
                }
                else if (functions.equalsIgnoreCase("stop")) {
                    return functions;
                }
            }
        }

        channel.sendMessage("Time's up!").queue();

        return "";
    }
    
    private String timerFunctions(User author, MessageChannel channel, User self, MessageHistory history, List<Message> messages, int currentTime) {
        
        String id;

        for (int j = 0; j < messages.size(); j++) {
            boolean b = false;
            boolean pause = false;

            id = messages.get(j).getId();
            String messageRaw = history.getMessageById(id).toString();

            String message = messageRaw.substring(messageRaw.indexOf(":", 2) + 1, messageRaw.lastIndexOf("("));

            //Troubleshooting
//            System.out.println(message);

            if (messages.get(j).getAuthor().getId().equals(author.getId())) {
                if (message.equalsIgnoreCase("check time") || message.equalsIgnoreCase("get time")) {
                    channel.sendMessage(convertTimeToString(currentTime)).queue();
                }
                else if (message.equalsIgnoreCase("pause timer")) {
                    pause = true;

                    channel.sendMessage("The timer has been paused. Type \"start timer\" or \"unpause timer\" to start the timer again.").queue();
                }
                else if (message.equalsIgnoreCase("stop timer") || message.equalsIgnoreCase("end timer")) {
                    channel.sendMessage("The timer has been ended.").queue();

                    return "stop";
                }

                b = true;
            }

            while (pause) {
                history = new MessageHistory(channel);
                messages = history.retrievePast(10).complete();

                id = messages.get(j).getId();
                messageRaw = history.getMessageById(id).toString();

                message = messageRaw.substring(messageRaw.indexOf(":", 2) + 1, messageRaw.lastIndexOf("("));

                for (int l = 0; l < messages.size(); l++) {
                    if (message.equalsIgnoreCase("start timer") || message.equalsIgnoreCase("unpause timer")) {

                        try {
                            createTimer(author, channel, self, currentTime);
                        }
                        catch (InterruptedException e) {
                            channel.sendMessage("There was a problem with creating the timer.").queue();
                        }

                        return "start";
                    }
                }
            }

            if (!messages.get(j).getAuthor().getId().equalsIgnoreCase(self.getId())) {

                try {
                    createTimer(author, channel, self, currentTime);
                }
                catch (InterruptedException e) {
                    channel.sendMessage("There was a problem with creating the timer.").queue();
                }

                return "start";
            }

            if (messages.get(j).getAuthor().getId().equals(self.getId())) {

                //Troubleshooting
//                System.out.println("Message Author ID: " + messages.get(j).getAuthor().getId());
//                System.out.println("Val ID: " + self.getId());
//                System.out.println("Message ID: " + id);
//                System.out.println("Message: " + message);

                channel.editMessageById(id, convertTimeToString(currentTime)).queue();

                b = true;
            }

            if (b) {
                break;
            }
        }

        return "";
    }
    
    private int getTotalTime(String[] times) {
        
        int time = 0;

        int multiplier = (int) Math.pow(60, (times.length - 1));

        for (String s : times) {
            time += (int) Math.round(Double.parseDouble(s)) * multiplier;
            multiplier /= 60;
        }

        return time;
    }

    private String convertTimeToString(int time) {
        
        String timeToString;
        String minutes;
        String seconds;

        String[] temp;

        if (time == 0) {
            return "00:00:00";
        }

        if (time % 3600 == time) {
            if (time % 60 == time) {
                 timeToString = "00:00:" + time;
            }
            else {
                minutes = Double.toString( (double) time / 60);

                if (minutes.contains(".")) {
                    temp = minutes.split("[.]");

                    seconds = Double.toString(Math.round(Double.parseDouble("0." + temp[1]) * 60));

                    if (Double.parseDouble(minutes) == 0) {
                        minutes = "00";
                    }
                    else if (Double.parseDouble(minutes) < 10) {
                        minutes = "0" +  minutes;
                    }

                    if (Double.parseDouble(seconds) == 0) {
                        seconds = "00";
                    }
                    else if (Double.parseDouble(seconds) < 10) {
                        seconds = "0" + seconds;
                    }

                    if (seconds.substring(0, 2).equals("60")) {
                        seconds = "00" + seconds.substring(2);
                        minutes = Double.toString(Double.parseDouble(minutes) + 1);
                    }

                    timeToString = "00:" + minutes.substring(0, 2) + ":" + seconds.substring(0, 2);
                }
                else {
                    if (minutes.substring(0, minutes.indexOf(".")).length() == 1) {
                        timeToString =  "00:0" + minutes.substring(0, 1) + ":00";
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
//        System.out.println(hours);

        if (hours.contains(".")) {
            temp = hours.split("[.]");

            //Troubleshooting
//            System.out.println(temp1);

            minutes = Double.toString(Double.parseDouble("0." + temp[1]) * 60);

            if (minutes.substring(0, 2).equals("60")) {
                minutes = "00" + minutes.substring(2);
                hours = Double.toString(Double.parseDouble(hours) + 1);
            }

            if (Double.parseDouble(minutes) < 10) {
                minutes = "0" +  minutes;
            }

            if (minutes.contains(".")) {
                temp = minutes.split("[.]");

                seconds = Double.toString(Math.round(Double.parseDouble("0." + temp[1]) * 60));

                if (Double.parseDouble(minutes) == 0) {
                    minutes = "00";
                }
                else if (Double.parseDouble(minutes) < 10) {
                    minutes = "0" +  minutes;
                }

                if (Double.parseDouble(seconds) == 0) {
                    seconds = "00";
                }
                else if (Double.parseDouble(seconds) < 10) {
                    seconds = "0" + seconds;
                }

                if (seconds.substring(0, 2).equals("60")) {
                    seconds = "00";
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