/**
 * @param {HTMLInputElement} inputElement
 * @returns {void}
 */
function previewImage(inputElement) {
  if (inputElement.files && inputElement.files[0]) {
    const reader = new FileReader();

    reader.onload = () => {
      const imagePreviewEl = document.getElementById("imagePreview");
      imagePreviewEl.src = reader.result;
      imagePreviewEl.style.display = 'block';
    };

    reader.readAsDataURL(inputElement.files[0]);

  }
}