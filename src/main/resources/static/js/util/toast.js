const $alertMsg = $("#alertMsg");

export function checkAndShowStoredMessage() {
  // URL 파라미터 확인
  const urlParams = new URLSearchParams(window.location.search);
  const message = urlParams.get('message');

  if (message) {
    // URL 파라미터로 전달된 메시지가 있으면 표시
    showAlertMsg(decodeURIComponent(message));
    // 메시지를 표시한 후 URL에서 파라미터 제거
    window.history.replaceState({}, document.title, window.location.pathname);
  } else if ($alertMsg.length) {
    // JSTL로 생성된 메시지가 있으면 toastClose 버튼에 클릭 이벤트 바인딩
    bindToastCloseEvent();
  }
}

export function showAlertMsg(message) {
  // 기존 알림 메시지 제거
  $alertMsg.remove();

  const alertMsgTag = `
    <div id="toast" class="toast toast--blue add-margin">
      <div class="toast__icon"></div>
      <div class="toast__content">
        <p id="alertMsg" class="toast__message text__black">${message}</p>
      </div>
      <div id="toastClose" class="toast__close">
        <img class="toast__img" src="/img/close.svg" alt="clickMe!" />
      </div>
    </div>`;

  // 새 알림 메시지 생성 및 추가
  $('body').append(alertMsgTag);

  // 클릭 이벤트 바인딩
  bindToastCloseEvent();
}

// toastClose 이벤트 함수로 분리
function bindToastCloseEvent() {
  $('#toastClose').off('click').on('click', function(e) {
    e.preventDefault();
    const parent = $(this).closest('#toast');
    parent.fadeOut(100, function () {
      $(this).remove();
    });
  });
}

// 페이지 로드 시 실행
checkAndShowStoredMessage();
