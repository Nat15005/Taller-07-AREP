const API_BASE_URL = "http://localhost:8081";

const firebaseConfig = {
    apiKey: "AIzaSyD04aBntsWMTHr7g3nSL26bS8XTV0fZwhg",
    authDomain: "taller7arep.firebaseapp.com",
    projectId: "taller7arep",
    storageBucket: "taller7arep.firebasestorage.app",
    messagingSenderId: "575114387466",
    appId: "1:575114387466:web:e4cb42065ecb2c40543ff3",
    measurementId: "G-3N1HSNH0MD"
};

firebase.initializeApp(firebaseConfig);
const auth = firebase.auth();

function loginWithGoogle() {
    const provider = new firebase.auth.GoogleAuthProvider();
    auth.signInWithPopup(provider)
    .then((result) => {
      console.log("Usuario autenticado:", result.user);

      result.user.getIdToken().then((token) => {
        localStorage.setItem("firebaseToken", token);
        localStorage.setItem("userId", result.user.uid);
        localStorage.setItem("email", result.user.email);
        alert(`Bienvenido, ${result.user.email}!`);
        setTimeout(() => {
          window.location.href = "home.html";
        }, 500);
      });
    })
    .catch((error) => {
      console.error("Error en login:", error);
      alert("Hubo un problema al iniciar sesión con Google.");
    });
}

function registerWithEmail() {
    const email = document.querySelector("#reg-email").value.trim();
    const password = document.querySelector("#reg-password").value.trim();

    if (!email || !password) {
        alert("Por favor, completa todos los campos.");
        return;
    }

    if (password.length < 6) {
        alert("La contraseña debe tener al menos 6 caracteres.");
        return;
    }

    const username = email.split("@")[0];

    auth.createUserWithEmailAndPassword(email, password)
        .then((userCredential) => {
            const user = userCredential.user;
            localStorage.setItem("email", user.email);
            localStorage.setItem("userId", user.uid);

            user.getIdToken().then((token) => {
                localStorage.setItem("firebaseToken", token);

                fetch(`${API_BASE_URL}/users`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${token}`
                    },
                    body: JSON.stringify({
                        id: user.uid,
                        email: email
                    }),
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Error al crear usuario en el backend");
                    }
                    return response.json();
                })
                .then(data => {
                    localStorage.setItem("userId", data.id);
                    alert("Usuario registrado correctamente.");

                    setTimeout(() => {
                        window.location.href = "home.html";
                    }, 500);
                })
                .catch(error => {
                    console.error("Error al crear usuario en el backend:", error);
                    alert("Hubo un problema al registrar el usuario en el backend.");
                });
            });
        })
        .catch((error) => {
            console.error("Error al registrar usuario en Firebase:", error);
            alert("Hubo un problema al registrar el usuario en Firebase.");
        });
}

function loginWithEmail() {
    const email = document.querySelector("#email").value.trim();
    const password = document.querySelector("#password").value.trim();

    if (!email || !password) {
        alert("Por favor, ingresa un correo electrónico y una contraseña.");
        return;
    }

    auth.signInWithEmailAndPassword(email, password)
        .then((userCredential) => {
            const user = userCredential.user;
            localStorage.setItem("email", user.email);

            user.getIdToken().then((token) => {
                localStorage.setItem("firebaseToken", token);

                fetch(`${API_BASE_URL}/users/email/${email}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${token}`
                    }
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Error al obtener usuario del backend");
                    }
                    return response.json();
                })
                .then(data => {
                    localStorage.setItem("userId", data.id);
                    alert("Inicio de sesión exitoso.");
                    setTimeout(() => {
                        window.location.href = "home.html";
                    }, 500);
                })
                .catch(error => {
                    console.error("Error al obtener usuario del backend:", error);
                    alert("Hubo un problema al obtener la información del usuario.");
                });
            });
        })
        .catch((error) => {
            console.error("Error al iniciar sesión en Firebase:", error);
            alert("Hubo un problema al iniciar sesión.");
        });
}

document.addEventListener("DOMContentLoaded", () => {
  const registerBtn = document.querySelector(".register-btn");
  const loginBtn = document.querySelector(".login-btn");
  const googleBtn = document.querySelector(".google-btn");


  if (window.location.pathname.includes("home.html")) {
    console.log("Token en home:", localStorage.getItem("firebaseToken"));
  }

  if (registerBtn) {
    registerBtn.addEventListener("click", registerWithEmail);
  }

    if (loginBtn) {
        loginBtn.addEventListener("click", loginWithEmail);
    }

    if (googleBtn) {
        googleBtn.addEventListener("click", loginWithGoogle);
    }
});