let token;
let tokenizado;

$(document).ready(function () {
    token = Cookies.get('token');
    tokenizado = parseJwt(token);

    // Asocia la función enviar al clic en el botón
    $("#crear-encuesta-btn").click(function () {
        enviar();
    });
});

function parseJwt(token) {
    const base64Url2 = token.split('.')[1];
    const base64 = base64Url2.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
        atob(base64)
            .split('')
            .map(function (c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            })
            .join('')
    );
    return JSON.parse(jsonPayload);
}

function enviar() {
    var formData = new FormData(document.getElementById("formulario-encuesta"));
    formData.append("id", tokenizado.id);

    $.ajax({
        type: "POST",
        url: "/api/encuestas/crear",
        headers: { 'Authorization': 'Bearer ' + token },
        data: formData,
        processData: false,
        contentType: false,
        success: function (respuesta) {
            // Manejar la respuesta del servidor (éxito al crear la encuesta)
            console.log(respuesta);
            // Puedes redirigir a una página de confirmación o realizar otras acciones
        },
        error: function (error) {
            // Manejar el error (fallo al crear la encuesta)
            console.error(error.responseText);
        }
    });
}
