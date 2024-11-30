export function execDaumPostcode() {
  const $layer = $('#layer');

  new daum.Postcode({
    oncomplete: function(data) {
      let addr = '';
      let extraAddr = '';

      if (data.userSelectedType === 'R') {
        addr = data.roadAddress;
      } else {
        addr = data.jibunAddress;
      }

      if(data.userSelectedType === 'R'){
        if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
          extraAddr += data.bname;
        }
        if(data.buildingName !== '' && data.apartment === 'Y'){
          extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
        }
        if(extraAddr !== ''){
          extraAddr = ' (' + extraAddr + ')';
        }
        $('#extraAddress').val(extraAddr);
      } else {
        $('#extraAddress').val('');
      }

      $('#postcode').val(data.zonecode);
      $('#address').val(addr);
      $('#detailAddress').focus();

      $layer.hide();
    },
    width : '100%',
    height : '100%',
    maxSuggestItems : 5
  }).embed($layer[0]);

  $layer.show();

  initLayerPosition();
}

export function initLayerPosition(){
  const $layer = $('#layer');
  const width = 400;
  const height = 500;
  const borderWidth = 1;

  $layer.css({
    width: width + 'px',
    height: height + 'px',
    border: borderWidth + 'px solid',
    left: (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px',
    top: (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px'
  });
}