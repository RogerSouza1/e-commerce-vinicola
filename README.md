# e-commerce-vinicola
## Sobre a Winery
> O projeto MedEasy é uma iniciativa desenvolvida para avaliar o conteúdo da disciplina de PI (Projeto Integrador) no curso de ADS do Centro Universitário Senac.
> Winery é um e-commerce desenvolvido para facilitar a compra de vinhos de alta qualidade diretamente de sua vinícola preferida. Com uma interface amigável e um sistema seguro, o Winery oferece uma experiência de compra tranquila e agradável para todos os amantes de vinho.
> O sistema será completo, incluindo módulos essenciais como compras, carrinho, criptografia de senha, entre outros. Apresentará uma interface front-end intuitiva para facilitar a interação do usuário, desde o cadastro. Todos os dados serão armazenados com segurança em nosso banco de dados, garantindo a privacidade dos usuários.

## 🔧Lista de Funcionalidades
#### Requisitos Funcionais 
- [ ] O sistema deve validar os dados de login no banco de dados.
- [ ] O login deve ser realizado utilizando o email do usuário.
- [ ] Se o login for bem-sucedido, o sistema deve redirecionar o usuário para a página principal do backoffice, onde haverá links para a página de lista de produtos e lista de usuários (para administradores).
- [ ] Ao fazer login, o sistema deve criar uma sessão com o usuário e seu grupo (administrador ou estoquista).
- [ ] Se um cliente tentar fazer login com email e senha, o sistema deve rejeitar o acesso, pois a tela de login é apenas para usuários de backoffice.
- [ ] O sistema deve listar todos os usuários cadastrados, exibindo o nome, email, status, e grupo na entrada da tela para o administrador.
- [ ] O sistema deve permitir que o administrador filtre a lista de usuários por nome de usuário.
- [ ] O sistema deve permitir ao administrador adicionar novos usuários.
- [ ] O sistema deve permitir ao administrador editar as informações de um usuário.
- [ ] O sistema deve permitir ao administrador mudar o status de um usuário (de ativo para inativo ou vice-versa).
- [ ] O cadastro de usuário deve registrar o usuário como ativo por padrão.


#### Requisitos Não Funcionais 
- [ ] A senha deve ser encriptada antes de ser validada com o dado no banco de dados, que também deve estar encriptado.
- [ ] Toda alteração realizada no sistema deve refletir diretamente no banco de dados.
- [ ] A confirmação de alteração de status deve ser feita através de uma mensagem de alerta, sem necessidade de entrar em outra página.
- [ ] No cadastro de usuário, o sistema deve solicitar a senha duas vezes e permitir o cadastro apenas se as duas senhas forem iguais.


#### Regras de Negócio
- [x] O sistema deve negar o acesso ao usuário se o login não for localizado no banco de dados.
- [x] Não é permitido cadastrar dois usuários com o mesmo login (email).
- [x] O CPF deve ser validado antes de ser gravado no banco de dados.
- [x] Não é permitido alterar o email do usuário após o cadastro.
- [x] É permitido alterar o grupo de um usuário, exceto se ele for o usuário logado no momento.
- [x] O sistema deve permitir alterar o nome, CPF e senha do usuário, desde que a senha seja validada duas vezes antes de ser atualizada no banco de dados.
