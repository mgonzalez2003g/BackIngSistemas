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

    function actualizarforo() {
        let datos = {
            contenido: $("#contenido").val(),
            files: $("#files").val()
        };
        let swalConfirmacion= Swal.fire({
            icon:'question',
            title:'¿Estas seguro de actulizar?',
            showCancelButton:true,
            confirmButtonText: "Actualizar",
            cancelButtonText: "Cancelar",
            showLoaderOnConfirm: true,
            allowOutsideClick: false,
            allowEscapeKey: false,
            preConfirm:function () {
                return new Promise(function (resolve, reject) {
                    $.ajax({
                        url: "/api/foros/actulizar",
                        type: "PUT",
                        data: JSON.stringify(foro),
                        dataType: "json",
                        contentType: "application/json; charset=utf-8",
                        success: function (datos) {
                            resolve(true); // Se resuelve con `true` para indicar la confirmación
                            Cookies.remove('token');
                            window.location.replace("/login.html");
                        },
                        error: function (xhr, status, error) {
                            reject();
                        }
                    });
                });
            }

            }).then(function (resault){
                if (resault.value===true){
                    Swal.fire({
                        icon: "success",
                        title: "Actualización Completada",
                        text: "Cambios realizados, por su seguridad actualizaremos el sistema",
                        showConfirmButton: false,
                        timer: 900,
                        allowOutsideClick: false,
                        allowEscapeKey: false
                    });
                }else if (result.dismiss === Swal.DismissReason.cancel) {
                    Swal.fire({
                        icon: "info",
                        title: "Proceso cancelado",
                        text: "La actualización ha sido cancelada",
                        showConfirmButton: false
                    });
                    // Redirecciona a pagina de inicio
                    setTimeout(function() {
                        window.location.replace("/inicio.html");
                    }, 900);
                }
        });
        // Detectar cuando se cierra el cuadro de diálogo de confirmación
        swalConfirmacion.then(function(result) {
            if (result && (result.dismiss === Swal.DismissReason.esc || result.dismiss === Swal.DismissReason.backdrop)) {
                Swal.fire({
                    icon: "info",
                    title: "Proceso cancelado",
                    text: "La actualización ha sido cancelada",
                    showConfirmButton: false});
                    // Redirecciona a pagina de inicio
                    setTimeout(function() {
                        window.location.replace("/inicio.html");
                    }, 900);
            }
                });

    }

function getAllData(){
    $.ajax({
        url: 'api/foros/getall',
        type:'GET',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            Swal.fire({
                icon: "success",
                title: "Visualización",
                text: "yeeii",
                showConfirmButton: false});
            console.log(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            Swal.fire({
                icon: "error",
                title: "Error en la visualización ",
                text: "efesota",
                showConfirmButton: false});
            console.log("toy aca");
        }
    });
}

function showall(data) {
    let daticos="";

    for (i=0; i<data.length;i++){
        daticos+=`
            <div class="content">
                <a class="header">${data[i].contenido}</a>
            </div>
        `;
    }
    $("#aqui").html(daticos);
}
