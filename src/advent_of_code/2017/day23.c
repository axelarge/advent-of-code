#include <stdio.h>

int isPrime (b) {
    // for (int d = 2; d < b; d++) {
    //     for (int e = 2; e < b; e++) {
    //         if (d * e == b) {
    //             return 0;
    //         }
    //     }
    // }
    // return 1;
    for (int i = 2; i * i <= b; i++) {
        if (b % i == 0) return 0;
    }
    return 1;
}

int solve(int b, int c) {
    int h = 0;
    for (int i = b; i <= c; i += 17) {
        if (!isPrime(i)) {
            h++;
        }
    }
    return h;
}

int main(int argc, char** argv) {
    if (argc != 2 || (argv[1][0] != '1' && argv[1][0] != '2')) {
        printf("Usage:\n  %s 1\n  %s 2\n", argv[0], argv[0]);
        return 1;
    }

    int part1 = argv[1][0] == '1';

    int b = part1 ? 81 : 108100;
    int c = part1 ? 81 : 125100;

    printf("%d\n", solve(b, c));

    return 0;
}
