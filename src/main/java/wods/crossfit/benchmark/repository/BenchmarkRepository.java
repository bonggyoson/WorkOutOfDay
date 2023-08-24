package wods.crossfit.benchmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wods.crossfit.benchmark.domain.Benchmark;

public interface BenchmarkRepository extends JpaRepository<Benchmark, Long> {

}
