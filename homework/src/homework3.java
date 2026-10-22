
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class homework3 {
    static HttpClient client = HttpClient.newHttpClient();


    public static void main(String[] args) throws Exception {

        System.out.println("ЗАДАНИЯ СО СЛАЙДА 31");

        task31_3();

        task31_4();

        task31_5();


        System.out.println("\nЗАДАНИЯ СО СЛАЙДА 33");

        ApiClient api = new ApiClient();

        System.out.println("\nGET-запрос через ApiClient");

        String getResult = api.get(
                "https://jsonplaceholder.typicode.com/posts/1"
        );

        System.out.println(getResult);


        System.out.println("\nPOST-запрос через ApiClient");

        String postJson = """
                {
                  "title": "Пост Насти",
                  "body": "Пример отправки нового сообщения",
                  "userId": 1
                }
                """;

        String postResult = api.post(
                "https://jsonplaceholder.typicode.com/posts",
                postJson
        );

        System.out.println(postResult);


        System.out.println("\nPUT-запрос через ApiClient");

        String putJson = """
                {
                  "id": 1,
                  "title": "Обновлённый пост",
                  "body": "Изменённое содержание сообщения",
                  "userId": 1
                }
                """;

        String putResult = api.put(
                "https://jsonplaceholder.typicode.com/posts/1",
                putJson
        );

        System.out.println(putResult);


        System.out.println("\nDELETE-запрос через ApiClient");

        String deleteResult = api.delete(
                "https://jsonplaceholder.typicode.com/posts/1"
        );

        System.out.println(deleteResult);


        asyncRequests();


        retryRequest(
                "https://jsonplaceholder.typicode.com/posts/1"
        );
    }


    public static void task31_3() throws Exception {

        System.out.println("\nЗадача 31.3");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(
                        URI.create(
                                "https://jsonplaceholder.typicode.com/users/1"
                        )
                )
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        System.out.println(
                "Код ответа: " + response.statusCode()
        );

        System.out.println(
                "Полученные данные:"
        );

        System.out.println(
                response.body()
        );
    }


    public static void task31_4() throws Exception {

        System.out.println("\nЗадача 31.4");

        String json = """
                {
                  "title": "Новый пост",
                  "body": "Текст сообщения от Насти",
                  "userId": 1
                }
                """;

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        "https://jsonplaceholder.typicode.com/posts"
                                )
                        )
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .POST(
                                HttpRequest.BodyPublishers
                                        .ofString(json)
                        )
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        System.out.println(
                "Код ответа: " + response.statusCode()
        );

        System.out.println(
                "Результат запроса: " + response.body()
        );

        if (response.statusCode() == 201) {

            System.out.println(
                    "Запись успешно создана"
            );
        }
    }


    public static void task31_5() throws Exception {

        System.out.println("\nЗадача 31.5");

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        "https://jsonplaceholder.typicode.com/users/999999"
                                )
                        )
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        int status = response.statusCode();

        if (status == 200) {

            System.out.println(
                    "Пользователь найден"
            );

        } else if (status == 404) {

            System.out.println(
                    "Ошибка 404: пользователь не найден"
            );

        } else {

            System.out.println(
                    "Получен другой код: " + status
            );
        }

        System.out.println(
                "Содержимое ответа: " + response.body()
        );
    }


    static class ApiClient {

        private final HttpClient client =
                HttpClient.newHttpClient();


        public String get(String url) throws Exception {

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(url)
                            )
                            .GET()
                            .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                    "GET, код ответа: "
                            + response.statusCode()
            );

            return response.body();
        }


        public String post(
                String url,
                String json
        ) throws Exception {

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(url)
                            )
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .POST(
                                    HttpRequest.BodyPublishers
                                            .ofString(json)
                            )
                            .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                    "POST, код ответа: "
                            + response.statusCode()
            );

            return response.body();
        }


        public String put(
                String url,
                String json
        ) throws Exception {

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(url)
                            )
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .PUT(
                                    HttpRequest.BodyPublishers
                                            .ofString(json)
                            )
                            .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                    "PUT, код ответа: "
                            + response.statusCode()
            );

            return response.body();
        }


        public String delete(String url)
                throws Exception {

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(url)
                            )
                            .DELETE()
                            .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                    "DELETE, код ответа: "
                            + response.statusCode()
            );

            return response.body();
        }
    }


    public static void asyncRequests() {

        System.out.println(
                "\nАсинхронные запросы"
        );

        HttpRequest request1 =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        "https://jsonplaceholder.typicode.com/users/1"
                                )
                        )
                        .GET()
                        .build();

        HttpRequest request2 =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        "https://jsonplaceholder.typicode.com/users/2"
                                )
                        )
                        .GET()
                        .build();

        HttpRequest request3 =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        "https://jsonplaceholder.typicode.com/users/3"
                                )
                        )
                        .GET()
                        .build();


        CompletableFuture<HttpResponse<String>> future1 =
                client.sendAsync(
                        request1,
                        HttpResponse.BodyHandlers.ofString()
                );

        CompletableFuture<HttpResponse<String>> future2 =
                client.sendAsync(
                        request2,
                        HttpResponse.BodyHandlers.ofString()
                );

        CompletableFuture<HttpResponse<String>> future3 =
                client.sendAsync(
                        request3,
                        HttpResponse.BodyHandlers.ofString()
                );


        CompletableFuture<Void> allRequests =
                CompletableFuture.allOf(
                        future1,
                        future2,
                        future3
                );

        allRequests.join();


        System.out.println(
                "Данные пользователя 1:"
        );

        System.out.println(
                future1.join().body()
        );


        System.out.println(
                "\nДанные пользователя 2:"
        );

        System.out.println(
                future2.join().body()
        );


        System.out.println(
                "\nДанные пользователя 3:"
        );

        System.out.println(
                future3.join().body()
        );
    }


    public static void retryRequest(
            String url
    ) throws Exception {

        System.out.println(
                "\nПовтор запроса при ошибке сервера"
        );

        int maxAttempts = 3;

        for (
                int attempt = 1;
                attempt <= maxAttempts;
                attempt++
        ) {

            System.out.println(
                    "Попытка №" + attempt
            );

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(url)
                            )
                            .GET()
                            .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            int status =
                    response.statusCode();

            System.out.println(
                    "Код ответа: " + status
            );


            if (
                    status >= 500
                            && status <= 599
            ) {

                System.out.println(
                        "Ошибка на стороне сервера"
                );

                if (attempt < maxAttempts) {

                    System.out.println(
                            "Повтор через 1 секунду..."
                    );

                    Thread.sleep(1000);
                }

            } else {

                System.out.println(
                        "Запрос завершён"
                );

                System.out.println(
                        response.body()
                );

                break;
            }
        }
    }
}
