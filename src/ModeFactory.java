public class ModeFactory {

    public static Validator getValidator(int mode) {
        switch (mode) {
            case 0:
                return new Mode0Validator();
            case 3:
                return new Mode3Validator();
            case 27:
                return new Mode27Validator();
            default:
                throw new IllegalArgumentException("Invalid mode");

        }
    }

}
