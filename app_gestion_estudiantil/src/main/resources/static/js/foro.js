let token;
let tokenizado;
let forito = null;

$(document).ready(function () {
    token = Cookies.get('token');
    tokenizado = parseJwt(token);

     $(document).on('click', '.mostrarFormulario', function() {
        // Encuentra el contenedor del foro actual
        var contenedorForo = $(this).closest('.foro');

        // Muestra el formulario específico para este foro
        contenedorForo.find('.comentarioFormulario').show();
    });

    console.log("Asociación de evento de clic realizada correctamente");
   $(document).on('click', '.enviarComentario', function() {
       var contenedorForo = $(this).closest('.foro');
       var nuevoComentario = contenedorForo.find('.nuevoComentario').val();
       var idForo = contenedorForo.data('id-foro');

       if (!nuevoComentario) {
           alert('Por favor, ingresa un comentario antes de enviarlo.');
           return;
       }

       agregarComentario(nuevoComentario, idForo);
       contenedorForo.find('.nuevoComentario').val('');
       contenedorForo.find('.comentarioFormulario').hide();
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

    console.log(formData)
    $.ajax({
        url: "/api/foros/guardar",
        type: "POST",
        headers:{'Authorization': 'Bearer '+token},
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {

            Swal.fire({
                icon: 'info',
                title: '¡Nueva Publicación!',
                showConfirmButton: false,
                timer: 750

            })
            console.log("lollll toy aqui");
            console.log(data);
        },
        error: function (xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: '¡Proceso interrumpido¡',
                showConfirmButton: false
            });
        }
    });
}

function getfile(nombrearchivo){
    var nombreArchivo = "nombrearchivo"; // Nombre del archivo a mostrar

    fetch("/api/archivo/bajar/" + nombreArchivo)
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

function traerforo(id) {
    $.ajax({
        url: '/api/foros/getbuscar/' + id,
        type: 'GET',
        headers: { 'Authorization': 'Bearer ' + token },
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            // Rellenar el campo de contenido del foro
            $("#contenido").val(data.contenido);

            // Rellenar el campo de archivos del foro si existe esa propiedad en tu objeto de respuesta
            if (data.archivos) {
                $("#archivos").val(data.archivos);
            }

            // Rellenar el campo oculto del ID del foro
            $("#foro-id").val(data.id);

            // Rellenar el campo de archivos seleccionados si existe esa propiedad en tu objeto de respuesta
            if (data.archivosSeleccionados) {
                $("#archivos-seleccionados").text(data.archivosSeleccionados.join(', '));
            }
        },
        error: function(error) {
            console.error('Error al cargar el foro:', error);
        }
    });
}

// Función para obtener todos los foros
function getAllData() {
    $.ajax({
        url: '/api/foros/getall',
        type: 'GET',
        headers:{'Authorization': 'Bearer '+token},
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
                console.log("foro", foro.id, "lista",foro.comentariosList[0], "corazoncitos", foro.reacciones)
                    console.log("lista",foro.comentariosList[0])
                     mostrarForoYArchivos(foro, foro.files, foro.comentariosList);
                } else {
                    // Si no hay archivos, mostramos solo el foro
                    mostrarForo(foro, foro.comentariosList);
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

function likes(foro_id){
    console.log(foro_id)
    $.ajax({
        url: "/api/foros/likes",
        type: "POST",
        headers: {'Authorization': 'Bearer ' + token},
        data: {
            id:foro_id
        },
        success: function (data) {
            Swal.fire({
                icon: 'success',
                title: '¡Le has dado like!',
                showConfirmButton: false,
                timer: 750
            });
        },
        error: function (xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: '¡algo ocurriò!',
                showConfirmButton: false
            });
        }
    });
}
function mostrarForos(foros) {
    const contenedor = document.getElementById("aqui");
    foros.forEach(function (foro) {
        let contenidoHTML = `
            <div class="foro" data-id-foro="${foro.id}">
                <p>Contenido: ${foro.contenido}</p>
                <p>Fecha: ${foro.fecha}</p>
                <button onClick="traerforo(${foro.id})">Editar</button>
                <button onClick="likes(${foro.id})">Corazoncito</button>
                <button class="mostrarFormulario">Agregar Comentario</button>

                <div class="comentarioFormulario" style="display: none;">
                    <label for="nuevoComentario">Nuevo Comentario:</label>
                    <input type="text" class="nuevoComentario">
                    <button class="enviarComentario">Enviar</button>
                </div>

                <div class="comentarios"></div>
            </div>
        `;

        contenedor.innerHTML += contenidoHTML;

        // Muestra el primer comentario si existe
        const comentariosDiv = contenedor.querySelector('.comentarios');
        if (foro.comentariosList && foro.comentariosList.length > 0) {
            const primerComentario = foro.comentariosList[0].contenido;
            comentariosDiv.innerHTML = `<p>Primer Comentario: ${primerComentario}</p>`;
        } else {
            comentariosDiv.innerHTML = '<p>No hay comentarios</p>';
        }
    });
}

function mostrarForoYArchivos(foro, archivos, comentarios) {
    const contenedor = document.getElementById("aqui");
    let contenidoHTML = `
        <div class="foro" data-id-foro="${foro.id}">
            <p>Contenido: ${foro.contenido}</p>
            <p>Fecha: ${foro.fecha}</p>
            <div class="imagenes">
    `;

    archivos.forEach(function (archivo) {
    console.log(archivo);
        const imageUrl = `images/${archivo}`;
        contenidoHTML += `<img src="/api/archivo/bajar/${archivo}">`;
    });

    contenidoHTML += `
            </div>
            <button onClick="traerforo(${foro.id})">Editar</button>
            <button onClick="likes(${foro.id})">Corazoncito</button>
            <button class="mostrarFormulario">Agregar Comentario</button>

            <div class="comentarioFormulario" style="display: none;">
                <label for="nuevoComentario">Nuevo Comentario:</label>
                <input type="text" class="nuevoComentario">
                <button class="enviarComentario">Enviar</button>
            </div>

            <div class="comentarios"></div>
        </div>
    `;

    contenedor.innerHTML += contenidoHTML;

 const comentariosDiv = contenedor.querySelector('.comentarios');
    if (foro.comentariosList && foro.comentariosList.length > 0) {
        const primerComentario = foro.comentariosList[0];
        comentariosDiv.innerHTML = `<p>Primer Comentario: ${primerComentario}</p>`;
    } else {
        comentariosDiv.innerHTML = '<p>No hay comentarios</p>';
    }

}

//  -----------------------------------COMENTARIOS---------------------------------------------
function agregarComentario(comentario, id_foro) {
    let data = {
        contenido : comentario,
        idUsuario : tokenizado.id,
        idForo : id_foro
    }

    console.log("hola mundo soy data", data)

        $.ajax({
            url: "/api/comentario/guardar",
            type: "POST",
            headers: {'Authorization': 'Bearer ' + token},
            data:data,
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
                    showConfirmButton: false
                });
            }
        });
    }