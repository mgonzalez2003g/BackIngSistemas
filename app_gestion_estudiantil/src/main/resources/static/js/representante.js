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
function enviarrepre() {
    let firstname = tokenizado.firstname;
    let id = tokenizado.id;
    let description = document.getElementById("description").value;
    let formData = new FormData();

    formData.append("id", id);
    formData.append("firstname", firstname);
    formData.append("description", description);

    let inputFile = document.getElementById("image-input");
    if (inputFile.files.length > 0) {
        formData.append("url", inputFile.files[0]);
    }

    console.log(formData);

    $.ajax({
        url: "/api/representantes/guardar",
        type: "POST",
        headers: { 'Authorization': 'Bearer ' + token },
        data: formData,
        processData: false, // Importante para el envío de archivos
        contentType: false, // Importante para el envío de archivos
        success: function (data) {
            console.log("exito");
            Swal.fire({
                icon: 'info',
                title: '¡Te has registrado exitosamente!',
                showConfirmButton: false,
                timer: 750
            });
        },
        error: function (xhr, status, error) {
            Swal.fire({
                icon: 'info',
                title: '¡Ya estas registrado!',
                showConfirmButton: false
            });
        }
    });
}


function getAllData() {
    $.ajax({
        url: '/api/representantes/getall',
        type: 'GET',
        headers:{'Authorization': 'Bearer '+token},
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function (lista) {
            Swal.fire({
                icon: "success",
                title: "Visualización",
                text: "yeeii",
                showConfirmButton: true
            });
            console.log(lista);
            lista.forEach(function (i) {
                mostrarcandidatos(i);
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
function votar(id, repre) {

    console.log(id, repre)

    $.ajax({
        url: "/api/representantes/voto",
        type: "POST",
        headers: {'Authorization': 'Bearer ' + token},
        data: {
            id:id,
            repre:repre
        },
        success: function (data) {
            Swal.fire({
                icon: 'success',
                title: '¡Voto Realizado!',
                showConfirmButton: false,
                timer: 750
            });
        },
        error: function (xhr, status, error) {
            Swal.fire({
                icon: 'info',
                title: '¡Ya tienes un voto registrado!',
                showConfirmButton: false
            });
        }
    });
}



function mostrarcandidatos(lista) {
    const contenedor = document.getElementById("aqui");

    let contenidoHTML = `
        <div class="candidatos">
            <p>Contenido: ${lista.description}</p>
            <img src="images/${lista.url}" alt="${lista.url}">
            
    `;

    contenidoHTML += `
            </div>
            <button onclick="votar(tokenizado.id, ${lista.id})">Votar</button>
        </div>
    `;

    contenedor.innerHTML += contenidoHTML;
}

function updateFileName() {
    const inputFile = document.getElementById("image-input");
    const fileNameLabel = document.getElementById("file-name");

    if (inputFile.files.length > 0) {
        fileNameLabel.textContent = "Nombre del archivo: " + inputFile.files[0].name;
    } else {
        fileNameLabel.textContent = ""; // Clear the label if no file is selected
    }
}