async function createUser() {
    const usernameInput = document.querySelector(".username-input"); // ✅ Corregido
    const username = usernameInput.value.trim();

    if (!username) {
        alert("Por favor, ingresa un nombre de usuario.");
        return;
    }

    const userData = { username };

    try {
        const response = await fetch("http://localhost:8080/users", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(userData),
        });

        if (!response.ok) {
            throw new Error("Error al registrar el usuario");
        }

        const newUser = await response.json();
        localStorage.setItem("userId", newUser.id);
        localStorage.setItem("username", newUser.username);
        alert(`Usuario registrado: ${newUser.username}`);
        usernameInput.value = "";

    } catch (error) {
        console.error("Error al registrar usuario:", error);
    }
}

async function updateFeed() {
    try {
        const response = await fetch("http://localhost:8080/stream/posts"); // Llamada a la API
        if (!response.ok) {
            throw new Error("Error al obtener el feed");
        }

        const posts = await response.json();
        const tweetsContainer = document.querySelector(".tweets-container"); // ✅ Corregido
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
        const response = await fetch("http://localhost:8080/posts", {
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
    document.querySelector(".register-btn").addEventListener("click", createUser);
    document.querySelector(".post-btn").addEventListener("click", createPost);
    updateFeed();
});
