$(document).ready(function () {
    $('#form-sign-in').submit(function () {
        $.ajax({
            type: 'POST',
            url: 'createSessionServlet',
            data: $('#form-sign-in').serialize(),
            success: function (response) {
                console.log(response);
                //очищаем поля формы
                //$('#form-sign-in')[0].reset();
                //отправляем ответ на страницу, ответ состоит из двух объектов - цвет текста и сообщение
                var responses = response.split("\n");
                var response1 = responses[0];
                var response2 = responses[1];
                $('#message-for-signin').html("");
                //авторизация прошла успешно
                if (response1 === "true") {
                    location.reload();//перезагружаем страницу, что бы фильтр пропутил переадресовал на страницу с документами
                }
                //авторизация не прошла
                else {
                    $('#message-for-signin').html(response2);//информируем пользователя
                }
            },
            error: function () {
                $('#message-for-signin').html("При авторизации произошла ошибка");
            }
        });
        return false;
    });
});
