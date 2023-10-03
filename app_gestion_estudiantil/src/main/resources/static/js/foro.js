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
    var contenido = $("#contenido").val();
    var archivo = $("#image-input")[0].files[0];
    var idUsuario = tokenizado.id;

    var formData = new FormData();
    formData.append("contenido", contenido);
    if (archivo) {
        formData.append("archivo", archivo);
    }
    formData.append("idUsuario", idUsuario);

    $.ajax({
        url: "/api/foros/guardar",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            Swal.fire({
                icon: 'info',
                title: '¡Nueva Publicación!',
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

    // Evento de clic para el botón "Votar"
    document.getElementById("botonVotar").addEventListener("click", function() {
        // Obtener el contenido de la publicación (o la información necesaria para el voto)
        const contenido = document.getElementById("contenido").value;
        // Supongamos que también necesitas el ID de usuario y la opción seleccionada
        const usuarioId = obtenerIdUsuario(); // Implementa esta función para obtener el ID del usuario
        const opcionSeleccionada = obtenerOpcionSeleccionada(); // Implementa esta función para obtener la opción seleccionada

        // Crear un objeto de voto con los datos necesarios
        const voto = {
            idUsuario: usuarioId,
            opcionSeleccionada: opcionSeleccionada,
            contenido: contenido // Puedes agregar el contenido de la publicación aquí si lo necesitas
        };

        // Realizar una solicitud POST al servidor para guardar el voto
        fetch("/votos/votar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(voto)
        })
            .then(response => {
                if (response.ok) {
                    // El voto se registró correctamente, puedes mostrar un mensaje de éxito o realizar alguna acción adicional si es necesario
                    console.log("Voto registrado correctamente");
                } else {
                    // Ocurrió un error al registrar el voto, muestra un mensaje de error o maneja la situación de acuerdo a tus necesidades
                    console.error("Error al registrar el voto");
                }
            })
            .catch(error => {
                console.error("Error de red:", error);
            });
    });
}