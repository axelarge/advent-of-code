IN = open("resources/inputs/2020/day13.txt").read().splitlines()
N = int(IN[0])
buses = sorted(((int(bus), i) for i, bus in enumerate(IN[1].split(",")) if bus != "x"), reverse=True)

first_bus = min((bus for bus, i in buses), key=lambda bus: -N % bus)
print(first_bus * (first_bus - N % first_bus))

t = 0
step = 1
for bus, dt in buses:
    while (t + dt) % bus:
        t += step
    step *= bus
print(t)
