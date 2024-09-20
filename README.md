# AnotaAi - Projeto de Gerenciamento de Notas

## Visão Geral

**AnotaAi** é um aplicativo mobile de gerenciamento de notas desenvolvido com **Jetpack Compose** e **Room (SQLite)** para persistência de dados. O projeto tem como objetivo simplificar o processo de criar, editar e armazenar notas, oferecendo uma interface amigável e moderna, que segue as diretrizes do **Material Design**.

O aplicativo permite que os usuários gerenciem suas anotações de maneira prática e eficiente, mesmo em modo offline, graças ao uso de banco de dados local.

## Funcionamento Negocial

### Fluxo de Uso

1. **Criação de Notas**: 
   - O usuário pode criar novas notas com um título e conteúdo.
   - Há validação em tempo real para garantir que o título não exceda **60 caracteres** e o conteúdo, **255 caracteres**.
   - Não é permitido salvar campos em branco.

2. **Listagem de Notas**:
   - A tela inicial exibe uma lista com todas as notas cadastradas.
   - Se não houver notas, uma mensagem personalizada e uma imagem são exibidas.
   - Um **botão de ação flutuante** permite adicionar novas notas.

3. **Edição de Notas**:
   - O usuário pode selecionar uma nota para editar.
   - A edição carrega os dados existentes nos campos, que podem ser alterados e salvos.
   - A validação também é aplicada ao editar.

4. **Visualização de Detalhes**:
   - As notas podem ser visualizadas em detalhe, com o título e conteúdo completo.
   - Há a opção de editar diretamente a partir da tela de detalhes.

5. **Exclusão de Notas**:
   - Cada nota na lista tem um ícone de **lixeira**, que permite excluir a nota.
   - A exclusão solicita confirmação do usuário para evitar acidentes.

### Funcionalidades Técnicas

- **Persistência de Dados Local**: O aplicativo utiliza **Room**, um ORM que facilita o armazenamento local usando SQLite, garantindo que as notas fiquem disponíveis mesmo sem internet.
- **Navegação Dinâmica**: O sistema de navegação do **Jetpack Compose** permite transições suaves entre as telas de criação, edição, listagem e detalhes.
- **Validações de Formulário**: Há validações em tempo real que asseguram o correto preenchimento dos campos de título e conteúdo.
- **UI Dinâmica**: A interface muda de acordo com a quantidade de notas cadastradas, proporcionando uma experiência mais personalizada.

## Como Funciona Negocialmente

O **AnotaAi** é direcionado para usuários que buscam um aplicativo simples e eficaz para gerenciar suas notas diárias, seja para fins pessoais ou profissionais. Aqui estão os principais benefícios:

- **Eficiência**: O aplicativo permite a criação, edição e exclusão de notas de forma rápida e direta.
- **Segurança de Dados**: Todas as informações são armazenadas localmente no dispositivo, sem necessidade de conexão com a internet.
- **Usabilidade Simples**: Com um design claro e intuitivo, o usuário não se sente sobrecarregado com opções complexas.

### Modelos de Negócio

O aplicativo pode ser usado de diferentes formas:

- **Ferramenta pessoal de produtividade**: Ideal para anotações rápidas, listas de tarefas, lembretes diários e ideias.
- **Plataforma de trabalho**: Pequenos negócios ou freelancers podem utilizá-lo para organizar informações, de forma prática e acessível, sem a necessidade de soluções mais complexas.

## Tecnologias Utilizadas

- **Jetpack Compose**: Para a construção de interfaces declarativas modernas no Android.
- **Room (SQLite)**: Para a persistência local dos dados, utilizando o banco de dados SQLite.
- **Material Design 3**: Para garantir uma interface de usuário atrativa e funcional, seguindo as diretrizes da Google.

---

Esse projeto busca oferecer uma solução eficiente e simples para gerenciamento de notas, focada em praticidade e segurança, sem depender de conexões externas para funcionar.
