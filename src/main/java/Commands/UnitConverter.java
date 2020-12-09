//Lawrence Zhang
package Commands;

import Bot.Commands;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

public class UnitConverter implements Commands {

    private static final HashMap<String, String> lengths = new LinkedHashMap<>();
    private static final HashMap<String, String> times = new LinkedHashMap<>();
        
    static {
        for (Length length: Length.values()) {
            lengths.put(length.singular, length.singular);
            lengths.put(length.plural, length.singular);
            lengths.put(length.abbreviation, length.singular);
        }

        for (Time time: Time.values()) {
            times.put(time.singular, time.singular);
            times.put(time.plural, time.singular);
            times.put(time.abbreviation, time.singular);
        }
    }

    public enum Length {

        NANOMETER (1, "NANOMETER", "NANOMETERS", "NM"),
        MICROMETER (1e3, "MICROMETER", "MICROMETERS", "UM"),
        MILLIMETER (1e6, "MILLIMETER", "MILLIMETERS", "MM"),
        CENTIMETER (1e7, "CENTIMETER", "CENTIMETERS", "CM"),
        METER (1e9, "METER", "METERS", "M"),
        KILOMETER (1e12, "KILOMETER", "KILOMETERS", "KM"),
        INCH (2.54e7, "INCH", "INCHES", "IN"),
        FOOT (3.048e8, "FOOT", "FEET", "FT"),
        YARD (9.144e8, "YARD", "YARDS", "YD"),
        MILE (1.609e12, "MILE", "MILES", "MI");

        private final double nanometers;
        private final String singular;
        private final String plural;
        private final String abbreviation;

        Length(double nanometers, String singular, String plural, String abbreviation) {
            this.nanometers = nanometers;
            this.singular = singular;
            this.plural = plural;
            this.abbreviation = abbreviation;
        }
    }

    public enum Time {

        NANOSECOND (1, "NANOSECOND", "NANOSECONDS", "NS"),
        MICROSECOND (1e3, "MICROSECOND", "MICROSECONDS", "US"),
        MILLISECOND (1e6, "MILLISECOND", "MILLISECONDS", "MS"),
        SECOND (1e9, "SECOND", "SECONDS", "S"),
        MINUTE (6e10, "MINUTE", "MINUTES", "MIN"),
        HOUR (3.6e12, "HOUR", "HOURS", "HR"),
        DAY (8.64e13, "DAY", "DAYS", "D"),
        WEEK (6.048e14, "WEEK", "WEEKS", "WK"),
        MONTH (2.628e15, "MONTH", "MONTHS", "MON"),
        YEAR (3.1536e16, "YEAR", "YEARS", "YR"),
        DECADE (3.1536e17, "DECADE", "DECADES", "DEC"),
        CENTURY (3.1536e18, "CENTURY", "CENTURIES", "CENT");

        private final double nanoseconds;
        private final String singular;
        private final String plural;
        private final String abbreviation;

        Time(double nanoseconds, String singular, String plural, String abbreviation) {
            this.nanoseconds = nanoseconds;
            this.singular = singular;
            this.plural = plural;
            this.abbreviation = abbreviation;
        }
    }

    public double convertLength(double input, Length one, Length two) {
        return input * (one.nanometers/ two.nanometers);
    }

    public double convertTime(double input, Time one, Time two) {
        return input * (one.nanoseconds / two.nanoseconds);
    }

    public boolean isValidLength(String unit) {
        return lengths.containsKey(unit);
    }
    
    public boolean isValidTime(String unit) {
        return times.containsKey(unit);
    }

    @Override
    public void execute(List<String> args, MessageReceivedEvent event) {
        Message m = event.getMessage();
        String[] content = m.getContentRaw().toLowerCase().split(" ");
        MessageChannel channel = event.getChannel();

        try {

            double input;
            String unitOne, unitTwo;

            //If no input is included
            //e.g. "convert in to m"
            if (content.length == 4) {
                input = 1;
                unitOne = content[1].toUpperCase();
                unitTwo = content[3].toUpperCase();
            }

            //Standard Usage
            //e.g. "-convert 2 km to ft"
            else {
                input = Double.parseDouble(content[1]);
                unitOne = content[2].toUpperCase();
                unitTwo = content[4].toUpperCase();
            }

            if (input < 0) {
                channel.sendMessage("Sorry, I can't convert negative values.").queue();
                return;
            }

            StringBuilder stringBuilder = new StringBuilder();

            double conversion;

            if (isValidLength(unitOne) && isValidLength(unitTwo)) {
                Length one = Length.valueOf(lengths.get(unitOne));
                Length two = Length.valueOf(lengths.get(unitTwo));
                conversion = convertLength(input, one, two);

                if (input == 1) {
                    unitOne = one.singular.toLowerCase();
                }
                else {
                    unitOne = one.plural.toLowerCase();
                }

                if (conversion == 1) {
                    unitTwo = two.singular.toLowerCase();
                }
                else {
                    unitTwo = two.plural.toLowerCase();
                }
            }
            else if (isValidTime(unitOne) && isValidTime(unitTwo)) {
                Time one = Time.valueOf(times.get(unitOne));
                Time two = Time.valueOf(times.get(unitTwo));
                conversion = convertTime(input, one, two);

                if (input == 1) {
                    unitOne = one.singular.toLowerCase();
                }
                else {
                    unitOne = one.plural.toLowerCase();
                }

                if (conversion == 1) {
                    unitTwo = two.singular.toLowerCase();
                }
                else {
                    unitTwo = two.plural.toLowerCase();
                }
            }
            else {
                channel.sendMessage("The conversion is not valid.").queue();
                return;
            }

            stringBuilder.append(input).append(" ").append(unitOne.toLowerCase()).append(" is equivalent to ").append(conversion).append(" ").append(unitTwo.toLowerCase()).append(".");

            channel.sendMessage(stringBuilder.toString()).queue();
        }
        catch (Exception e) {
            channel.sendMessage("The conversion is not valid or there was an error.").queue();
            channel.sendMessage("The error was:" + e.getMessage()).queue();
        }
    }

    @Override
    public String getKeyword() {
        return "Convert";
    }
}
