import java.util.Comparator;


public class MenuItemNameComparator implements Comparator<MenuItem> {

    @Override
    public int compare(MenuItem a, MenuItem b) {
        String s1 = a.getName();
        String s2 = b.getName();

        int len1 = s1.length();
        int len2 = s2.length();
        int min = Math.min(len1, len2);

        for (int i = 0; i < min; i++) {
            char c1 = normalize(s1.charAt(i));
            char c2 = normalize(s2.charAt(i));

            if (c1 != c2) {
                return c1 - c2;
            }
        }
        return len1 - len2;
    }

    private char normalize(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char)(c + 32);
        }
        return c;
    }
}
