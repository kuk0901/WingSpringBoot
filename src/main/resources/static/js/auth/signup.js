import {
  emailRegex, pwdRegex, userNameRegex, phoneRegex, salaryRegex, forbiddenPatterns,
  validateInput, updateUI, setupCustomValidityMessages
} from '../util/validation.js';
import { formatNumber, formatPhoneNumber, unformatNumber } from "../util/format.js";
import { showAlertMsg } from "../util/toast.js";
import { encodeCustom } from "../util/base62.js"

const $form = $("#signupForm");
const $email = $("#email");
const $emailError = $("#emailError");
const $pwdInput = $("#pwd");
const $pwdError = $("#pwdError");
const $pwdCheck = $("#pwdCheck");
const $pwdCheckError = $("#pwdCheckError");
const $userName = $("#userName");
const $userNameError = $("#userNameError");
const $phoneInput = $("#phone");
const $phoneError = $("#phoneError");
const $salaryInput = $("#salary");
const $salaryError = $("#salaryError");
const $payInput = $("#pay");
const $payError = $("#payError");
const $submitBtn = $("#submitBtn");

function validateEmail(email) {
  const baseValidation = validateInput(email, emailRegex, "올바른 이메일 형식이 아닙니다.");
  if (!baseValidation.isValid) return baseValidation;
  if (forbiddenPatterns.some(pattern => email.toLowerCase().includes(pattern))) {
    return { isValid: false, message: "사용할 수 없는 이메일입니다." };
  }
  return { isValid: true, message: "" };
}

function validatePassword() {
  const pwdValue = $pwdInput.val();
  const pwdCheckValue = $pwdCheck.val();

  const isPwdValid = pwdRegex.test(pwdValue);

  if (!isPwdValid) {
    $pwdError.text("비밀번호는 8-21자의 영문자와 숫자 조합이어야 합니다.")
        .removeClass("text__correct")
        .addClass("text__error");
  } else {
    $pwdError.text("")
        .removeClass("text__error text__correct");
  }

  const isMatching = pwdValue === pwdCheckValue;

  if (pwdCheckValue) {
    $pwdCheckError.text(isMatching ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다.")
        .removeClass("text__correct text__error")
        .addClass(isMatching ? "text__correct" : "text__error");
  } else {
    $pwdCheckError.text("")
        .removeClass("text__correct text__error");
  }

  return { isPwdValid, isMatching };
}


function updateSubmitButtonState() {
  const isEmailValid = validateEmail($email.val()).isValid;
  const { isPwdValid, isMatching } = validatePassword();
  const isUserNameValid = validateInput($userName.val(), userNameRegex, "").isValid;
  const isPhoneValid = validateInput($phoneInput.val(), phoneRegex, "").isValid;
  const isSalaryValid = validateInput($salaryInput.val(), salaryRegex, "").isValid;
  const isPayValid = validateInput($payInput.val(), salaryRegex, "").isValid;

  $submitBtn.prop("disabled", !(isEmailValid && isPwdValid && isMatching && isUserNameValid && isPhoneValid && isSalaryValid && isPayValid));
}

$email.on("blur", function() {
  updateUI($email, $emailError, validateEmail($(this).val()));
  updateSubmitButtonState();
});

$pwdInput.on("focus", function() {
  $pwdError.text("").removeClass("text__error text__correct");
  $pwdCheckError.text("").removeClass("text__error text__correct");
});

$pwdInput.on("blur", function() {
  const pwdValue = $(this).val();
  if (pwdValue === "") {
    $pwdError.text("필수 입력 항목입니다.")
        .removeClass("text__correct")
        .addClass("text__error");
  } else {
    validatePassword();
  }
  updateSubmitButtonState();
});

$pwdCheck.on("blur", function() {
  const pwdCheckValue = $(this).val();
  if (pwdCheckValue === "") {
    $pwdCheckError.text("필수 입력 항목입니다.")
        .removeClass("text__correct")
        .addClass("text__error");
  } else {
    validatePassword();
  }
  updateSubmitButtonState();
});

$userName.on("blur", function() {
  updateUI($userName, $userNameError, validateInput($(this).val(), userNameRegex, "2~7자의 한글 이름을 입력해주세요."));
  updateSubmitButtonState();
});

$phoneInput.on("blur", function() {
  formatPhoneNumber(this);
  updateUI($phoneInput, $phoneError, validateInput($(this).val(), phoneRegex, "올바른 휴대폰 번호 형식으로 입력해주세요."));
  updateSubmitButtonState();
});

$salaryInput.on("blur", function() {
  formatNumber(this);
  updateUI($salaryInput, $salaryError, validateInput($(this).val(), salaryRegex, "올바른 형식으로 입력해주세요. (예: 4,500 또는 6,000)"));
  updateSubmitButtonState();
});

$payInput.on("blur", function() {
  formatNumber(this);
  updateUI($payInput, $payError, validateInput($(this).val(), salaryRegex, "올바른 형식으로 입력해주세요. (예: 300 또는 500)"));
  updateSubmitButtonState();
});

$form.on("submit", function(e) {
  e.preventDefault();

  if (!this.checkValidity()) {
    this.reportValidity();
    return;
  }

  const formData = {};
  $(this).serializeArray().forEach(function(item) {
    switch(item.name) {
      case "yearlySalary":
      case "monthlySalary":
        formData[item.name] = unformatNumber(item.value);
        break;
      case "pwd":
        formData[item.name] = encodeCustom(item.value);
        break;
      default:
        formData[item.name] = item.value;
    }
  });

  $.ajax({
    type: "POST",
    url: "/api/auth/signup",
    data: JSON.stringify(formData),
    contentType: "application/json",
    dataType: "json",
    success: function(res) {
      const message = encodeURIComponent(res.alertMsg || "회원가입이 완료되었습니다.");
      window.location.href = `/?message=${message}`;
    },
    error: function(xhr, status, error) {
      let msg = xhr.responseJSON ? xhr.responseJSON.alertMsg : "알 수 없는 오류가 발생했습니다.";

      if (xhr.responseJSON.emailMsg) {
        msg = xhr.responseJSON.emailMsg;
        updateUI($email, $emailError, { isValid: false, message: msg });
      }

      showAlertMsg(msg);
    }
  });
});

// 커스텀 유효성 검사 메시지 설정
setupCustomValidityMessages($form, {
  pwd: "올바른 비밀번호 형식으로 입력해주세요. (8자의 영문자와 숫자 조합, 대소문자 구분, 공백 제외)"
});