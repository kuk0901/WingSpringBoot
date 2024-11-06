import { formatPhoneNumber } from "../util/format.js";
import { setupCustomValidityMessages } from "../util/validation.js";

const $form = $("#findAccountForm");

// 커스텀 유효성 검사 메시지
const messages = {
  pwd: "올바른 비밀번호 형식으로 입력해주세요. (8자의 영문자와 숫자 조합, 대소문자 구분 없음, 공백 제외)"
};

setupCustomValidityMessages($form, messages);

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
    url: "/api/auth/find/account",
    data: JSON.stringify(formData),
    contentType: "application/json",
    dataType: "json",
    success: function(res) {
      createMemberAccountView(res.member);
    },
    error: function(xhr, status, error) {
      const msg = xhr.responseJSON ? xhr.responseJSON.errorMsg : "알 수 없는 오류가 발생했습니다.";
      $("#errorMsg").addClass("active");
    }
  });
});

function createMemberAccountView(data) {
  const memberAccountView = `
  <div class="account-container">
    <div class="label-container">
      <label for="email" class="text__white">계정</label>
    </div>
    <div class="input-container">
      <input
        id="email"
        name="email"
        type="email"
        readonly
        pattern="^(?=.{6,36}$)[a-z0-9_]+@[a-z0-9.\-]+\.[a-z]{2,}$"  
        class="signin--input__m"
        autocomplete="username"
        value="${data.email}"
      />
    </div>
  </div>
    
  <div class="btn-container">
    <button id="backBtn" class="btn btn__sign text__white">돌아가기</button>
  </div>
  `;

  $form.html(memberAccountView);

  $("#backBtn").on("click", function(e) {
    e.preventDefault();

    document.cookie = "userEmail=" + encodeURIComponent(data.email) + ";max-age=1;path=/";

    location.href = "/";
  })
}

$("#phone").on("blur", function() {
  formatPhoneNumber(this);
});