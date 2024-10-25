<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty alertMsg}">
  <div id="toast" class="toast toast--blue add-margin">
    <div class="toast__icon"></div>
    <div class="toast__content">
      <p id="alertMsg" class="toast__message text__black">${alertMsg}</p>
    </div>
    <div id="toastClose" class="toast__close">
      <img class="toast__img" src="/img/close.svg" alt="clickMe!" />
    </div>
  </div>
</c:if>
