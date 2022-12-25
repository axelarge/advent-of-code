const std = @import("std");
const root = @import("../main.zig");
const Result = root.Result;

pub const Solution = root.Solution{ .year = 2022, .day = 25, .run = run };

fn run(input: []const u8) !Result {
    var part1: i64 = 0;
    var lines = std.mem.split(u8, input, "\n");
    while (lines.next()) |line| {
        part1 += toInt(line);
    }
    return Result.ofStrings(try toSnafu(part1, root.resultAllocator), "");
}

fn toInt(snafu: []const u8) i64 {
    var res: i64 = 0;
    var times: i64 = 1;
    var i: usize = snafu.len;
    while (i > 0) {
        i -= 1;
        res += times * toDigit(snafu[i]);
        times *= 5;
    }
    return res;
}

inline fn toDigit(c: u8) i64 {
    return switch (c) {
        '=' => -2,
        '-' => -1,
        '0' => 0,
        '1' => 1,
        '2' => 2,
        else => unreachable,
    };
}

fn toSnafu(num: i64, allocator: std.mem.Allocator) ![]u8 {
    var n = std.math.absCast(num);
    const neg = num < 0;
    var buf = try allocator.alloc(u8, std.math.log(u64, 5, n) + 2);
    var i: usize = 0;
    while (n != 0) {
        const digit = n % 5;
        buf[i] = "012=-"[digit];
        i += 1;
        if (digit > 2) {
            n += 5 - digit;
        }
        n /= 5;
    }
    if (neg) {
        buf[i] = '-';
        i += 1;
    }
    std.mem.reverse(u8, buf[0..i]);
    return buf[0..i];
}

test {
    const sample =
        \\1=-0-2
        \\12111
        \\2=0=
        \\21
        \\2=01
        \\111
        \\20012
        \\112
        \\1=-1=
        \\1-12
        \\12
        \\1=
        \\122
    ;
    const eql = std.testing.expectEqualStrings;
    const res = try run(sample);
    try eql("2=-1=0", res.part1.str);
}
