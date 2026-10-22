const API = "http://localhost:8080/links";

const formulario = document.getElementById("formulario");
const campoUrl = document.getElementById("campo-url");
const erro = document.getElementById("erro");
const lista = document.getElementById("lista");

async function carregarLinks() {
    const resposta = await fetch(API);
    const links = await resposta.json();

    lista.innerHTML = "";

    for (const link of links) {
        const item = document.createElement("li");

        const curto = document.createElement("a");
        curto.href = link.urlCurta;
        curto.textContent = link.urlCurta;
        curto.target = "_blank";

        const original = document.createElement("div");
        original.className = "original";
        original.textContent = link.urlOriginal + " · criado em " + new Date(link.criadoEm).toLocaleString("pt-BR");

        item.append(curto, original);
        lista.append(item);
    }
}

async function criarLink(evento) {
    evento.preventDefault();
    erro.textContent = "";

    const resposta = await fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ url: campoUrl.value })
    });

    if (!resposta.ok) {
        erro.textContent = "Não foi possível encurtar. Confira a URL.";
        return;
    }

    campoUrl.value = "";
    await carregarLinks();
}

formulario.addEventListener("submit", criarLink);
carregarLinks();