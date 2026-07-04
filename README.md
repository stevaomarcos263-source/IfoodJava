# 🍽️ FoodJava — Sistema de Pedidos para Restaurante

> Projeto base para o **Projeto Final** da disciplina de **Programação Orientada a Objetos**  
> Curso Superior de Análise e Desenvolvimento de Sistemas — IFPB Campus Monteiro

---

## 👥 Equipe

| Nome                          | Matrícula    |
|-------------------------------|--------------|
| José Marcos de Oliveira Silva | 202525020002 |

---

## ✅ Pré-requisitos

- **Java Development Kit (JDK) 21** ou superior  
  Verifique com: `java -version`  
  Download: https://adoptium.net

- **Maven 3.8+** instalado e disponível no PATH  
  Verifique com: `mvn -version`  
  Download: https://maven.apache.org/download.cgi

---

## 🚀 Como Executar

### 1. Clone o repositório

```bash
git clone https://github.com/stevaomarcos263-source/IfoodJava.git
cd FoodJava
```

### 2a. Executar pelo Terminal

```bash
mvn javafx:run
```

> Execute sempre a partir da **raiz do projeto** (onde está o `pom.xml`).

---
```bash
mvn javafx:run
```

Alternativamente, use o painel **Maven** na barra lateral → expanda o projeto → **Plugins → javafx → javafx:run** (duplo clique).

---

## 🗂️ Estrutura do Projeto

```
foodjava/
├── src/
│   └── main/
│       ├── java/
│       │   └── br/edu/ifpb/ads/foodjava/
│       │       ├── MainApp.java          ← ponto de entrada da aplicação
│       │       ├── model/                ← entidades do domínio (Cliente, Pedido, etc.)
│       │       ├── view/                 ← classes Java que constroem telas via código
│       │       │                            (alternativa ao FXML quando a tela é gerada
│       │       │                             programaticamente, sem arquivo externo)
│       │       ├── controller/           ← controladores das telas
│       │       ├── repository/           ← leitura e escrita de arquivos JSON
│       │       ├── exception/            ← exceções personalizadas
│       │       └── util/                 ← utilitários (validações, hash, etc.)
│       └── resources/
│           ├── fxml/                     ← arquivos .fxml criados no SceneBuilder
│           │                                (definem o layout visual das telas de forma
│           │                                 declarativa, separada do código Java)
│           ├── css/                      ← folhas de estilo
│           ├── images/                   ← imagens estáticas do sistema
│           └── data/                     ← arquivos JSON gerados em runtime
├── exemplos-json/
│   ├── cardapio_exemplo.json             ← exemplo de importação de cardápio
│   └── imagens/                          ← imagens referenciadas no JSON de exemplo
├── pom.xml                               ← configuração Maven
└── README.md
```

## 📄 Documentação Completa

A descrição detalhada do projeto — requisitos funcionais, regras de negócio, exceções, arquitetura e critérios de avaliação — está disponível no PDF entregue pelo professor no Google Classroom.

---

*Projeto acadêmico — IFPB Campus Monteiro · 2026.1*
