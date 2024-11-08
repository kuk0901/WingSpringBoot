import { formatPhoneNumber } from "../util/format.js";
import { showAlertMsg } from "../util/toast.js";
import { pwdRegex, setupCustomValidityMessages } from "../util/validation.js";

// const $pwdInput = $("#pwd");
// const $pwdError = $("#pwdError");
// const $pwdCheck = $("#pwdCheck");
// const $pwdCheckError = $("#pwdCheckError");

const $form = $("#findPwdForm");

const messages = {
  pwd: "올바른 비밀번호 형식으로 입력해주세요. (8자의 영문자와 숫자 조합, 대소문자 구분 없음, 공백 제외)"
};

setupCustomValidityMessages($form, messages);

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
    url: "/api/auth/find/password",
    data: JSON.stringify(formData),
    contentType: "application/json",
    dataType: "json",
    success: function(res) {
      createNewPasswordSetView(res.member);
    },
    error: function(xhr, status, error) {
      const msg = xhr.responseJSON ? xhr.responseJSON.errorMsg : "알 수 없는 오류가 발생했습니다.";
      $("#errorMsg").addClass("active");
    }
  });
});

// FIXME: UI 수정
function createNewPasswordSetView(data) {
  const memberNewPasswordSetView = `
    <div class="password-container">
      <div class="label-container">
        <label for="userName" class="text__white">성명</label>
        <span id="userNameError"></span>
      </div>
      <div class="input-container">
        <input
          id="userName"
          name="userName"
          type="text"
          required
          pattern="[가-힣]{2,7}"
          placeholder="ex) 홍길동"
          value="${data.userName}"
        />
      </div>
    </div>

    <div class="password-container">
      <div class="label-container">
        <label for="email" class="text__white">이메일</label>
        <span id="emailError"></span>
      </div>
      <div class="input-container email-check-porision">
        <input
          id="email"
          name="email"
          type="email"
          required
          pattern="^(?=.{6,36}$)[a-z0-9_]+@[a-z0-9.\\-]+\\.[a-z]{2,}$"
          placeholder="ex) wing_@gmail.com"
          autocomplete="off"
          value="${data.email}"
        />
      </div>
    </div>
    
    <div class="password-container">
      <div class="label-container">
        <label for="pwd" class="text__white">패스워드</label>
        <span id="pwdError"></span>
      </div>
      <div class="input-container">
        <input
          id="pwd"
          name="pwd"
          type="password"
          placeholder="비밀번호를 작성해 주세요."
          required
          pattern="^(?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z\\d]{8,21}$"
          autocomplete="off"
        />
      </div>
    </div>

    <div class="password-container">
      <div class="label-container">
        <label for="pwdCheck" class="text__white">패스워드 확인</label>
        <span id="pwdCheckError"></span>
      </div>
      <div class="input-container">
        <input
          id="pwdCheck"
          name="pwdCheck"
          type="password"
          placeholder="동일한 비밀번호를 작성해 주세요."
          required
          pattern="^(?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z\\d]{8,21}$"
          autocomplete="off"
        />
      </div>
    </div>
    
    <div class="submit-container">
      <button id="changePasswordButton" class="btn btn__sign text__white">Change</button>
    </div>
  `;

  $form.html(memberNewPasswordSetView);
  $("#formContainer").addClass("active");
  setupNewPasswordForm();
}

function setupNewPasswordForm() {
  const $newPwdInput = $("#pwd");
  const $newPwdError = $("#pwdError");
  const $newPwdCheck = $("#pwdCheck");
  const $newPwdCheckError = $("#pwdCheckError");

  function validatePassword() {
    const pwdValue = $newPwdInput.val();
    const pwdCheckValue = $newPwdCheck.val();

    const isPwdValid = pwdRegex.test(pwdValue);

    if (!isPwdValid) {
      $newPwdError.text("비밀번호는 8-21자의 영문자와 숫자 조합이어야 합니다.")
          .removeClass("text__correct")
          .addClass("text__error");
    } else {
      $newPwdError.text("")
          .removeClass("text__error text__correct");
    }

    const isMatching = pwdValue === pwdCheckValue;

    if (pwdCheckValue) {
      $newPwdCheckError.text(isMatching ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다.")
          .removeClass("text__correct text__error")
          .addClass(isMatching ? "text__correct" : "text__error");
    } else {
      $newPwdCheckError.text("")
          .removeClass("text__correct text__error");
    }

    return { isPwdValid, isMatching };
  }

  $newPwdInput.on("blur", validatePassword);
  $newPwdCheck.on("blur", validatePassword);

  $("#changePasswordButton").on("click", function(e) {
    e.preventDefault();

    if (!$form[0].checkValidity()) {
      $form[0].reportValidity();
      return;
    }

    const formData = {};
    $form.serializeArray().forEach(function(item) {
      formData[item.name] = item.value;
    });

    // 비밀번호 확인
    if (formData.pwd !== formData.pwdCheck) {
      $newPwdCheckError.text("비밀번호가 일치하지 않습니다.");
      return;
    }

    // pwdCheck 필드 제거
    delete formData.pwdCheck;

    $.ajax({
      type: "PATCH",
      url: "/api/auth/find/password",
      data: JSON.stringify(formData),
      contentType: "application/json",
      dataType: "json",
      success: function(res) {
        if (res.status === "success") {
          document.cookie = "alertMsg=" + encodeURIComponent(res.alertMsg) + ";max-age=1;path=/";
          window.location.href = "/";
        } else {
          showAlertMsg(res.alertMsg || "비밀번호 변경에 실패했습니다.");
        }
      },
      error: function(xhr, status, error) {
        const msg = xhr.responseJSON ? xhr.responseJSON.alertMsg : "알 수 없는 오류가 발생했습니다.";
        showAlertMsg(msg);
      }
    });
  });
}

$("#phone").on("blur", function() {
  formatPhoneNumber(this);
});