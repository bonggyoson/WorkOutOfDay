DELETE FROM wods.profile_benchmark;
DELETE FROM wods.benchmark;
ALTER TABLE wods.benchmark AUTO_INCREMENT = 1;

insert into wods.benchmark(created_at, updated_at, benchmark_title, benchmark_content)
values (now(), now(), 'BENCH PRESS', 'lb');

insert into wods.benchmark(created_at, updated_at, benchmark_title, benchmark_content)
values (now(), now(), 'BACK SQUAT', 'lb');

insert into wods.benchmark(created_at, updated_at, benchmark_title, benchmark_content)
values (now(), now(), 'FRAN', 'Min/Sec');

insert into wods.benchmark(created_at, updated_at, benchmark_title, benchmark_content)
values (now(), now(), 'DEADLIFT', 'lb');

insert into wods.benchmark(created_at, updated_at, benchmark_title, benchmark_content)
values (now(), now(), 'MAX PULLUP', 'Reps');

insert into wods.benchmark(created_at, updated_at, benchmark_title, benchmark_content)
values (now(), now(), 'GRACE', 'Min/Sec');

insert into wods.benchmark(created_at, updated_at, benchmark_title, benchmark_content)
values (now(), now(), 'SNATCH', 'lb');

insert into wods.benchmark(created_at, updated_at, benchmark_title, benchmark_content)
values (now(), now(), 'CLEAN', 'lb')

