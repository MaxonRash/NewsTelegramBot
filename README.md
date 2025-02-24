**SpringBoot** telegram bot for sending news containing the specified word. News are sent daily at **12am** and **8pm (GMT+3)**.
Uses my service <a href="https://github.com/MaxonRash/NewsAPIClient">NewsApiClient</a> to get news, messaging with **Kafka**. All interactions are via commands in chat with bot.
********
**Technologies** used in these apps:
- **Java 17-18**
- **SpringBoot 2-3**
- **Maven, Spring Data, Spring Security, Spring Scheduler, Zookeeper, Apache Kafka** for messaging between main app and service, **CI:** Github Actions
- **Postgresql, MongoDB** as databases
- **Flyway** - db migration
- **Junit, Mockito** for tests
- **Docker, Docker-compose** for deployment

You can test how the bot works here: <a href="https://t.me/news_provider_telegram_bot">Telegram</a> (Turned off now, due to server problems)

**Commands:**
- **/help** - get help with commands
- **/start** - start working with bot
- **/stop** - stop working with bot
- **/get_subs** - get the list of all words you've subscribed to
- **/sub 'word'** - subscribe to news containing this word
- **/unsub 'word'** - unsubscribe from news containing this word
- **/unsub_all** - unsubscribe from all news
*****
To launch it on your server you need: **Docker, docker-compose, Postgres as standalone**. Simply use `./start.sh 'your_bot_name' 'your_bot_token' 'db_username' 'db_password'` This script executes docker-compose launching **This app, Apache Kafka** and **Zookeeper** 