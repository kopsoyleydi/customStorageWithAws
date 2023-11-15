document.addEventListener('DOMContentLoaded', function() {
    var imageInput = document.getElementById('imageInput');
    var userIdInput = document.getElementById('userIdInput');
    var uploadButton = document.getElementById('uploadButton');
    var imageView = document.getElementById('imageView');

    uploadButton.addEventListener('click', function() {
        var file = imageInput.files[0];
        var userId = 6;

        if (file && userId) {
            var formData = new FormData();
            formData.append('image', file);
            formData.append('userId', userId.toString());

            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/files/upload' + 6, true);

            xhr.onload = function() {
                if (xhr.status === 200) {
                    // Обработка успешного ответа от сервера, если необходимо
                    console.log('Image uploaded successfully.');
                } else {
                    console.error('Error uploading image. Status:', xhr.status);
                }
            };

            xhr.onerror = function() {
                console.error('Network error while uploading image.');
            };

            xhr.send(formData);
        } else {
            console.error('No image selected or user ID provided.');
        }
    });
});