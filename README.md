
#  Back-end Files API

Realizei a criação desse repositorio, para guardar os conhecimentos adquiridos ao realizar a criação dessa API que trabalha com gerenciamento de arquivos, permitindo salvar e também realizar o download de arquivos salvos, é possivel também listar todos os arquivos possiveis para realizar o download


## Referência

 - [Linkedin Pessoal](https://www.linkedin.com/in/vitorlucascrispim/)



## Documentação da API

#### O projeto está no ar ate o presente momento para acessar sua Documentação acesse a url abaixo: 

```http
https://backend-filesapi.onrender.com/swagger-ui/index.html#/
```
#### O servico na sua verão 1.0 conta com três requisições

```http
 POST:/files/upload
 que espera uma aquivo variado no formato de "multipart/form-data"

 GET:/files/archives
 um GET que traz todos os arquivos disponiveis para download

  GET:/files/download/{fileName:.+}"
 um GET que traz o download do arquivo caso o nome e tipo do arquivo seja passado corretamente
```


## Instalação

Necessario todo ambiente spring caso vá rodar localmente
  - [Documentação Spring - Intellij](https://www.jetbrains.com/help/idea/spring-support.html)   
- [Documentação Spring - Eclipse](https://www.eclipse.org/community/eclipse_newsletter/2018/february/springboot.php)   

## Aprendizados

Foi utilizado somente o ecossistema Spring para o gerenciamente de arquivos.


## Utilizado

- [Spring Upload Files](https://spring.io/guides/gs/uploading-files/)   




## Stack utilizada

**Back-end:** Spring boot

