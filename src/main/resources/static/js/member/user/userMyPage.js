import { formatNumber,formatPhoneNumber,unformatNumber } from "../../util/format.js"; // 상대 경로로 import


$(document).ready(function() {


    const phoneInput = document.getElementById('phone');
    const yearlySalaryInput = document.getElementById('yearlySalary');
    const monthlySalaryInput = document.getElementById('monthlySalary');

    // 전화번호 입력 시 formatPhoneNumber 함수 호출
    phoneInput.addEventListener('input', function() {
        formatPhoneNumber(phoneInput);
    });

    // 연봉 입력 시 formatNumber 함수 호출
    yearlySalaryInput.addEventListener('input', function() {
        formatNumber(yearlySalaryInput);
    });

    // 월급 입력 시 formatNumber 함수 호출
    monthlySalaryInput.addEventListener('input', function() {
        formatNumber(monthlySalaryInput);
    });

    const memberNo = $("#memberNo").val();  // 숨겨진 필드에서 memberNo 값을 가져옴
    $.ajax({
        url: "/member/api/user/myPage/info",
        type: "GET",
        data: { memberNo: memberNo },
        success: function(response) {

            $("#email").val(response.email);
            $("#Name").val(response.userName);
            $("#phone").val(response.phone);
            formatPhoneNumber(phoneInput);
            const formattedDate = formatDate(response.creDate);
            $("#creDate").val(formattedDate); // 포맷된 날짜를 #creDate에 설정
            $("#yearlySalary").val(response.yearlySalary);
            $("#monthlySalary").val(response.monthlySalary);
            $("#password").val(response.pwd);  // 비밀번호 필드 채우기
            $("#confirmPassword").val(response.pwd);  // 비밀번호 확인 필드 채우기
            // 초기 포맷팅을 위해 응답 받은 후 formatNumber 호출
            formatNumber(yearlySalaryInput);
            formatNumber(monthlySalaryInput);
            // 만원 단위 추가하기
            yearlySalaryInput.value += "만원"; // 만원 추가
            monthlySalaryInput.value += "만원"; // 만원 추가

            // 구매 상태 확인 후 카드 영역 표시
            if (response.productPurchase === 'Y') {
                fetchSellingCards(memberNo); // 구매 상태가 'Y'인 경우에만 카드 정보를 가져오는 함수 호출
            }
        },
        error: function() {
            alert("회원 정보를 불러오는 데 실패했습니다.");
        }
    });

    $('#updateButton').on('click', function (e) {
        e.preventDefault(); // 기본 제출 방지

        // 폼 유효성 검사
        if (!this.checkValidity()) {
            this.reportValidity(); // 유효성 검사 결과 보고
            return; // 유효하지 않으면 종료
        }

        // formData 객체 명시적으로 정의
        const formData = {
            memberNo:memberNo,
            email: $email.val(),
            userName: $("#Name").val(),
            pwd: $pwdInput.val(),
            phone: $phoneInput.val(),
            monthlySalary: unFormatNumberString($monthlySalaryInput.val()), // 숫자 형식 변환
            yearlySalary: unFormatNumberString($salaryInput.val()), // 숫자 형식 변환
        };
        console.log(formData)
        // AJAX 요청 함수 호출
        updateMemberInfo(formData);
    });
    /*  fetchSellingCards()*/
    $('#quitMemberButton').on('click', handleQuitButtonClick);
});


// unformatNumberString 함수
function unFormatNumberString(value) {
    return parseInt(value.replace(/만원/g, '').replace(/,/g, '').trim());
}
function formatDate(dateString) {
    if (!dateString) return '해당 사항 없음'; // null 또는 undefined일 경우
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = date.getMonth() + 1; // getMonth는 0부터 시작
    const day = date.getDate();
    return `${year}년 ${month}월 ${day}일`;
}

//가져옴


const $form = $("#myPageForm");
const $email = $("#email");
const $emailError = $("#emailError");
const $pwdInput = $("#password");
const $pwdCheck = $("#confirmPassword");
const $pwdError = $("#pwdError");
const $phoneInput = $("#phone");
const $salaryInput = $("#yearlySalary");
const $monthlySalaryInput = $("#monthlySalary");
const $updateButton = $("#updateButton");

let emailValCheck = $email.val();
const emailRegex = /^(?=.{6,36}$)[a-z0-9_]+@[a-z0-9.\-]+\.[a-z]{2,}$/;
const forbiddenPatterns = ['null', 'undefined', 'admin'];

function validateEmail(email) {
    if (!emailRegex.test(email)) {
        return false;
    }
    return !forbiddenPatterns.some(pattern => email.toLowerCase().includes(pattern));
}

function updateEmailUI(currentValue) {
    if (currentValue !== emailValCheck) {
        $emailError.removeClass("text__error").addClass("text__transparent");
    } else if (!validateEmail(currentValue)) {
        $emailError.text("사용할 수 없는 이메일입니다.")
            .removeClass("text__transparent")
            .addClass("text__error");
    } else {
        $emailError.removeClass("text__transparent").addClass("text__error");
    }
    updateSubmitButtonState();
}

function validatePassword() {
    const isValid = $pwdInput.val() === $pwdCheck.val();
    $pwdError.text(isValid ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다.")
        .removeClass("text__correct text__error")
        .addClass(isValid ? "text__correct" : "text__error");
    updateSubmitButtonState();
    return isValid;
}

function updateSubmitButtonState() {
    const isEmailValid = validateEmail($email.val());
    const isPasswordValid = $pwdInput.val() === $pwdCheck.val() || $pwdInput.val() === ''; // 패스워드는 비워두는 것도 허용
    $updateButton.prop("disabled", !(isEmailValid && isPasswordValid));
}

$email.on("blur", function() {
    emailValCheck = $(this).val();
    updateEmailUI($(this).val());
});

$pwdCheck.on("blur", validatePassword);

$phoneInput.on("blur", function() {
    formatPhoneNumber(this);
});

$salaryInput.on("blur", function() {
    formatNumber(this);
});

$monthlySalaryInput.on("blur", function() {
    formatNumber(this);
});

const messages = {
    email: "올바른 이메일 형식으로 입력해주세요. (예: wind@gmail.com)",
    pwd: "비밀번호는 8자의 영문자와 숫자 조합이어야 합니다. (대소문자 구분 없음, 공백 제외)",
    pwdCheck: "비밀번호가 일치하지 않습니다.",
    userName: "2~7자의 한글 이름을 입력해주세요.",
    phone: "올바른 휴대폰 번호 형식으로 입력해주세요. (예: 01012345678)",
    salary: "올바른 형식으로 입력해주세요. (예: 4,500 또는 6,000)",
    pay: "올바른 형식으로 입력해주세요. (예: 300 또는 500)"
};

$form.find("input").        each(function() {
    $(this).on("invalid", function() {
        if (!this.validity.valid) {
            this.setCustomValidity(messages[this.id] || "올바른 값을 입력해주세요.");
        }
    }).on("input", function() {
        this.setCustomValidity("");
    });
});




// AJAX 요청 함수
function updateMemberInfo(data) {
    $.ajax({
        url: '/member/api/user/myPage/update', // API 엔드포인트
        type: 'PUT', // PUT 요청
        contentType: 'application/json', // JSON 형식으로 데이터 전송
        data: JSON.stringify(data), // JSON 문자열로 변환
        success: function (response) {
            alert("회원 정보가 수정되었습니다."); // 성공 메시지
            // 필요시 페이지 리로드 또는 다른 동작 수행
            // location.reload();
        },
        error: function (xhr, status, error) {
            console.error("Error updating member info:", error); // 오류 로그
            alert("회원 정보 수정 중 오류가 발생했습니다."); // 오류 메시지
        }
    });
}

/*카드컨테이너*/
// 구매 상태가 'Y'인 경우 카드 정보를 가져오는 함수
function fetchSellingCards(memberNo) {
    $.ajax({
        url: `/member/api/sellingCard/sellingCards/${memberNo}`, // 판매 카드 정보를 가져오는 API 호출
        type: "GET",
        success: function(cards) {
            // sellingCard를 기반으로 HTML 생성 (하나의 카드만 처리)
            console.log(cards); // 전체 카드 배열 출력

            // 카드가 하나만 있다는 가정 하에 첫 번째 카드 가져오기
            const card = cards[0]; // 첫 번째 카드 객체를 가져옴

            console.log('Card Number:', card.CARDNO); // 이제 제대로 접근 가능해야 함

            const cardHTML = `
                <div class="bg__white">
                    <div class="list-container list-content">
                        <div class="list-item box__s">${card.CARDNAME}
                             <div class="img-container">
                                <img class="card--img" src="/img/card/${card.STOREDFILENAME}" alt="${card.CARDNAME}" />
                              </div>
                        </div>
                        <div class="list-item box__l" id="benefit-container"></div>
                        <div class="list-item box__date">
                            ${formatDate(card.SELLINGDATE)} <!-- date formatting function must be implemented -->
                        </div>
                        <div class="list-item box__no">${card.MEMBERCARDNO}</div>
                        <div class="list-item box__btn">
                            <div id="button-container">
                                <button class="btn__generate btn--vertical">해지 신청</button>
                                <button class="btn__generate btn--vertical">상세 내역</button>
                            </div>
                        </div>
                    </div>
                </div>
            `;
            $("#card-container").append(cardHTML); // #card-container에 카드 영역 추가
            // 카드 혜택 정보를 비동기적으로 가져오기
            fetchCardBenefit(card.CARDNO).then(benefitHtml => {
                // 카드 혜택 정보를 가져온 후 해당 카드의 혜택 컨테이너에 추가
                $(`#benefit-container`).html(benefitHtml);
            });
        },
        error: function() {
            alert("판매 카드 정보를 불러오는 데 실패했습니다.");
        }
    });
}


function fetchCardBenefit(cardNo) {
    return $.ajax({
        url: '/member/api/sellingCard/cardBenefit/' + cardNo, // 카드 혜택 정보 API URL
        type: 'GET', // HTTP 요청 방법
        dataType: 'json', // 응답 데이터 타입
    }).then(function(data) {
        console.log('카드 혜택 정보:', data);

        // data가 배열 형태로 반환된다고 가정
        let cardBenefitHTML = '';
        data.forEach(benefit => {
            const { cardPercentage, cardBenefitDetail, cardBenefitDivision } = benefit;
            // 카드 혜택 정보를 HTML로 조립
            cardBenefitHTML += `
                <div class="card-benefit">
                    <p> ${cardBenefitDetail} ${cardPercentage}% ${cardBenefitDivision} </p>
                </div>
            `;
        });

        // 조립한 HTML을 페이지에 삽입
        $('#cardBenefit').html(cardBenefitHTML);
        return cardBenefitHTML; // 생성된 HTML을 반환
    }).fail(function(xhr, status, error) {
        console.error('AJAX 요청 실패:', error);
        $('#cardBenefit').html('<p>카드 혜택 정보를 불러오는 데 실패했습니다.</p>');
        return '<p>카드 혜택 정보를 불러오는 데 실패했습니다.</p>';
    });
}


// 탈퇴 버튼 클릭 시 호출될 함수
function handleQuitButtonClick() {
    // 탈퇴 확인
    if (!confirm("정말 탈퇴하시겠습니까?")) {
        return;
    }

    const memberNo = $('#memberNo').val(); // 회원 번호 가져오기

    // 탈퇴 신청 AJAX 요청 호출
    submitQuitRequest(memberNo);
}

// 탈퇴 신청 요청 함수
function submitQuitRequest(memberNo) {
    $.ajax({
        type: "PATCH",
        url: "/member/api/user/quit",
        contentType: "application/json",
        data: JSON.stringify({ memberNo: memberNo }),
        success: function (response) {
            alert("탈퇴 신청이 완료되었습니다.");
            window.location.reload(); // 페이지 새로고침
        },
        error: function (xhr, status, error) {
            const errorMsg = xhr.responseJSON ? xhr.responseJSON.message : "탈퇴 처리 중 오류가 발생했습니다.";
            alert(errorMsg);
        }
    });
}