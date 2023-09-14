let token;
let tokenizado;

$(document).ready(function () {
    token = Cookies.get('token');
    tokenizado = parseJwt(token);

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
function enviarpubli() {
    let data = {
        contenido : $("#contenido").val(),
        idUsuario : tokenizado.id
        // idForo : tokenizado.id
    }

    $.ajax({
        url: "/api/comentarios/guardar",
        type: "POST",
        contentType:'application/json; charset=utf-8',
        dataType:"json",
        data:JSON.stringify(data),
        success: function (data) {
            Swal.fire({
                icon: 'info',
                title: '¡Comentario Nuevo!',
                showConfirmButton: false,
                timer: 750

            }).then(function () {
                window.location.reload();
            });
        },
        error: function (xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: '¡Proceso interrumpido¡',
                showconfirmButton: false
            });
        }
    });
}