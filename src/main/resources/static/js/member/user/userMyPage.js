import { formatNumber,formatPhoneNumber,unformatNumber } from "../../util/format.js"; // 상대 경로로 import
import { checkAndShowStoredMessage, showAlertMsg  } from "../../util/toast.js";

$(document).ready(function() {



    const phoneInput = document.getElementById('phone');
    const yearlySalaryInput = document.getElementById('yearlySalary');
    const monthlySalaryInput = document.getElementById('monthlySalary');





    const memberNo = $("#memberNo").val();  // 숨겨진 필드에서 memberNo 값을 가져옴
    $.ajax({
        url: "/member/api/user/myPage/info",
        type: "GET",
        data: { memberNo: memberNo },
        success: function(response) {
            console.log(response);
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


            // 구매 상태 확인 후 카드 영역 표시
            if (response.productPurchase === 'Y') {
                fetchSellingCards(memberNo); // 구매 상태가 'Y'인 경우에만 카드 정보를 가져오는 함수 호출
            }

            // 탈퇴 신청 상태 확인 후 메시지 표시
            if (response.quitApply === 'Y') {
                $("#quitApplyNotice").text("탈퇴 신청이 완료되었습니다.").show(); // 메시지를 보여줍니다
            } else {
                $("#quitApplyNotice").hide(); // 탈퇴 신청이 아닐 경우 메시지를 숨김
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
            return // 유효하지 않으면 종료
        }

        // formData 객체 명시적으로 정의
        const formData = {
            memberNo:memberNo,
            email: $email.val(),
            userName: $userName.val(),
            pwd: $pwdInput.val(),
            phone: $phoneInput.val(),
            monthlySalary: unFormatNumberString($monthlySalaryInput.val()), // 숫자 형식 변환
            yearlySalary: unFormatNumberString($salaryInput.val()), // 숫자 형식 변환
        };

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
//여기부터 수정용
const $submitBtn =$("#updateButton");
const $form = $("#myPageForm");
const $email = $("#email");
const $userName = $("#Name");
const $emailError = $("#emailError");
const $pwdInput = $("#password");
const $pwdCheck = $("#confirmPassword");
const $pwdCheckError = $("#pwdCheckError");
const $pwdError = $("#pwdError");
const $phoneInput = $("#phone");
const $phoneError = $("#phoneError");
const $salaryInput = $("#yearlySalary");
const $monthlySalaryInput = $("#monthlySalary");
const $salaryError = $("#salaryError");
const $payError = $("#payError");
const $userNameError  =$("#userNameError");


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
    const isPayValid = validateInput($monthlySalaryInput.val(), salaryRegex, "").isValid;

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

$monthlySalaryInput.on("blur", function() {
    formatNumber(this);
    updateUI($monthlySalaryInput, $payError, validateInput($(this).val(), salaryRegex, "올바른 형식으로 입력해주세요. (예: 300 또는 500)"));
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






// AJAX 요청 함수
function updateMemberInfo(data) {


    $.ajax({
        url: '/member/api/user/myPage/update', // API 엔드포인트
        type: 'PUT', // PUT 요청
        contentType: 'application/json', // JSON 형식으로 데이터 전송
        data: JSON.stringify(data), // JSON 문자열로 변환
        success: function (response) {
            showAlertMsg(response.alertMsg)
        },
        error: function (xhr, status, error) {
            alert("회원 정보 수정 중 오류가 발생했습니다."); // 오류 메시지
        }
    });
}

/*카드컨테이너*/
// 구매 상태가 'Y'인 경우 카드 정보를 가져오는 함수
function fetchSellingCards(memberNo) {
    $.ajax({
        url: `/member/api/sellingCard/purchase/${memberNo}`, // 판매 카드 정보를 가져오는 API 호출
        type: "GET",
        success: function(cards) {
            // sellingCard를 기반으로 HTML 생성 (하나의 카드만 처리)

            // 카드가 하나만 있다는 가정 하에 첫 번째 카드 가져오기
            const card = cards[0]; // 첫 번째 카드 객체를 가져옴

            const cardHTML = `
                
                  <div class="card-title">보유 카드</div>
                  <div class="card-header">
                    <div class="header-item">카드 명</div>
                    <div class="header-item">혜택 요약</div>
                    <div class="header-item">등록 날짜</div>
                    <div class="header-item">카드 번호</div>
                    <div class="header-item">비고
                    </div>
                  </div>
                  
                <div class="bg__white">
                    <div class="list-container list-content">
                        <div class="list-item box__s text__center">${card.CARDNAME}
                             <div class="img-container">
                                <img class="card--img" src="/img/card/${card.STOREDFILENAME}" alt="${card.CARDNAME}" />
                              </div>
                        </div>
                        <div class="list-item box__l" id="benefit-container"></div>
                        <div class="list-item box__date text__center">
                            ${formatDate(card.SELLINGDATE)} <!-- date formatting function must be implemented -->
                        </div>
                        <div class="list-item box__no text__center">${card.MEMBERCARDNO}</div>
                        <div class="list-item box__btn">
                            <div id="button-container">
                                 <button   id="terminationRequestButton" class="btn btn__generate btn--vertical">해지 신청</button>
                                <button id="cardUseDetail" class="btn btn__generate btn--vertical">상세 내역</button>
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
                $("#card-container").show(); // card-container를 보이게 함
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
        type: "POST",
        url: "/member/api/user/quit",
        contentType: "application/json",
        data: JSON.stringify({ memberNo: memberNo }),
        success: function (response) {
            showAlertMsg(response.alertMsg)
        },
        error: function (xhr, status, error) {
            const errorMsg = xhr.responseJSON ? xhr.responseJSON.message : "탈퇴 처리 중 오류가 발생했습니다.";
            alert(errorMsg);
        }
    });
}


/*여기서부터해지신청란*/

$(document).on("click", "#terminationRequestButton", function() {
    const memberNo = $("#memberNo").val();
    fetchCardsForTerminationRequest(memberNo);
});
function fetchCardsForTerminationRequest(memberNo) {
    $.ajax({
        url: `/member/api/sellingCard/purchase/${memberNo}`, // 판매 카드 정보를 가져오는 API 호출
        type: "GET",
        success: function(cards) {
            // sellingCard를 기반으로 HTML 생성 (하나의 카드만 처리)

            // 카드가 하나만 있다는 가정 하에 첫 번째 카드 가져오기
            const card = cards[0]; // 첫 번째 카드 객체를 가져옴

            const terminationPageHTML = `
              <div class="title-container" xmlns="http://www.w3.org/1999/html">
                <div class="title-containerSecond">
                       <div class="title btn__blue text__white">
                               카드 해지 신청
                      </div>
                     <div class="btn-back-container">
                            <button id="backButton" class="btn btn__generate">돌아가기</button>
                     </div>
                 </div>
                </div>
                   
                 <main class="main-container">
                 <div class="card-container">
                   <div class="card-title">보유 카드</div>
                  <div class="card-header">
                    <div class="header-item">카드 명</div>
                    <div class="header-item">취소 상태</div>
                    <div class="header-item">등록 날짜</div>
                    <div class="header-item">카드 번호</div>
                    <div class="header-item">비고
                    </div>
                  </div>
                <div class="bg__white">
                    <div class="list-container list-content">
                        <div class="list-item box__s text__center">${card.CARDNAME}
                             <div class="img-delete-container">
                                <img class="card--delete--img" src="/img/card/${card.STOREDFILENAME}" alt="${card.CARDNAME}" />
                              </div>
                        </div>
                        <div class="list-item box__tem">${card.CARDTERMINATION}</div>
                        <div class="list-item box_delete_date text__center">
                            ${formatDate(card.SELLINGDATE)} <!-- date formatting function must be implemented -->
                        </div>
                        <div class="list-item box__delete_no text__center">${card.MEMBERCARDNO}</div>
                        <div class="list-item box__btn">
                            <div id="button-container">
                                <button   id="deleteCardButton" class="btn btn__generate btn--vertical">해지 신청</button>
                            </div>
                        </div>
                    </div>
                </div>
               <div class="card-delete-container">
              <div class="card-title">해지 사유</div>
                <div id="terminationReasonForm"  class="termination-input" >
                    <textarea id="terminationReason" class="input__text" 
                     placeholder="해지 신청 사유를 작성한 후 해지 신청의 해지 버튼을 클릭해주세요"></textarea>
                </div>
                 </div>
                <div class="benefit-container">
                        <div class="list-item box_title">이런 <span class="underline">혜택</span>이 사라져요!</div>
                       <div class="list-item box__l" id="benefit-container"></div>
                </div>
                </div>
                </main>
            `;
            $("#content").html(terminationPageHTML);  // #card-container에 카드 영역 추가
            // 카드 혜택 정보를 비동기적으로 가져오기
            fetchCardBenefit(card.CARDNO).then(benefitHtml => {
                // 카드 혜택 정보를 가져온 후 해당 카드의 혜택 컨테이너에 추가
                $(`#benefit-container`).html(benefitHtml);
            });
            $('#backButton').on('click', function () {
                window.location.href = './myPage';  // myPage로 이동
            });
            $('#deleteCardButton').on('click', function() {
                console.log(memberNo)
                $.ajax({
                    url: '/member/api/sellingCard/cardSoftDelete/' + memberNo, // URL 설정
                    type: 'POST', // 요청 타입
                    dataType: 'json', // 응답 데이터 타입
                    success: function(response) {
                        // 성공 시
                        console.log(response)
                        showAlertMsg(response.alertMsg); // alertMsg를 알림으로 표시
                    },
                    error: function(xhr, status, error) {
                        // 오류 발생 시
                        if (xhr.status === 404) {
                            alert('카드 삭제에 실패했습니다. 카드가 존재하지 않습니다.');
                        } else {
                            alert('서버 오류가 발생했습니다. 다시 시도해 주세요.');
                        }
                    }
                });
            });
        },
        error: function() {
            alert("판매 카드 정보를 불러오는 데 실패했습니다.");
        }
    });
}
