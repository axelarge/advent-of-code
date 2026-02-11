const std = @import("std");
const root = @import("../main.zig");
const Result = root.Result;
const resizeZFill = root.support.resizeZFill;

pub const Solution = root.Solution{ .year = 2020, .day = 15, .run = run };

fn run(input: []const u8) !Result {
    const gpa = std.heap.page_allocator;
    const initSize = 1 << 25; // Enough to solve given input without reallocating
    var seen = try std.ArrayList(u32).initCapacity(gpa, initSize);
    defer seen.deinit(gpa);
    try resizeZFill(u32, &seen, gpa, initSize);

    var i: u32 = 0;
    var nums = std.mem.tokenizeAny(u8, input, ",\n");
    while (nums.next()) |num| {
        const n = try std.fmt.parseInt(u32, num, 10);
        i += 1;
        try resizeZFill(u32, &seen, gpa, n + 1);
        seen.items[n] = i;
    }

    var part1: u64 = undefined;
    var prev = i;
    var n: usize = undefined;
    while (i < 30000000) {
        n = i - prev;
        try resizeZFill(u32, &seen, gpa, n + 1);
        i += 1;
        prev = if (seen.items[n] != 0) seen.items[n] else i;
        seen.items[n] = i;
        if (i == 2020) {
            part1 = n;
        }
    }

    return Result.of(part1, n);
}
