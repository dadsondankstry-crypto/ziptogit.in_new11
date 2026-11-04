# PROTÓTIPO NINE KINGDOM — COMPARAÇÃO DE TECNOLOGIAS

Este é um MINIJOGO NOVO de demonstração, não é conversão da V8.0. Compare o comportamento das tecnologias usando os mesmos objetivos:

- Ande com as setas / direcional na parte inferior esquerda.
- Aproxime-se da Cuidadora Mel e toque em AÇÃO.
- Depois, encontre o Slime, aproxime-se e toque AÇÃO três vezes para derrotá-lo.
- Observe a mensagem de missão, a barra de vida e a resposta dos controles.

Todas as figuras são desenhadas por código, sem recursos de rede. Não há salvamento persistente nem inventário completo. Sem permissões perigosas; o resultado é offline. APK DEBUG apenas para testes. Cada protótipo usa um identificador Android distinto, podendo coexistir no celular.

FLUXO PELO CELULAR: crie um repositório vazio no GitHub, envie SOMENTE O ZIP DESTE PROTÓTIPO pelo ZiptoGit para a branch main, confira o arquivo oculto `.github/workflows/main.yml`, entre em Actions, execute caso preciso, baixe o artifact com APK, extraia e instale. O ZIP tem os arquivos já na RAIZ, sem pasta-pai. Um repositório por ZIP. Se o ZiptoGit não enviar `.github`, copie o YAML avulso pelo editor do GitHub. Se for distribuir ou atualizar a instalação para outras pessoas, configure assinatura própria e versionCode; não armazene chaves privadas no GitHub.

Compilação pelo GitHub Actions NÃO foi executada aqui. Em caso de erro, envie os logs e é preciso corrigir o ZIP completo.

## Kotlin nativo
Compila com Gradle 8.13 / AGP 8.13.2 / Kotlin 2.2.21 / JDK 17. Sem bibliotecas de jogo; Android Canvas nativo. `minSdk 23`, `targetSdk 36`. Tela com movimento e interação. APK: `nk-kotlin-debug.apk`. Não usa rede nem pede permissões.
