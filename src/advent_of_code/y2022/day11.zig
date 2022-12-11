const std = @import("std");
const root = @import("../main.zig");
const Result = root.Result;

pub const Solution = root.Solution{ .year = 2022, .day = 11, .run = run };

const Item = u64;
const ItemList = std.ArrayList(Item);

fn run(input: []const u8) !Result {
    var buf: [2048]u8 = undefined;
    var fba = std.heap.FixedBufferAllocator.init(&buf);
    const allocator = fba.allocator();

    var monkeys = try std.ArrayList(Monkey).initCapacity(allocator, 8);
    var rawMonkeys = std.mem.split(u8, input, "\n\n");
    while (rawMonkeys.next()) |rawMonkey| {
        if (rawMonkey.len == 0) break;
        var lines = std.mem.split(u8, rawMonkey, "\n");

        // Monkey 0
        _ = lines.next();

        // Starting items: 93, 54, 69
        var items = ItemList.init(allocator);
        var numbers = std.mem.tokenize(u8, lines.next().?[18..], " ,");
        while (numbers.next()) |numStr| {
            const item = try std.fmt.parseInt(Item, numStr, 10);
            try items.append(item);
        }

        // Operation: new = old * 3
        var tokens = std.mem.split(u8, lines.next().?[19..], " ");
        var lhs = try Arg.parse(tokens.next().?);
        var opType = OpType.parse(tokens.next().?).?;
        var rhs = try Arg.parse(tokens.next().?);
        var op: Op = .{ .opType = opType, .lhs = lhs, .rhs = rhs };

        // Test: divisible by 7
        const divTest = try std.fmt.parseInt(u32, lines.next().?[21..], 10);
        // If true:  throw to monkey 4
        const trueIdx = try std.fmt.parseInt(u32, lines.next().?[29..], 10);
        // If false:  throw to monkey 3
        const falseIdx = try std.fmt.parseInt(u32, lines.next().?[30..], 10);

        try monkeys.append(.{
            .items = items,
            .op = op,
            .divTest = divTest,
            .trueIdx = trueIdx,
            .falseIdx = falseIdx,
        });
    }

    return Result.of(
        try solve(monkeys.items, true),
        try solve(monkeys.items, false),
    );
}

fn solve(monkeys: []Monkey, comptime part1: bool) !u64 {
    var buf: [4096]u8 = undefined;
    var fba = std.heap.FixedBufferAllocator.init(&buf);
    const allocator = fba.allocator();

    var totalItems: usize = 0;
    var mod: u32 = 1;
    for (monkeys) |m| {
        totalItems += m.items.items.len;
        mod *= m.divTest;
    }

    var inventory = try allocator.alloc(ItemList, monkeys.len);
    for (inventory) |*slot, i| {
        var list = try ItemList.initCapacity(allocator, totalItems);
        for (monkeys[i].items.items) |item| {
            list.appendAssumeCapacity(item);
        }
        slot.* = list;
    }
    var times = try allocator.alloc(u32, monkeys.len);
    std.mem.set(u32, times, 0);

    var i: usize = 0;
    while (i < if (part1) 20 else 10000) : (i += 1) {
        for (monkeys) |m, idx| {
            var items = &inventory[idx];
            for (items.items) |oldItem| {
                times[idx] += 1;
                var item = m.op.result(oldItem);
                if (part1) {
                    item /= 3;
                } else {
                    item %= mod;
                }
                const nextIdx = if (item % m.divTest == 0) m.trueIdx else m.falseIdx;
                inventory[nextIdx].appendAssumeCapacity(item);
            }
            items.clearRetainingCapacity();
        }
    }
    std.sort.sort(u32, times, {}, std.sort.desc(u32));
    return @intCast(u64, times[0]) * @intCast(u64, times[1]);
}

const Monkey = struct {
    items: ItemList,
    op: Op,
    divTest: u32,
    trueIdx: usize,
    falseIdx: usize,
};

const ArgType = enum { old, int };
const Arg = union(ArgType) {
    old: void,
    int: Item,

    fn parse(buf: []const u8) !Arg {
        if (buf.len == 3 and std.mem.eql(u8, buf, "old")) {
            return .{ .old = {} };
        } else {
            const int = std.fmt.parseInt(Item, buf, 10) catch |e| return e;
            return .{ .int = int };
        }
    }

    fn resolve(self: Arg, old: Item) Item {
        return switch (self) {
            .old => old,
            .int => |i| i,
        };
    }
};

const OpType = enum {
    add,
    mul,

    fn parse(buf: []const u8) ?OpType {
        return switch (buf[0]) {
            '+' => .add,
            '*' => .mul,
            else => null,
        };
    }
};

const Op = struct {
    opType: OpType,
    lhs: Arg,
    rhs: Arg,

    fn result(self: Op, old: Item) Item {
        return switch (self.opType) {
            .add => self.lhs.resolve(old) + self.rhs.resolve(old),
            .mul => self.lhs.resolve(old) * self.rhs.resolve(old),
        };
    }
};

test {
    const example =
        \\Monkey 0:
        \\  Starting items: 79, 98
        \\  Operation: new = old * 19
        \\  Test: divisible by 23
        \\    If true: throw to monkey 2
        \\    If false: throw to monkey 3
        \\
        \\Monkey 1:
        \\  Starting items: 54, 65, 75, 74
        \\  Operation: new = old + 6
        \\  Test: divisible by 19
        \\    If true: throw to monkey 2
        \\    If false: throw to monkey 0
        \\
        \\Monkey 2:
        \\  Starting items: 79, 60, 97
        \\  Operation: new = old * old
        \\  Test: divisible by 13
        \\    If true: throw to monkey 1
        \\    If false: throw to monkey 3
        \\
        \\Monkey 3:
        \\  Starting items: 74
        \\  Operation: new = old + 3
        \\  Test: divisible by 17
        \\    If true: throw to monkey 0
        \\    If false: throw to monkey 1
    ;
    const res = try run(example);
    try std.testing.expectEqual(@as(u64, 10605), res.part1.int);
    try std.testing.expectEqual(@as(u64, 2713310158), res.part2.int);
}
