// DOM 요소들에 대한 참조
const $form = $("#signinForm");

// 커스텀 유효성 검사 메시지
const messages = {
  email: "올바른 이메일 형식으로 입력해주세요. (예: wind@gmail.com)",
  pwd: "올바른 비밀번호 형식으로 입력해주세요. (대소문자 구분 없음, 공백 제외)"
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
      console.log(res.message);
    },
    error: function(xhr, status, error) {
      console.log(error.message);
    }
  });
});



