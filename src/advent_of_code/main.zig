const std = @import("std");
const assert = std.debug.assert;

pub const support = @import("support.zig");
const ResultType = support.ResultType;
const ResultVal = support.ResultVal;
pub const Result = support.Result;
pub const Solution = support.Solution;

const maxFileSize = 1_000_000;

var resultsBuf: [2048]u8 = undefined;
var fba = std.heap.FixedBufferAllocator.init(&resultsBuf);
pub const resultAllocator = fba.allocator();

pub fn main() !void {
    var arena = std.heap.ArenaAllocator.init(std.heap.page_allocator);
    defer arena.deinit();
    const allocator = arena.allocator();

    var stderr_buf: [4096]u8 = undefined;
    var stderr_writer = std.fs.File.stderr().writer(&stderr_buf);
    const stderr = &stderr_writer.interface;
    var stdout_buf: [4096]u8 = undefined;
    var stdout_writer = std.fs.File.stdout().writer(&stdout_buf);
    const stdout = &stdout_writer.interface;
    defer stdout.flush() catch {};

    const args = try std.process.argsAlloc(allocator);
    if (args.len >= 3) {
        const year = try std.fmt.parseInt(u32, args[1], 10);
        const day = try std.fmt.parseInt(u32, args[2], 10);
        const useStdIn = args.len == 4 and std.mem.eql(u8, args[3], "-");
        const solution = getSolution(year, day) orelse {
            try stderr.print("No implementation for year {} day {}\n", .{ year, day });
            try stderr.flush();
            return error.NoImplError;
        };
        try runSolution(allocator, solution, useStdIn, stdout);
    } else if (args.len == 2) {
        const year = std.fmt.parseInt(u32, args[1], 10) catch null;
        if (year == null and !std.mem.eql(u8, args[1][0..], "all")) {
            try printUsage(stderr);
            try stderr.flush();
            return;
        }
        var timer = try std.time.Timer.start();
        for (solutions) |solution| {
            if (year != null and solution.year != year.?) continue;
            try runSolution(allocator, solution, false, stdout);
            try stdout.print("\n", .{});
        }
        try stdout.print("Total time: {D}\n", .{lap(&timer)});
    } else {
        try printUsage(stderr);
        try stderr.flush();
    }
}

fn printUsage(stderr: *std.Io.Writer) !void {
    try stderr.print("Usage: main [year] [day]? or main all\n", .{});
}

fn getSolution(year: u32, day: u32) ?Solution {
    for (solutions) |solution| {
        if (solution.year == year and solution.day == day)
            return solution;
    }
    return null;
}

fn runSolution(allocator: std.mem.Allocator, solution: Solution, useStdIn: bool, stdout: *std.Io.Writer) !void {
    const year = solution.year;
    const day = solution.day;

    var timer = try std.time.Timer.start();
    const input = switch (useStdIn) {
        true => try std.fs.File.stdin().readToEndAlloc(allocator, maxFileSize),
        false => try readInputFile(allocator, year, day),
    };
    defer allocator.free(input);
    const readTime = lap(&timer);

    const res = try solution.run(input);
    const runTime = lap(&timer);

    try stdout.print("Year {} day {}\n", .{ year, day });
    try printResult(res.part1, 1, stdout);
    try printResult(res.part2, 2, stdout);
    try stdout.print("Took {D} + {D} = {D} to read / run\n", .{
        readTime,
        runTime,
        readTime + runTime,
    });
}

fn lap(timer: *std.time.Timer) u64 {
    const time = timer.lap();
    return time - time % std.time.ns_per_us;
}

fn readInputFile(allocator: std.mem.Allocator, year: u32, day: u32) ![]const u8 {
    var buf: [32]u8 = undefined;
    const fileName = try std.fmt.bufPrint(&buf, "resources/inputs/{}/day{:0>2}.txt", .{ year, day });

    const inputFile = try std.fs.cwd().openFile(fileName, .{});
    defer inputFile.close();

    return try inputFile.readToEndAlloc(allocator, maxFileSize);
}

fn printResult(result: ResultVal, part: usize, stdout: *std.Io.Writer) !void {
    switch (result) {
        .int => try stdout.print("Part {}: {}\n", .{ part, result.int }),
        .str => try stdout.print("Part {}: {s}\n", .{ part, result.str }),
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
