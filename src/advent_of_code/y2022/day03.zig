const std = @import("std");
const root = @import("../../main.zig");
const Result = root.Result;

fn BitSet(comptime T: type) type {
    return struct {
        bits: T = 0,

        const Self = @This();
        const V = std.math.Log2Int(T);

        inline fn full() Self {
            return .{ .bits = ~@as(T, 0) };
        }

        inline fn add(self: *Self, val: V) void {
            self.bits |= @as(T, 1) << val;
        }

        inline fn contains(self: Self, val: V) bool {
            return self.bits & @as(T, 1) << val != 0;
        }

        inline fn intersect(self: *Self, other: Self) void {
            self.bits &= other.bits;
        }
    };
}

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
        var seen = BitSet(u64).full();
        var i: u8 = 0;
        while (i < 3) : (i += 1) {
            var seenNow: BitSet(u64) = .{};
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
    if (c >= 'a' and c <= 'z') {
        return @intCast(u6, c - 'a' + 1);
    } else if (c >= 'A' and c <= 'Z') {
        return @intCast(u6, c - 'A' + 1 + 26);
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
