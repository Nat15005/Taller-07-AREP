const API_BASE_URL = "http://localhost:8081";

function showUserInfo(username) {
    const registerForm = document.querySelector(".register-form");
    const userInfo = document.querySelector(".user-info");
    const loggedUsername = document.querySelector(".logged-username");

    registerForm.style.display = "none";
    userInfo.style.display = "block";
    loggedUsername.textContent = username;
}

function showRegisterForm() {
    const registerForm = document.querySelector(".register-form");
    const userInfo = document.querySelector(".user-info");

    registerForm.style.display = "block";
    userInfo.style.display = "none";
}

async function createUser() {
    const usernameInput = document.querySelector(".username-input");
    const username = usernameInput.value.trim();

    if (!username) {
        alert("Por favor, ingresa un nombre de usuario.");
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/users/username/${username}`);

        if (response.ok) {
            const user = await response.json();
            localStorage.setItem("userId", user.id);
            localStorage.setItem("username", user.username);
            alert(`Bienvenido de nuevo, ${user.username}!`);
            showUserInfo(user.username);
        } else if (response.status === 404) {
            const userData = { username };
            const createResponse = await fetch(`${API_BASE_URL}/users`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(userData),
            });

            if (!createResponse.ok) {
                throw new Error("Error al registrar el usuario");
            }

            const newUser = await createResponse.json();
            localStorage.setItem("userId", newUser.id);
            localStorage.setItem("username", newUser.username);
            alert(`Usuario registrado: ${newUser.username}`);
            showUserInfo(newUser.username);
        } else {
            throw new Error("Error al verificar el usuario");
        }

        usernameInput.value = "";

    } catch (error) {
        console.error("Error al manejar el usuario:", error);
        alert("Hubo un problema al procesar tu solicitud.");
    }
}



function logout() {
    localStorage.removeItem("userId");
    localStorage.removeItem("username");

    showRegisterForm();
}

async function updateFeed() {
    try {
        const response = await fetch(`${API_BASE_URL}/stream/posts`);
        if (!response.ok) {
            throw new Error("Error al obtener el feed");
        }

        const posts = await response.json();
        const tweetsContainer = document.querySelector(".tweets-container");
        tweetsContainer.innerHTML = "";

        posts.forEach(post => {
            const tweetElement = document.createElement("div");
            tweetElement.classList.add("tweet");
            tweetElement.innerHTML = `<p><strong>@${post.user.username}</strong>: ${post.content}</p>`;
            tweetsContainer.appendChild(tweetElement);
        });
    } catch (error) {
        console.error("Error al actualizar el feed:", error);
    }
}

async function createPost() {
    const tweetInput = document.querySelector(".tweet-input");
    const tweetContent = tweetInput.value.trim();
    const userId = localStorage.getItem("userId");

    if (!tweetContent) {
        alert("El post no puede estar vacío.");
        return;
    }

    if (!userId) {
        alert("Error: No se ha encontrado un usuario registrado.");
        return;
    }

    const postData = {
        content: tweetContent,
        user: { id: userId },
        stream: { id: 1 }
    };

    try {
        const response = await fetch(`${API_BASE_URL}/posts`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(postData),
        });

        if (!response.ok) {
            throw new Error("Error al crear el post");
        }

        const newPost = await response.json();
        alert(`Post creado: ${newPost.content}`);

        tweetInput.value = "";
        updateFeed();
    } catch (error) {
        console.error("Error al crear post:", error);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const loggedUsername = localStorage.getItem("username");
    if (loggedUsername) {
        showUserInfo(loggedUsername);
    }

    document.querySelector(".register-btn").addEventListener("click", createUser);
    document.querySelector(".post-btn").addEventListener("click", createPost);
    document.querySelector(".logout-btn").addEventListener("click", logout);

    updateFeed();
});