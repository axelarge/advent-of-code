MOD = 20201227


def find_loop_size(target):
    loop_size = 0
    num = 1
    while num != target:
        loop_size += 1
        num = num * 7 % MOD
    return loop_size


pub1, pub2 = map(int, open("resources/inputs/2020/day25.txt"))
print(pow(pub2, find_loop_size(pub1), MOD))
print(pow(pub1, find_loop_size(pub2), MOD))
