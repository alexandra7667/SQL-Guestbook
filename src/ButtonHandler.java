/**
 * This class handles functionality of the Button 'btnSign'.
 * It replaces potential HTML code in filled-out fields with "Censur"
 * It adds current date and time to the Guestbook entry
 * It sends the Strings to DBConnection and updates the TextArea (the historic guestbook GUI)
 *
 * @author Alexandra Härnström
 * @version 1
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ButtonHandler {
    private DBConnection dbConnection;
    private final String HTML = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    private final Pattern PATTERN = Pattern.compile(HTML);

    public ButtonHandler(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * This method retrieves the TextFields values from the GUI.
     * It checks their validity, adds a timestamp,
     * and sends the values to the class DBConnection.
     * @param values - The values retrieved from the GUI (user input)
     */
    public void transformValues(String[] values) {
        String[] checkedValues = checkHTMLTags(values);

        checkedValues[4] = getDateTime();

        dbConnection.signGuestbook(checkedValues);
    }

    /**
     * This method loops through the String array 'values'
     * and if any field contains HTML code, it is replaced with "Censur".
     * @param values - The values retrieved from the GUI (user input)
     * @return - The updated values without eventual HTML code
     */
    private String[] checkHTMLTags(String[] values) {
        for(int i = 0; i < 4; i++) {
            if(hasHTMLTags(values[i])) {
                values[i] = "Censur";
            }
        }
        return values;
    }

    /**
     * This method checks if a String contains HTML code
     *
     * @param text - The String to be checked
     * @return - Returns true if there is HTML code
     */
    public boolean hasHTMLTags(String text) {
        Matcher matcher = PATTERN.matcher(text);
        return matcher.find();
    }

    /**
     * This method gets current date and time and formats it into a String
     * @return - The formatted String of current date and time
     */
    private String getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }
}
