package wods.crossfit.profileBenchmark.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.benchmark.domain.Benchmark;
import wods.crossfit.global.common.BaseEntity;
import wods.crossfit.profile.domain.Profile;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileBenchmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_benchmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benchmark_id")
    private Benchmark benchmark;

    @Column(name = "profile_benchmark_record")
    private String record;

    @Builder
    public ProfileBenchmark(Profile profile, Benchmark benchmark, String record) {
        this.profile = profile;
        this.benchmark = benchmark;
        this.record = record;
    }

    public void updateRecord(String record) {
        this.record = record;
    }
}
