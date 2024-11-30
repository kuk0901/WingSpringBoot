import { checkAndShowStoredMessage, showAlertMsg } from "../util/toast.js";
import { deleteCookie } from "../util/cookie.js";

const email = decodeURIComponent(document.cookie.replace(/(?:(?:^|.*;\s*)userEmail\s*\=\s*([^;]*).*$)|^.*$/, "$1"));
const alertMsg = decodeURIComponent(document.cookie.replace(/(?:(?:^|.*;\s*)alertMsg\s*\=\s*([^;]*).*$)|^.*$/, "$1"));

if (email) {
  $("#email").val(email);
  deleteCookie("userEmail");
}

if (alertMsg) {
  showAlertMsg(alertMsg);
  deleteCookie("alertMsg");
}

checkAndShowStoredMessage();

// DOM 요소들에 대한 참조
const $form = $("#signinForm");

// 커스텀 유효성 검사 메시지
const messages = {
  email: "올바른 이메일 형식으로 입력해주세요. (예: wind@gmail.com)",
  pwd: "올바른 비밀번호 형식으로 입력해주세요. (8자의 영문자와 숫자 조합, 대소문자 구분 없음, 공백 제외)"
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
    url: "/api/auth/signin",
    data: JSON.stringify(formData),
    contentType: "application/json",
    dataType: "json",
    success: function(res) {
      const message = encodeURIComponent(res.alertMsg || "로그인에 성공했습니다.");

      switch (res.grade) {
        case "ADMIN":
          location.href = `/admin/dashboard/card?message=${message}`;
          break;
        case "MEMBER":
          location.href = `/member/accountBook/list?message=${message}`;
          break;
        default: location.reload();
      }
    },
    error: function(xhr, status, error) {
      const msg = xhr.responseJSON ? xhr.responseJSON.alertMsg : "알 수 없는 오류가 발생했습니다.";

      showAlertMsg(msg);
    }
  });
});


