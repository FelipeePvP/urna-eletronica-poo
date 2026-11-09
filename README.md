# Urna Cidadã — simulador educativo

Projeto acadêmico de demonstração para a disciplina de Programação Orientada a Objetos. **Não é uma urna oficial e não deve ser usado em eleições reais.** Os candidatos e partidos são fictícios.

## Requisitos
- JDK 17 ou superior
- Maven 3.8 ou superior
- Internet apenas para baixar dependências na primeira compilação e para usar o Firestore

## Executar sem configurar o Firebase
1. Abra a pasta do projeto no IntelliJ IDEA, Eclipse (com suporte a Maven) ou VS Code com Java.
2. Aguarde o Maven baixar as dependências.
3. Execute `br.edu.urna.Main`, ou no terminal, na pasta do projeto, rode `mvn clean compile exec:java`.
4. Sem a variável `GOOGLE_APPLICATION_CREDENTIALS`, o programa usa um repositório em memória. Os votos **não ficam salvos depois que o aplicativo fecha**. Isso permite testar a interface sem credenciais.

## Habilitar o Firebase Cloud Firestore
1. Peça ao professor/equipe responsável autorização para criar ou usar um projeto Firebase.
2. No console Firebase, crie um projeto e habilite o **Cloud Firestore**.
3. Em Configurações do projeto > Contas de serviço, gere uma chave privada de conta de serviço, se a instituição permitir. Guarde o JSON fora do repositório.
4. Defina a variável de ambiente `GOOGLE_APPLICATION_CREDENTIALS` apontando para o caminho completo do JSON.
   - Windows PowerShell (somente na janela atual): `$env:GOOGLE_APPLICATION_CREDENTIALS="C:\caminho\seguro\service-account.json"`
   - Linux/macOS: `export GOOGLE_APPLICATION_CREDENTIALS="/caminho/seguro/service-account.json"`
5. Execute o programa novamente. Se a configuração estiver correta, ele usará o Firestore. Se não conseguir iniciar o Firebase, o programa informa o erro e volta ao modo em memória.
6. No Firestore, os documentos serão gravados na coleção `votos_simulados`. Cada documento contém cargo, código do candidato (ou BRANCO/NULO), nome fictício, horário e identificador da sessão.

### Segurança das credenciais
- **Nunca** envie o JSON de conta de serviço ao GitHub, a colegas em chat ou a ferramentas de IA.
- Não coloque a chave dentro do código-fonte.
- Para um projeto de sala de aula, use um projeto de teste, permissões mínimas possíveis e combine com o professor quem será responsável pela conta.
- O aplicativo é uma demonstração; não possui as garantias de segurança, auditoria ou sigilo de uma urna eleitoral real.

## Funcionalidades
- Votação sequencial para dois cargos fictícios.
- Seleção por número, confirmação, correção, voto em branco e voto nulo.
- Organização em classes e pacotes.
- Persistência opcional no Cloud Firestore.
- Resumo da sessão atual ao final (o resumo é local à execução).

## Limitações e próximos passos
- O resumo final contabiliza os votos da sessão atual, não todos os votos históricos do Firestore.
- Não há autenticação, prevenção contra votos repetidos por eleitor, painel administrativo ou exportação de resultados.
- O número digitado é validado; a interface é educativa e não reproduz todos os detalhes da urna oficial.
- Antes de apresentar, teste todos os fluxos e confira as exigências específicas do professor.
