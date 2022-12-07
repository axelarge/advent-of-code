const std = @import("std");

pub const Error = error{NoImplError};
pub const ResultType = enum { int, str };
pub const ResultVal = union(ResultType) {
    int: i32,
    str: []const u8,
};

pub const Result = struct {
    part1: ResultVal,
    part2: ResultVal,

    pub fn of(part1: i32, part2: i32) Result {
        return .{ .part1 = .{ .int = part1 }, .part2 = .{ .int = part2 } };
    }

    pub fn ofStrings(part1: []const u8, part2: []const u8) Result {
        return .{ .part1 = .{ .str = part1 }, .part2 = .{ .str = part2 } };
    }
};

pub const Solution = struct {
    year: u32,
    day: u32,
    run: *const fn ([]const u8) anyerror!Result,
};

pub fn BitSet(comptime T: type) type {
    return struct {
        bits: T = 0,

        const Self = @This();
        const V = std.math.Log2Int(T);

        pub inline fn empty() Self {
            return .{};
        }

        pub inline fn full() Self {
            return .{ .bits = ~@as(T, 0) };
        }

        pub inline fn add(self: *Self, val: V) void {
            self.bits |= @as(T, 1) << val;
        }

        pub inline fn contains(self: Self, val: V) bool {
            return self.bits & @as(T, 1) << val != 0;
        }

        pub inline fn intersect(self: *Self, other: Self) void {
            self.bits &= other.bits;
        }
    };
}
