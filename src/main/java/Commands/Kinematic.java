//Nolan Amblard did this
package Commands;

import Bot.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Kinematics implements Commands {

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        if( message.length == 1) {
            event.getMessage().getChannel().sendMessage("Use the format `-kinematics [displacement] [initial velocity] [final velocity] [acceleration] [time]` For what you are trying to solve put `x` (there can up to two x's)").queue();
        }
        if( message.length == 2) {
            event.getMessage().getChannel().sendMessage("Use the format `-kinematics [displacement] [initial velocity] [final velocity] [acceleration] [time]` For what you are trying to solve put `x` (there can up to two x's)").queue();
        }
        if( message.length == 3) {
            event.getMessage().getChannel().sendMessage("Use the format `-kinematics [displacement] [initial velocity] [final velocity] [acceleration] [time]` For what you are trying to solve put `x` (there can up to two x's)").queue();
        }
        if( message.length == 4) {
            event.getMessage().getChannel().sendMessage("Use the format `-kinematics [displacement] [initial velocity] [final velocity] [acceleration] [time]` For what you are trying to solve put `x` (there can up to two x's)").queue();
        }
        if( message.length == 5) {
            event.getMessage().getChannel().sendMessage("Use the format `-kinematics [displacement] [initial velocity] [final velocity] [acceleration] [time]` For what you are trying to solve put `x` (there can up to two x's)").queue();
        }
        if( message.length == 6) {
            //double dis = Double.parseDouble(message[1]);
            //double vel0 = Double.parseDouble(message[2]);
            //double vel = Double.parseDouble(message[3]);
            //double accel = Double.parseDouble(message[4]);
            //double time = Double.parseDouble(message[5]);
            if( message[1].equals("x") && !message[2].equals("x") && !message[3].equals("x") && !message[4].equals("x") && !message[5].equals("x")) {
                //Solve for only displacement
                double dis = 0;
                double vel0 = Double.parseDouble(message[2]);
                double vel = Double.parseDouble(message[3]);
                double accel = Double.parseDouble(message[4]);
                double time = Double.parseDouble(message[5]);
                dis = vel0 * time + 0.5 * accel * (time * time);
                event.getMessage().getChannel().sendMessage("The displacement is " + dis + ". We used the 2nd kinematic for this.").queue();
            }
            else if( !message[1].equals("x") && message[2].equals("x") && !message[3].equals("x") && !message[4].equals("x") && !message[5].equals("x")) {
                //Solve for only initial velocity
                double dis = Double.parseDouble(message[1]);
                double vel0 = 0;
                double vel = Double.parseDouble(message[3]);
                double accel = Double.parseDouble(message[4]);
                double time = Double.parseDouble(message[5]);
                vel0 = vel - accel * time;
                event.getMessage().getChannel().sendMessage("The initial velocity is " + vel0 + ". We used the 1st kinematic for this.").queue();
            }
            else if( !message[1].equals("x") && !message[2].equals("x") && message[3].equals("x") && !message[4].equals("x") && !message[5].equals("x")) {
                //Solve for only final velocity
                double dis = Double.parseDouble(message[1]);
                double vel0 = Double.parseDouble(message[2]);
                double vel = 0;
                double accel = Double.parseDouble(message[4]);
                double time = Double.parseDouble(message[5]);
                vel = vel0 + accel * time;
                event.getMessage().getChannel().sendMessage("The final velocity is " + vel + ". We used the 1st kinematic for this.").queue();
            }
            else if( !message[1].equals("x") && !message[2].equals("x") && !message[3].equals("x") && message[4].equals("x") && !message[5].equals("x")) {
                //Solve for only acceleration
                double dis = Double.parseDouble(message[1]);
                double vel0 = Double.parseDouble(message[2]);
                double vel = Double.parseDouble(message[3]);
                double accel = 0;
                double time = Double.parseDouble(message[5]);
                accel = (vel - vel0)/time;
                event.getMessage().getChannel().sendMessage("The accel is " + accel + ". We used the 1st kinematic for this.").queue();
            }
            else if( !message[1].equals("x") && !message[2].equals("x") && !message[3].equals("x") && !message[4].equals("x") && message[5].equals("x")) {
                //Solve for only time
                double dis = Double.parseDouble(message[1]);
                double vel0 = Double.parseDouble(message[2]);
                double vel = Double.parseDouble(message[3]);
                double accel = Double.parseDouble(message[4]);
                double time = 0;
                time = (vel - vel0)/accel;
                event.getMessage().getChannel().sendMessage("The time is " + time + ". We used the 1st kinematic for this.").queue();
            }
            else if( message[1].equals("x") && message[2].equals("x") && !message[3].equals("x") && !message[4].equals("x") && !message[5].equals("x")) {
                //Solve for displacement and initial velocity
                double dis = 0;
                double vel0 = 0;
                double vel = Double.parseDouble(message[3]);
                double accel = Double.parseDouble(message[4]);
                double time = Double.parseDouble(message[5]);
                vel0 = vel - accel * time;
                dis = vel0 * time + 0.5 * accel * (time * time);
                event.getMessage().getChannel().sendMessage("The displacement is " + dis + ". We used the 2nd kinematic for this. The initial velocity is " + vel0 + ". We used the 1st kinematic for this.").queue();
            }
            else if( message[1].equals("x") && !message[2].equals("x") && message[3].equals("x") && !message[4].equals("x") && !message[5].equals("x")) {
                //Solve for displacement and final velocity
                double dis = 0;
                double vel0 = Double.parseDouble(message[2]);
                double vel = 0;
                double accel = Double.parseDouble(message[4]);
                double time = Double.parseDouble(message[5]);
                dis = vel0 * time + 0.5 * accel * (time * time);
                vel = vel0 + accel * time;
                event.getMessage().getChannel().sendMessage("The displacement is " + dis + ". We used the 2nd kinematic for this. The final velocity is " + vel + ". We used the 1st kinematic for this.").queue();
            }
            else if( message[1].equals("x") && !message[2].equals("x") && !message[3].equals("x") && message[4].equals("x") && !message[5].equals("x")) {
                //Solve for displacement and acceleration
                double dis = 0;
                double vel0 = Double.parseDouble(message[2]);
                double vel = Double.parseDouble(message[3]);
                double accel = 0;
                double time = Double.parseDouble(message[5]);
                accel = (vel - vel0) / time;
                dis = vel0 * time + 0.5 * accel * (time * time);
                event.getMessage().getChannel().sendMessage("The displacement is " + dis + ". We used the 2nd kinematic for this. The acceleration is " + accel + ". We used the 1st kinematic for this.").queue();
            }
            else if( message[1].equals("x") && !message[2].equals("x") && !message[3].equals("x") && !message[4].equals("x") && message[5].equals("x")) {
                //Solve for displacement and time
                double dis = 0;
                double vel0 = Double.parseDouble(message[2]);
                double vel = Double.parseDouble(message[3]);
                double accel = Double.parseDouble(message[4]);
                double time = 0;
                time = (vel - vel0) / accel;
                dis = vel0 * time + 0.5 * accel * (time * time);
                event.getMessage().getChannel().sendMessage("The displacement is " + dis + ". We used the 2nd kinematic for this. The time is " + time + ". We used the 1st kinematic for this.").queue();
            }
            else if( !message[1].equals("x") && message[2].equals("x") && message[3].equals("x") && !message[4].equals("x") && !message[5].equals("x")) {
                //Solve for initial velocity and final velocity
                double dis = Double.parseDouble(message[1]);
                double vel0 = 0;
                double vel = 0;
                double accel = Double.parseDouble(message[4]);
                double time = Double.parseDouble(message[5]);
                vel0 = (dis - 0.5 * accel * (time * time))/time;
                vel = vel0 + accel * time;
                event.getMessage().getChannel().sendMessage("The initial velocity is " + vel0 + ". We used the 2nd kinematic for this. The final velocity is " + vel + ". We used the 1st kinematic for this.").queue();
            }
            else if( !message[1].equals("x") && message[2].equals("x") && !message[3].equals("x") && message[4].equals("x") && !message[5].equals("x")) {
                //Solve for initial velocity and acceleration
                double dis = Double.parseDouble(message[1]);
                double vel0 = 0;
                double vel = Double.parseDouble(message[3]);
                double accel = 0;
                double time = Double.parseDouble(message[5]);
                accel = (dis - vel * time) / (0.5 * (time * time) - (time * time));
                vel0 = vel - accel * time;
                event.getMessage().getChannel().sendMessage("The initial velocity is " + vel0 + ". We used the 1st kinematic for this. The acceleration is " + accel + ". We used the 1st and 2nd kinematic for this.").queue();
            }
            else if( !message[1].equals("x") && message[2].equals("x") && !message[3].equals("x") && !message[4].equals("x") && message[5].equals("x")) {
                //Solve for initial velocity and time
                double dis = Double.parseDouble(message[1]);
                double vel0 = 0;
                double vel = Double.parseDouble(message[3]);
                double accel = Double.parseDouble(message[4]);
                double time = 0;
                vel0 = Math.sqrt((vel * vel) - 2 * accel * dis);
                time = (vel - vel0)/accel;
                event.getMessage().getChannel().sendMessage("The initial velocity is " + vel0 + ". We used the 3rd kinematic for this. The time is " + time + ". We used the 1st kinematic for this.").queue();
            }
            else if( !message[1].equals("x") && !message[2].equals("x") && message[3].equals("x") && message[4].equals("x") && !message[5].equals("x")) {
                //Solve for final velocity and acceleration
                double dis = Double.parseDouble(message[1]);
                double vel0 = Double.parseDouble(message[2]);
                double vel = 0;
                double accel = 0;
                double time = Double.parseDouble(message[5]);
                accel = (dis - vel0 * time) / (2 * (time * time));
                vel = vel0 + accel * time;
                event.getMessage().getChannel().sendMessage("The final velocity is " + vel + ". We used the 1st kinematic for this. The acceleration is " + accel + ". We used the 2nd kinematic for this.").queue();
            }
            else if( !message[1].equals("x") && !message[2].equals("x") && message[3].equals("x") && !message[4].equals("x") && message[5].equals("x")) {
                //Solve for final velocity and time
                double dis = Double.parseDouble(message[1]);
                double vel0 = Double.parseDouble(message[2]);
                double vel = 0;
                double accel = Double.parseDouble(message[4]);
                double time = 0;
                vel = Math.sqrt((vel0 * vel0) + 2 * accel * dis);
                time = (vel - vel0) / accel;
                event.getMessage().getChannel().sendMessage("The final velocity is " + vel + ". We used the 3rd kinematic for this. The time is " + time + ". We used the 1st kinematic for this.").queue();
            }
            else if( !message[1].equals("x") && !message[2].equals("x") && !message[3].equals("x") && message[4].equals("x") && message[5].equals("x")) {
                //Solve for acceleration and time
                double dis = Double.parseDouble(message[1]);
                double vel0 = Double.parseDouble(message[2]);
                double vel = Double.parseDouble(message[3]);
                double accel = 0;
                double time = 0;
                accel = ((vel * vel) - (vel0 * vel0)) / (2 * dis);
                time = (vel - vel0) / accel;
                event.getMessage().getChannel().sendMessage("The acceleration is " + accel + ". We used the 3rd kinematic for this. The time is " + time + ". We used the 1st kinematic for this.").queue();
            }

        }
    }

    @Override
    public String getKeyword() {
        return "Kinematics";
    }
}
