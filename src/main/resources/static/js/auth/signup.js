import {formatNumber, formatPhoneNumber} from "../util/fotmat.js";

const $form = $("#signupForm");
const $pwdInput = $("#pwd");
const $pwdCheck = $("#pwdCheck");
const $errorMsg = $("#pwdError");
const $phoneInput = $("#phone");
const $salaryInput = $("#salary");
const $payInput = $("#pay");
const $submitBtn = $("#submitBtn");


// 비밀번호 유효성 검사
function validatePassword() {
  const isValid = $pwdInput.val() === $pwdCheck.val();
  $errorMsg.text(isValid ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다.")
      .removeClass("text__correct text__error")
      .addClass(isValid ? "text__correct" : "text__error");
  $submitBtn.prop("disabled", !isValid);
  return isValid;
}

$phoneInput.on("blur", function ()  {
  formatPhoneNumber(this)
})

// salary와 pay 입력 필드에 이벤트 리스너 추가
$salaryInput.on("blur", function() {
  formatNumber(this);
});

$payInput.on("blur", function() {
  formatNumber(this);
});

// 커스텀 유효성 검사 메시지
const messages = {
  email: "올바른 이메일 형식으로 입력해주세요. (예: wind@gmail.com)",
  pwd: "비밀번호는 8자의 영문자와 숫자 조합이어야 합니다. (대소문자 구분 없음, 공백 제외)",
  pwdCheck: "비밀번호가 일치하지 않습니다.",
  userName: "2~7자의 한글 이름을 입력해주세요.",
  phone: "올바른 휴대폰 번호 형식으로 입력해주세요. (예: 01012345678)",
  salary: "올바른 형식으로 입력해주세요. (예: 4,500 또는 6,000)",
  pay: "올바른 형식으로 입력해주세요. (예: 300 또는 500)"
};

// 커스텀 유효성 검사 메시지 설정
$form.find("input").each(function() {
  $(this).on("invalid", function() {
    if (!this.validity.valid) {
      this.setCustomValidity(messages[this.id] || "올바른 값을 입력해주세요.");
    }
  }).on("input", function() {
    this.setCustomValidity("");
  });
});

// 폼 제출 이벤트 처리
$form.on("submit", function(e) {
  e.preventDefault();

  if (!this.checkValidity()) {
    this.reportValidity();
    return;
  }

  const formData = {};
  $(this).serializeArray().forEach(function(item) {
    formData[item.name] = item.value;
  });

  $.ajax({
    type: "POST",
    url: "/api/auth/signup",
    data: JSON.stringify(formData),
    contentType: "application/json",
    dataType: "json",
    success: function(res) {
      console.log(res.message);
    },
    error: function(xhr, status, error) {
      console.log(error.message);
    }
  });
});

//이메일 검증
$("#checkEmailBtn").on("click", function() {
  const email = $("#email").val(); // 이메일 입력값 가져오기

  // 이메일 검증 요청
  $.ajax({
    type: "POST",
    url: "/api/auth/check-email", // 이메일 검증 요청 URL
    data: JSON.stringify({ email: email }), // 요청 데이터
    contentType: "application/json",
    dataType: "json",
    success: function(res) {
      if (res.status === "failed") {
        alert(res.message); // 이메일 중복 메시지 표시
      } else {
        alert("사용 가능한 이메일입니다."); // 사용 가능 메시지 표시
      }
    },
    error: function(xhr, status, error) {
      console.log("이메일 검증 중 오류 발생: " + error.message); // 오류 메시지 표시
    }
  });
});
// 비밀번호 확인 필드에 blur 이벤트 리스너 추가
$pwdCheck.on("blur", validatePassword);
