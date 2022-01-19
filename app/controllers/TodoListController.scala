package controllers

import models.{NewTodoListItem, TodoListItem}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.Singleton
import javax.inject.Inject
import scala.collection.mutable
import play.api.libs.json._

@Singleton
class TodoListController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  implicit val todoListJson = Json.format[TodoListItem]
  implicit val newTodoListJson = Json.format[NewTodoListItem]


  private var todoList = new mutable.ListBuffer[TodoListItem]()
  todoList += TodoListItem(1, "test", true)
  todoList += TodoListItem(2, "some other value", false)

  /**
   * curl localhost:9000/todo
   *
   * @return
   */
  def getAll(): Action[AnyContent] = Action {
    if (todoList.isEmpty) {
      NoContent
    } else {
      Ok(Json.toJson(todoList))
    }
  }

  /**
   * curl localhost:9000/todo/1
   *
   * @param itemId
   * @return
   */
  def getById(itemId: Long) = Action {
    val foundItem = todoList.find(item => item.id == itemId)
    foundItem match {
      case Some(item) => Ok(Json.toJson(item))
      case None => NotFound
    }

  }

  /**
   * curl -X PUT localhost:9000/todo/done/1
   *
   * @param itemId
   * @return
   */
  def markAsDone(itemId: Long) = Action {
    val foundItem = todoList.find(item => item.id == itemId)
    foundItem match {
      case Some(item) =>
        val newItem = item.copy(isItDone = true)
        todoList = todoList.dropWhile(item => item.id == itemId)
        todoList += newItem
        Accepted(Json.toJson(newItem))
      case None => NotFound
    }
  }

  /**
   * curl -X DELETE localhost:9000/todo/done
   *
   * @return
   */
  def deleteAllDone() = Action {
    todoList = todoList.dropWhile(item => item.isItDone == true)
    Accepted
  }

  /**
   * curl -v -d '{"description": "some new item"}' -H 'Content-Type: application/json' -X POST localhost:9000/todo
   *
   * @return
   */
  def addNewItem() = Action { implicit request =>
    val content = request.body
    val jsonObject = content.asJson

    val todoListItem: Option[NewTodoListItem] = jsonObject.flatMap(Json.fromJson[NewTodoListItem](_).asOpt)

    todoListItem match {
      case Some(newItem) =>
        val nextId = todoList.map(elem => elem.id).max + 1
        val toBeAdded = TodoListItem(nextId, newItem.description, false)
        todoList += toBeAdded
        Created(Json.toJson(toBeAdded))
      case None =>
        BadRequest
    }
  }
}
