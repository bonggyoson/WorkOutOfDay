package wods.crossfit.profile.service;

import java.io.IOException;
import wods.crossfit.profile.domain.dto.ProfileDto;
import wods.crossfit.profile.domain.dto.ProfileDto.ProfileResponse;

public interface ProfileService {

    void updateProfile(ProfileDto.ProfileRequest dto) throws IOException;

    ProfileResponse findByMemberId(long id);

}
