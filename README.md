# eCare web application Technical Solution Description

```
Developed by
Andrew Golubev
golubevcg@gmail.com

Java School, Deutsche Telekom IT Solutions
2020
```
## Content

- 1 [Introduction](#1-Introduction)
- 2 [Technologies](#2-Technologies)
- 3 [Model](#3-Model)
   - 3.1 [Entity description](#31-Entity-description)
   - 3.2 [Database scheme](#32-Database-scheme)
- 4 [Architecture description](#4-Architecture-description)
- 5 [User interface](#5-User-interface)
   - 5.1 [Client account](#51-Client-account)
      - 5.1.1. [Entrance and login pages](#511-Entrance-and-login-pages)
      - 5.1.2. [Clients account page and contract page](#512-Clients-account-page-and-contract-page)
      - 5.1.3. [Cart page](#513-Cart-page)
   - 5.2 [Employee account](#51-Client-account)
      - 5.2.1 [Searches and header](#51-Client-account)
      - 5.2.2 [New user registration](#51-Client-account)
      - 5.2.3 [Contract editing](#51-Client-account)
      - 5.2.4 [Tariff editing](#51-Client-account)
      - 5.2.5 [Options editing](#51-Client-account)
- 6 [Ad editing, message sender](#6-Ad-editing-message-sender)
- 7 [Ecare advertisment stand](#7-Ecare-advertisment-stand)
- 8 [Code quality and testing. Logs and javadoc](#8-Code-quality-and-testing-Logs-and-javadoc)
- 9 [Further development](#9-Further-development)


## 1. Introduction

ECare is a web application for a fictional telecommunication company, which is focused on
improving relationships between mobile operators and clients. An application can quickly
manage all contracts, tariffs, and options with deep dependencies for business needs for clients and
company employees both. It improves the way we interact with mobile operators by providing a strict
and clear system of option dependencies, multiple contract management, and option rules editing.

Company employees can register new clients, add new tariffs, options, and contracts, and edit
option dependency rules. Also, the app provides for employees to quickly changing of Ad’s, content of
which can be edited in a real-time update in the advertisement stands.

Clients of a company can check new options, tariffs and edit their contracts in their
accounts. All with great and intuitive interfaces take this experience to a whole new level of
convenience.

The second part of the application is an advertisement stand, which receives information from
main application and updates it in real-time.


## 2. Technologies

List of technologies and frameworks, which were used inside the application:
- Spring Framework;
- Hibernate;
- Lombok;
- Jquery, CSS;
- JSP, JSF;
- EJB;
- Maven;
- Wildfly;
- ArtemisMQ;
- Apache Tomcat;
- PostgreSQL.

## 3. Model

### 3.1 Entity description

In the Application was used 6 types of entities:
1) User entity consist with user_id, login, first and second names, date of birth, passport
info, email, password, is active, set of roles, and list of contracts, currently owned by the user.

2) Contract is a contract with a phone number, which is connected to every company
client. It has a contract_id field, contract number (equals to a phone number), is blocked – which is
used for blocking a contract, user, set of options, and connected Tariff.

3) Tariff have also tariff_id, name, price, connection price, short description, set of contracts,
which chose this tariff, set of available for this tariff options and is an active attribute, in case it needs
to be removed.

4) Option entity have option_id, name, price, connection cost, short description, is active
attribute, set of tariffs, which have this option as available, set of contracts that have this option
added to them and two sets of obligatory and incompatible options.

5) Ad – advertisement entity, used in advertisement stands. Have id field, active(if it deleted
or not), name field and List of Tariffs, which will be used in stand for advertisement.

6) Role has role id, role name, and set of users with this role.


### 3.2 Database scheme

![Dbschema](readMeImages/db_schema.png)

## 4. Architecture description

The application consists of two modules – the main app and the advertisement stand. Both at its core use
Model View Controller architectural pattern, but have different implementations on its basis.

Main app base on Spring MVC for controllers and requests handling, for view responsible
JSP – Java Server Pages framework, which helps to render server information on HTML pages. Model
is handled by Hibernate ORM (wish goes as Object Relational Mapping) for connecting entities
with the database. For this application, I choose the relational database PostgreSQL. For connections
between these two modules (main app and advertisement stand), I used message queen and Java
Messaging Service – industry-standard API for sending messages to message broker. Messages are
sent to build-in message broker inside ad stand application server. The first app is deployed on
Apache Tomcat server. All database connections are wrapped in dao (Data Acces Objects) interfaces
and its implementation, also for efficient use of queries there is included Transaction Manager
inside all dao related service methods.

The second application is based on the WildFly application server, with the same usage of the MVC
pattern, but with a different technology stack. In this one heavily used JSF for view-server
connection, it is real-time updates any changes received from the first application. To process and
listen to messages there was written Message Driven Bean.


## 5. User interface

### 5.1 Client account

#### 5.1.1. Entrance and login pages

![MainAndLogin](readMeImages/main_and_login.png)

When entering the app, any user will see an entrance page with tariffs and options advertisements.
In the top right corner, there is a button for login. After clicking on it he will be redirected to the login page,
in which login and password validation are implemented, and all the rest pages (except options and
tariffs information) will be blocked by Spring Security.

In view, a lot of used Bootstrap forms, which is a free set of tools includes HTML and CSS design
templates for web forms, buttons, labels, navigation boxes, and other web interface components.


#### 5.1.2. Clients account page and contract page

![ContractDetails](readMeImages/contract_details.png)

The client account page consists of a list with connected contracts. Clicking on the Details button will
transfer the user to the contact page, in which the client can edit the contract, based on his needs – change tariff,
change the option or even block a contract. This page heavily used JQuery and Ajax request with
JavaScript, to update page information, based on client's choices. For example during the tariff
selection, options available for this tariff will be automatically updated without page refreshing.

All option rules – obligatory and incompatible dependencies already included in the selection. If
the client will turn on some option which he liked, the application will immediately check its dependencies,
and if it will find some, then related options will be enabled or blocked, based on the option rule.

![Options](readMeImages/options.png)

#### 5.1.3. Cart page

![CartPage](readMeImages/cart_page.png)

To submit changes from contracts client need to proceed to the cart page, before submitting with
created changes he can, for example, remove any of them if he changed his mind. All changes during
this contracts editing are stored in session. When the client enters the contract list page in the session stored
set with the user's contacts, and this set is duplicated. On duplicated version applied all changes created in
contract page. On the cart page, this changes compared with values from DB(which are already stored in
the session attribute), and the difference is revealed on the page.


### 5.2 Employee account

#### 5.2.1 Searches and header

![AccountPage](readMeImages/account_page.png)

All employees inside the application have an “Admin” role and extended functionality. Such users
after login in will see the admin account homepage. It consists of three searches, in which workers
can search for contract, tariff, or option, typing in the search field. Results are links, from which
the employee can proceed for editing of entities if he needs to. Search results can be pretty big, so to
escape this problem on this page was pagination created, which returns 5 results on a single page.


The header will depend on the currently logged user role, if will change menu items, for example, if
user logged as “Admin” he will see a drop-down list with items like this:

![HeaderMenu](readMeImages/header_menu.png)

#### 5.2.2 New user registration

![HeaderMenu](readMeImages/registration_validation.png)

By clicking on user registration employee will be transferred to the user registration page, in
which he can register a new user, add a contract to him, select tariff, and set additional options. The options
list will be updated based on tariff selection. All field values have validation underneath and after
submitting this request all values will be checked and if one of it will be something wrong it
will reveal a validation message.

There is a Company member checkbox, when it is pressed, fields, related to the contract will be
hidden. This checkbox is created to register new company employees. If this checkbox will be set,
then the “Admin” role will be assigned to this user, after proceeding with this registration.


#### 5.2.3 Contract editing

![ContractEditing](readMeImages/contract_editing.png)

#### If in the search field find some contract and click on it, then you will be redirected to

contract editing page, in which the existing contract can be changed. By default all fields are disabled,
to edit it, the user needs to click on the edit button. This button is only seen by users with “Admin” authority.
Selected user field if the search for the user by login, so if you want to change the user of
the current contract, you need to pick one from a drop-down list, which will pop up during the search. If
login will be from a non-existing database user, this field will be refused by validation. The new
contract window looks almost the same.

#### 5.2.4 Tariff editing

![TariffEditing](readMeImages/tariff_editing.png)

The tariff editing window looks very similar to contract editing, except for one thing. Because our
options have rules, in which one can be obligatory to the other, we need to think about it during new
tariff registration or editing. To escape the problem in which obligatory option will not be connected to
the tariff, so it will be impossible to enable, so it will be an impossible dependency. So all connected
obligatory options to selected options will be added automatically to the list. Recursively, so dependency
of depended options will be included.


#### 5.2.5 Options editing

![OptionsEditing](readMeImages/option_editing.png)

If you need to edit the option you need to type the option name in a search field and click on one of
the results. On the option editing page, you can also edit all rules, which you can apply to the selected option.
For example, you want to add a more obligatory option for this one. Obligatory options –
options, which should be enabled in the contract for enabling this option. Incompatible options –
options, which should be disabled for this option to be enabled. To escape from impossible
dependencies, on this page written a lot of checks. When the user adds a new option, it will be checked for
obligatory options in parent dependencies, and selected one will be checked to be compatible with
already selected ones. It all happens without page reloading, using Javascript Ajax requests. If there
is will be found some incompatible dependencies, then there is will alert window pop up, which says
which option should be changed to enable the option which the user chooses.


## 6. Ad editing, message sender

![AdEditing](readMeImages/ad_editing_page.png)

If the administrator will proceed to the Ad editing page, he will see three cars of tariffs, which is
currently displayed on the second application advertisement stand. Changing tariffs will result in an
immediate change in advertisement stand. For such purposes, applications have separate class message
sender, which connects to message broker Artemis MQ, which is included in WildFly application
server, on which eCare advertisement stand runs. Any editing of and tariff or an option will update
tariffs in the ad too.


## 7. Ecare advertisment stand

![AdPage](readMeImages/ad_page.png)

The second application is used as an advertisement page, which should show dependencies from the first
application. So, there is a written a rest method, which takes tariffs from the first application during
the first deployment, and each time a message is received in a queue it gets new information from a first app.
A queue is always listened to by Message Driven Bean with the listener method. As a Message broker user
builds in WildFly Artemis MQ, which works great. Messages in a queue are used only to say that the first
application has some changes, please check to render a page in this part we use Java Server Faces
technology and, for a real-time update, without using Javascript AJAX request manually – it’s a great
tool to work with. The Github page with the source code of a second application is [here](https://github.com/golubevcg/eCareAdStand).


## 8. Code quality and testing. Logs and JavaDoc

![SonarQube](readMeImages/sonar_qube.png)

Application is checked using Sonar Qube, which is used for code quality analysis. To project
added Jacoco library, which measures test coverage of a whole project. This lib is connected to
Sonar, for showing coverage inside Sonars report. Sonar results you can look at this image:

Quality gate pass is failed because code coverage not more than 80%, there is a field to improve,
currently only 56.2% of application covered with tests.


## 9. Further development

Future development will depend on business needs, but already in a current state of
an application, there is a field for improvement. It would be a great idea to add another role in an app,
for example, an employee, and give him part of the privileges. Because it is now so good when all
employees have all admin privileges. Also, an app needs a messaging system between user and
employee, for the ability to send messages and receive ones daily inside this app. Next,
almost all entities have fields with price or connections cost, so adding a payment system would make
sense. Ad system can be much more improved, in which there should be an ability to choose not
only tariffs but options too. Would be a good idea to add functionally for adding new ads, and
connect them to this application. The currently developed application has a good basis for further
development and improvement of the functionality.


