package wods.crossfit.profile.domain.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import wods.crossfit.member.domain.Member;
import wods.crossfit.profile.domain.Profile;
import wods.crossfit.profileBenchmark.domain.dto.ProfileBenchMarkDto.ProfileBenchmarkResponse;

public class ProfileDto {

    @Getter
    @AllArgsConstructor
    public static class ProfileRequest {

        private String height;
        private String weight;
        private String instagram;
        private MultipartFile profileImage;
        private long memberId;

        @Builder
        public Profile toEntity(Member member, UploadFile uploadFile) {
            return Profile.builder()
                    .height(height)
                    .weight(weight)
                    .instagram(instagram)
                    .uploadFileName(uploadFile.uploadFileName)
                    .storeFileName(uploadFile.storeFileName)
                    .storePath(uploadFile.storePath)
                    .member(member)
                    .build();
        }

        @Builder
        public Profile toEntity(Member member) {
            return Profile.builder()
                    .height(height)
                    .weight(weight)
                    .instagram(instagram)
                    .member(member)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UploadFile {

        private String uploadFileName;
        private String storeFileName;
        private String storePath;

        public UploadFile(String uploadFileName, String storeFileName, String storePath) {
            this.uploadFileName = uploadFileName;
            this.storeFileName = storeFileName;
            this.storePath = storePath;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ProfileResponse {

        private long id;
        private String height;
        private String weight;
        private String uploadFileName;
        private String storeFileName;
        private String storePath;
        private String instagram;
        private List<ProfileBenchmarkResponse> profileBenchmarks;

        public ProfileResponse(Profile profile) {
            this.id = profile.getId();
            this.height = profile.getHeight();
            this.weight = profile.getWeight();
            this.storeFileName = profile.getStoreFileName();
            this.uploadFileName = profile.getUploadFileName();
            this.storePath = profile.getStorePath();
            this.instagram = profile.getInstagram();
            this.profileBenchmarks = profile.getProfileBenchmarks()
                    .stream().map(ProfileBenchmarkResponse::new)
                    .collect(Collectors.toList());
        }
    }

}
