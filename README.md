# Golden Raspberry Awards API

Esta é uma API RESTful para consultar os produtores com o maior e menor intervalo entre dois prêmios consecutivos na categoria "Pior Filme" do Golden Raspberry Awards.

## Requisitos

* Java 17 ou superior
* Maven 3.x

## Como Rodar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/fernandoguide/goldenraspberryawards
    cd goldenraspberryawards
    ```

2.  **Coloque o arquivo CSV:**
    Certifique-se de que o arquivo `movielist.csv` esteja localizado na pasta `src/main/resources/`.
    **Importante:** O CSV deve usar `;` (ponto e vírgula) como delimitador de colunas, como mostrado na imagem. Se o seu CSV usar `,` (vírgula), você precisará ajustar `line.split(";")` para `line.split(",")` na classe `CsvDataLoaderService.java`.

3.  **Compile e execute a aplicação:**
    Você pode usar o Maven para construir e rodar:
    ```bash
    mvn clean install
    ```

    ```bash
    mvn spring-boot:run
    ```
    A aplicação será iniciada na porta padrão 8080.

## Endpoints da API

A API expõe o seguinte endpoint:

### `GET /api/awards/producer-intervals`

Exemplo de requisição usando `curl`:

```bash
   curl http://localhost:8080/api/awards/producer-intervals
```



Retorna os produtores com o maior intervalo entre dois prêmios consecutivos e os que obtiveram dois prêmios mais rápido.

**Exemplo de Resposta (JSON):**

```json
{
    "min": [
        {
            "producer": "Joel Silver",
            "interval": 1,
            "previousWin": 1990,
            "followingWin": 1991
        }
    ],
    "max": [
        {
            "producer": "Matthew Vaughn",
            "interval": 13,
            "previousWin": 2002,
            "followingWin": 2015
        }
    ]
}
