const std = @import("std");
const assert = std.debug.assert;

pub const support = @import("support.zig");
const ResultType = support.ResultType;
const ResultVal = support.ResultVal;
pub const Result = support.Result;
pub const Solution = support.Solution;

const Timer = std.time.Timer;
const fmtDuration = std.fmt.fmtDuration;

const stderr = std.io.getStdErr().writer();
var bw = std.io.bufferedWriter(std.io.getStdOut().writer());
const stdout = bw.writer();

const maxFileSize = 1_000_000;

var resultsBuf: [2048]u8 = undefined;
var fba = std.heap.FixedBufferAllocator.init(&resultsBuf);
pub const resultAllocator = fba.allocator();

pub fn main() !void {
    var arena = std.heap.ArenaAllocator.init(std.heap.page_allocator);
    defer arena.deinit();
    const allocator = arena.allocator();

    defer bw.flush() catch {};

    const args = try std.process.argsAlloc(allocator);
    if (args.len >= 3) {
        const year = try std.fmt.parseInt(u32, args[1], 10);
        const day = try std.fmt.parseInt(u32, args[2], 10);
        const useStdIn = args.len == 4 and std.mem.eql(u8, args[3], "-");
        const solution = getSolution(year, day) orelse {
            try stderr.print("No implementation for year {} day {}\n", .{ year, day });
            return error.NoImplError;
        };
        try runSolution(allocator, solution, useStdIn);
    } else if (args.len == 2) {
        const year = std.fmt.parseInt(u32, args[1], 10) catch null;
        if (year == null and !std.mem.eql(u8, args[1][0..], "all")) {
            try printUsage();
            return;
        }
        var timer = try Timer.start();
        for (solutions) |solution| {
            if (year != null and solution.year != year.?) continue;
            try runSolution(allocator, solution, false);
            try stdout.print("\n", .{});
        }
        try stdout.print("Total time: {}\n", .{fmtDuration(lap(&timer))});
    } else {
        try printUsage();
    }
}

fn printUsage() !void {
    try stderr.print("Usage: main [year] [day]? or main all\n", .{});
}

fn getSolution(year: u32, day: u32) ?Solution {
    for (solutions) |solution| {
        if (solution.year == year and solution.day == day)
            return solution;
    }
    return null;
}

fn runSolution(allocator: std.mem.Allocator, solution: Solution, useStdIn: bool) !void {
    const year = solution.year;
    const day = solution.day;

    var timer = try Timer.start();
    const input = switch (useStdIn) {
        true => try std.io.getStdIn().readToEndAlloc(allocator, maxFileSize),
        false => try readInputFile(allocator, year, day),
    };
    defer allocator.free(input);
    var readTime = lap(&timer);

    const res = try solution.run(input);
    var runTime = lap(&timer);

    try stdout.print("Year {} day {}\n", .{ year, day });
    try printResult(res.part1, 1);
    try printResult(res.part2, 2);
    try stdout.print("Took {} + {} = {} to read / run\n", .{
        fmtDuration(readTime),
        fmtDuration(runTime),
        fmtDuration(readTime + runTime),
    });
}

fn lap(timer: *Timer) u64 {
    const time = timer.lap();
    return time - time % std.time.ns_per_us;
}

fn readInputFile(allocator: std.mem.Allocator, year: u32, day: u32) ![]const u8 {
    var buf: [32]u8 = undefined;
    var fileName = try std.fmt.bufPrint(&buf, "resources/inputs/{}/day{:0>2}.txt", .{ year, day });
    errdefer stderr.print("Could not open file {s}\n", .{fileName}) catch {};

    const inputFile = try std.fs.cwd().openFile(fileName, .{});
    defer inputFile.close();

    return try inputFile.readToEndAlloc(allocator, maxFileSize);
}

fn printResult(result: ResultVal, part: usize) !void {
    switch (result) {
        ResultType.int => try stdout.print("Part {}: {}\n", .{ part, result.int }),
        ResultType.str => try stdout.print("Part {}: {s}\n", .{ part, result.str }),
    }
}

const y2022 = @import("y2022/y2022.zig");
const solutions = [_]Solution{
    y2022.day01.Solution,
    y2022.day03.Solution,
    y2022.day04.Solution,
    y2022.day05.Solution,
    y2022.day06.Solution,
    y2022.day11.Solution,
    y2022.day23.Solution,
    y2022.day25.Solution,
    @import("y2020/day15.zig").Solution,
};

test {
    std.testing.refAllDecls(@This());
}
