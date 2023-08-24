// 회원 가입
const saveMember = document.getElementById('save-member');

if (saveMember) {
  saveMember.addEventListener("click", () => {

    if (emptyMemberFormChecking.check()) {
      fetch("/api/member", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: document.getElementById('email').value,
          password: document.getElementById('password').value,
          name: document.getElementById('name').value,
          box: document.getElementById('box').value,
        }),
      }).then((response) => {
        response.json()
        .then((data) => {
          if (data.status === 'CREATED') {
            swal({
              text: data.message,
              icon: "success",
              button: false,
            });
            setTimeout(() => {
              location.replace("/login");
            }, 1000);
          } else {
            swal({
              text: data.message,
              icon: "error",
              button: false,
            });

            if (data.data != null) {
              data.data.forEach((x) =>
                  document.getElementById(x.field).className += ' field-error'
              );
            } else {
              document.getElementById('email').className += ' field-error'
            }

            return false;
          }
        })
      })
    }
  });
}

// 회원 수정
const updateMember = document.getElementById('update-member');

if (updateMember) {
  updateMember.addEventListener("click", () => {

    let id = document.getElementById('id').value;

    if (emptyMemberFormChecking.check()) {
      fetch(`/api/member/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: document.getElementById('email').value,
          password: document.getElementById('password').value,
          name: document.getElementById('name').value,
          box: document.getElementById('box').value,
        }),
      }).then((response) => {
        response.json()
        .then(data => {
          if (data.status === 'OK') {
            swal({
              text: data.message,
              icon: "success",
              button: false,
            });
            setTimeout(() => {
              location.replace("/myPage");
            }, 1500);
          } else {
            swal({
              text: data.message,
              icon: "error",
              button: false,
            });
            document.getElementById('email').className += ' field-error';
            return false;
          }
        })
      })
    }
  });
}

// 회원 삭제
const deleteMember = document.getElementById('delete-member');

if (deleteMember) {
  deleteMember.addEventListener('click', event => {
    let id = document.getElementById('id').value;

    swal({
      title: "회원을 탈퇴 하시겠습니까?",
      text: "회원을 탈퇴 하실 경우 작성 하신 오늘의 운동이 모두 삭제됩니다.",
      icon: "warning",
      buttons: ["아니요", "예"],
      dangerMode: true,
    })
    .then((result) => {
      if (result) {
        fetch(`/api/member/${id}`, {
          method: 'DELETE'
        })
        .then((response) => {
          response.json()
          .then(data => {
            swal({
              text: data.message,
              icon: "success"
            });
            setTimeout(() => {
              location.replace("/");
            }, 1500);
          })
        })
      }
    });
  })
}

const emptyMemberFormChecking = {
  check: () => {
    if (document.getElementById('email').value === '') {
      document.getElementById('email').className += ' field-error';
      swal({
        text: "이메일을 입력해주세요.",
        icon: "error",
        button: false,
      });
      return false;
    } else {
      document.getElementById('email').classList.remove('field-error');
    }

    if (document.getElementById('password').value === '') {
      document.getElementById('password').className += ' field-error';
      swal({
        text: "비밀번호를 입력해주세요.",
        icon: "error",
        button: false,
      });
      return false;
    } else {
      document.getElementById('password').classList.remove('field-error');
    }

    if (document.getElementById('name').value === '') {
      document.getElementById('name').className += ' field-error';
      swal({
        text: "이름을 입력해주세요.",
        icon: "error",
        button: false,
      });
      return false;
    } else {
      document.getElementById('name').classList.remove('field-error');
    }

    return true;
  }
}