// 댓글 등록
const saveComment = document.getElementById('save-comment');

if (saveComment) {
  let memberId = document.getElementById("member-id").value;
  let workoutId = document.getElementById("workout-id").value;

  saveComment.addEventListener("click", () => {
    if (document.getElementById('comment').value === '') {
      swal({
        text: "댓글을 입력해주세요.",
        icon: "warning",
        button: false,
      });
      return false;
    } else {
      fetch("/api/comment", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          content: document.getElementById('comment').value,
          memberId: memberId,
          workoutId: workoutId,
        }),
      }).then((response) => {
        if (response.ok) {
          swal({
            text: "댓글이 등록되었습니다.",
            icon: "success",
            button: false,
          });
          setTimeout(() => {
            location.replace(`/workout/${workoutId}`);
          }, 1500);
        }
      })
    }
  });
}

// 댓글 수정
const updateComment = document.getElementById('update-comment');

if (updateComment) {
  let id = document.getElementById('comment-id').value;
  let memberId = document.getElementById("member-id").value;
  let workoutId = document.getElementById('workout-id').value;

  updateComment.addEventListener("click", () => {
    if (document.getElementById('updateCommentContent').value === '') {
      swal({
        text: "댓글을 입력해주세요.",
        icon: "warning",
        button: false,
      });
      return false;
    } else {
      fetch(`/api/comment/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          content: document.getElementById('updateCommentContent').value,
        }),
      }).then((response) => {
        if (response.ok) {
          swal({
            text: "댓글이 수정되었습니다.",
            icon: "success",
            button: false,
          });
          setTimeout(() => {
            if (document.location.href.includes('myComment')) {
              location.replace(`/myPage/myComment?id=${memberId}`);
            } else {
              location.replace(`/workout/${workoutId}`);
            }
          }, 1500);
        }
      })
    }
  });
}

// 댓글 삭제
function deleteComment(id) {
  let workoutId = document.getElementById('workout-id').value;
  let memberId = document.getElementById("member-id").value;

  swal({
    title: "선택한 댓글을 삭제하시겠습니까?",
    icon: "warning",
    buttons: ["아니요", "예"],
    dangerMode: true,
  })
  .then((result) => {
    if (result) {
      fetch(`/api/comment/${id}`, {
        method: 'DELETE'
      })
      .then(() => {
        swal("선택한 댓글이 삭제되었습니다.", {
          icon: "success",
        });
        setTimeout(() => {
          if (document.location.href.includes('myComment')) {
            location.replace(`/myPage/myComment?id=${memberId}`);
          } else {
            location.replace(`/workout/${workoutId}`)
          }
        }, 1500);
      })
    }
  });
}