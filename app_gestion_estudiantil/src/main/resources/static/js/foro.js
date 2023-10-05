let token;
let tokenizado;
let forito = null;

$(document).ready(function () {
    token = Cookies.get('token');
    tokenizado = parseJwt(token);
    $("#nuevo-archivo").change(function() {
            // Obtiene el nombre del archivo seleccionado
            const nombreArchivo = $(this).val().split('\\').pop();

            // Actualiza el campo de texto con el nombre del archivo
            $("#archivos").val(nombreArchivo);
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
}function traerforo(id) {
     $.ajax({
         url: '/api/foros/getbuscar/' + id,
         type: 'GET',
         dataType: 'json',
         contentType: 'application/json; charset=utf-8',
         success: function (data) {
             const contenidoInput = $("#contenido");
             const archivosInput = $("#archivos");

             // Mostrar el contenido del foro en el campo de texto
             contenidoInput.val(data.contenido);

             // Preparar una cadena con los nombres de los archivos
             let archivosString = "";
             if (data.files && data.files.length > 0) {
                 archivosString = data.files.map(archivo => archivo.nombre_archivo).join(", ");
             } else {
                 archivosString = "No hay archivos asociados a este foro.";
             }

             // Asignar la cadena de archivos al campo de texto
             archivosInput.val(archivosString);

             // Asignar el objeto data a forito
             forito = data;
         },
         error: function (jqXHR, textStatus, errorThrown) {
             // Maneja el error según sea necesario
             $("#apodo").val("");
             Swal.fire({
                 icon: 'error',
                 title: 'Oops...',
                 text: 'Foro no encontrado',
                 confirmButtonText: "Aceptar"
             });
         }
     });
 }
function actualizarforo() {
    let contenido = $("#contenido").val().trim();
    var archivosInput = $("#nuevo-archivo")[0].files[0];
    let foroId = forito.id;
    console.log(contenido);
    console.log(archivosInput);

    console.log(foroId);

    // Verificar si hay contenido o archivos seleccionados
    if (!contenido && archivos.length === 0) {
        Swal.fire({
            icon: "warning",
            title: "Campos Vacíos",
            text: "Por favor, asegúrate de llenar al menos uno de los campos antes de actualizar.",
            confirmButtonText: "Entendido"
        });
        return;
    }

    const formData = new FormData();
        formData.append("id", foroId);
        formData.append("contenido", contenido);
        if (archivosInput) {
            formData.append("archivos", archivosInput);
        }

    Swal.fire({
        icon: 'question',
        title: '¿Estás seguro de actualizar?',
        showCancelButton: true,
        confirmButtonText: "Actualizar",
        cancelButtonText: "Cancelar",
        showLoaderOnConfirm: true,
        allowOutsideClick: false,
        allowEscapeKey: false,
        preConfirm: function () {
            return new Promise(function (resolve, reject) {
                $.ajax({
                    url: '/api/foros/actualizar',
                    type: 'PUT',
                    headers:{'Authorization': 'Bearer '+token},
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (data) {
                        resolve(true);
                        window.location.reload();
                    },
                    error: function (xhr, status, error) {
                        reject(error);
                    }
                });
            });
        }
    }).then(function (result) {
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
            setTimeout(function () {
                window.location.replace("/foro_intento2.html");
            }, 900);
        }
    }).catch(function (error) {
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

            foros.forEach(function (foro) {
                if (foro.files && foro.files.length > 0) {
                     mostrarForoYArchivos(foro, foro.files);
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



function mostrarForo(foro) {
    // Aquí puedes pintar el contenido y la fecha del foro en el elemento deseado
    const contenedor = document.getElementById("aqui");
    const contenidoHTML = `
        <div class="foro">
            <p>Contenido: ${foro.contenido}</p>
            <p>Fecha: ${foro.fecha}</p>
        </div>

        </div>
            <button onclick="agregarComentario(${foro.id})">Agregar Comentario</button>
            <button onclick="traerforo(${foro.id})">Editar</button>
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
