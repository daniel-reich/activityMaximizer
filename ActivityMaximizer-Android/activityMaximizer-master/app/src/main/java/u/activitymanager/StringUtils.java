package u.activitymanager;

import android.util.Patterns;

/**
 * Created by surender on 2/23/2017.
 */
public abstract class StringUtils {

    public static boolean isEmpty(CharSequence sequence) {
        return sequence == null || sequence.length() == 0;
    }

    public static boolean isValidEmail(String email) {
        return !isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return !isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }

    public static long clamp(long value, long min, long max) {
        return Math.min(Math.max(value, min), max);
    }

}
