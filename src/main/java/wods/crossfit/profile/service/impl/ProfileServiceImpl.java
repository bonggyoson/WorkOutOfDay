package wods.crossfit.profile.service.impl;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import wods.crossfit.global.util.FileStore;
import wods.crossfit.member.domain.Member;
import wods.crossfit.profile.domain.Profile;
import wods.crossfit.profile.domain.dto.ProfileDto;
import wods.crossfit.profile.domain.dto.ProfileDto.ProfileResponse;
import wods.crossfit.profile.domain.dto.ProfileDto.UploadFile;
import wods.crossfit.member.repository.MemberRepository;
import wods.crossfit.profile.repository.ProfileRepository;
import wods.crossfit.profile.service.ProfileService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    private final MemberRepository memberRepository;

    private final FileStore fileStore;

    @Override
    @Transactional
    public void updateProfile(ProfileDto.ProfileRequest dto) throws IOException {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new NotFoundException("해당 하는 회원이 존재하지 않습니다."));

        UploadFile uploadFile = fileStore.storeFile(dto.getProfileImage());

        Profile profile = profileRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new NotFoundException("회원의 프로필이 존재하지 않습니다."));

        if (uploadFile == null) {
            uploadFile = new UploadFile(profile.getUploadFileName(), profile.getStoreFileName(),
                    profile.getStorePath());
            profile.update(dto, uploadFile);
        } else {
            profile.update(dto, uploadFile);
        }
    }

    @Override
    public ProfileResponse findByMemberId(long id) {
        return profileRepository.findByMemberId(id).map(ProfileResponse::new)
                .orElseThrow(() -> new NotFoundException("해당 프로필이 존재하지 않습니다."));
    }
}
