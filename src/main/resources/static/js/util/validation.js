export const emailRegex = /^(?=.{6,36}$)[a-z0-9_]+@[a-z0-9.\-]+\.[a-z]{2,}$/;
export const pwdRegex = /^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{8,21}$/;
export const userNameRegex = /^[가-힣]{2,7}$/;
export const phoneRegex = /^01[016789]-?[0-9]{3,4}-?[0-9]{4}$/;
export const forbiddenPatterns = ['null', 'undefined', 'admin'];
export const salaryRegex = /^[1-9]\d{0,3}(,\d{3})*$/;

export function validateInput(value, regex, errorMessage) {
  if (value == null || value === "") {
    return { isValid: false, message: "필수 입력 항목입니다." };
  }
  if (!regex.test(value)) {
    return { isValid: false, message: errorMessage };
  }
  return { isValid: true, message: "" };
}

export function updateUI($input, $error, validationResult) {
  if (validationResult.isValid) {
    $error.text("").removeClass("text__error");
  } else {
    $error.text(validationResult.message).addClass("text__error");
  }
}

export function setupCustomValidityMessages($form, messages) {
  $form.find("input").each(function() {
    $(this).on("invalid", function() {
      if (!this.validity.valid) {
        this.setCustomValidity(messages[this.id] || "올바른 값을 입력해주세요.");
      }
    }).on("input", function() {
      this.setCustomValidity("");
    });
  });
}