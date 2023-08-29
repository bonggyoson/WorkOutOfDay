package wods.crossfit.profile.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wods.crossfit.profileBenchmark.domain.ProfileBenchmark;
import wods.crossfit.global.common.BaseEntity;
import wods.crossfit.profile.domain.dto.ProfileDto.ProfileRequest;
import wods.crossfit.profile.domain.dto.ProfileDto.UploadFile;
import wods.crossfit.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @Column(name = "profile_height")
    private String height;

    @Column(name = "profile_weight")
    private String weight;

    @Column(name = "profile_upload_filename")
    private String uploadFileName;

    @Column(name = "profile_store_filename")
    private String storeFileName;

    @Column(name = "profile_store_path")
    private String storePath;

    @Column(name = "profile_instagram")
    private String instagram;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "profile")
    private List<ProfileBenchmark> profileBenchmarks = new ArrayList<>();

    @Builder
    public Profile(String height, String weight, Member member, String uploadFileName,
            String storeFileName, String storePath, String instagram) {
        this.height = height;
        this.weight = weight;
        this.member = member;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.storePath = storePath;
        this.instagram = instagram;
    }

    public void update(ProfileRequest dto, UploadFile uploadFile) {
        this.height = dto.getHeight();
        this.weight = dto.getWeight();
        this.uploadFileName = uploadFile.getUploadFileName();
        this.storeFileName = uploadFile.getStoreFileName();
        this.storePath = uploadFile.getStorePath();
        this.instagram = dto.getInstagram();
    }
}
