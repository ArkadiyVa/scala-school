/**
  * Вы решили написать свой тестовый фреймворк.
  *
  * Одной из ваших задач является создание абстракции,
  * которая позволяет настраивать окружение перед запуском каждого теста.
  *
  * Нужно учитывать, что пользователи вашей библиотеки захотят создавать
  * множество вариантов установки окружения и чтобы помочь им, напишите пример теста,
  * который устанавливает значение переменной X = System.currentTime, выводит сообщение что тест начался,
  * и проверяет, что `X > 0` (ну так, на всякий случай).
  */

trait TestCase {
  def test(value: Boolean): Unit
}