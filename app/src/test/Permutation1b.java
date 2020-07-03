package test;

import java.util.Arrays;

public class Permutation1b {
    static int n = 4;
    static int a[] = new int[n];
    static int vis[] = new int[n];

    static void dfs(int k) {
        if (k == n) {
            System.out.println(Arrays.toString(a));
            return;
        }
        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {
                a[k] = i;
                vis[i] = 1;
                dfs(k + 1);
                vis[i] = 0;
            }
        }
    }

    public static void main(String[] args) {
        dfs(0);
    }
}
