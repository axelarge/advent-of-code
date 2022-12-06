const std = @import("std");
const root = @import("../../main.zig");
const Result = root.Result;

const Stack = std.ArrayList(u8);
const Stacks = std.ArrayList(Stack);

pub fn run(input: []const u8) !Result {
    var buf: [2048]u8 = undefined;
    var fba = std.heap.FixedBufferAllocator.init(&buf);
    var alloc = fba.allocator();

    var raw = std.mem.split(u8, input, "\n\n");

    const rawStacks = raw.next().?;
    // std.debug.print("Raw stacks: \n{s}\n", .{rawStacks});
    const lineLen = std.mem.indexOfScalar(u8, rawStacks, '\n').? + 1;
    const stackCount = lineLen / 4;
    const stackSize = blk: {
        var size: usize = 0;
        for (rawStacks) |ch| {
            size += @boolToInt(std.ascii.isAlphabetic(ch));
        }
        break :blk size;
    };
    std.debug.print("Stack max size: {}\n", .{stackSize});

    var stacks = try Stacks.initCapacity(alloc, stackCount);
    var stacks2 = try Stacks.initCapacity(alloc, stackCount);

    // Create stacks
    {
        var i: u8 = 1;
        while (i < lineLen) : (i += 4) {
            var stack = try Stack.initCapacity(alloc, stackSize);
            var stack2 = try Stack.initCapacity(alloc, stackSize);
            var j = (rawStacks.len / lineLen);
            while (j > 0) : (j -= 1) {
                const idx = (j - 1) * lineLen + i;
                const ch = rawStacks[idx];
                if (ch == ' ') break;
                stack.appendAssumeCapacity(ch);
                stack2.appendAssumeCapacity(ch);
            }
            stacks.appendAssumeCapacity(stack);
            stacks2.appendAssumeCapacity(stack2);
        }
    }

    // Perform steps
    var steps = std.mem.split(u8, raw.next().?, "\n");
    while (steps.next()) |step| {
        if (step.len == 0) break;

        var tokens = std.mem.tokenize(u8, step, " ");
        _ = tokens.next();
        const n = try std.fmt.parseInt(u8, tokens.next().?, 10);
        _ = tokens.next();
        const fromIdx = try std.fmt.parseInt(u8, tokens.next().?, 10) - 1;
        _ = tokens.next();
        const toIdx = try std.fmt.parseInt(u8, tokens.next().?, 10) - 1;

        // Part 1
        var i: u8 = 0;
        while (i < n) : (i += 1) {
            stacks.items[toIdx].appendAssumeCapacity(stacks.items[fromIdx].pop());
        }

        // Part 2
        stacks2.items[toIdx].appendSliceAssumeCapacity(dropLast(u8, &stacks2.items[fromIdx], n));
    }

    std.debug.print("Part 1: {s}\n", .{getResult(stacks)});
    std.debug.print("Part 2: {s}\n", .{getResult(stacks2)});

    var part1: i32 = 0;
    var part2: i32 = 0;

    return .{ .part1 = part1, .part2 = part2 };
}

fn dropLast(comptime T: type, list: *std.ArrayList(T), n: usize) []T {
    const dropped = list.items[list.items.len - n ..];
    list.items.len -= n;
    return dropped;
}

fn getResult(stacks: Stacks) [16]u8 {
    var result = [_]u8{0} ** 16;
    for (stacks.items) |stack, i| {
        result[i] = stack.items[stack.items.len - 1];
    }
    return result;
}

test {
    const sample =
        \\    [D]    
        \\[N] [C]    
        \\[Z] [M] [P]
        \\ 1   2   3 
        \\
        \\move 1 from 2 to 1
        \\move 3 from 1 to 3
        \\move 2 from 2 to 1
        \\move 1 from 1 to 2
    ;
    const res = try run(sample);
    try std.testing.expectEqual(@as(i32, 0), res.part1);
    try std.testing.expectEqual(@as(i32, 0), res.part2);
}
