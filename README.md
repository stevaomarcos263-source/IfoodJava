# 🍽️ FoodJava — Sistema de Pedidos para Restaurante

> Projeto base para o **Projeto Final** da disciplina de **Programação Orientada a Objetos (POO)**  
> Curso Superior de Análise e Desenvolvimento de Sistemas — IFPB Campus Monteiro

---

## 👥 Equipe

| Nome                          | Matrícula    |
|-------------------------------|--------------|
| José Marcos de Oliveira Silva | 202525020002 |

---

## 🛠️ Tecnologias Utilizadas

O projeto foi desenvolvido explorando os conceitos de POO, interface gráfica e persistência em arquivos utilizando as seguintes tecnologias:

* **Java 25.0.3**
* **JavaFX** (Interface Gráfica)
* **Maven** (Gerenciador de Dependências)
* **Gson / JSON** (Persistência e manipulação de dados)
* **Git & GitHub** (Controle de versão)

---

## 🚀 Funcionalidades do Sistema

### Gerente
* Cadastra o restaurante;
* Importa cardápio através de arquivos estruturados;
* Edita itens do cardápio existente;
* Acesso completo à lista de todos os pedidos recebidos;
* Cancela e avança o status do pedido de acordo com as regras de negócio;
* Acesso ao painel de lucro diário;
* Autonomia para ativar e desativar itens específicos do cardápio.

### Cliente
* Cadastro e autenticação no sistema;
* Visualização dinâmica do cardápio disponível;
* Acesso ao histórico pessoal de pedidos realizados;
* Realização de novos pedidos;
* Acompanhamento do status do pedido em tempo real;
* Opção de cancelar pedidos que ainda não foram confirmados pelo gerente.

---

## ✅ Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:
* **Java Development Kit (JDK) 21** ou superior (Compilado usando Java 25)  
  Verifique com: `java -version`  
  Download: [Adoptium](https://adoptium.net)
* **Maven 3.8+** instalado e disponível no PATH  
  Verifique com: `mvn -version`  
  Download: [Apache Maven](https://maven.apache.org/download.cgi)

---

## 💻 Como Executar o Projeto

### 1. Clone o repositório
Clonar
```bash
git clone https://github.com/stevaomarcos263-source/IfoodJava.git
```
Acessar pasta
```bash
cd FoodJava
```
```bash
mvn javafx:run
```

## 🗂️ Estrutura do Projeto

```text
foodjava/
├── src/
│   └── main/
│       ├── java/
│       │   └── br/edu/ifpb/ads/foodjava/
│       │       ├── MainApp.java           ← ponto de entrada da aplicação
│       │       ├── model/                 ← entidades do domínio (Cliente, Pedido, etc.)
│       │       ├── view/                  ← classes Java que constroem telas via código
│       │       │                            (alternativa ao FXML quando a tela é gerada
│       │       │                             programaticamente, sem arquivo externo)
│       │       ├── controller/            ← controladores das telas
│       │       ├── repository/            ← leitura e escrita de arquivos JSON
│       │       ├── exception/             ← exceções personalizadas
│       │       └── util/                  ← utilitários (validações, hash, etc.)
│       └── resources/
│           ├── fxml/                      ← arquivos .fxml criados no SceneBuilder
│           │                                (definem o layout visual das telas de forma
│           │                                 declarativa, separada do código Java)
│           ├── css/                       ← folhas de estilo
│           ├── images/                    ← imagens estáticas do sistema
│           └── data/                      ← arquivos JSON gerados em runtime
├── exemplos-json/
│   ├── cardapio_exemplo.json              ← exemplo de importação de cardápio
│   └── imagens/                           ← imagens referenciadas no JSON de exemplo
├── pom.xml                                ← configuração Maven
└── README.md```

