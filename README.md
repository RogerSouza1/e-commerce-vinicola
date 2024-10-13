# e-commerce-vinicola
## Sobre a Winery
> O projeto Winery é uma iniciativa desenvolvida para avaliar o conteúdo da disciplina de PI (Projeto Integrador) no curso de ADS do Centro Universitário Senac.
> Winery é um e-commerce desenvolvido para facilitar a compra de vinhos de alta qualidade diretamente de sua vinícola preferida. Com uma interface amigável e um sistema seguro, o Winery oferece uma experiência de compra tranquila e agradável para todos os amantes de vinho.
> O sistema será completo, incluindo módulos essenciais como compras, carrinho, criptografia de senha, entre outros. Apresentará uma interface front-end intuitiva para facilitar a interação do usuário, desde o cadastro. Todos os dados serão armazenados com segurança em nosso banco de dados, garantindo a privacidade dos usuários.

## 🔧Lista de Funcionalidades
### Como Usuário Não Logado

#### Requisitos Funcionais
- [ ] O sistema deve validar os dados de login no banco de dados.
- [ ] O login deve ser realizado utilizando o email do usuário.
- [ ] A senha deve ser encriptada antes de ser validada com o dado no banco de dados.
- [ ] O sistema deve negar a entrada do usuário no backoffice se o login não for localizado.
- [ ] Se as credenciais estiverem corretas e o usuário estiver habilitado, o sistema deve redirecionar para a página principal do backoffice.
- [ ] O sistema deve criar a sessão com o usuário e seu grupo (administrador ou estoquista) ao logar.
- [ ] O sistema deve rejeitar clientes que tentarem logar com email e senha na tela de login do backoffice.

#### Requisitos Não Funcionais
- [ ] A senha deve ser encriptada antes de ser validada com o dado no banco de dados, que também deve estar encriptado.
- [ ] Toda alteração realizada no sistema deve refletir diretamente no banco de dados.
- [ ] O sistema deve garantir a performance adequada durante a validação e encriptação de senhas.
- [ ] O sistema deve ser seguro e proteger as informações dos usuários, especialmente as senhas.

#### Regras de Negócio
- [ ] O sistema deve negar o acesso ao usuário se o login não for localizado no banco de dados.
- [ ] Não é permitido cadastrar dois usuários com o mesmo login (email).

### Como Administrador

#### Requisitos Funcionais
- [ ] O sistema deve listar todos os usuários cadastrados na entrada da tela, mostrando Nome, email, status e grupo.
- [ ] O sistema deve permitir cadastrar um novo usuário ao clicar no sinal de "+".
- [ ] O sistema deve permitir alterar um usuário existente ao clicar em "alterar usuário".
- [ ] O sistema deve permitir inativar ou reativar um usuário ao clicar no botão correspondente.
- [ ] O sistema deve permitir filtrar a lista de usuários por nome.
- [ ] O sistema deve permitir cadastrar nome, CPF, email, senha e grupo (admin/estoquista) no banco de dados.
- [ ] O sistema deve pedir a senha duas vezes no cadastro de usuário e só permitir o cadastro se as duas senhas forem iguais.
- [ ] A senha deve ser encriptada antes de ser enviada para o banco de dados.
- [ ] O sistema deve cadastrar o usuário como ativo por padrão.
- [ ] O sistema deve impedir o cadastro de dois usuários com o mesmo email.
- [ ] O sistema deve validar o CPF antes de gravar no banco de dados.
- [ ] O sistema deve permitir alterar o grupo de um usuário, exceto se for o usuário logado no momento.
- [ ] O sistema deve permitir alterar o nome, CPF e senha do usuário, sempre validando a senha duas vezes e armazenando-a de forma encriptada no banco de dados.
- [ ] O sistema não deve permitir a alteração do email do usuário.
- [ ] Todas as alterações realizadas no sistema devem refletir diretamente no banco de dados.
- [ ] O sistema deve permitir alterar o status dos usuários (ativo/inativo) na mesma tela de listagem de usuário, confirmando a alteração por uma mensagem de alerta.

- [ ] Após o login do administrador, a tela principal do backoffice terá o botão de Listar Produtos.
- [ ] Ao clicar no botão, haverá a abertura da tela de produtos, que por default deve listar os últimos (decrescente) produtos inseridos na base.
- [ ] A tela de produtos deve ter um campo de busca de produto com busca parcial (Ex. smart - traz tudo que contém smart no nome do produto).
- [ ] A lista deve apresentar o código do produto, o nome do produto, a quantidade em estoque, o valor e o status (ativo ou desativado).
- [ ] Terá um botão para chamar a tela de cadastro de produto (representado por um sinal de +).
- [ ] A lista deve mostrar no máximo 10 produtos por página e criar uma barra de paginação.
- [ ] Para cada produto, deve haver um ícone/link com as ações permitidas (alterar, inativar, reativar, visualizar).
- [ ] O sistema deve permitir incluir dados de nome de produto (máx 200 caracteres), avaliação (de 1 a 5 variando de 0,5 em 0,5), descrição detalhada (2000 caracteres), preço do produto (valor monetário) com 2 casas decimais, e quantidade em estoque (valor inteiro).
- [ ] O sistema deve permitir incluir e associar múltiplas imagens ao mesmo produto (não limitado).
- [ ] O sistema deve permitir definir uma imagem como padrão.
- [ ] A imagem deve ser carregada antes no diretório do projeto (pelo botão procurar).
- [ ] Ao carregar a imagem, o sistema deve trocar o nome e armazenar o caminho com o novo nome no banco de dados.
- [ ] Deve ser possível criar um marcador na imagem que será considerada principal e mostrada na página de landing page.
- [ ] O botão salvar deve salvar o produto e as referências das imagens no banco de dados e voltar para a tela de lista de produtos.
- [ ] O botão cancelar deve voltar para a tela de lista de produtos.
- [ ] As alterações nas informações e imagens de um produto devem ser refletidas no banco de dados.
- [ ] Não é necessário entrar em tela de edição para inativar/reativar um produto; ao clicar no botão, deve alternar o status do produto.
- [ ] Antes de mudar o status, deve aparecer um pop-up perguntando se confirma ou não a alteração.
- [ ] A tela deve mostrar como a página de detalhe do produto será exibida para o usuário final.
- [ ] O botão de comprar na tela de detalhes do produto deve ficar desabilitado.
- [ ] O carrossel com as imagens deve ser funcional, mostrando o produto e a avaliação.

#### Requisitos Não Funcionais
- [ ] A senha deve ser encriptada antes de ser validada com o dado no banco de dados, que também deve estar encriptado.
- [ ] Toda alteração realizada no sistema deve refletir diretamente no banco de dados.
- [ ] O sistema deve garantir a performance adequada durante a validação e encriptação de senhas.
- [ ] O sistema deve ser seguro e proteger as informações dos usuários, especialmente as senhas.

- [ ] O sistema deve refletir todas as alterações no banco de dados.
- [ ] A imagem deve ser carregada antes no diretório do projeto (pelo botão procurar).

#### Regras de Negócio
- [ ] Não é permitido cadastrar dois usuários com o mesmo login (email).
- [ ] O CPF deve ser validado antes de ser gravado no banco de dados.
- [ ] O cadastro de usuário deve sempre registrar o usuário como ativo.
- [ ] O administrador não pode alterar seu próprio grupo.
- [ ] As alterações de status (ativo/inativo) devem ser confirmadas por uma mensagem de alerta antes de serem aplicadas.

- [ ] O sistema deve listar os produtos por default em ordem decrescente de inserção.
- [ ] O sistema deve permitir incluir dados de nome de produto, avaliação, descrição detalhada, preço e quantidade em estoque.
- [ ] Deve ser possível definir uma imagem como padrão.
- [ ] As alterações de status (ativo/inativo) devem ser confirmadas por um pop-up antes de serem aplicadas.
- [ ] A tela de produtos deve permitir a busca parcial de produtos pelo nome.
- [ ] A lista de produtos deve apresentar código, nome, quantidade em estoque, valor e status.
- [ ] Deve haver uma barra de paginação para a lista de produtos, mostrando no máximo 10 produtos por página.
- [ ] A tela de detalhe do produto deve mostrar o produto e sua avaliação de forma funcional.

### Como Estoquista

#### Requisitos Funcionais
- [ ] Após o login do Estoquista, a tela principal do backoffice terá o botão de Produtos.
- [ ] Ao clicar no botão, haverá a abertura da tela de produtos, que por default deve listar os últimos (decrescente) produtos inseridos na base.
- [ ] A tela de produtos deve ter um campo de busca de produto com busca parcial (Ex. smart - traz tudo que contém smart no nome do produto).
- [ ] A lista deve apresentar o código do produto, o nome do produto, a quantidade em estoque, o valor e o status (ativo ou desativado).
- [ ] A lista deve mostrar no máximo 10 produtos por página e criar uma barra de paginação.
- [ ] Para cada produto, deve haver um ícone/link com a ação permitida (apenas alterar).
- [ ] O sistema deve permitir alterar apenas a quantidade de produto, todos os outros campos devem estar desabilitados.
- [ ] As alterações na quantidade de produto devem ser refletidas no banco de dados H15.

#### Requisitos Não Funcionais
- [ ] O sistema deve refletir todas as alterações no banco de dados H15.

#### Regras de Negócio
- [ ] O sistema deve listar os produtos por default em ordem decrescente de inserção.
- [ ] A tela de produtos deve permitir a busca parcial de produtos pelo nome.
- [ ] A lista de produtos deve apresentar código, nome, quantidade em estoque, valor e status.
- [ ] Deve haver uma barra de paginação para a lista de produtos, mostrando no máximo 10 produtos por página.
- [ ] O sistema deve permitir alterar apenas a quantidade de produto pelo estoquista, todos os outros campos devem estar desabilitados.

### Como Cliente logado e não logado

#### Requisitos Funcionais
- [ ] A página deve exibir o logo da loja.
- [ ] A página deve ter ícones de carrinho no lado direito.
- [ ] Deve haver um link para identificação do cliente (faça login/crie seu login) que não precisa funcionar.
- [ ] A lista de produtos deve ser no formato de cards e apresentar a imagem configurada como principal no cadastro.
- [ ] Cada card deve conter pelo menos a imagem principal cadastrada, o nome do produto, preço e um botão para detalhes.
- [ ] Ao clicar no botão de detalhes, a página de detalhes do produto deve ser aberta.
- [ ] O carrossel de imagens na página de detalhes deve ser funcional.
- [ ] A página de detalhes deve apresentar o nome do produto, descrição detalhada, valor e a avaliação.
- [ ] Ao clicar em comprar, o produto deve ser adicionado ao carrinho.
- [ ] O carrinho deve acumular os produtos selecionados.
- [ ] É possível comprar o mesmo produto mais de uma vez, adicionando à quantidade no carrinho.
- [ ] Ao clicar em comprar, seja na página de detalhes ou na principal, o usuário deve ser redirecionado para o carrinho ou para a página inicial (continuar comprando) com o indicador do carrinho atualizado.
- [ ] O item adicionado ao carrinho deve ser gravado (em sessão, banco de dados ou outro meio escolhido).
- [ ] É possível aumentar a quantidade de um produto no carrinho, recalculando o subtotal.
- [ ] O subtotal deve levar em consideração o frete calculado.
- [ ] É possível diminuir a quantidade de um produto no carrinho, recalculando o subtotal.
- [ ] É possível remover um item do carrinho, recalculando o subtotal.
- [ ] O subtotal deve levar em consideração o frete calculado.

#### Requisitos Não Funcionais
- [ ] O sistema deve refletir todas as alterações do carrinho (em sessão, banco de dados ou outro meio escolhido).

#### Regras de Negócio
- [ ] O frete para clientes não logados é de livre escolha, podendo escolher entre 3 valores de frete.

### Como Cliente não logado

#### Requisitos Funcionais
- [ ] O e-mail do cliente não pode existir na base de dados.
- [ ] O CPF deve ser único e validado.
- [ ] O endereço de faturamento é obrigatório, devendo incluir:
  - [ ] CEP
  - [ ] Logradouro
  - [ ] Número
  - [ ] Complemento
  - [ ] Bairro
  - [ ] Cidade
  - [ ] UF
- [ ] Os dados de nome completo, data de nascimento e gênero também devem ser coletados.
- [ ] O CEP deve ser validado por uma API, por exemplo: `https://viacep.com.br/ws/09760280/json/`.
- [ ] O endereço de entrega é obrigatório e pode ser copiado do endereço de faturamento.
- [ ] O cliente pode ter mais de um endereço de entrega.
- [ ] O nome do cliente deve conter no mínimo 2 palavras, com pelo menos 3 letras em cada palavra.
- [ ] A senha deve ser armazenada de forma encriptada no banco de dados.
- [ ] Ao final do cadastro, o cliente deve ser armazenado na base de dados.
- [ ] O cliente deve ser direcionado para a tela de login após o cadastro.
- [ ] Validar usuário e senha (usuário = e-mail) no banco de dados.
- [ ] Se o usuário existir, deve-se criar uma sessão com o cliente logado.
- [ ] Caso não exista, deve ser gerado um erro informando que não foi localizado o usuário e/ou senha.

### Como Cliente logado

#### Requisitos Funcionais
- [ ] O cliente logado pode trocar seu nome, data de nascimento e gênero.
- [ ] Deve ser permitido também a alteração da senha.
- [ ] O cliente pode incluir mais endereços de entrega.
- [ ] Deve permitir a alteração do cadastro para incluir um novo endereço de entrega.
- [ ] A partir do segundo endereço, é possível escolher qual endereço será o endereço padrão (que será pré-carregado) no checkout.
- [ ] A validação e funcionalidade (consulta à API) do endereço devem ser as mesmas do cadastramento.
- [ ] Permitir a mudança de qual endereço de entrega é padrão ou não.
- [ ] Não é permitido alterar os dados de um endereço de entrega existente; deve-se adicionar um novo.
- [ ] Deve alertar que está saindo da sessão.
- [ ] Limpar a sessão do login do cliente.
