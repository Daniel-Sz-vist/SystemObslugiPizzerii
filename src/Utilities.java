public class Utilities {
    public static void printBanner(String text) {
        System.out.println("=== " + text + " ===");
    }


    public static <T> void printArray(T[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }


    public static <T> T findFirstNonNull(T[] arr) {
        for (T t : arr) {
            if (t != null) return t;
        }
        return null;
    }
}