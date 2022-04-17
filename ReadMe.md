# Финальное задание (свой вариант)

### Описание
Процесс выполнения заказа на изготовление шариковой ручки от подачи заявки до сборки.

### Запуск
- собрать проект
- в корневой папке выполнить


    docker-compose down
    docker rmi camunda:latest
    docker-compose up

логин/пароль для доступка к кокпиту: demo/demo

  
### curl-запросы для тестирования (green-way)

_запустить процесс_ (длина корпуса и стержня должны совпадать)

    curl -XPOST http://localhost:8080/processes/start/new -d'{"length":"15","diameter":"1","color":"RED"}' -H'Content-type:application/json'     

_подтвердить или отклонить оплату_

    - подтвердить
    curl -XPOST http://localhost:8080/processes/approve/{id заказа}
    или (для всех разом)
    curl -XPOST http://localhost:8080/engine-rest/message -H'Content-type:Application/json' -d'{"messageName":"orderPaidMessage"}'

    - отклонить
    curl -XPOST http://localhost:8080/processes/approve/{id заказа}
    или (для всех разом)
    curl -XPOST http://localhost:8080/engine-rest/message -H'Content-type:Application/json' -d'{"messageName":"orderPaidMessage"}'

_получить списки заказов и деталей_

    curl http://localhost:8080/api/orders/list
    curl http://localhost:8080/api/bodies/list
    curl http://localhost:8080/api/refills/list

_создать детали_ (доступные цвета: _RED_, _GREEN_, _BLUE_)

    curl -XPOST http://localhost:8080/api/bodies/create -d'{"length":"7","diameter":"3","price":"50"}' -H'Content-type:application/json'
    curl -XPOST http://localhost:8080/api/refills/create -d'{"length":"7","color":"GREEN","price":"50"}' -H'Content-type:application/json'

в случае успешного завершения в логах появится запись

    Complete {id заказа}

если не подтвердить оплату в течение 3х минут, заказ будет отменён