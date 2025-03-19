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

const app = firebase.initializeApp(firebaseConfig);
const auth = firebase.auth();

function showUserInfo(email) {
    const userInfo = document.querySelector(".user-info");
    const loggedUsername = document.querySelector(".logged-username");

    userInfo.style.display = "block";
    loggedUsername.textContent = email;
}

function logout() {
    auth.signOut().then(() => {
        localStorage.removeItem("firebaseToken");
        localStorage.removeItem("userId");
        localStorage.removeItem("email");
        window.location.href = "index.html";
    }).catch((error) => {
        console.error("Error al cerrar sesión:", error);
    });
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
    const email = localStorage.getItem("email");

    if (email) {
        showUserInfo(email);
    } else {
        window.location.href = "index.html";
    }

    document.querySelector(".post-btn").addEventListener("click", createPost);
    document.querySelector(".logout-btn").addEventListener("click", logout);

    updateFeed();
});