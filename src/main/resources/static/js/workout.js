// 오늘의 운동 등록
const createWorkout = document.getElementById('save-workout');

if (createWorkout) {
  createWorkout.addEventListener("click", () => {

    if (emptyWorkoutFormChecking.check()) {
      changeTagify.change();

      fetch("/api/workouts", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          memberId: document.getElementById('id').value,
          title: document.getElementById('title').value,
          content: document.getElementById('content').value,
          hashtag: tagify.value,
        }),
      }).then((response) => {
        response.json().then(data => {
          if (data.status === 'CREATED') {
             swal({
              text: data.message,
              icon: "success",
              button: false,
            });
            setTimeout(() => {
              location.replace("/workout");
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
            }
            return false;
          }
        })
      })
    }
  });
}

// 오늘의 운동 수정
const updateWorkout = document.getElementById('update-workout');

if (updateWorkout) {
  updateWorkout.addEventListener("click", () => {

    let id = document.getElementById('id').value;
    let memberId = document.getElementById('memberId').value;

    if (emptyWorkoutFormChecking.check()) {
      changeTagify.change();

      fetch(`/api/workout/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title: document.getElementById('title').value,
          content: document.getElementById('content').value,
          hashtag: tagify.value,
          memberId: memberId,
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
              if (document.location.href.includes('myPage')) {
                location.replace(`/myPage/myWorkout?id=${memberId}`);
              } else {
                location.replace('/workout/updateWorkout/' + id);
              }
            }, 1500);
          } else {
            swal({
              text: data.message,
              icon: "error",
              button: false,
            });
            return false;
          }
        })
      })
    }
  });
}

// 오늘의 운동 삭제
const deleteWorkout = document.getElementById('delete-workout');

if (deleteWorkout) {
  deleteWorkout.addEventListener('click', () => {
    let id = document.getElementById('id').value;
    let memberId = document.getElementById('memberId').value;

    swal({
      title: "오늘의 운동을 삭제하시겠습니까?",
      icon: "warning",
      buttons: ["아니요", "예"],
      dangerMode: true,
    })
    .then((result) => {
      if (result) {
        fetch(`/api/workout/${id}`, {
          method: 'DELETE'
        })
        .then((response) => {
          response.json().then(data => {
            swal({
              text: data.message,
              icon: "success",
            });
            setTimeout(() => {
              location.replace("/myPage/myWorkout?id=" + memberId);
            }, 1500);
          })
        })
      }
    });
  })
}

// 오늘의 운동 등록 및 수정 시 중복 로직 메서드 분리
const emptyWorkoutFormChecking = {
  check: () => {
    if (document.getElementById('title').value === '') {
      document.getElementById('title').className += ' field-error';
      swal({
        text: "제목을 입력해주세요.",
        icon: "error",
        button: false,
      });
      return false;
    } else {
      document.getElementById('title').classList.remove('field-error');
    }

    if (document.getElementById('content').value === '') {
      document.getElementById('content').className += ' field-error';
      swal({
        text: "본문을 입력해주세요.",
        icon: "error",
        button: false,
      });
      return false;
    } else {
      document.getElementById('content').classList.remove('field-error');
    }

    return true;
  },
}

// tagify 객체 수정
const changeTagify = {
  change: () => {
    for (let idx in tagify.value) {
      if (tagify.value[idx].hasOwnProperty('value')) {
        tagify.value[idx].content = tagify.value[idx].value;
        delete tagify.value[idx].value;
        delete tagify.value[idx].__isValid;
        delete tagify.value[idx].__tagId;
      }
    }
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

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
  fetch(url, {
    method: method,
    headers: {
      "Content-Type": "application/json",
    },
    body: body,
  }).then((response) => {
    response.json().then((data) => {
      console.log(data);
    })

    if (response.status === 200 || response.status === 201) {
      return success();
    } else {
      return fail();
    }
    // const refresh_token = getCookie("refresh_token");
    // if (response.status === 401 && refresh_token) {
    //   fetch("/api/token", {
    //     method: "POST",
    //     headers: {
    //       Authorization: "Bearer " + localStorage.getItem("access_token"),
    //       "Content-Type": "application/json",
    //     },
    //     body: JSON.stringify({
    //       refreshToken: getCookie("refresh_token"),
    //     }),
    //   }).then((res) => {
    //     if (res.ok) {
    //       return res.json();
    //     }
    //   })
    //   .then((result) => {
    //     // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
    //     localStorage.setItem("access_token", result.accessToken);
    //     httpRequest(method, url, body, success, fail);
    //   })
    //   .catch((error) => fail());
    // } else {
    //   return fail();
    // }
  });
}