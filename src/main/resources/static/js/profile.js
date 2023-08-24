// 벤치마크 등록
const createBenchmark = document.getElementById('create-benchmark');

if (createBenchmark) {
  createBenchmark.addEventListener("click", () => {
    let memberId = document.getElementById('memberId').value;
    let profileId = document.getElementById('profileId').value;
    let benchmark_arr = [];

    $("input[name='benchmark']:checked").each(function () {
      let chked = $(this).val();
      benchmark_arr.push(chked);
    });

    fetch("/api/profile/benchmark", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        id: benchmark_arr,
        profileId: profileId,
      }),
    }).then((response) => {
      if (response.ok) {
        swal({
          text: "벤치마크가 등록되었습니다.",
          icon: "success",
          button: false,
        });
        setTimeout(() => {
          location.replace(`/myPage/myProfile?id=${memberId}`);
        }, 1500);
      }
    })
  });
}

// 벤치마크 삭제
function deleteBenchmark(id) {
  let memberId = document.getElementById('memberId').value;

  swal({
    title: "해당 벤치마크를 삭제하시겠습니까?",
    icon: "warning",
    buttons: ["아니요", "예"],
    dangerMode: true,
  })
  .then((result) => {
    if (result) {
      fetch(`/api/profile/benchmark/${id}`, {
        method: 'DELETE'
      })
      .then(() => {
        swal("벤치마크가 삭제되었습니다.", {
          icon: "success",
        });
        setTimeout(() => {
          location.replace(`/myPage/myProfile?id=${memberId}`);
        }, 1500);
      })
    }
  });
}

// 벤치마크 기록 등록
function updateBenchmark(id) {
  let memberId = document.getElementById('memberId').value;
  let record = document.getElementById('benchmarkRecord-' + id).value;

  let regExp = /^[0-9]*$/;

  if (regExp.test(record) && record !== '') {
    fetch(`/api/profile/benchmark/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        record: record,
      }),
    }).then((response) => {
      if (response.ok) {
        swal({
          text: "벤치마크 기록이 등록되었습니다.",
          icon: "success",
          button: false,
        });
        setTimeout(() => {
          location.replace(`/myPage/myProfile?id=${memberId}`);
        }, 1500);
      }
    })
  } else {
    swal({
      text: "숫자만 입력 가능합니다.",
      icon: "error",
      button: false,
    });
    return false;
  }
}

function inputBenchmark(e) {
  if (document.getElementById('benchmark_' + e).innerHTML
      === '<input id="test" type="text">') {
    document.getElementById('test').remove();
  }
  document.getElementById(
      'benchmark_' + e).innerHTML = "<input id='test' type='text'>"
}