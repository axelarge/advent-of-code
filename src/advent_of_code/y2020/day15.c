#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    int size = 1 << 25; // Enough to solve given input without reallocating
    int *seen = calloc(size, sizeof(int));
    int i = 0;
    int n;
    while (scanf("%d", &n) != EOF) {
        seen[n] = ++i;
        getchar();
    }

    int prev = i;
    while (++i <= 30000000) {
        n = i - 1 - prev;
        if (n >= size) {
            if (!(seen = realloc(seen, size * 2 * sizeof(int)))) return 1;
            memset(seen + size, 0, size * sizeof(int));
            size *= 2;
        }
        prev = seen[n] ? seen[n] : i;
        seen[n] = i;
        if (i == 2020) {
            printf("%d\n", n);
        }
    }
    printf("%d\n", n);
}