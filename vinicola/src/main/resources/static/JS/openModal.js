
    function validateForm() {
    const nome = document.getElementById("nome").value.trim();
    const avaliacao = document.getElementById("avaliacao").value;
    const descricao = document.getElementById("descricao").value.trim();
    const preco = document.getElementById("preco").value.replace(/[^\d,]/g, '').replace(',', '.');
    const qtdEstoque = document.getElementById("qtdEstoque").value;
    const hiddenImageUpload = document.getElementById('hiddenImageUpload').files;
    const principalImage = document.getElementById('principalImage').value;

    if (nome === "" || descricao === "" || preco === "" || qtdEstoque === "") {
    alert("Todos os campos são obrigatórios.");
    return false;
}

    if (isNaN(preco) || parseFloat(preco) <= 0) {
    alert("Preço deve ser um número positivo.");
    return false;
}

    if (isNaN(qtdEstoque) || parseInt(qtdEstoque) < 0) {
    alert("Quantidade deve ser um número não negativo.");
    return false;
}

    if (hiddenImageUpload.length === 0) {
    alert('Por favor, adicione ao menos uma imagem.');
    return false;
}

    if (!principalImage) {
    alert('Por favor, selecione uma imagem principal.');
    return false;
}

    document.getElementById("preco").value = preco;

    return true;
}

    function formatCurrency(input) {
    let value = input.value.replace(/\D/g, '');
    value = (value / 100).toFixed(2);
    value = value.replace('.', ',');
    input.value = 'R$ ' + value;
}

    function clearForm() {
    document.getElementById("nome").value = "";
    document.getElementById("avaliacao").value = 0;
    document.getElementById("descricao").value = "";
    document.getElementById("preco").value = "0,00";
    document.getElementById("qtdEstoque").value = 0;
    document.getElementById("imagePreviewList").innerHTML = "";
    document.getElementById("imageUpload").value = "";
    document.getElementById("hiddenImageUpload").value = "";
    document.getElementById("principalImage").value = "";
}

    let principalImageIndex = -1;

    function openModal() {
    document.getElementById("imageModal").style.display = "block";
}

    function closeModal() {
    document.getElementById("imageModal").style.display = "none";
}

    function addImage() {
    const fileInput = document.getElementById("imageUpload");
    const files = fileInput.files;
    const imagePreviewList = document.getElementById("imagePreviewList");

    imagePreviewList.innerHTML = ''; // Limpa as imagens anteriores

    for (let i = 0; i < files.length; i++) {
    const file = files[i];

    const reader = new FileReader();
    reader.onload = function(e) {
    const img = document.createElement("img");
    img.src = e.target.result;
    img.style.width = "80px";
    img.style.cursor = "pointer";
    img.style.border = "2px solid transparent";

    img.onclick = function() {
    setPrincipalImage(i, img);
};

    imagePreviewList.appendChild(img);

    // Define a primeira imagem como principal se for a única
    if (files.length === 1) {
    setPrincipalImage(0, img);
}
};
    reader.readAsDataURL(file);
}
}

    function setPrincipalImage(index, imgElement) {
    const imagePreviewList = document.getElementById("imagePreviewList").children;

    for (let i = 0; i < imagePreviewList.length; i++) {
    imagePreviewList[i].style.border = "2px solid transparent";
}

    imgElement.style.border = "2px solid red";

    document.getElementById('principalImage').value = index;

    alert("Imagem principal definida.");
}

    function saveAndCloseModal() {
    const fileInput = document.getElementById("imageUpload");
    const hiddenFileInput = document.getElementById("hiddenImageUpload");

    const dt = new DataTransfer();
    for (let i = 0; i < fileInput.files.length; i++) {
    dt.items.add(fileInput.files[i]);
}

    hiddenFileInput.files = dt.files;

    closeModal();
}
