# 🍷Winery
## Sobre
>Winery é um e-commerce desenvolvido para facilitar a compra de vinhos de alta qualidade diretamente por meio de uma plataforma web. Com sua interface amigável e um sistema seguro, o Winery oferece uma experiência de compra ideal para todos os amantes de vinho.
> 
>O sistema será completo, incluindo módulos essenciais para operações de CRUD, validações de permissionamento, criptografia de senhas, entre outros. Apresentará o front office para interação com os usuários e um back office para a gestão de funcionários, produtos e controle de pedidos. Todos os dados serão armazenados com segurança em nosso banco de dados, garantindo a integridade das informações.
>
>
## 📋Funcionalidades
### Requisitos Funcionais
>
>
#### Como Administrador
- [x] O administrador poderá listar todos os usuários cadastrados na entrada da tela, visualizando informações como nome, e-mail, grupo (administrador ou estoquista) e status (ativo ou inativo).
- [x] O administrador poderá cadastrar um novo usuário com nome, CPF, email, senha (encriptada) e grupo (admin/estoquista) no banco de dados.
- [x] O administrador poderá alterar um usuário existente, como os campos de nome, cpf, e-mail e grupo. O usuário não pode editar o próprio grupo.
- [x] O administrador poderá inativar ou reativar um usuário ao clicar no botão correspondente.
- [x] O administrador poderá filtrar a lista de usuários por nome.
- [x] O administrador poderá acessar a lista de produtos, que será exibida de forma decrescente. Os campos que serão exibidos são o código do produto, o nome, a quantidade em estoque, o valor e o status (ativo ou desativado), e será possível buscar um produto específico pelo seu nome.
- [x] O administrador poderá cadastrar um novo produto, com os campos, nome de produto (máx 200 caracteres), avaliação (de 1 a 5 variando de 0,5 em 0,5), descrição detalhada (2000 caracteres), preço do produto (valor monetário) com 2 casas decimais, quantidade em estoque (valor inteiro) e multiplas Imagens. Uma imagem deve ser definida como principal.
- [x] O administrador poderá alterar todas as informações de um produto, incluindo as imagens.
- [x] O administrador poderá ativar ou desativar produtos na lista.
- [x] O administrador poderá visualizar como o produto será mostrado no front-office para o cliente.
- [x] O administrador, poderá sair do sistema, com isso seus dados armazenados na sessão serão limpos.
>
#### Como Estoquista
- [x] O estoquista, poderá mudar a quantidade de produto no estoque.
- [x] O estoquista, poderá visualizar a lista de todos os pedidos realizados dentro do sistema.
- [x] O estoquista, poderá mudar o status do pedido.
- [x] O estoquista, poderá sair do sistema, com isso seus dados armazenados na sessão serão limpos.
>
#### Como Cliente logado e não logado
- [x] O cliente, logado ou não, poderá visualizar os produtos da loja na página principal.
- [x] O cliente, logado ou não, poderá visualizar os detalhes do produto, como Nome, Descrição Detalhada, Valor, Avaliação e Carrossel de Imagens.
- [x] O cliente, logado ou não, a partir da tela de visualizar os detalhes do produto, poderá adicionar o item ao carrinho. Caso o item já esteja presente no carrinho, ele será incrementado.
- [x] O cliente, logado ou não, poderá no carrinho incrementar ou decrementar a quantidade de produtos adicionados, e com isso o subtotal e total deverão ser recalculados.
- [x] O cliente, logado ou não, poderá no carrinho remover um produto, e com isso o subtotal e o total deverão ser recalculados.
- [x] O cliente, logado ou não, poderá no carrinho calculando frete escolher de livre escolha três opções pré-definidas.
>
#### Como Cliente não logado
- [x] O cliente, não logado, deve conseguir realizar login com o e-mail e senha (que será encriptada antes da validação), e os mesmos deve ser validado no banco de dados. Caso corretos ele será direcionado a página inicial e seu carrinho (caso exista) será carregado.
- [x] O cliente, não logado, deve conseguir se cadastrar no sistema, e para isso ele deve fornecer, Nome (Com no mínimo 2 palavras e 3 letras em cada), Email (Sendo único e válido), CPF (Sendo único e válido), Endereço de Faturamento, Endereço de Entrega e Senha. Caso os dados forem válidos o sistema direcionará o cliente para a tela de login, caso não, o erro será mostrado ao cliente.
>
#### Como Cliente logado
- [x] O cliente, logado, será permitido a alteração de nome, data de nascimento, gênero e senha.
- [x] O cliente, logado, será permitido a adição de novos endereços de entrega
- [x] O cliente, logado, possuindo mais de um endereço de entregas adicionados, poderá escolher qual deles será o padrão.
- [x] O cliente, logado, poderá sair do sistema, com isso seus dados armazenados na sessão serão limpos.
- [x] O cliente, logado, ao clicar em finalizar compra no carrinho, deve ser enviado para a tela de checkout. Caso o cliente não esteja logado, ele deverá ser enviado para a tela de login.
- [x] O cliente, logado, poderá escolher entre seus endereços de entrega adicionados, e se necessário, adicionar um novo
- [x] O cliente, logado, poderá escolher entre, pelo menos, duas formas de pagamento, sendo elas boleto e cartão. Ao escolher cartão deverá preencher todos os campos.
- [x] O cliente, logado, poderá visualizar o resumo do seu pedido, com todos os produtos, os valores unitários, quantidades, valores totais, frete, total geral, endereço de entrega, forma de pagamento e apresentar o botão de concluir compra e voltar.
- [x] Ao finalizar a compra, o mesmo deve ser criado no banco de dados com o status de “aguardando pagamento”. Junto a isso, deverá ser gerado o número do pedido, e exibido para o cliente.
- [x] O cliente, logado, deve ter um item de menu para poder visualizar os pedidos criados. Esse menu deve listar todos os pedidos informando o número, data, valor total, status e botão de mais detalhes.
- [x] O cliente poderá visualizar os detalhes dos itens pedido, o endereço e forma pagamento do pedido.
>
### Requisitos Não-Funcionais
- [x] Desempenho
    - Filtragem por nome: A listagem de usuários deve suportar filtragem eficiente por nome para evitar sobrecarga ao sistema quando houver muitos usuários cadastrados.
    - Paginação e Busca Eficiente: Paginação e Busca eficiente no banco de dados para evitar possíveis sobrecargas de vários resultados
    - Usuários Simultâneos: O sistema deve permitir o acesso de no mínimo 10 usuários de forma simultânea.
- [x] Usabilidade
    - Interface amigável: O sistema deve evitar direcionamentos desnecessários, sempre optando por validações e alterações de estados via pop-up.
    - Acessibilidade: O sistema deve ser acessível para pessoas com deficiência ou dificuldade visual.
- [x] Segurança
    - Autenticação: O sistema deve implementar autenticação segura, garantindo que senhas sejam armazenadas de forma criptografada e que a autenticação utilize práticas recomendadas, como o uso de tokens.
- [x] Portabilidade
    - Suporte a navegadores: O sistema deve ser acessível e funcional em navegadores modernos, incluindo Chrome, Firefox, Safari e Edge.
    - Suporte a plataformas: O sistema deve ser desenvolvido para funcionar Suporte aem diferentes sistemas operacionais, como plataformas Windows, macOS e Linux, sem necessidade de ajustes significativos.
- [x] Manutenção
    - Facilidade de Atualizações: O sistema deve ser estruturado de forma a permitir atualizações e manutenções regulares com impacto mínimo na disponibilidade do serviço.
    - Documentação: A documentação do sistema deve ser completa e atualizada, facilitando a manutenção e a capacitação de novos desenvolvedores.
## ✅ Testes 
### Requisitos de Sistema

#### Exemplos:
##### Requisitos Funcionais
- Login do Usuário: Usuário faz login com e-mail e senha. Acesso liberado se dados forem corretos.
- Cadastrar produtos: Administrador cadastra produtos com nome, descrição, preço, estoque e imagens.
##### Requisitos Não Funcionais
- Desempenho: Filtragem, paginação eficiente e suporte para 10 usuários simultâneos.
- Usabilidade: Interface amigável com pop-ups e acessibilidade para deficientes visuais.

### Estratégia de teste
#### Níveis de teste

**Nome do Teste**       | **Técnicas**    | **Descrição**                                                                                 |
-------------------------|-----------------|---------------------------------------------------------------------------------------------|
Teste Unitário          | Caixa Branca    | Utilização do JUnit para testar individualmente as funções e métodos do sistema, garantindo que cada unidade de código funcione conforme esperado. |
Teste de Integração     | Caixa Preta     | Uso do GitHub para gerenciar Branch de integração, testando a interação entre diferentes componentes do sistema. Realizando revisões de código para garantir a qualidade e segurança de cada nova integração ao sistema. |
Teste de Sistema        | Caixa Preta     | Testando Funcionalidades.                                                         |
Teste de Aceitação      | Caixa Preta     | Feedback do professor.                                                                      |

#### Tipos de teste

| **Tipo do Teste**   | **Técnicas** | 
|---------------------|--------------| 
| Desempenho          | JMeter       |
| Usabilidade         | LightHouse   |
| Portabilidade       | Teste manual |

### Ambiente de teste

| **Recurso**                 | **Descrição**                                                                 |
|-----------------------------|------------------------------------------------------------------------------|
| **Computador cliente**      | Desktop ou laptop                                                            |
| **Mobile**                  | Não Aplicável                                                                |
| **Servidor de aplicação**   | Servidor embutido do Spring Boot (Tomcat)                                    |
| **Servidor de banco de dados** | H2 DATABASE                                                               |
| **Criação de relatórios**   | Ferramenta de geração de relatórios de testes (ex: JUnit, TestNG)            |
| **Browser cliente**         | Google Chrome                                                                |
| **SO cliente**              | Sistema Operacional Windows                                                  |
| **JDK**                     | Java Development Kit Oracle 21                                               |

## 🧑‍💻 Integrantes

| <a href="https://github.com/RogerSouza1"><img src="https://github.com/user-attachments/assets/c548756f-271a-4042-a7ef-f23e4cab25b5" height="120"></a> | <a href="https://github.com/carladfb"><img src="https://github.com/user-attachments/assets/a0e6e602-f915-4aae-a96d-254efb45ddfe" height="120"></a> | <a href="https://github.com/Pelifefe"><img src="https://github.com/user-attachments/assets/13b37fca-018d-4762-a147-14a713f90449" height="120"></a> | <a href="https://github.com/GeorgiaLSousa"><img src="https://github.com/user-attachments/assets/4483a206-e2be-4af2-a073-1c3691e7f5d5" height="120"></a> | <a href="https://github.com/heloysasa"><img src="https://github.com/user-attachments/assets/391317fe-98fc-4e75-aeb0-8d671fc38188" height="120"></a> |
|:--------------------------------------------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------:|
| **[RogerSouza1](https://github.com/RogerSouza1)**                                                                                               | **[carladfb](https://github.com/carladfb)**                                                                                                    | **[Pelifefe](https://github.com/Pelifefe)**                                                                                           | **[GeorgiaLSouza](https://github.com/GeorgiaLSousa)**                                                                                         | **[heloysasa](https://github.com/heloysasa)**                                                                                         |
