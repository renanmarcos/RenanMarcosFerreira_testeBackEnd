# Criado por Renan Marcos Ferreira

* É necessário utilizar o driver: mysql-connector-java-5.1.46

* O sistema foi criado e testado utilizando a IntelliJ IDEA em conjunto com o
Apache + MySQL (com o XAMPP).

* O sistema cria automaticamente o banco de dados, as tabelas e insere dados automáticos
ao se criar uma instância da classe "BancoDeDados" e definir  no construtor
uma quantidade de registros (que pode variar entre 2701 a 8999).

* Para saber a média do saldo dos clientes, basta chamar o método "mediaDoValorTotal()"
da instância de classe "BancoDeDados". Caso você crie várias instâncias da classe,
o banco não será recriado a todo momento.

* As configurações do banco se encontra na classe "BancoDeDados" e tem os
valores iniciais: 
    
    usuário: root
    
    servidor: localhost

* A senha é inexistente. Configurações testadas com uma instalação normal do XAMPP, sem
ter alterado nada.

## Para ver o sistema em ação, é só rodar a classe principal. 