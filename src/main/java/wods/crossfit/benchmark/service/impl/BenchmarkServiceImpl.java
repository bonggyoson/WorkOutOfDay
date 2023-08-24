package wods.crossfit.benchmark.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wods.crossfit.benchmark.domain.dto.BenchmarkDto;
import wods.crossfit.benchmark.repository.BenchmarkRepository;
import wods.crossfit.benchmark.service.BenchmarkService;

@Service
@RequiredArgsConstructor
public class BenchmarkServiceImpl implements BenchmarkService {

    private final BenchmarkRepository benchmarkRepository;

    @Override
    public List<BenchmarkDto.BenchmarkResponse> getBenchmarks() {
        return benchmarkRepository.findAll().stream()
                .map(BenchmarkDto.BenchmarkResponse::new)
                .collect(Collectors.toList());
    }
}
