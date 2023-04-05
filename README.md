# Introduction
Welcome to the HHA CBR Manager project by team Saturn!

The goal of the project is to create an Android app that satisfies the customers requirements as stated in the initial presentation and any requirements to be modified or added through feedback.


## Tools
Our development tools include using Android Studio for the frontend including all UI elements and Django as the backend server which is used to communicate with the database and handle any additional logic performed on the data.

## Progress

### Iteration 1
In iteration 1, we've designed the UI for the majority of required pages and created the classes and models for users, clients, visits and alerts. So now all users can create, store and retrieve their clients' and visits' data to and from the server, either ran locally or through the one deployed on Heroku. The admin can also send alerts to all and create new users that have seperate sets of clients and visit data.

### Iteration 2
In iteration 2, we focused on adding the ability for users to create referrals for clients, which can then be viewed and edited. Speaking of editing, this was another area we focused on this iteration. Now clients and visits can also be edited through the app as well. We also placed a search feature in a lot of the pages, which will be handy once the number of clients and visits grows. Another focus this iteration was on using a local db with syncing to allow users to use the app offline. Finally, we improved a lot of the app's UI this iteration, based on feedback from the customer, and also based on having a better understanding of the flow of the app and how to use Android at this point. One of these changes was login caching, which we feel definitely makes the app more convenient to use (and to test!).

### Iteration 3
In iteration 3, we focused on goals, offline functionality, maps, statistics, admin privileges, the risk algorithm, baseline surveys, and general quality of life improvements for the user. Goals are now their own objects rather than simply being an attribute of client, so they can be created and updated in several places, and their full history can be viewed. Nearly the entire app is now functional offline. Every table except baseline surveys (which is less likely to be accessed offline) can be written to, updated, and viewed from offline using a local database, which will sync to the global database when internet becomes available again. There is now also a map showing all client locations, and a statistics page that can export CSV files to Google Drive (if the user is an admin). The risk algorithm is now fully operational, with complex-ish calculations under the hood, but displaying the results in easily digestible English categories. Users can also now fill out baseline surveys for clients. Finally, the UI across the board has been cleaned up, instructions are added for the first time a user uses the app, and some code refactoring has been done to clean up the code.

## To do in the future
Baseline surveys definitely should get offline functionality. Baseline surveys should also be represented in the statistics. There are also a few counterintuitive quirks in the UI that should be changed, such as when creating a goal in visit for a category that doesn't yet have a goal, where it will ask the status of the nonexistent goal. It's a process, so I'm sure there will also be other things to change based on the customer's feedback. Overall though, we're very happy with what we've accomplished on this app this term. We're proud of the "final" product, and we've all learned a lot.


# How to setup Local Development

## Django

### Setting up via Docker

1. You can get the backend running easily using Docker.  If you don't have Docker installed on your computer, you can install it from the official provider at https://www.docker.com/.  If you are on MacOS, you can check to see that you have Docker installed using the following command:

  ```
  docker --version
  ```

2. Head to `/backend` and run the following command.  You should now have a Docker container running the backend of cbr-manager listening in on port 8000.

  ```
  make start
  ```

3. Now, you need to create a superuser which you can login to app with by SSHing into the container which you just created.  Copy the container ID of the Docker container you just created.  The container ID should You can see the container ID by running 

  ```
  docker ps
  ```

the container ID should be next to the image name "cbr-manager".  Now SSH into the container with the following command.

  ```docker exec -it <CONTAINER_ID> sh```

4. Create a super user.  Remember the username and password credentials as this is what you will use to login to the app.

  ```
  python manage.py createsuperuser
  ```

5. Exit the SSH shell by typing `exit` then get your local computer IP

  - Open a terminal
  - Run `ipconfig` or for mac `ipconfig getifaddr en0`
  - Look for an `IPV4 Address` not `Default Gateway` (if on Windows)
  - Use this address to access Django and copy this somewhere. We will need this for the Android section.

### Manual setup

1. First, you need to ensure that you have Python installed on your computer.  On MacOS you can install Python via an installer which you can download off the official website https://www.python.org/.  On MacOS, you can check to see if you have Python installed using the following command:

  ```
  python --version
  ```

2. Next, you need to ensure you have Django installed.  Django is installed via PIP which is Python's package manager.  PIP should have been automatically installed when you installed Python in the previous step.  Check to see if you have PIP correctly installed:

  ```
  pip --version
  ```

3. Next, you need to install all project dependencies.  Run the following command:

  ```
  pip install -r requirements.txt
  ```

4. Now, run 

  ```
  python manage.py makemigrations && python manage.py migrate
  ```

5. Create a super user.  Remember the username and password credentials as this is what you will use to login to the app.

  ```
  python manage.py createsuperuser
  ```

6. Run your server at 0.0.0.0 on port 8000:

  ```
  python manage.py runserver 0.0.0.0:8000
  ```

7. Exit the SSH shell with CTRL-C or CMD-C (if on macOS) then get your local computer IP

  - Open a terminal
  - Run `ipconfig` or for mac `ipconfig getifaddr en0`
  - Look for an `IPV4 Address` not `Default Gateway` (if on Windows)
  - Use this address to access Django and copy this somewhere. We will need this for the Android section.

# Android

1. Head to `frontend/` and find `local.properties`.  If it does not exist, create it.

2. Add a line to the file, and place your IP you found earlier in the appropriate field

   `API_URL="http://<YOUR_IP>:8000/"`

   - quotes are important here
   - the last slash is important

3. Add an additional line

  `MAPS_API_KEY=AIzaSyDhUGaY6JXS4kY7al7IbpHeIImT-eGlzXc`

4. Your file should look like this now

  ```
  sdk.dir=C\:\\Users\\tangj\\AppData\\Local\\Android\\Sdk
  API_URL="http://<YOUR_IP>:8000/"
  MAPS_API_KEY=AIzaSyDhUGaY6JXS4kY7al7IbpHeIImT-eGlzXc
  ```

  or if you are on MacOS

  ```
  sdk.dir=/Users/dev/Library/Android/sdk
  API_URL="http://<YOUR_IP>:8000/"
  MAPS_API_KEY=AIzaSyDhUGaY6JXS4kY7al7IbpHeIImT-eGlzXc
  ```

# Done!

Now you should be able to connect to the Django API with Android.

# Directory Structure

## High Level

At the root directory there are two folders `backend` and `frontend`.

- frontend contains the source code for the Android app that act as the user interface for communicating with the backend
- backend contains the source code for the Django server that maintains the API, models, and database operations.

## Backend directory

Here we follow the standard Django folder structure that gets generated when a project gets initialized. Each component in the app gets their own folder. i.e. clients, visits, alerts ... as an application or sub application. Within each app we have definitions of models, serializers to serialize the model, and API endpoints to expose the model to operations via HTTP methods.

- models.py - where models get defined.
- views.py - where the API logic of endpoints get defined.
- serializer.py - the serializer that serializes a model.

`cbrsite` is the root app for the Django project where new apps are registered into `cbrsite/settings.py`. It also contains are `urls.py` that controls routing for the entire Django project.

`images` contains the media that gets uploaded to the server. Examples includes client and user photos.

One special addition is a `scripts` folder that contains scripts that help us with development. For example, one script generates and populates clients with random data into our database.

For dependencies, `requirements.txt` contains the needed dependencies to run the Django project.

## Frontend directory

Here we follow the standard Android folder structure that gets generated by Android studios.

For logic and the packages, we have the following organization:

- `service` package - contains all the services, and models that defines and manages requests to be sent to the backend. These services are responsible for deserializing JSON to Java objects from requests responses so we can manipulate them in the Java environment.
- `ui` package - contains our activities, fragments, and any components or logic that have to deal with the user interface. This includes our login page, client listing page, etc.
- `utils` package - contains helper and utility classes to be used through the entire Android app.

For layouts, that is `frontend\app\src\main\res\layout`, contains our UI elements. They are placed in flat folder structure and are referenced as needed.

`assets` contains our static front end assets.

`app/src/build.gradle` contains the dependencies for the application.

# Project Build Requirements

## Android Studio
1. Download the latest version of Android studio from [this link](https://developer.android.com/studio), available for Windows, Mac, Linux and Chrome OS.

2. Follow the download and installation instruction until you open Android Studio

 ![Android studio](/readme-images/build-setup01.PNG)

### Android Emulator
Android Emulator can be use on [Windows](#windows), [Mac](#mac) and [Linux](#linux)

#### Windows
1. Go to Control Panel and search `feature` on the search box.

2. Click on `Turn windows feature on/off` and make sure Hyper-V/Windows Hypervisor Platform or any other virtual machine features are unchecked

 ![unchecked](/readme-images/build-setup02.PNG)

 you will need to restart your computer.

3. If you are using AMD processor go to [this step](#amd-processor), otherwise if you are using Intel processor continue on.

##### Intel processor
1. Open Android studio and go to Configure>SDK manager>SDK Update sites

 ![SDK Update sites](/readme-images/build-setup03.PNG)

2. Select and install Intel HAXM

3. Follow the instruction, after installation you can check if it is installed correctly by typing `sc query intelhaxm` on the Window command prompt

 ![command prompt](/readme-images/build-setup04.PNG)

if you can see `STATE 4 RUNNING` it mean the installation run correctly

4. Go to [AVD Setup](#android-virtual-device-setup)

##### AMD processor
1. Open Android studio and go to Configure>SDK manager>SDK Tool

 ![SDK Tool](/readme-images/build-setup05.PNG)

2. Select and install Android Emulator Hypervisor Driver for AMD Processors.

3 Follow the instruction, after installation you can check if it is installed correctly by typing `sc query gvm` on the Window command prompt, if you can see `STATE 4 RUNNING` it means the installation run correctly.

4. Go to [AVD Setup](#android-virtual-device-setup)

#### Mac
1. On MacOS X v10.10 Yosemite or higher, Android emulator use the built-in Hypervisor.Framework, if your MacOS version is lower you can go to Android studio Configure>SDK Manager>SDK Update sites and install Intel HAXM

2. Go to [AVD Setup](#android-virtual-device-setup)
#### Linux
1. For both Intel and AMD processor you need to install KVM, first check whether kvm is already installed on your system by using cpu-checker and kvm-ok commmand, run these commands in order

 cpu-checker
 ```
 sudo apt-get install cpu-checker
 egrep -c '(vmx|svm)' /proc/cpuinfo
 ```

 kvm-ok
 ```
 kvm-ok
 ```

 Final result should show `KVM acceleration can be used`

2. If KVM is not installed on your system, run the following command
 ```
 sudo apt-get install qemu-kvm libvirt-bin ubuntu-vm-builder bridge-utils ia32-libs-multiarch
 ```

3. Go to [AVD Setup](#android-virtual-device-setup)
#### Android Virtual Device Setup
1. Go to Android studio>Configure>AVD Manager

 ![AVD Manager](/readme-images/build-setup06.PNG)

2. Create the virtual device you want to test the application on, this application is designed for Phone type android devices. API level can be as high as you want - for reference, according to April 2020 survey, over 30% of android devices use Pie API level 28 (also called Pie 9)

3. Finish the creation process and wait for download.

## Django Virtual environment
1. Download and install Python 3.9 from [this link](https://www.python.org/downloads/release/python-390/)

2. Open the project backend folder [(backend directory explanation)](#backend-directory)

3. Open a terminal or Windows Command prompt in this directory and run the following commands in order
 ```
 python -m venv venv
 For Linux: source venv/bin/activate
 For Windows: venv\Scripts\activate

 pip install django
 pip install djangorestframework
 ```

4. Follow the instruction from [How to setup local development](#how-to-setup-local-development)


## Maps

To get maps to work, an additional dependency needs to be installed.

1. Open up Android Studio and go to Tools -> SDK Manager -> Android SDK -> SDK Tools
2. Download and install 'Google Play services'
3. Search for the 'local.properties' file using Android Studio search and add this line:

MAPS_API_KEY=AIzaSyDhUGaY6JXS4kY7al7IbpHeIImT-eGlzXc

![Map Setup](/readme-images/map-readme.png)



## Peter's Contribution

### Dashboard (Iteration 1)

The dashboard was created with the vision of something like a newsfeed, where a user can view important information such as the alerts and statistics drawn from all sections of the app.

**User story:** As a user, I want to be able to see alerts from the dashboard, so I can priorituze clients based on urgency

**User story:** As an admin, I want to be able to view various CBR statistics, so I can monitor the CBR team's progress.
* The dashboard is split into sections: alerts, high priority clients, visits, and referrals (for the future).  The Alerts section shows the most recent alert sent by an admin. The High Priority Clients Section shows the top 5 highest risk clients, along with two buttons to view all clients and add clients.
* The Visits section show statistics about visits: total visits made, locations visited, total clients visited.


### Clients List (Iteration 1)
The clients list allows the user to quickly view basic information about all clients. If necessary, the user may select a user in order to view more specific information.

**User story:** As a user, I want to be able to search for one or some of my clients, so I can monitor and record client related activity
* The client list currently shows the profile picture, name, location, and risk rating for each client. The client and then be selected so the user may view additional details and create a visit.

### Creation of New Visits (Iteration 1)

The creation of a new visit can currently be accessed through a button found in the details of a client. The survey is compiled into one scrollable activity. Once submitted, the data is uploaded to the backend.

**User Story:** As a user, I want to be able to record and edit client/visit data, so I can better manage my duties.

The New Visit Survey follows the logic listed:
1. If CBR is not selected as a purpose, then all provision sections are visible.
2. If CBR is selected as a purpose, the provisions are hidden unless the user selects a provision to be visible using the chips.
3. Descriptions of provisions and goal conclusion descriptions are hidden unless the user selects the provision chip or selects the "goal met" radio button.

The flow of the survey is an adaptation of our Piazza post, question #47.

**Unfinished parts:**

1. Implementation of required fields is missing. User must not be able to submit a visit survey unless required fields are filled out.

### Navigation Drawer (Iteration 1)
The navigation bar acts as an index for the app, where the user can quickly jump between different sections such as client list, visit list, dashboard, and client creation.

**Unfinished parts:**
1. A logout/close button within the navigation drawer.

### User Interface Consistency (Iteration 1)

This was an additional task undertook to ensure that the interface has a consistency (such as the same font, same background colour for buttons).

**Questions for the customer:**

1. Overall, what is your impression of how the app looks? Are there any particular sections which especially do not resonate with you?
2. Regarding the colour pallette, we have chosen purple to be the app's main colour. Do you have any other colour scheme in mind?
3. We have tried to include a minimal amount of colours, leaving most of the app to be white. In terms of visuals, would you like the user interface to be more colourful? Or to have more images?
4. Are there any comparable sections in apps that you enivision this app could look like (for example, the profile section of the Facebook app, the lists of songs in the Spotify app)? This could help us to modify the interface to look more as you imagine it to look.

### New Referrals Creation
**User story:** As a user, I want to be able to record and edit client/visit data, so I can better manage my duties

Implemented the user interface, front end logic, and referral uploading. Also ensured that the required fields are filled out.

### Search bars
**User story:** As a user, I want to be able to search for one or some of my clients, so I can monitor and record client related activity

For lists within the app, I implemented the searchview to help the user quickly find the item they are looking for.

### Client Details UI Redo
**User story:** As a user, I want to be able to view all my clients/visits, so I can monitor and record client related activity

Redid the client details UI to be more clean and consistent by removing colours, adding margins, and manipulating layouts.

### Dashboard UI Redo
**User story:** As an admin, I want to be able to view various CBR statistics, so I can monitor the CBR team's progress.

At the request of the customer, redid the UI of the dashboard to be much less cluttered by adding whitespace, removing colours, and redoing the general look.

### Iteration 3
1. Baseline Survey: Implement UI, backend, frontend, and upload to server.
2. General UI: Consistent margins, fonts, sizes, etc
3. Specific UI modifications: Login page, Home page, User creation, Navigation bar
4. Rework: New Referrals into stepper activity, Complete rework of New Visits to stepper activity
5. Add risk levels to client creation
6. Goals: goal history in client details, goals in new visits with ability to create new goal.
7. Onboarding UX via TapTarget
8. General nice-to-haves: Gallery upload, back button alert dialog
9. Misc bug fixes

## Andrew's Contribution

### Creation of New Users (Iteration 1)

**User story:** As an admin, I want to be able to edit all user (CBR worker, clinician) data, so I can perform management duties if input was incorrect or priorities changed

* User creation(new user registration),including making the user creation page in android and uploading the new user info to the backend.

### Alerts Creation, List and Details (Iteration 1)

**User story:** As a user, I want to be able to see alerts from the dashboard, so I can prioritize clients/activities based on urgency

* The alerts Object was created both in the backend and frontend, so the admin/user can broadcast alert messages to the server where other users will retrieve from.
* The alerts include a title, body and date.
* The newest alerts(currently 1) show up in the the dashboard

### Referrals List and Details (Iteration 2)

**User story:** As a user, I want to be able to view all my clients/visits, so I can monitor and record client related activity

* Referral List to hold either referrals of a specific client or all, depending on where the list was accessed from(client details for that client, all for from dashboard/nav bar)
* Referral Details to show all fields related to a certain referral.

### Referrals Editing (Iteration 2)

**User story:** As a user, I want to be able to record and edit client/visit data, so I can better manage my duties

* Referral Editing page for editing and updating a referral's status and other info intended for update (refer to field, outcome descriptions etc.).

### Tag Filtering logic (Iteration 2)

**User story:** As a user, I want to be able to search for one or some of my clients, so I can monitor and record client related activity

* Implemented resuable filtering logic with checkboxes. Currently used for filtering outstanding referral items . If referral status is resolved then it won't show if "Outstanding" is checked.
* It works in conjunction with the search bar that looks for matching substrings, so if either is changed the results refresh.

### Iteration 3
1. User permissions for admin and normal user. Only admin can make user, alerts, view stats.
2. Search/ Filtering with tags for client list: five keywords including location disability gender etc.
3. Offline fucntionality for Referrals, automatically syncs all operations from local to server
4. Search/ Filtering with tags for client list: keywords include provision, purpose etc.
5. Offline fucntionality for Alerts: automatically syncs all operations from local to server
6. Mark as read for Alerts: be able to mark alerts as read
7. Alert badge only counts unread alerts
8. Remove all legacy API calls for Referrals and Alerts
9. Misc bug fixes


## Kevin's Contribution

### Client details activity

*User story:** (#5)
As a user, I want to be able to view all my clients/visits, so I can monitor and record client related activity

* Client details, listing all relevant information about the client

*User story:** (4)
As a user, I want to be able to record and edit client/visit data, so I can better manage my duties

* Button on client details page which routes to edit client form
* Button on visits list page which routes to create visit page
* Edit client form UI which allows a user to edit a client's information on their client details page
* User will be able to access their visits which correspond with the client they are viewing on the client details page by clicking a button
* User will be able to create a new visit through the client details page

**Iteration 2:**

* Updated client details and client edit details page UI
* Revamped visit edit details page to be more consistent with client edit details
* User will be able to edit client information through the client details page.  
* User has the ability to edit visit information by accessing a client's details, navigating to their visit list, selecting a visit and then tapping edit button (pencil) on the visit
* Fixed backend problem with visit model not processing PUT requests properly to edit visit information
* Added additional fields to visit edit details which are more intuitive to be editable by the user


**Iteration 3:**

**User story:** (#5)
As a user, I want to be able to view all my clients/visits, so I can monitor and record client related activity

* Fixed baseline survey multiple submission bug
* Implemented Map activity which uses Google Maps API
* Created custom information windows for Google Maps markers
* Added custom latitude and longitude location coordinate field for Client model
* Map activity displays all the clients that have been created as markers


## Kun Hyung Park(Arthur)'s Contribution

### Creation of New Clients

**User story:**  (#4)
As an admin, I want to be able to create new clients' data, so I can track their progress and keep data as reference

* Client creation ,including making the client creation page in android and uploading the new client info to the backend.

**Iteration 2:**

### Design / Create Home page

**User story:**
   As a admin/user, I want to be directed to a home page after login, so I can have a general overview of the app and choose which task to complete.


### Visit Details Edit

**User story:**
   As a user, I want to be able to edit the visit details, so I can update new informations.

* Improved UI experience by making visit details page show only filled fields, so that the user doesn't see unnecessary blank spaces.
* Linked the visit details and visit details edit page to backend database so it properly stores and updates new data.

**Iteration 3:**

### Implement Goal Model to frontend

**User story:**
   As a admin/user, I want to be able to create and modify goals so that clients can track their progress better.

* Created Goal Class and set up API calls to store / update goal data in the backend.

### Add initial goals to client creation process
**User story:**
   As a admin/user, I want to be able to create initial goals during client creation process.

### Allows users to edit goals in client details page
**User story:**
   As a admin/user, I want to be able to modify goals on client details page.


## Sean's Contribution

### Visit management/details
After a user creats a visits for clients, there needs to be a convenient way for the user to view these visits. If accessing the page from the navigation bar, all visits will be shown. Users can also see a list of visits for specific clients, and then see more details for those visits.

**User Story** (#5)
As a user, I want to be able to view all my clients/visits, So I can monitor and record client related activity

* The items in the visit list include the client's name and number
* Visits can be viewed either by client or in one big list
* The per-client visits page uses nearly all the same logic as the full visits page, but it is passed the clientID and filters by it.
* The visits details page for each visit also shows additional details

**Iteration 2:**

* The visit list now displays the date of the visit
* The visit details page now shows all the desired information with no hardcoding and writes properly to the backend database
* Reorganized the client and visit backend models so that much of the information that new visits were writing to the client model now write to the visit model
* When a health goal, education goal, or social goal is inputted in a visit, it now updates the visit's respective client and shows  this new information on the client details page
* Decoupled the per-client visit list and the general visit list so that they could be contained in one fragment

### Login page
The login page allows users to log in to reach their personalized home screen

**User Story** (#1)
As an admin, I want to be able to edit all user (CBR worker, clinician) data, so I can perform management duties if input was incorrect or priorities changed

**Iteration 3:**

**User story:**
   As a admin/user, I want to be able to create and modify goals so that clients can track their progress better.

* I created the goal model on the back end, which was used in the app in multiple places.

**User story:**
   As a admin/user, I want to see clients' risk scores and sort by these scores so I can prioritize more at-risk clients.

* I designed the new risk algorithm, which uses a log scale with some priority modifiers to capture Izzy's requested prioritization of clients. I then modified the risk labels that the user will see to just be English categories, since the numbers became very high, and would likely have been more confusing for users to explicity see, so the numbers are mostly just used behind the scenes now.

**User story:**
   As a admin/user, I want to be able to use the app offline, so that I can go to areas without internet and not worry about losing functionality.

* I was responsible for giving offline functionality to everything related to goals. This includes viewing goals on the client details, goal history and create visit pages. It also includes creating goals on the create client and create visit pages. Finally, it includes editing goals on the edit client details and create visit pages. Jonathan (who had previously implemented offline functionality for visits) was a very big help with this.


## William Tran's Contribution

### Iteration 1 Creation of New Clients
**User story:** (#4)
As a user, I want to be able to record my client's data to a remote server so I can manage my duties.

*Client models and image root for upload and saving client data.

### Iteration 2 Integrating with Room database
Integrating app with local database to persistently save data onto devices in offline conditions
All these tasks deals with Business logic for utilizing the data under the abstraction of the UI

* Update Client and Visit as entity for Room database inclusion
* Create database access object interface for Client and Visit
* Implement basic CRUD operations with local database using executuors threads
* Create a mock-up of sync logic to be used when making the synchronization architecture for the app

### Iteration 3 Syncing data between Room database and Django database
Make sure each data in local Room database correspond to an entry on Django server database
Mainly deals with the Repository pattern, making a prototype from client model for other type of data

* Create initial sync logic (ended up being changed to Repository Pattern)
* Update Client repo with sync and offline data
* Debug Repository pattern for client database
* Create worker class for client photo upload (also used in referrals)
* Test and review implementations of Repository for other models (group work)


## Vincent's Contribution


### Iteration 1

**User story:**  (#1)
As an admin, I want to be able to edit all user (CBR worker, clinician) data, so I can perform management duties if input was incorrect or priorities changed

* Created the backend info for the user such that we can create or modify users in the backend. This includes a username, password, email, first name, last name

**User story:**  (#4)
As a user, I want to be able to record and edit client/visit data, so I can better manage my duties.

* Modified the create clients page such that it uses fragments rather than activities. This would give some performance gain.

### Iteration 2

For this iteration, I've done some minor changes to the home page, fixed a few bugs, and done some research on the frontend history.

1. Added a navigation drawer to the homepage
2. Fixed a bug where some buttons on homepage was not working
3. Researched some techniques on having history on the frontend (likely be done by iteration 3).

### Iteration 3

For this iteration, I worked on a statistics page, and also added a functionality to export the statistics page as a CSV file.

1. Added a statistics page
2. Added an export to CSV button for the statistics page (ability to export with gmail or google drive)

## Jonathan's Contribution

### Iteration 2

For this iteration I mostly focused on UI enhancement and research on the architecture to support offline functionality of the app. Here are some highlights

1. Improve the Client creation to be more user friendly by adding error checking, and a visual stepper to inform the user where they are on the form.
2. Introduce cached logins so users do not need to login every time the app closes. The users authentication token is cached using SharedPreferences.
3. Introduce a ClientHistoryRecord model to track changes made to the client.
4. Add and generate CBR client ids that are based off of their first and last name.
5. Fix some bugs to production deployment and add a home page to our API where the user can download the APK.
6. Research into architecture to support offline app usage. Will hopefully be setup and usable for iteration 3.

### Iteration 3

For this iteration, I spent majority of my time looking into offline support as it was a tough task to tackle.

- Researched and implemented offline architecture using MVVM design and WorkManager to support offline viewing and operations. I implemented Visits as the first offline fully  supported offline feature. This would be used as a reference to implement the other components.
- Improved build variants such as the `stagingRelease` which uses its own API_URL aside from the one used in development.
- Refactored various components in the app, using best practices, proper life cycle method, and simplifying code.
- Allowed for apks to be easily uploaded from the Django admin.
- Added login functionality offline.
- Added docker scripts that makes managing docker container easier.

# Deployment with Docker

Use docker to deploy

## Prerequisites

`.env.dev` used to store development secrets

```
DEBUG=1 # sets django into development mode
SECRET_KEY=foo
DB_USER=postgres
DB_PASS=postgres
DB_HOST=db
DB_PORT=5432
DB_ENGINE=django.db.backends.postgresql
DB_NAME=postgres
```

`.env.prod` used to store production secrets

```
SECRET_KEY=foo
DB_NAME=postgres
DB_USER=postgres
DB_PASS=postgres
DB_HOST=db
DB_PORT=5432
DB_ENGINE=django.db.backends.postgresql
```

Here's a list of commands to for docker-compose

```
# start production
sudo docker-compose -f docker-compose.prod.yml -p prod up -d --build

# restart production
sudo docker-compose -f docker-compose.prod.yml -p prod restart

# tear down production
sudo docker-compose -f docker-compose.prod.yml -p prod down -v

# ssh into production container
sudo docker-compose -f docker-compose.prod.yml -p prod run web bash

# start dev
sudo docker-compose -f docker-compose.yml -p dev up -d --build

# restart dev
sudo docker-compose -f docker-compose.yml -p dev restart

# tear down dev
sudo docker-compose -f docker-compose.yml -p dev down -v

# ssh into dev container
sudo docker-compose -f docker-compose.yml -p dev run web bash
```

Production will be deployed on port 80

Development will be deployed on port 8001

# How to Navigate Application

Once the app is running, you will be on the login screen. Input the username and password for the superuser you created in the "How to setup Local Development" section of this README here. If you have logged in before on the same device, it may skip past this screen automatically.

<img src="/readme-images/login.png"  width="432" height="888">

After logging in, you will see the homepage. Here, you can click different icons to quickly go to their pages.

<img src="/readme-images/homepage.png"  width="432" height="888">

If you click on the dashboard, you  can view alerts, see high priority clients and visit information, and use the navigation bar to access other pages.  For your case, you should not be able to see any clients or alerts right now because you have not created any.

<img src="/readme-images/dashboard.png"  width="432" height="888">

There are now tutorial messages that get displayed the first time a user uses the app.

<img src="/readme-images/tutorial.png"  width="432" height="888">

To access the navigation bar, click on the three horizontal bars in the top left corner.

<img src="/readme-images/nav_bar.png"  width="432" height="888">

To create a new user, click on "User Creation (Admin Only)" from the navigation bar. Here, you can create a new user that can log in.

<img src="/readme-images/user_creation.png"  width="432" height="888">

To create a new alert, click on "Alert Creation (Admin Only)" from the navigation bar. Here, you can fill in the text boxes to create a new alert.

<img src="/readme-images/alert_creation.png"  width="432" height="888">

Now you can see this alert at the top of the dashboard.

<img src="/readme-images/new_alert_dash.png"  width="432" height="888">

To view all alerts, click "See More" in the alert section at the top of the dashboard.

<img src="/readme-images/alert_list.png"  width="432" height="888">

You can click on an alert from here to view more details about it.

<img src="/readme-images/new_alert_details.png"  width="432" height="888">

You can register a new client by clicking on "New Client" from the home page. This will take you to the new client page. Here, you will go through several screens of questions including some drop down menus before being able to submit the new client.

<img src="/readme-images/create_client_1.png"  width="432" height="888">

To create a visit for a client, go to that client's details page and click on "New Visit". From here, fill out the fields and select options from the drop down menu(s).

<img src="/readme-images/create_visit_1.png"  width="432" height="888">

Certain selections will prompt you with other questions. For example, if you select CBR for the "Purpose of Visit" question, you will be prompted with another "CBR Type" question.

<img src="/readme-images/create_visit_2.png"  width="432" height="888">

To create a referral for a client, go to that client's details page and click on "New Referral". From here, fill out the fields and select options from the drop down menu(s).

<img src="/readme-images/create_referral.png"  width="432" height="888">

To create a referral for a client, go to that client's details page and click on "New Referral". From here, fill out the fields and select options from the drop down menu(s).

<img src="/readme-images/all_referrals.png"  width="432" height="888">

You can toggle this page so that it only show outstanding referrals.

<img src="/readme-images/outstanding_referrals.png"  width="432" height="888">

Click on a referral to see its details.

<img src="/readme-images/referral.png"  width="432" height="888">

You can edit a referral by clicking the edit button. One major thing you might edit is to change a referral status from CREATED to RESOLVED.

<img src="/readme-images/edit_referral.png"  width="432" height="888">

Once a referral is marked RESOLVED, it will no longer show up in the outstanding toggle

<img src="/readme-images/no_outstanding_referrals.png"  width="78" height="517">

To view clients, click on "Client List" from the navigation bar.  You can create clients that will show up here by selecting the NEW CLIENT button from the home page.

<img src="/readme-images/client_list.png"  width="432" height="888">

To make it easier, you can use the search bar

<img src="/readme-images/client_search.png"  width="432" height="888">

From here, you can select a client to see more details about them.

<img src="/readme-images/client_details.png"  width="432" height="888">

You can edit a client by clicking on the edit button on the client details page.

<img src="/readme-images/edit_client.png"  width="432" height="888">

From the client details page, you can see a client's visits by clicking on "See Visits".

<img src="/readme-images/per_client_visits.png"  width="432" height="888">

You can click on any individual visit to see more visit details.

<img src="/readme-images/visit_details.png"  width="432" height="888">

You can also edit a visit by clicking on the edit button.

<img src="/readme-images/edit_visit.png"  width="432" height="888">

You can also view all visits for all clients by clicking on "Visits" on the navigation bar.

<img src="/readme-images/all_visits.png"  width="432" height="888">

You can view the location of all clients through the map, which is accessible from the homepage

<img src="/readme-images/map.png"  width="432" height="888">

You can also create a baseline survey for a client by selecting "BASELINE SURVEY" on the home page and selecting a client

<img src="/readme-images/baseline_survey.png"  width="432" height="888">

Admins can view statistics on the statistics page, accessible through the navigation bar.

<img src="/readme-images/statistics.png"  width="432" height="888">

They then can export the statistics to Google Drive

<img src="/readme-images/export.png"  width="432" height="888">

There, it can be viewed as a CSV file

<img src="/readme-images/statistics_csv.png">