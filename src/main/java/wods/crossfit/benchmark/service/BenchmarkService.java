package wods.crossfit.benchmark.service;

import java.util.List;
import wods.crossfit.benchmark.domain.dto.BenchmarkDto;

public interface BenchmarkService {

    List<BenchmarkDto.BenchmarkResponse> getBenchmarks();

}
