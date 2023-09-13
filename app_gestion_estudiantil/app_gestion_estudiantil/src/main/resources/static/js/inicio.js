let token;
let tokenizado;

$(document).ready(function () {
    token = Cookies.get('token');
    tokenizado = parseJwt(token);
    saluda()
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

function saluda(){
    window.location.replace("/foro.html")


}
