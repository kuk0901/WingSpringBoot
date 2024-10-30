import { formatNumber, formatPhoneNumber, unformatNumber } from "../util/format.js";
import { showAlertMsg } from "../util/toast.js";

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

const emailRegex = /^(?=.{6,36}$)[a-z0-9_]+@[a-z0-9.\-]+\.[a-z]{2,}$/;
const forbiddenPatterns = ['null', 'undefined', 'admin'];
const pwdRegex = /^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{8,21}$/;
const userNameRegex = /^[가-힣]{2,7}$/;
const phoneRegex = /^01[016789]-?[0-9]{3,4}-?[0-9]{4}$/;
const salaryRegex = /^[1-9]\d{0,3}(,\d{3})*$/;

function validateInput(value, regex, errorMessage) {
  if (value == null || value === "") {
    return { isValid: false, message: "필수 입력 항목입니다." };
  }
  if (!regex.test(value)) {
    return { isValid: false, message: errorMessage };
  }
  return { isValid: true, message: "" };
}

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

function updateUI($input, $error, validationResult) {
  if (validationResult.isValid) {
    $error.removeClass("text__error").addClass("text__transparent");
  } else {
    $error.text(validationResult.message)
        .removeClass("text__transparent")
        .addClass("text__error");
  }
  updateSubmitButtonState();
}

function updateSubmitButtonState() {
  const isEmailValid = validateEmail($email.val()).isValid;

  // 비밀번호 유효성 검사 결과 받기
  const { isPwdValid, isMatching } = validatePassword();

  const isUserNameValid = validateInput($userName.val(), userNameRegex, "").isValid;
  const isPhoneValid = validateInput($phoneInput.val(), phoneRegex, "").isValid;
  const isSalaryValid = validateInput($salaryInput.val(), salaryRegex, "").isValid;
  const isPayValid = validateInput($payInput.val(), salaryRegex, "").isValid;

  // 버튼 활성화 상태 업데이트
  $submitBtn.prop("disabled", !(isEmailValid && isPwdValid && isMatching && isUserNameValid && isPhoneValid && isSalaryValid && isPayValid));
}

$email.on("blur", function() {
  updateUI($email, $emailError, validateEmail($(this).val()));
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
    validatePassword(); // 비밀번호 유효성 검사
  }
});

$pwdCheck.on("blur", function() {
  const pwdCheckValue = $(this).val();
  if (pwdCheckValue === "") {
    $pwdCheckError.text("필수 입력 항목입니다.")
        .removeClass("text__correct")
        .addClass("text__error");
  } else {
    validatePassword(); // 비밀번호 확인 유효성 검사
  }
});

$userName.on("blur", function() {
  updateUI($userName, $userNameError, validateInput($(this).val(), userNameRegex, "2~7자의 한글 이름을 입력해주세요."));
});

$phoneInput.on("blur", function() {
  formatPhoneNumber(this);
  updateUI($phoneInput, $phoneError, validateInput($(this).val(), phoneRegex, "올바른 휴대폰 번호 형식으로 입력해주세요."));
});

$salaryInput.on("blur", function() {
  formatNumber(this);
  updateUI($salaryInput, $salaryError, validateInput($(this).val(), salaryRegex, "올바른 형식으로 입력해주세요. (예: 4,500 또는 6,000)"));
});

$payInput.on("blur", function() {
  formatNumber(this);
  updateUI($payInput, $payError, validateInput($(this).val(), salaryRegex, "올바른 형식으로 입력해주세요. (예: 300 또는 500)"));
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
      case 'yearlySalary':
      case 'monthlySalary':
        formData[item.name] = unformatNumber(item.value);
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