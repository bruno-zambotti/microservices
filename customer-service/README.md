#### Teste local sem integração com demais aplicações

Caso deseje testar este projeto de forma individual, realize os passos descritos a seguir:

1. Realize a instalação do gerenciador de dependências [npm](https://www.npmjs.com/). E em seguida realize a instalação da dependência json-server com o seguinte comando: 
```npm install -g json-server```

2. Realize a criação de um arquivo chamado mock.json, abra-o e em seu interior informe o seguinte conteúdo:
- Opção de retorno sem conteúdo:
```   
   {
     "order": []
   }
```
- Opção de retorno com conteúdo:
```
    {
      "customerId": 1,
      "id": 1,
      "items": [
        {
          "productId": 1,
          "quantity": 10
        }
      ],
      "status": "NOVO"
    }
```
3. Por fim, informe o seguinte comando no console, para que o mock suba corretamente:
```json-server --watch mock.json```