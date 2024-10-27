function buscarCep(cep) {
    cep = cep.replace(/\D/g, '');
    if (cep.length === 8) {
        // URL da API de CEP
        const url = `https://viacep.com.br/ws/${cep}/json/`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                if (!data.erro) {
                    document.getElementById('logradouro').value = data.logradouro;
                    document.getElementById('bairro').value = data.bairro;
                    document.getElementById('cidade').value = data.localidade;
                    document.getElementById('estado').value = data.uf;
                } else {
                    alert("CEP não encontrado.");
                }
            })
            .catch(error => {
                alert("Erro ao buscar o CEP.");
                console.error(error);
            });
    }
}

function buscarCepCadastrar(cep, tipo) {
    // Remove qualquer traço ou espaço
    cep = cep.replace(/\D/g, '');

    // Verifica se o CEP tem 8 dígitos
    if (cep.length === 8) {
        // URL da API de CEP
        const url = `https://viacep.com.br/ws/${cep}/json/`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                if (!data.erro) {
                    if (tipo === 'faturamento') {
                        // Preenche os campos de faturamento
                        document.getElementById('logradouro').value = data.logradouro;
                        document.getElementById('bairro').value = data.bairro;
                        document.getElementById('cidade').value = data.localidade;
                        document.getElementById('estado').value = data.uf;
                    } else if (tipo === 'entrega') {
                        // Preenche os campos de entrega
                        document.getElementById('logradouroEntrega').value = data.logradouro;
                        document.getElementById('bairroEntrega').value = data.bairro;
                        document.getElementById('cidadeEntrega').value = data.localidade;
                        document.getElementById('estadoEntrega').value = data.uf;
                    }
                } else {
                    alert("CEP não encontrado.");
                }
            })
            .catch(error => {
                alert("Erro ao buscar o CEP.");
                console.error(error);
            });
    }
}