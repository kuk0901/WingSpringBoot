import { formatNumber, formatPhoneNumber } from "../../util/fotmat.js";

const $form = $("#updateForm");//폼필드이름
const $email = $("#email");
const $emailError = $("#emailError");
const $nameInput = $("#name"); // 이름 입력 필드
const $pwdInput = $("#pwd");
const $pwdCheck = $("#pwdCheck");
const $phoneInput = $("#phone");
const $submitBtn = $("#submitBtn"); // 제출 버튼
const $pwdError = $("#pwdError");


// email error 처리용 변수
let emailValCheck = $email.val();

$email.on("blur", function() {
    emailValCheck = $(this).val();
    updateEmailUI($(this).val());
});

$email.on("input", function() {
    updateEmailUI($(this).val());
});

// 비밀번호 유효성 검사
function validatePassword() {
    const isValid = $pwdInput.val() === $pwdCheck.val();
    $pwdError.text(isValid ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다.")
        .removeClass("text__correct text__error")
        .addClass(isValid ? "text__correct" : "text__error");
    $submitBtn.prop("disabled", !isValid);
    return isValid;
}

$phoneInput.on("blur", function ()  {
    formatPhoneNumber(this)
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


    // memberNo를 정수형으로 변환
    formData.memberNo = parseInt($("#memberNo").val(), 10); // 여기에 로그인한관리자의 memberNo 값을 추가
    $.ajax({
        type: "PATCH",
        url: "/api/admin/member/update",
        data: JSON.stringify(formData),
        contentType: "application/json",
        dataType: "json",
        success: function(res) {
            console.log('성공');
        },
        error: function(xhr, status, error) {

            let msg = xhr.responseJSON ? xhr.responseJSON.msg : "알 수 없는 오류가 발생했습니다.";

            if (xhr.responseJSON.emailMsg) {
                msg = xhr.responseJSON.emailMsg;

                $emailError.text(msg ? msg : "")
                    .removeClass("text__transparent text__error")
                    .addClass(msg ? "text__error" : "text__transparent" );

                emailValCheck = $email.val(); // 서버 검증 후 emailValCheck 업데이트
            }

            console.log(msg);
        }
    });
});

// 비밀번호 확인 필드에 blur 이벤트 리스너 추가
$pwdCheck.on("blur", validatePassword);

function updateEmailUI(currentValue) {
    if (currentValue !== emailValCheck) {
        $emailError.removeClass("text__error").addClass("text__transparent");
    } else {
        $emailError.removeClass("text__transparent").addClass("text__error");
    }
}