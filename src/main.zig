const std = @import("std");
const y2022 = @import("advent_of_code/y2022/y2022.zig");
const assert = std.debug.assert;
const Timer = std.time.Timer;

pub const Result = struct { part1: i32, part2: i32 };
const Runner = *const fn ([]const u8) anyerror!Result;

pub fn main() !void {
    var arena = std.heap.ArenaAllocator.init(std.heap.page_allocator);
    defer arena.deinit();
    const allocator = arena.allocator();

    var bw = std.io.bufferedWriter(std.io.getStdOut().writer());
    defer bw.flush() catch {};
    const stdout = bw.writer();
    const stderr = std.io.getStdErr().writer();

    const args = try std.process.argsAlloc(allocator);
    if (args.len < 3) {
        try stderr.print("Usage: main 2022 3\n", .{});
        return;
    }
    var year = try std.fmt.parseInt(u32, args[1], 10);
    var day = try std.fmt.parseInt(u32, args[2], 10);

    var runner: Runner = undefined;
    if (year == 2022 and day == 1) {
        runner = &y2022.day01.run;
    } else if (year == 2022 and day == 3) {
        runner = &y2022.day03.run;
    } else {
        try stderr.print("No implementation for year {} day {}\n", .{ year, day });
        return;
    }

    var fileName = try std.fmt.allocPrint(allocator, "resources/inputs/{}/day{:0>2}.txt", .{ year, day });
    const inputFile = std.fs.cwd().openFile(fileName, .{}) catch |err| {
        try stderr.print("Could not open file {s}\n", .{fileName});
        return err;
    };
    defer inputFile.close();

    var timer = try Timer.start();
    const content = try inputFile.readToEndAlloc(allocator, std.math.maxInt(usize));
    const readTime = timer.lap() / std.time.ns_per_us;
    var res = try runner(content);
    const runTime = timer.read() / std.time.ns_per_us;

    try stdout.print("Part 1: {}\n", .{res.part1});
    try stdout.print("Part 2: {}\n", .{res.part2});

    std.debug.print("Year {} day {} took {} + {} = {}µs to read / run\n", .{ year, day, readTime, runTime, readTime + runTime });
}

test {
    std.testing.refAllDecls(@This());
}
