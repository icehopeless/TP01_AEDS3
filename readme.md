# Relatório 
# TP1 — AEDs III — TERMINALME 1.0: Relacionamento 1:N

Trabalho prático 1 de Algoritmos e Estruturas de Dados III (PUC Minas, 2026.2).

## Participantes

| Nome | GitHub |
|---|---|
| Gabriel Agostinho | [icehopeless](https://github.com/icehopeless) |
| Isaque de Sousa | [Ws49](https://github.com/Ws49) |
| Kaio Ferreira Dias | [KaioT2](https://github.com/KaioT2) |
| Natan França | [Nt-0411](https://github.com/Nt-0411) |

## Descrição do sistema

O **TERMINALME 1.0** (*Terminal Question & Answer System*) é o nome que o grupo deu ao sistema
pedido no enunciado como AJUDA AÍ 1.0. É uma versão simplificada do StackOverflow, feita em Java
para o terminal. Neste TP1 o sistema cobre duas entidades:

- **Usuários**, que se cadastram, entram com email e senha, recuperam a senha por uma pergunta
  secreta e alteram ou apagam os próprios dados.
- **Perguntas**, que pertencem a um usuário. Cada usuário inclui, lista, altera e arquiva as
  próprias perguntas. As respostas e os votos ficam para o TP2, e as opções correspondentes do
  menu mostram "Opção em construção!".
  
### Apresentação do Sistema
Link do vídeo: https://youtu.be/DJMiSdkOcdA 

### Organização do código

O código segue o padrão **MVC**, com um pacote para cada camada:

| Pacote | Papel |
|---|---|
| `model` | as entidades (`User`, `Pergunta`) e o acesso aos arquivos (`model.AcessDataBase`) |
| `control` | o `Controller`, que valida as regras de negócio e liga a visão ao modelo, e o `SingletonUser`, que guarda o usuário logado |
| `view` | as telas do terminal |
| `Security` | o hash SHA-256 da senha |

As telas usam o padrão **State**:
- cada tela é uma subclasse de `StateView` com um método `draw()`;
- o `ContextView` desenha a tela atual e passa para a próxima quando a tela chama `changeState(...)`;
- o `SingletonPWD` guarda o caminho de navegação mostrado no topo (ex.: `~Início > Minha Área > Minhas perguntas`).

### Persistência

As duas entidades são gravadas pela classe genérica `Arquivo<T>` do professor. O programa cria os
arquivos na pasta `dados/`, a partir da pasta onde é executado.

- **Cabeçalho do arquivo:** 12 bytes, com o último ID usado (`int`) e o endereço do primeiro espaço
  livre (`long`).
- **Cada registro:** lápide (`boolean`), tamanho (`short`) e os bytes da entidade.
- **Índice direto:** uma Tabela Hash Extensível de pares (ID; endereço), usada por `read`, `update`
  e `delete`.
- **Espaços livres:** registros excluídos entram numa lista ordenada por tamanho e são
  reaproveitados quando o desperdício fica abaixo de 50 bytes.

| Arquivo | Conteúdo |
|---|---|
| `dados/users/arquivo.db` | registros dos usuários |
| `dados/users/indiceID.diretorio.db`, `indiceID.cestos.db` | índice direto dos usuários (hash extensível) |
| `dados/users/indiceEmail.d.db`, `indiceEmail.c.db` | índice indireto por email (hash extensível) |
| `dados/perguntas/arquivo.db` | registros das perguntas |
| `dados/perguntas/indiceID.diretorio.db`, `indiceID.cestos.db` | índice direto das perguntas (hash extensível) |
| `dados/perguntas/relUsuarioPergunta.db` | Árvore B+ do relacionamento 1:N, com pares (idUsuario; idPergunta) |

### Entidades

**Usuário (`User`)**

| Atributo | Tipo | Observação |
|---|---|---|
| `idUsuario` | `int` | gerado pelo `Arquivo`, sequencial, nunca aparece na tela |
| `nome` | `String` | |
| `email` | `String` | é o login e é único; tem índice indireto na hash extensível |
| `hashSenha` | `String` | SHA-256 da senha |
| `perguntaSecreta` | `String` | usada na recuperação da senha |
| `hashRespostaSecreta` | `int` | hash da resposta secreta |

**Pergunta (`Pergunta`)**

| Atributo | Tipo | Observação |
|---|---|---|
| `id` | `int` | gerado pelo `Arquivo`, sequencial e único no sistema todo |
| `idUsuario` | `int` | chave estrangeira para o autor; não existe `setIdUsuario`, então nunca muda |
| `criacao` | `long` | data e hora da criação, em milissegundos |
| `alteracao` | `long` | data e hora da última alteração, atualizada a cada alteração e no arquivamento |
| `nota` | `short` | soma dos votos (TP2); começa em 0 |
| `pergunta` | `String` | o texto, com até 4.000 caracteres e várias linhas |
| `palavrasChave` | `String` | termos separados por `;`, com até 1.000 caracteres |
| `ativa` | `boolean` | `false` quando a pergunta está arquivada |

### Como compilar e executar

Na raiz do repositório:

```
javac -encoding UTF-8 -d bin $(find src -name "*.java")
java -cp bin Main
```

No Windows, pelo PowerShell:

```
javac -encoding UTF-8 -d bin (Get-ChildItem -Recurse src -Filter *.java).FullName
java "-Dstdout.encoding=UTF-8" -cp bin Main
```

No Windows, pelo Prompt de Comando:

```
chcp 65001
javac -encoding UTF-8 -d bin src\*.java src\Security\*.java src\control\*.java src\model\*.java src\model\AcessDataBase\*.java src\view\*.java
java -Dstdout.encoding=UTF-8 -cp bin Main
```

O terminal precisa estar em UTF-8 para os acentos e as bordas das caixas aparecerem certos. A
leitura do teclado não depende dessa configuração: o `StateView` cria o `Scanner` com a
codificação que o próprio console informa, como explica a seção "Operações especiais".

## Capturas das telas

O texto digitado pelo usuário aparece depois de cada `TerminalMe >`.

### Tela inicial

```
  ████████╗███████╗██████╗ ███╗   ███╗██╗███╗   ██╗ █████╗ ██╗     ███╗   ███╗███████╗
  ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║██║████╗  ██║██╔══██╗██║     ████╗ ████║██╔════╝
     ██║   █████╗  ██████╔╝██╔████╔██║██║██╔██╗ ██║███████║██║     ██╔████╔██║█████╗
     ██║   ██╔══╝  ██╔══██╗██║╚██╔╝██║██║██║╚██╗██║██╔══██║██║     ██║╚██╔╝██║██╔══╝
     ██║   ███████╗██║  ██║██║ ╚═╝ ██║██║██║ ╚████║██║  ██║███████╗██║ ╚═╝ ██║███████╗
     ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝     ╚═╝╚══════╝

                         Terminal Question & Answer System
                                Version 1.0.0

    ┌──────────────────────────────────────────────────────────────┐
    │                        MAIN MENU                             │
    ├──────────────────────────────────────────────────────────────┤
    │                                                              │
    │   [0]  LOGIN                                                 │
    │   [1]  CRIAR CONTA                                           │
    │   [2]  SAIR                                                  │
    │                                                              │
    └──────────────────────────────────────────────────────────────┘

 TerminalMe > 1
```

### Criar conta

```
Deseja continuar (S/N) : S
 Digite seu email:
 TerminalMe > ana@exemplo.com
 Digite seu nome:
 TerminalMe > Ana Souza
 Digite sua senha:
 TerminalMe > senha123
 Digite sua pergunta secreta:
 TerminalMe > Nome do primeiro pet
 Digite sua resposta secreta
 TerminalMe > Rex
```

### Login

```
Deseja continuar (S/N) : S
 Digite seu email:
 TerminalMe > ana@exemplo.com
 Digite sua senha:
 TerminalMe > senha123
```

### Login com senha errada

```
Deseja continuar (S/N) : S
 Digite seu email:
 TerminalMe > ana@exemplo.com
 Digite sua senha:
 TerminalMe > senha errada
Email ou senha incorretos!
   [0]  Tentar novamente
   [1]  Recuperar senha
   [2]  Retornar ao menu inicial
 TerminalMe > 1
Nome do primeiro pet ?
rex
 Digite a nova senha:
 TerminalMe > outrasenha
```

A resposta secreta é aceita com qualquer acentuação e qualquer caixa, porque o hash é gerado a
partir do texto já normalizado. São três tentativas; esgotadas, o sistema volta ao menu inicial.

### Menu do usuário logado

```
┌───────┐
│~Início│
└───────┘
┌──────────────────────────────────────────────────────────────┐
│                                                              │
│   [0]  Minha Área                                            │
│   [1]  Buscar Perguntas                                      │
│                                                              │
│   [2]  Retornar                                              │
│                                                              │
└──────────────────────────────────────────────────────────────┘

 TerminalMe > 0
```

### Minha Área

```
┌────────────────────┐
│~Início > Minha Área│
└────────────────────┘
┌──────────────────────────────────────────────────────────────┐
│                                                              │
│   [0]  Meus dados                                            │
│   [1]  Minhas perguntas                                      │
│   [2]  Minhas respostas                                      │
│   [3]  Meus votos                                            │
│                                                              │
│   [4]  Retornar ao menu anterior                             │
│                                                              │
└──────────────────────────────────────────────────────────────┘

 TerminalMe > 0
```

### Meus dados

```
┌─────────────────────────────────┐
│~Início > Minha Área > Meus dados│
└─────────────────────────────────┘
┌──────────────────────────────────────────────────────────────┐
│                                                              │
 Nome : Ana Souza
 Email : ana@exemplo.com
 Pergunta : Nome do primeiro pet
│                                                              │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│                                                              │
│   [0]  Alterar nome                                          │
│   [1]  Alterar email                                         │
│   [2]  Alterar senha                                         │
│   [3]  Alterar pergunta e resposta de recuperação da senha   │
│   [4]  Apagar Conta                                          │
│                                                              │
│   [5]  Retornar ao menu anterior                             │
│                                                              │
└──────────────────────────────────────────────────────────────┘

 TerminalMe > 5
```

### Minhas perguntas

```
┌───────────────────────────────────────┐
│~Início > Minha Área > Minhas perguntas│
└───────────────────────────────────────┘
┌──────────────────────────────────────────────────────────────┐
│                                                              │
│   [0]  Listar                                                │
│   [1]  Incluir                                               │
│   [2]  Alterar                                               │
│   [3]  Arquivar                                              │
│                                                              │
│   [4]  Retornar ao menu anterior                             │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

### Incluir pergunta

O texto pode ter várias linhas, e uma linha vazia termina o texto.

```
 TerminalMe > 1
 Digite a pergunta (linha vazia para terminar):
 TerminalMe > Como funciona a árvore B+ usada no relacionamento 1:N?
 TerminalMe > Ela aceita chaves repetidas?
 TerminalMe >
 Digite as palavras chave separadas por ; :
 TerminalMe > arvore b+; indice; 1:N
Pergunta incluída com sucesso!
 Pressione Enter para continuar...
```

### Alterar pergunta

A lista numera as perguntas sem mostrar o ID. Enter vazio mantém o valor atual.

```
 TerminalMe > 2
 (1) 19/09/2026 14:12
 Como funciona a árvore B+ usada no relacionamento 1:N?
 Ela aceita chaves repetidas?
 Palavras chave: arvore b+; indice; 1:N

 (2) 19/09/2026 14:12
 Qual a diferença entre índice direto e indireto?
 Palavras chave: indice; hash

 (3) 19/09/2026 14:12
 Por que o registro guarda uma lápide?
 Palavras chave: arquivo; lapide

 Digite o número da pergunta (0 para cancelar):
 TerminalMe > 2
 Texto atual:
 Qual a diferença entre índice direto e indireto?
 Digite o novo texto (linha vazia para terminar, só Enter mantém o atual):
 TerminalMe >
 Palavras chave atuais: indice; hash
 Digite as novas palavras chave separadas por ; (só Enter mantém as atuais):
 TerminalMe > indice direto; indice indireto; hash extensivel
Pergunta alterada com sucesso!
 Pressione Enter para continuar...
```

### Arquivar pergunta

```
 TerminalMe > 3
 (1) 19/09/2026 14:12
 Como funciona a árvore B+ usada no relacionamento 1:N?
 Ela aceita chaves repetidas?
 Palavras chave: arvore b+; indice; 1:N

 (2) 19/09/2026 14:12
 Qual a diferença entre índice direto e indireto?
 Palavras chave: indice direto; indice indireto; hash extensivel

 (3) 19/09/2026 14:12
 Por que o registro guarda uma lápide?
 Palavras chave: arquivo; lapide

 Digite o número da pergunta (0 para cancelar):
 TerminalMe > 3
Arquivar é definitivo, a pergunta não poderá mais ser alterada. Tem certeza (s/n) ?
s
Pergunta arquivada com sucesso!
 Pressione Enter para continuar...
```

### Listar perguntas

O estado só aparece quando a pergunta está arquivada.

```
 TerminalMe > 0
 (1) 19/09/2026 14:12
 Como funciona a árvore B+ usada no relacionamento 1:N?
 Ela aceita chaves repetidas?
 Palavras chave: arvore b+; indice; 1:N

 (2) 19/09/2026 14:12
 Qual a diferença entre índice direto e indireto?
 Palavras chave: indice direto; indice indireto; hash extensivel

 (3) ARQUIVADA 19/09/2026 14:12
 Por que o registro guarda uma lápide?
 Palavras chave: arquivo; lapide

 Pressione Enter para continuar...
```

## Classes e métodos

### Classes criadas pelo grupo

**`Main`**
- `main(String[])`: cria o `ArquivoUser`, o `ArquivoPergunta`, o `ManagerEntities`, o `ContextView` com a `StateInicial` e o `Controller`, que inicia as telas.

**`Security.Crypt`**
- `getSHA256(String)`: devolve o hash SHA-256 do texto em hexadecimal. É usado na senha.

**`control.Controller`**, as regras de negócio:

| Método | O que faz |
|---|---|
| `addUser(User)` | valida o email e grava o usuário |
| `updateUser(User)` | valida o email e regrava o usuário |
| `updateSenhaUser(String email, String hashSenha)` | troca a senha na recuperação |
| `deleteUser(int id)` | apaga as perguntas do usuário (em cascata) e depois o usuário |
| `findUser(String email)` | diz se já existe usuário com o email |
| `readUser(String email, String hashSenha)` | faz o login, conferindo email e senha |
| `recoveryQuestion(String email)` | devolve a pergunta secreta e o hash da resposta |
| `normalizeResponse(String)` | tira os espaços das pontas, remove os acentos e passa para minúsculas |
| `hashRespostaSecreta(String)` | normaliza a resposta e devolve o hash dela; é o único ponto do sistema que gera esse valor |
| `listarMinhasPerguntas()` | devolve as perguntas do usuário logado, pela Árvore B+ |
| `addPergunta(String texto, String palavras)` | valida (texto e palavras não vazios, até 4.000 e 1.000 caracteres, usuário existente) e grava a pergunta ligada ao usuário logado |
| `updatePergunta(Pergunta, String texto, String palavras)` | confere o dono e se a pergunta está ativa, mantém o campo deixado em branco, atualiza a data de alteração e regrava |
| `arquivarPergunta(Pergunta)` | confere o dono e se a pergunta ainda está ativa, marca como arquivada e regrava |

**`control.SingletonUser`**
- `setUser(User)` e `getInstanceUser()`: guardam e devolvem o usuário logado.

**`model.Registro`** (interface)
- `setId`, `getId`, `serialize` e `deserialize`: o contrato que o `Arquivo<T>` exige das entidades.

**`model.User`**
- os construtores, os getters e setters dos atributos, e `serialize()` e `deserialize(byte[])`.

**`model.Pergunta`**
- `Pergunta(int idUsuario, String pergunta, String palavrasChave)`: preenche a criação e a alteração com o momento atual, a nota com 0 e `ativa` com `true`;
- os getters, o `setId` exigido pelo `Arquivo`, e os setters do texto, das palavras-chave e da
  data de alteração. Não há setter para `idUsuario`, `criacao` nem `ativa`;
- `arquivar()`: põe `ativa = false`. Não existe o caminho de volta;
- `getCriacaoFormatada()` e `getAlteracaoFormatada()`: a data no formato `dd/MM/yyyy HH:mm`;
- `serialize()` e `deserialize(byte[])`.

**`model.ManagerEntities`**
- `getArqUsers()` e `getArqPergunta()`: dão ao controle o acesso aos dois arquivos.

**`model.AcessDataBase.ArquivoUser`** (estende `Arquivo<User>`)

| Método | O que faz |
|---|---|
| `create(User)` | recusa email repetido, confere se o email cabe no par de 64 bytes, e só então grava o registro e inclui o par (email; id) na hash extensível |
| `read(String email)` | busca o usuário pelo índice indireto de email |
| `update(User)` | quando o email muda, confere que o novo está livre, monta o par novo, inclui no índice e só então apaga o antigo; o registro é regravado depois, então uma falha no índice não deixa a conta inacessível |
| `delete(int id)` e `delete(String email)` | apagam o usuário e o par do índice de email |

**`model.AcessDataBase.ArquivoPergunta`** (estende `Arquivo<Pergunta>`)

| Método | O que faz |
|---|---|
| `create(Pergunta)` | grava a pergunta e inclui o par (idUsuario; idPergunta) na Árvore B+ |
| `readByUser(int idUsuario)` | busca na Árvore B+ todos os pares do usuário e lê cada pergunta pelo índice direto |
| `delete(int id)` | remove o par da Árvore B+ e depois o registro |

O `update` é herdado do `Arquivo` sem alteração. O ID e o `idUsuario` de uma pergunta nunca mudam,
então o par da árvore continua válido mesmo quando o registro muda de lugar no arquivo.

**`model.AcessDataBase.ParIDEMAIL`**
- é o par (email; id) da hash extensível, com tamanho fixo de 64 bytes. A chave é `Math.abs(email.hashCode())`, e emails acima de 60 bytes são recusados.

**Pacote `view`:**

| Classe | Papel |
|---|---|
| `ContextView` | `start()` e `render()` desenham a tela atual; `changeState(StateView)` define a próxima; `reportFail(Exception)` limpa a tela, mostra a mensagem do erro e espera um segundo antes de redesenhar |
| `StateView` | classe base das telas: `draw()` (abstrato), `clearScreen()`, `lerOpcao()` (lê a opção do menu sem derrubar o programa se vier uma letra), `avisarEmConstrucao()` e `getTheSameScan()`, que cria o `Scanner` único na codificação do console |
| `StateInicial` | menu de acesso: login, criar conta e sair |
| `StateCreateAccount` | cadastro de usuário; um email já cadastrado devolve a própria tela, para o usuário informar outro |
| `StateLogin` | login, mensagem de erro com as opções de tentar novamente, recuperar senha ou retornar, e a recuperação pela pergunta secreta em até três tentativas |
| `StateMain` | menu do usuário logado |
| `StateAreaUser` | Minha Área |
| `StateDataUser` | Meus dados: `getDataUser()` monta o quadro com os dados, e `draw()` trata a alteração e a exclusão da conta. Na troca de email, monta um `User` novo e só substitui o usuário do `SingletonUser` depois que a gravação dá certo |
| `StateQuestionsUser` | Minhas perguntas: `listar()`, `incluir()`, `alterar()` e `arquivar()`, com os auxiliares `mostrarLista()`, `escolherPergunta()`, `lerTexto()` (várias linhas), `lerNumero()` e `pausar()` |
| `SingletonPWD` | `push(String)`, `pop()` e `getPwd()`: o caminho de navegação |
| `Layouts` e `Color` | o desenho dos menus e as cores do terminal |

### Classes do professor usadas sem alteração de lógica

- **`Arquivo<T>`:** o CRUD genérico com lápide, índice direto e reaproveitamento de espaço. Os métodos são `create`, `read`, `readAll`, `update`, `delete`, `insertFreeSlot`, `findFreeSlot`, `reorganizar` e `close`.
- **`HashExtensivel<T>`**, com a interface `RegistroHashExtensivel<T>` e o par `ParIDEndereco`.
- **`ArvoreBMais<T>`**, com a interface `InterfaceArvoreBMais<T>` e o par `ParIdId`.

## Operações especiais

1. **Índice indireto por email.** O login e a busca por email usam a Tabela Hash Extensível de
   pares (email; id), e não uma leitura sequencial do arquivo.
2. **Relacionamento 1:N com Árvore B+.** Cada pergunta nova grava o par
   (idUsuario; idPergunta) na árvore, de ordem 5. Para listar as perguntas de um usuário, o
   `readByUser` busca `new ParIdId(idUsuario, -1)`. No `compareTo` do `ParIdId`, o `-1` faz a
   comparação considerar só o `idUsuario`, então a árvore devolve todos os pares desse usuário, em
   ordem de ID. O caminho inverso, da pergunta para o usuário, é o próprio atributo `idUsuario`.
3. **Chave estrangeira.** A pergunta sempre recebe o ID do usuário logado, e o `addPergunta`
   confere que ele existe antes de gravar.
4. **Exclusão em cascata.** Ao apagar a conta, o `deleteUser` apaga antes todas as perguntas do
   usuário, inclusive as arquivadas, e cada exclusão remove também o par da Árvore B+. Os filhos
   saem antes do pai, então uma falha no meio nunca deixa pergunta órfã.
5. **Arquivamento definitivo.** Não existe exclusão de pergunta: arquivar é pôr `ativa = false`.
   Não existe desarquivar, a pergunta arquivada não pode ser alterada nem arquivada de novo, e na
   listagem do autor ela aparece como `ARQUIVADA`. O arquivamento também atualiza a data de
   alteração.
6. **Número da tela ligado ao ID real.** Alterar e arquivar mostram a lista numerada (1), (2)...,
   sem ID. O número escolhido menos 1 é a posição na mesma `ArrayList` que foi mostrada, e cada
   elemento dessa lista guarda o ID real da pergunta.
7. **Alteração que muda o tamanho do registro.** Se o texto novo cabe no espaço antigo, o registro
   é regravado no mesmo lugar. Se não cabe, o antigo recebe a lápide e vai para a lista de espaços
   livres, o novo é gravado em outro lugar, e só o índice direto é atualizado.
8. **Limites de tamanho.** O tamanho do registro é gravado num `short` e não pode passar de
   65.535 bytes. Por isso o texto da pergunta vai até 4.000 caracteres e as palavras-chave até
   1.000. No pior caso, com caracteres de 3 bytes, o registro fica em cerca de 15.000 bytes.
9. **Menus que não caem.** A opção é lida como texto pelo `lerOpcao()`. Uma letra ou um número
   inválido redesenha o menu, em vez de lançar exceção ou encerrar o programa.
10. **Resposta secreta normalizada antes do hash.** O `Controller.hashRespostaSecreta` passa a
    resposta pelo `normalizeResponse`, que tira os espaços das pontas, decompõe os caracteres em
    NFD, remove os acentos e passa tudo para minúsculas. Quem cadastra "Réx" recupera a senha
    digitando "rex". O cadastro, a alteração em Meus dados e a conferência na recuperação chamam
    esse mesmo método, então os três sempre concordam. A pergunta secreta, ao contrário, é gravada
    exatamente como foi digitada, porque ela é mostrada na tela.
11. **Índice validado antes da gravação.** O arquivo de dados e o índice de email precisam contar
    a mesma história. Por isso, tanto no `create` quanto no `update` do `ArquivoUser`, tudo que pode
    falhar acontece antes de qualquer escrita: a busca do email no índice e a montagem do
    `ParIDEMAIL`, cujo construtor recusa email que não caiba nos 64 bytes do par. Na troca de email,
    o par novo entra no índice antes de o antigo ser apagado. Assim, uma operação recusada não deixa
    registro sem índice nem índice apontando para registro errado, e a conta continua acessível.
12. **Leitura do teclado na codificação do console.** O `new Scanner(System.in)` decodifica a
    entrada com a codificação padrão da JVM, que no JDK 18 em diante é UTF-8. O console do Windows,
    porém, envia os bytes em cp850, e cada acento digitado chegava ao programa como o caractere de
    substituição, indo assim para o arquivo. O `getTheSameScan()` lê a propriedade `stdin.encoding`
    (com `native.encoding` e UTF-8 como alternativas) e cria o `Scanner` com ela, então nome, texto
    da pergunta, palavras-chave e resposta secreta preservam a acentuação em qualquer terminal.

## Onde e como usamos IA

| Onde | O que a IA fez | O que o grupo fez |
|---|---|---|
| `Security.Crypt.getSHA256` | escreveu a implementação do hash SHA-256 em Java, a partir do `MessageDigest` | escolheu SHA-256 no lugar do MD5, por causa da facilidade de força bruta no MD5, e integrou a classe ao cadastro, ao login e à troca de senha |
| `StateView.getTheSameScan` | apontou que o `new Scanner(System.in)` decodifica a entrada como UTF-8 enquanto o console do Windows envia cp850, e escreveu a leitura da propriedade `stdin.encoding` | reproduziu o defeito, em que um acento digitado virava `?` dentro do arquivo, aplicou a correção e testou com nome, pergunta e resposta secreta acentuados |
| Revisão e depuração | leu o código procurando defeitos, reproduziu cada um em execução e sugeriu as correções pontuais | decidiu o que aceitar, aplicou as alterações e repetiu os testes |
| Este relatório | organizou as seções e escreveu o texto a partir do código e dos testes | revisou o conteúdo e conferiu cada afirmação contra o programa |

Os defeitos encontrados nessa revisão foram a resposta secreta que não era normalizada antes do
hash, o login inválido sem mensagem de erro, o cadastro que voltava ao menu quando o email já
existia e a ordem de escrita no índice de email, que podia deixar uma conta inacessível.

A modelagem das entidades, a estrutura de arquivos, o relacionamento 1:N com a Árvore B+, o índice
indireto por email, as telas e a divisão do código em MVC com o padrão State foram feitos pelo
grupo. Todo trecho sugerido por IA foi lido, testado e integrado manualmente, e nenhum foi aceito
sem passar pelos testes descritos no checklist.


## Checklist

**1. Há um CRUD de usuários (que estende a classe Arquivo, acrescentando Tabelas Hash Extensíveis
e Árvores B+ como índices diretos e indiretos conforme necessidade) que funciona corretamente?**

**Sim.** O `ArquivoUser` estende o `Arquivo`, que já traz o índice direto em hash extensível, e
acrescenta o índice indireto por email, também em hash extensível. As quatro operações foram
testadas:
- **criar:** recusa email repetido e recusa email que não cabe no par de 64 bytes, sem gravar nada
  no arquivo de dados nos dois casos;
- **ler:** o login busca pelo índice de email, e o `read(int)` herdado busca pelo índice de ID;
- **alterar:** nome, senha, pergunta e resposta secreta trocam sem mexer no índice de email. Na
  troca de email, o novo é procurado no índice antes de qualquer gravação. Se já pertencer a outro
  usuário, a operação é recusada com "Não é possível alocar este email" e nada muda. Se estiver
  livre, o par novo entra no índice, o antigo sai e o registro é regravado, nessa ordem;
- **excluir:** apaga o registro, o par do índice de email e, em cascata, as perguntas do usuário.

Foram testadas as duas formas de falhar na troca de email, com email já cadastrado e com email de
65 bytes: nos dois casos as contas envolvidas continuaram entrando normalmente depois da mensagem
de erro.

**2. Há um CRUD de perguntas (que estende a classe Arquivo, acrescentando Tabelas Hash Extensíveis
e Árvores B+ como índices diretos e indiretos conforme necessidade) que funciona corretamente?**

**Sim.** O `ArquivoPergunta` estende o `Arquivo`, que tem o índice direto em hash extensível, e
acrescenta a Árvore B+ como índice do relacionamento. Os testes incluíram:
- inclusão com limites de tamanho;
- listagem;
- alteração com o registro crescendo várias vezes seguidas e mudando de lugar no arquivo;
- arquivamento;
- exclusão em cascata;
- 24 perguntas de dois usuários criadas intercaladas.

Ao final de cada teste, o arquivo de dados, o índice direto e a árvore continuaram consistentes
entre si.

**3. As perguntas estão vinculadas aos usuários usando o idUsuario como chave estrangeira?**

**Sim.** Cada pergunta guarda o `idUsuario` do autor:
- ele vem do usuário logado, e o `addPergunta` confere que o usuário existe;
- o atributo não tem setter e nunca muda;
- apagar o usuário apaga as perguntas dele.

**4. Há uma árvore B+ que registre o relacionamento 1:N entre usuários e perguntas?**

**Sim.** É a `ArvoreBMais<ParIdId>` de ordem 5, no arquivo `dados/perguntas/relUsuarioPergunta.db`,
com pares (idUsuario; idPergunta). O `ArquivoPergunta` inclui o par no `create`, remove no
`delete` e usa a árvore no `readByUser`.

**5. O trabalho compila corretamente?**

**Sim.** Compila sem erros com o JDK 21 e com o JDK 25, pelos comandos da seção "Como compilar e executar".

**6. O trabalho está completo e funcionando sem erros de execução?**

**Sim.** As duas entidades estão completas dentro do escopo do TP1. Foram testados o cadastro com
email repetido e com email inválido, o login com email e senha errados, a recuperação de senha pela
pergunta secreta, a alteração de todos os dados do usuário, a exclusão da conta em cascata, e a
inclusão, a listagem, a alteração e o arquivamento de perguntas, inclusive com o registro crescendo
e mudando de lugar no arquivo. Os menus não caem com letras ou números fora da faixa, e nenhum
caminho encerra o programa sem o usuário pedir. As opções de respostas e de votos, que pertencem ao
TP2, avisam que estão em construção.

**7. O trabalho é original e não a cópia de um trabalho de outro grupo?**

**Sim.** O trabalho foi desenvolvido pelo grupo, sobre o código-base fornecido pelo professor.
