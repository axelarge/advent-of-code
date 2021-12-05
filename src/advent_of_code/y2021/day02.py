IN = open("resources/inputs/2021/day02.txt").readlines()

depth = 0
pos = 0
for command in IN:
    n = int(command.split()[-1])
    if "down" in command:
        depth += n
    elif "up" in command:
        depth -= n
    elif "forward" in command:
        pos += n

print(depth * pos)

depth = 0
pos = 0
aim = 0
for command in IN:
    n = int(command.split()[-1])
    if "down" in command:
        aim += n
    elif "up" in command:
        aim -= n
    elif "forward" in command:
        pos += n
        depth += aim * n

print(depth * pos)
