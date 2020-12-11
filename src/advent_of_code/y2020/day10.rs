use crate::advent_of_code::support::*;

fn solve(nums: &Vec<u32>) -> (u32, u64) {
    let mut q: [u64; 3] = [0, 0, 1];
    let mut i = 0;
    let mut prev = 0;
    let mut ones = 0;
    let mut threes = 0;
    for &x in nums {
        let d = x - prev;
        prev = x;
        match d {
            1 => ones += 1,
            3 => threes += 1,
            _ => ()
        }
        for _ in 1..d {
            q[i % 3] = 0;
            i += 1;
        }
        q[i % 3] = q[0] + q[1] + q[2];
        i += 1;
    }
    (ones * (threes + 1), q[(i - 1) % 3])
}

pub fn parse(s: &String) -> Vec<u32> {
    let mut nums: Vec<u32> = s.lines().map(|s| s.parse().unwrap()).collect();
    nums.sort_unstable();
    nums
}

pub fn run() {
    let input = get_input(2020, 10);
    let nums = parse(&input);

    println!("{:?}", timed("With parsing", || { solve(&parse(&input)) }));
    println!("{:?}", timed("Without parsing", || { solve(&nums) }));
}
