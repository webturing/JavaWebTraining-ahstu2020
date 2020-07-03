package test;

import java.util.Arrays;

public class Permutation1c {
    static int[] FAC = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};

    public static void main(String[] args) {
        int n=7;
        for (int i = 0; i < FAC[n]; i++) {
            int[] arr = codel(i, n);
            System.out.println(Arrays.toString(arr));
        }
    }

    public static int[] codel(int x, int m) {
        int[] label = new int[m];
        int[] n = new int[m];
        int cnt;
        for (int i = 0; i < m; i++)
            label[i] = 1;
        for (int i = 0; i < m; i++) {
            cnt = x / FAC[m - 1 - i];
            x = x % FAC[m - 1 - i];
            for (int j = 0; j < m; j++) {
                if (label[j] == 0)
                    continue;
                if (cnt == 0) {
                    label[j] = 0;
                    n[i] = j;
                    break;
                }
                cnt--;
            }
        }
        return n;
    }
}
