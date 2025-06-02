Browser
http://localhost:8080/register
http://localhost:8080/user/home
http://localhost:8080/admin/home
http://localhost:8080/admin/panel

Postman
REST-реєстрація через Postman
1.	POST http://localhost:8080/auth/register з і тілом
{
 "login": "newuser",
  "password": "newuserpass"
}
Прийде User registered successfully

REST-автентифікація через Basic Auth
http://localhost:8080/login 
{
 "login": "Lora",
  "password": "adminpass"
}


2.	  POST    http://localhost:8080/auth/login
В Body 
{
  "login": "PetClinikOwner",
  "password": "ownerpassword"
}
Прийде токен

Використати токен
•	URL: GET http://localhost:8080/api/admin/panel
Запити через токен

PetClinikOwner (роль юзер)  , може тільки переглядати інфу(працює тільки GET)
Для Lora (роль адмін) ,  може додавати інфу+ переглядати інфу(працює  GET + POST)
http://localhost:8080/api/admin/pets
{
  "name": "Мурчик",
  "species": "Кіт",
  "ownerName": "Іван",
  "birthDate": "2022-04-10"
}
GET Отримати список всіх тварин
http://localhost:8080/api/admin/pets- for admin
http://localhost:8080/api/user/pets - for user
GET  Отримати тварину по ID
http://localhost:8080/api/admin/pets/1

Створити ветеринара
POST http://localhost:8080/api/admin/vets
{
  "name": "Олена",
  "specialization": "Терапевт"
}
{
 "name": "Андрій",
 "specialization": "Хірург"
}

Отримати список ветеринарів
GET http://localhost:8080/api/admin/vets
GET  Отримати ветеринара по ID
http://localhost:8080/api/admin/vets/1
Створити візит
POST http://localhost:8080/api/admin/visits
{
  "visitDate": "2025-05-20",
  "description": "Рутинна перевірка",
  "pet": {
    "id": 1
  }
}

{
  "visitDate": "2025-05-21",
  "description": "Гельмінтизація",
  "pet": {
    "id": 2
  }
}

Отримати візити по petId
GET http://localhost:8080/api/admin/visits/by-pet/1

