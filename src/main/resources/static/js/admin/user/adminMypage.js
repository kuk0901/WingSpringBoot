import {formatNumber, formatPhoneNumber, unformatNumber} from "../../util/format.js";
import { checkAndShowStoredMessage, showAlertMsg  } from "../../util/toast.js";
const $form = $("#updateForm"); // 폼 필드 이름
const $email = $("#email");
const $emailError = $("#emailError");
const $userName  = $("#name"); // 이름 입력 필드
const $userNameError = $("#userNameError");
const $pwdInput = $("#pwd");
const $pwdCheck = $("#pwdCheck");
const $phoneInput = $("#phone");
const $submitBtn = $("#submitBtn"); // 제출 버튼
const $pwdError = $("#pwdError");

const emailRegex = /^(?=.{6,36}$)[a-z0-9_]+@[a-z0-9.\-]+\.[a-z]{2,}$/;
const forbiddenPatterns = ['null', 'undefined', 'admin'];
const pwdRegex = /^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{8,21}$/;
const userNameRegex = /^[가-힣]{2,7}$/;
const phoneRegex = /^01[016789]-?[0-9]{3,4}-?[0-9]{4}$/;

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
    if (!baseValidation.isValid){
        console.log("이메일 형식 오류:", email);
        return baseValidation;
    }
    if (forbiddenPatterns.some(pattern => email.toLowerCase().includes(pattern))) {
        console.log("금지된 패턴 포함:", email);
        return { isValid: false, message: "사용할 수 없는 이메일입니다." };
    }
    console.log("이메일 유효:", email);
    return { isValid: true, message: "" };
}




// 비밀번호 유효성 검사

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
        $pwdError.text(isMatching ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다.")
            .removeClass("text__correct text__error")
            .addClass(isMatching ? "text__correct" : "text__error");
    } else {
        $pwdError.text("")
            .removeClass("text__correct text__error");
    }

    return { isPwdValid, isMatching };
}

// 전화번호 포맷팅 처리
$phoneInput.on("change", function() {
    formatPhoneNumber(this);
});

// 커스텀 유효성 검사 메시지
const messages = {
    email: "올바른 이메일 형식으로 입력해주세요. (예: wind@gmail.com)",
    pwd: "비밀번호는 8자의 영문자와 숫자 조합이어야 합니다. (대소문자 구분 없음, 공백 제외)",
    salary: "올바른 형식으로 입력해주세요. (예: 4,500 또는 6,000)",
    pay: "올바른 형식으로 입력해주세요. (예: 300 또는 500)"
};


// 비밀번호 확인 필드에 blur 이벤트 리스너 추가
$pwdCheck.on("change", validatePassword);

function updateUI($input, $error, validationResult) {
    if (validationResult.isValid) {
        console.log("유효성 검사 통과 - 오류 메시지 제거");
        $error.removeClass("text__error").addClass("text__transparent");
    } else {
        console.log("유효성 검사 실패 - 오류 메시지 표시:", validationResult.message);
        $error.text(validationResult.message)
            .removeClass("text__transparent")
            .addClass("text__error");
    }
    console.log("제출 버튼 상태 업데이트");
    updateSubmitButtonState();
}


function updateSubmitButtonState() {
    const isEmailValid = validateEmail($email.val()).isValid;

    // 비밀번호 유효성 검사 결과 받기
    const { isPwdValid, isMatching } = validatePassword();

    const isUserNameValid = validateInput($userName.val(), userNameRegex, "").isValid;
    console.log("이거문제인가?",isUserNameValid)
    const isPhoneValid = validateInput($phoneInput.val(), phoneRegex, "").isValid;
    console.log("검증 이거문제인가:", validateInput($userName.val(), userNameRegex, "오류"));
    console.log("이메일 btn 유효성 검사 결과:", isEmailValid);
    // 버튼 활성화 상태 업데이트
    $submitBtn.prop("disabled", !(isEmailValid && isPwdValid && isMatching && isUserNameValid && isPhoneValid));
}

$email.on("change", function() {
    const emailValue = $(this).val();
    console.log("입력된 이메일:", emailValue);
    const validationResult = validateEmail(emailValue);
    console.log("검증 결과:", validationResult);

    updateUI($email, $emailError, validateEmail($(this).val()));

});
$pwdInput.on("focus", function() {
    $pwdError.text("").removeClass("text__error text__correct");
    $pwdCheckError.text("").removeClass("text__error text__correct");
});


$pwdInput.on("change", function() {
    const pwdValue = $(this).val();
    if (pwdValue === "") {
        $pwdError.text("필수 입력 항목입니다.")
            .removeClass("text__correct")
            .addClass("text__error");
    } else {
        validatePassword(); // 비밀번호 유효성 검사
    }
});

$pwdCheck.on("change", function() {
    const pwdCheckValue = $(this).val();
    if (pwdCheckValue === "") {
        $pwdCheckError.text("필수 입력 항목입니다.")
            .removeClass("text__correct")
            .addClass("text__error");
    } else {
        validatePassword(); // 비밀번호 확인 유효성 검사
    }
});

$userName.on("change", function() {
    updateUI($userName, $userNameError, validateInput($(this).val(), userNameRegex, "2~7자의 한글 이름을 입력해주세요."));
});

$phoneInput.on("change", function() {
    formatPhoneNumber(this);
    updateUI($phoneInput, $phoneError, validateInput($(this).val(), phoneRegex, "올바른 휴대폰 번호 형식으로 입력해주세요."));
});


$form.on("submit", function(e) {
    e.preventDefault();

   /* // 폼 유효성 검사
    if (!this.checkValidity()) {
        this.reportValidity();
        console.log("폼 유효성 검사실패")
        return;
    }*/

    const formData = {};
    $(this).serializeArray().forEach(function(item) {
        formData[item.name] = item.value;
    });
    console.log("formData:", formData);
    // memberNo를 정수형으로 변환
    formData.memberNo = parseInt($("#memberNo").val(), 10); // 여기에 로그인한 관리자의 memberNo 값을 추가

    
    $.ajax({
        type: "PATCH",
        url: "/admin/api/member/update",
        data: JSON.stringify(formData),
        contentType: "application/json",
        dataType: "json",
        success: function(res) {
            console.log(res)
            /*window.location.href = `./${formData.memberNo}?message=${res.alertMsg}`;*/
            /*showAlertMsg(res.alertMsg)*/
        },
        error: function(xhr, status, error) {
            let msg = xhr.responseJSON ? xhr.responseJSON.alertMsg : "알 수 없는 오류가 발생했습니다.";
            console.log("왜안되??")
            if (xhr.responseJSON.emailMsg) {
                msg = xhr.responseJSON.emailMsg;
                updateUI($email, $emailError, { isValid: false, message: msg });
            }

            showAlertMsg(msg);
        }
    });
});

