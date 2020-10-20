# Cricao de uma API utilizando java utilizando framework Spring utilizando banco mysql
api e banco estao armazenados no docker para rodar a aplicacao basta subir o docker compose
docker-compose
o commando abaixo ira executar o docker-compose.yml e subira os docker's oqual ja ira se encarregar
do banco e api apos o comando a api ja esta rodando na porta http://127.0.0.1:8080/developers ou http://localhost:8080/developers
gazin_api e gazin_db
```
docker-compose up

```

 
# Especificação Mysql
Ao subir a api ja ira criar o banco com os campos nos padroes descritos
```
CREATE TABLE  IF NOT EXISTS `db`.`developer` (
 `id` INT NOT NULL AUTO_INCREMENT,
 `nome` VARCHAR(255) NULL,
 `sexo` VARCHAR(1) NULL,
 `idade` INT NULL,
 `hobby` VARCHAR(255) NULL,
 `datanascimento` DATE NULL,

 PRIMARY KEY (`id`) );
 
 ```
 
# API endpoints

```
GET /developers
Codes 200

[ {
    "id": 11,
    "nome": "manoel ricardo wwww",
    "sexo": "M",
    "idade": 17,
    "hobby": "beber",
    "datanascimento": "1993-06-05"
} ]
```
Retorna todos os desenvolvedores


```
GET /developers?
Codes 200 / 404

/developers?sexo=m&page=2&size=2

"content": [
        {
            "id": 68,
            "nome": null,
            "sexo": null,
            "idade": 0,
            "hobby": null,
            "datanascimento": null
        },
     
     ///pageable:content/// 
      
      ]

```
Retorna os desenvolvedores de acordo com o termo passado via querystring e
paginação

```
GET /developers/{id}
Codes 200 / 404

  {
        "id": 11,
        "nome": "manoel ricardo wwww",
        "sexo": "M",
        "idade": 17,
        "hobby": "beber",
        "datanascimento": "1993-06-05"
   } 
```
Retorna os dados de um desenvolvedor



```
POST /developers
Codes 201 / 400

{    
    "nome": "manoel ricardo azevedo",
    "sexo": "M",
    "idade": 27,
    "hobby": "beber",
    "datanascimento": "1993-06-05",
    "teste": "teste"
}

```
Adiciona um novo desenvolvedor

```
PUT /developers/{id}
Codes 200 / 400

/developers/11

{    
    "nome": "manoel ricardo azevedo",
    "sexo": "M",
    "idade": 27,
    "hobby": "beber",
    "datanascimento": "1993-06-05",
    "teste": "teste"
}

```
Atualiza os dados de um desenvolvedor

```
DELETE /developers/{id}
Codes 204 / 400

```
Apaga o registro de um desenvolvedor

