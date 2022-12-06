const std = @import("std");
const root = @import("../../main.zig");
const Result = root.Result;
const BitSet = @import("../../support.zig").BitSet(u64);

pub fn run(input: []const u8) !Result {
    return .{ .part1 = part1(input), .part2 = try part2(input) };
}

fn part1(input: []const u8) i32 {
    var res: i32 = 0;
    var lines = std.mem.split(u8, input, "\n");
    while (lines.next()) |line| {
        if (line.len == 0) break;
        const len = line.len / 2;
        const idx = std.mem.indexOfAny(u8, line[0..len], line[len..]);
        res += value(line[idx.?]);
    }
    return res;
}

fn part2(input: []const u8) !i32 {
    var lines = std.mem.split(u8, input, "\n");
    var res: i32 = 0;
    while (lines.rest().len > 1) {
        var seen = BitSet.full();
        var i: u8 = 0;
        while (i < 3) : (i += 1) {
            var seenNow = BitSet.empty();
            for (lines.next().?) |c| {
                seenNow.add(value(c));
            }
            seen.intersect(seenNow);
        }
        res += @ctz(seen.bits);
    }
    return res;
}

fn value(c: u8) u6 {
    return switch (c) {
        'a'...'z' => @intCast(u6, c - 'a' + 1),
        'A'...'Z' => @intCast(u6, c - 'A' + 1 + 26),
        else => unreachable,
    };
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
