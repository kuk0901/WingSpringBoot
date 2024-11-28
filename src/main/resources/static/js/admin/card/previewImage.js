/**
 * @param {HTMLInputElement} inputElement
 * @returns {void}
 */
function previewImage(inputElement) {
  const $imgContainer = $("#imgContainer");
  const $imagePreviewEl = $("#imagePreview");
  const $previewPlaceholder = $("#previewPlaceholder");

  if (inputElement.files && inputElement.files[0]) {
    const reader = new FileReader();

    reader.onload = (e) => {
      $imagePreviewEl.attr('src', e.target.result);
      $imagePreviewEl.on('load', function() {
        $previewPlaceholder.addClass('hidden');
        $imagePreviewEl.removeClass('hidden');
      });
    };

    reader.readAsDataURL(inputElement.files[0]);
  } else {
    $imagePreviewEl.attr('src', '#').addClass('hidden');
    $previewPlaceholder.removeClass('hidden');
  }
}