package wods.crossfit.member.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wods.crossfit.workout.domain.Workout;
import wods.crossfit.global.common.BaseEntity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email", nullable = false)
    private String email;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "member_box")
    private String box;

    @OneToMany(mappedBy = "member")
    private List<Workout> workouts = new ArrayList<>();

    @Builder
    public Member(String email, String password, String name, String box) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.box = box;
    }

    public void resetPassword(String password) {
        this.password = password;
    }

    public void update(String email, String password, String name, String box) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.box = box;
    }

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // 사용자 id 반환 (고유한 값)
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자 패스워드 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료 여부 확인 로직
        return true; // 만료 x -> true | 만료 o -> false
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인 로직
        return true; // 잠금 x -> true | 잠금 o -> false
    }

    // 패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인 로직
        return true; // 만료 x -> true | 만료 o -> false
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true;
    }
}
