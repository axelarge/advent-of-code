const std = @import("std");
const root = @import("../../main.zig");
const Result = root.Result;

pub fn run(input: []const u8) !Result {
    return .{ .part1 = part1(input), .part2 = try part2(input) };
}

fn part1(input: []const u8) i32 {
    var res: i32 = 0;
    var lines = std.mem.split(u8, input, "\n");
    while (lines.next()) |line| {
        const len = line.len / 2;
        const idx = std.mem.indexOfAny(u8, line[0..len], line[len..]);
        res += value(line[idx.?]);
    }
    return res;
}

fn part2(input: []const u8) !i32 {
    var buffer: [1024]u8 = undefined;
    var fba = std.heap.FixedBufferAllocator.init(&buffer);
    const allocator = fba.allocator();

    const Map = std.AutoHashMap(u8, void);
    var map1 = Map.init(allocator);
    var map2 = Map.init(allocator);

    var lines = std.mem.split(u8, input, "\n");
    var res: i32 = 0;
    while (lines.next()) |line| {
        if (line.len == 0) break;

        for (line) |c| {
            try map1.put(c, {});
        }

        for (lines.next().?) |c| {
            if (map1.contains(c)) {
                try map2.put(c, {});
            }
        }

        for (lines.next().?) |c| {
            if (map2.contains(c)) {
                res += value(c);
                break;
            }
        }

        map1.clearRetainingCapacity();
        map2.clearRetainingCapacity();
    }
    return res;
}

fn value(c: u8) i32 {
    if (c >= 'a' and c <= 'z') {
        return c - 'a' + 1;
    } else if (c >= 'A' and c <= 'Z') {
        return c - 'A' + 1 + 26;
    } else {
        unreachable;
    }
}

test "2022.03 sample" {
    const sample =
        \\vJrwpWtwJgWrhcsFMMfFFhFp
        \\jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        \\PmmdzqPrVvPwwTWBwg
        \\wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        \\ttgJtRGJQctTZtZT
        \\CrZsJsPPZsGzwwsLwLmpwMDw
    ;

    var res = try run(sample);
    try std.testing.expectEqual(@as(i32, 157), res.part1);
    try std.testing.expectEqual(@as(i32, 70), res.part2);
}
