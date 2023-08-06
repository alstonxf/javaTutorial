package DEMO.mvc2;

import DEMO.mvc2.model.StudentEntityBean;

/*
        MVC是一种常用的软件架构模式，代表着模型-视图-控制器（Model-View-Controller）。它是一种将应用程序的逻辑、用户界面和数据分离的设计模式，旨在提高代码的可维护性和可扩展性。

        下面是MVC模式中各个组件的简要说明：

        1. 模型（Model）：模型代表应用程序的数据和业务逻辑。它负责处理数据的获取、存储、验证和处理，以及执行与应用程序逻辑相关的操作。模型通常封装了与数据存储和处理相关的操作。
        2. 视图（View）：视图是用户界面的呈现层。它负责向用户展示数据，并接收用户的输入。视图通常是用户与应用程序交互的界面，例如图形界面、网页或命令行界面。
        3. 控制器（Controller）：控制器是模型和视图之间的中介，负责处理用户输入、更新模型数据和更新视图。它接收用户的操作，根据需要更新模型的状态，并相应地更新视图以反映最新的数据。

        MVC模式的核心思想是将应用程序分为三个独立的组件，从而实现松散耦合和单一职责原则。这种分离使得代码更易于理解、测试和维护，并促进了可重用性和扩展性。

        需要注意的是，MVC模式可以在不同的编程语言和框架中有不同的实现方式和细节。它已被广泛应用于Web开发、桌面应用程序和移动应用程序等领域。
*/

public class Main {
    public static void main(String[] args) {
        StudentEntityBean studentEntityBean = new StudentEntityBean(100,"Tom");
        StudentView studentView = new StudentView();

        StudentController studentController = new StudentController(studentEntityBean,studentView);

        studentController.UpdateStudentName(200,"Tom");
        System.out.println("-------------");
        studentController.UpdateStudentName(50,"Tom");
    }
}
