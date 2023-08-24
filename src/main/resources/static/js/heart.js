// 좋아요
const increaseHeart = document.getElementById('increaseHeart');

if (increaseHeart) {
  let memberId = document.getElementById("member-id").value;
  let workoutId = document.getElementById("workout-id").value

  increaseHeart.addEventListener("click", () => {
    fetch("/api/heart", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        memberId: memberId,
        workoutId: workoutId
      }),
    }).then((response) => {
      if (response.ok) {
        swal({
          text: "좋아요!!",
          icon: "success",
          button: false,
        });
        setTimeout(() => {
          location.replace(`/workout/${workoutId}`);
        }, 1500);
      }
    })
  });
}

// 좋아요 취소
const decreaseHeart = document.getElementById('decreaseHeart');

if (decreaseHeart) {
  let memberId = document.getElementById("member-id").value;
  let workoutId = document.getElementById("workout-id").value;

  decreaseHeart.addEventListener("click", () => {
    fetch("/api/heart", {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        memberId: memberId,
        workoutId: workoutId,
      }),
    }).then((response) => {
      if (response.ok) {
        swal({
          text: "취소되었습니다 ㅠ",
          icon: "warning",
          button: false,
        });
        setTimeout(() => {
          location.replace(`/workout/${workoutId}`);
        }, 1500);
      }
    })
  });
}
