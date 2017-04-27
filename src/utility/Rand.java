package utility;

import java.security.SecureRandom;

public class Rand {
    private static SecureRandom random = new SecureRandom();

    public static double randDouble(double bound){
        return random.nextDouble()*bound;
    }
}
