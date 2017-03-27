package u.activitymanager;

/**
 * Created by surender on 2/23/2017.
 */
public class utils {


    public final static boolean isValidEmail(String em) {

        if (em == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(em).matches();
        }
    }

}
