/**
 *
 */
function sendData(){
    let apodo = $("#apodo").val();
    let firstname = $("#firstname").val();
    let lastname = $("#lastname").val();
    let email = $("#email").val();
    let password = $("#password").val();
    let carrera = $("#carrera").val();

    if (!apodo || !firstname || !lastname || !email || !password || !carrera) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Todos los campos son necesarios',
            confirmButtonText: 'Aceptar'
        });
        return;
    }

    let data = {
        apodo: apodo,
        firstname: firstname,
        lastname: lastname,
        email: email,
        password: password,
        carrera: carrera
    };
    $.ajax({
        url:"/api/auth/register",
        type:"POST",
        contentType:'application/json; charset=utf-8',
        dataType:"json",
        data:JSON.stringify(data),

        success: function(datar) {
            Cookies.set('token', datar.token);
            Swal.fire({
                icon: 'success',
                title: 'Registro Exitoso',
                showConfirmButton: false, // Oculta el botón de "OK"
                timer: 700 // Cierra automáticamente después de 7 segundos
            })
            setTimeout(function () {
                window.location.replace('/login.html');
            }, 1000);
        },
        error: function(xhr, status) {
            $("#apodo").val(""),
                $("#firstname").val(""),
                $("#lastname").val(""),
                $("#email").val(""),
                $("#password").val(""),
                $("#carrera").val("")
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Todos los campos son necesarios',
                confirmButtonText: "Aceptar"
            })

        }

    });
}

function atras(){
    window.location.replace("/login.html")
}