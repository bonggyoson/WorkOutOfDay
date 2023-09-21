// 회원 가입
const saveMember = document.getElementById('save-member');

if (saveMember) {
  saveMember.addEventListener("click", () => {

    if (emptyMemberFormChecking.check()) {
      fetch("/api/members", {
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

// 로그인
const login = document.getElementById('login');

if (login) {
  login.addEventListener("click", () => {
    // body = JSON.stringify({
    //   email: document.getElementById('username').value,
    //   password: document.getElementById('password').value,
    // });
    //
    // function success() {
    //   location.replace("/");
    // }
    //
    // function fail() {
    //   swal({
    //     title: "WORK OUT OF DAY",
    //     text: "가입되지 않은 계정이거나 \n 아이디 또는 패스워드를 잘못 입력하셨습니다.",
    //     icon: "error",
    //     button: false,
    //   });
    // }
    //
    // httpRequest("POST", "/login", body, success, fail);

    fetch(`/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: document.getElementById('username').value,
        password: document.getElementById('password').value,
      }),
    }).then((response) => {
      if (response.status === 200) {
        location.replace("/");
      } else {
        swal({
          title: "WORK OUT OF DAY",
          text: "가입되지 않은 계정이거나 \n 아이디 또는 패스워드를 잘못 입력하셨습니다.",
          icon: "error",
          button: false,
        });
      }
    })
  })
}

// 회원 수정
const updateMember = document.getElementById('update-member');

if (updateMember) {
  updateMember.addEventListener("click", () => {

    let id = document.getElementById('id').value;

    if (emptyMemberFormChecking.check()) {
      fetch(`/api/members/${id}`, {
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
        fetch(`/api/members/${id}`, {
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

// 쿠키를 가져오는 함수
function getCookie(key) {
  let result = null;
  let cookie = document.cookie.split(";");
  cookie.some(function (item) {
    item = item.replace(" ", "");

    let dic = item.split("=");

    if (key === dic[0]) {
      result = dic[1];
      return true;
    }
  });

  return result;
}

function httpRequest(method, url, body, success, fail) {
  fetch(url, {
    method: method,
    headers: {
      Authorization: "Bearer " + localStorage.getItem("access_token"),
      "Content-Type": "application/json",
    },
    body: body,
  }).then((response) => {
    response.json().then(data => {
      if (data.status === 'OK') {
        // localStorage.setItem("access_token", data.data.accessToken);
        // localStorage.setItem("refresh_token", data.data.refreshToken);
        success();
      } else {
        fail();
      }
    });
    const refresh_token = getCookie("refresh_token");
  })
}