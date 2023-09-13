function togglePasswordVisibility() {
    var passwordInput = document.getElementById("password");
    var toggleIcon = document.getElementById("toggleIcon");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        toggleIcon.innerHTML = "&#x1F649;"; // Emoji de monito 🙉
    } else {
        passwordInput.type = "password";
        toggleIcon.innerHTML = "&#x1F648;"; // Emoji de monito 🙈
    }
}
function red(){
    window.location.replace("/registro.html")
}
function sendauth(){
    let data={
        email:$("#email").val(),
        password:$("#password").val()
    }
    $.ajax({
        url:"api/auth/authenticate",
        type:"POST",
        contentType:'application/json; charset=utf-8',
        dataType:"json",
        data:JSON.stringify(data),

        success: function(datar) {
            //Guarda en cookie
            Cookies.set('token', datar.token);
            //Da el aviso de exito
            Swal.fire({
                icon: 'success',
                title: 'Proceso Exitoso',
                text: 'Bienvenido',
                showConfirmButton: false, // Oculta el botón de "OK"
                timer: 700 // Cierra automáticamente después de 1.5 segundos
            })
            //Redirecciona a pagina de inicio
            setTimeout(function() {
                window.location.replace('/inicio.html');
            }, 1000);
        },
        error: function(xhr, status) {
            //Como es incorrecta la información limpia los campos para que lo vuelva a ingresar
            $("#email").val("");
            $("#password").val("");
            //Muestra el aviso de error
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Usuario o constraseña incorrectos',
                confirmButtonText: "Aceptar"
            })
        }
    });
}