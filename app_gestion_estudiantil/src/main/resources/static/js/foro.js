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
                window.location.replace("/foro_intento2.html");
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
function traerforo(id) {
    $.ajax({
        url: '/api/foros/getbuscar/' + id,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            $("#contenido").val(data.contenido);
            $("#files").val(data.files);
            // Si "files" es una lista de archivos, puedes convertirla en una cadena antes de mostrarla en el campo de texto
            /*if (data.files && data.files.length > 0) {
                const filesString = data.files.map(file => file.nombre_archivo).join(', '); // Concatenar nombres de archivos
                $("#files").val(filesString);
            }*/
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $("#apodo").val("");
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Foro no encontrado',
                confirmButtonText: "Aceptar"
            })
        }
    });
}

function actualizarforo() {
    let datos = {
        contenido: $("#contenido").val(),
        files: $("#files").val()
    };

    let swalConfirmacion = Swal.fire({
        icon: 'question',
        title: '¿Estás seguro de actualizar?',
        showCancelButton: true,
        confirmButtonText: "Actualizar",
        cancelButtonText: "Cancelar",
        showLoaderOnConfirm: true,
        allowOutsideClick: false,
        allowEscapeKey: false,
        preConfirm: function() {
            return new Promise(function(resolve, reject) {
                $.ajax({
                    url: '/api/foros/actualizar',
                    type:'PUT',
                    data: JSON.stringify(datos),
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        resolve(true); //
                        window.location.reload();
                    },
                    error: function(xhr, status, error) {
                        reject(error); // Rechaza con el mensaje de error
                    }
                });
            });
        }
    }).then(function(result) {
        if (result.value === true) {
            Swal.fire({
                icon: "success",
                title: "Actualización Completada",
                text: "Cambios realizados, por su seguridad actualizaremos el sistema",
                showConfirmButton: false,
                timer: 900,
                allowOutsideClick: false,
                allowEscapeKey: false
            });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            Swal.fire({
                icon: "info",
                title: "Proceso cancelado",
                text: "La actualización ha sido cancelada",
                showConfirmButton: false
            });
            // Redirecciona a página de inicio
            setTimeout(function() {
                window.location.replace("/foro_intento2.html");
            }, 900);
        }
    }).catch(function(error) {
        Swal.fire({
            icon: "error",
            title: "Error en la actualización",
            text: "Hubo un error al actualizar los datos: " + error,
            showConfirmButton: false
        });
    });
}

// Función para obtener todos los foros
function getAllData() {
    $.ajax({
        url: '/api/foros/getall',
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function (foros) {
            Swal.fire({
                icon: "success",
                title: "Visualización",
                text: "yeeii",
                showConfirmButton: true
            });
            console.log(foros);

            // Recorremos los foros y obtenemos las listas de archivos para cada uno
            foros.forEach(function (foro) {
                if (foro.files && foro.files.length > 0) {
                    getListFiles(foro);
                } else {
                    // Si no hay archivos, mostramos solo el foro
                    mostrarForo(foro);
                }
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            Swal.fire({
                icon: "error",
                title: "Error en la visualización",
                text: "efesota",
                showConfirmButton: false
            });
            console.log("toy acaunito");
        }
    });
}

function getListFiles(foro) {
    $.ajax({
        url: '/api/archivo/getbuscarlista/' + foro.id,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function (archivos) {
            Swal.fire({
                icon: "success",
                title: "Archivitos",
                text: "yeeii",
                showConfirmButton: true
            });
            console.log(archivos);

            // Mostramos el foro y sus archivos
            mostrarForoYArchivos(foro, archivos);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            Swal.fire({
                icon: "error",
                title: "Error no se encuentran archivos",
                text: "efesota",
                showConfirmButton: false
            });
            console.log("id", foro.id);
            console.log("toy aca");
        }
    });
}

function mostrarForo(foro) {
    // Aquí puedes pintar el contenido y la fecha del foro en el elemento deseado
    const contenedor = document.getElementById("aqui");
    const contenidoHTML = `
        <div class="foro">
            <p>Contenido: ${foro.contenido}</p>
            <p>Fecha: ${foro.fecha}</p>
        </div>
    `;
    contenedor.innerHTML += contenidoHTML;
}

function mostrarForoYArchivos(foro, archivos) {
    // Aquí puedes pintar el contenido, la fecha y los archivos del foro en el elemento deseado
    const contenedor = document.getElementById("aqui");
    let contenidoHTML = `
        <div class="foro">
            <p>Contenido: ${foro.contenido}</p>
            <p>Fecha: ${foro.fecha}</p>
            <div class="imagenes">
    `;

    // Recorremos los archivos y los agregamos al contenido
    archivos.forEach(function (archivo) {
        const imageUrl = `images/${archivo.nombre_archivo}`; // Esta ruta debe coincidir con la ubicación de tus imágenes
        contenidoHTML += `<img src="${imageUrl}" alt="${archivo.nombre_archivo}">`;
    });


    contenidoHTML += `
            </div>
            <button onclick="agregarComentario(${foro.id})">Agregar Comentario</button>
            <button onclick="traerforo(${foro.id})">Editar</button>
        </div>
    `;

    contenedor.innerHTML += contenidoHTML;
}

function mostrarArchivo(rutaArchivo) {

    fetch("/api/archivo/bajar/" + rutaArchivo)
        .then(response => {
            if (response.ok) {
                return response.blob();
            } else {
                throw new Error("Error al mostrar el archivo");
            }
        })
        .then(blob => {
            // Crear una URL temporal para el archivo
            var fileURL = URL.createObjectURL(blob);

            // Abrir el archivo en una nueva ventana o pestaña del navegador
            window.open(fileURL);
        })
        .catch(error => {
            console.error("Error en la solicitud AJAX:", error);
        });

}