package edu.wkd.adminappbanghangonline.ultil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_NUMBER_PATTERN =
            "^(\\+[0-9]+[\\- \\.]*)?([0-9][\\- \\.]*){6,14}$";

    private static final String DATE_TIME =
            "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static final Pattern patternPhone = Pattern.compile(PHONE_NUMBER_PATTERN);

    public static boolean isValidPhone(String phoneNumber) {
        Matcher matcher = patternPhone.matcher(phoneNumber);
        return matcher.matches();
    }

    private static final Pattern patternDateTime = Pattern.compile(DATE_TIME);

    public static boolean isValidDateTime(String dateTime) {
        Matcher matcher = patternDateTime.matcher(dateTime);
        return matcher.matches();
    }
}
