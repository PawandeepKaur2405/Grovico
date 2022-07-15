# Grovico - Grow virtual with connected learning!  

## <img src="https://github.com/TheDudeThatCode/TheDudeThatCode/blob/master/Assets/Developer.gif" width="45px"> Problem Statement :
Technology has revolutionized over a period of time. During this pandemic, there has been a significant increase in demand for online learning platforms. It has been a real 
struggle for both the teachers and the students, to find a platform for organized learning. And technology is seen as the only hope to overcome these hurdles and to continue
learnings even during the toughest times.

## <img src = "https://media1.giphy.com/media/JZ40cnfnN11KycrvMF/giphy.gif?cid=ecf05e47a0n3gi1bfqntqmob8g9aid1oyj2wr3ds3mg700bl&rid=giphy.gif" width = 23px> Proposed Solution :
This App, Grovico is developed with a long vision that can help students and teachers to provide a platform that can manage all the class stuff in a single app. Teachers and students can log in using their roles in the app. Teachers can upload assignments and schedule meetings for individual courses, sections, and semesters. Students can also see their assignments listed in the Assignment section, keep important notes in an organized manner ranging from low to high, and can chat with individuals of the class. A teacher can create a class and send class link from which students can join that specific class. The Android Room Database is used to save class notes and information, while assignments are saved in Google's Firebase Firestore. Graphic Era University is currently using the app, but as time goes on, we will integrate with more universities and improve the functionalities.

## 📸 Screenshots : 
<img width="900" alt="Screenshot 2022-01-11 at 4 44 42 PM" src="https://user-images.githubusercontent.com/77102514/148933235-d97c00e8-def9-4726-82e3-3fdfda26f7b5.png">
<img width="900" alt="Screenshot 2022-01-11 at 4 44 47 PM" src="https://user-images.githubusercontent.com/77102514/148933244-19829e7c-3e87-41d9-afb3-4c9fdea4cd98.png">
<img width="900" alt="Screenshot 2022-01-11 at 4 44 59 PM" src="https://user-images.githubusercontent.com/77102514/148933250-c8b8632b-2c5c-4cb8-9e52-10cb9b4e34f7.png">
<img width="900" alt="Screenshot 2022-01-11 at 4 45 02 PM" src="https://user-images.githubusercontent.com/77102514/148933259-4e91056a-48e7-4744-bfb5-9c67b945f239.png">



## 👩🏻‍💻 Functionalities and Concepts used :
```Constraint Layout``` - The app uses constraint layout for flexible positioning and sizing of widgets.   

```Frame Layout``` - It is used to ensure that we always have the space available to accommodate the largest fragment layout.  

```Recycler View``` - The RecyclerView is a widget that is a more flexible and advanced version of GridView and ListView. It is a container for displaying large datasets which can 
be scrolled efficiently by maintaining a limited number of views.   

```Card View``` - It can be used to display any sort of data by providing a rounded corner layout along with a specific elevation. The best part about CardView is that it extends 
Frame layout and can be displayed on all platforms of Android. It can also be used for creating items in a listview or inside Recycler View.

```Jetpack Navigation``` - The app uses Jetpack Navigation to navigate between the screens with certain settings, and controllers with pop functionality to keep the proper sequence of
the navigation stack. It uses safeArgs to send data from one activity to another.   

```Live Data, View Model and, Coroutines``` - Live Data is used to observe changes in Notes maintained by the user. ViewModel helps to retain and save notes in the Room database.
All the information related to class, notes, and assignments is fetched and written safely using coroutines.   

```Firebase Authentication and Firestore``` - Firebase Authentication is used to authenticate the user with Google. Firestore is used to store user details, assignments and provide
data whenever needed.   

## 🔮 Application Link & Future Scope : 

The app is currently in the ```Alpha testing``` phase in Graphic Era University with a limited no. of users. You can access our app here : https://drive.google.com/drive/folders/1n2u5Umqvd6qStjrXvugRuhw0kQ3aCjKX?usp=sharing
(USE THE APP ONLY IN LIGHT MODE). 

Once the app is fully tested, we will be expanding to other neighboring universities to improve their online class systems. We are planning to introduce the following features:   
1. Video Meeting Functionality (which is currently removed due to large app size) for live classes.
2. Feature to submit the assignments in the app itself.
3. To expand it to other mentors and universities and build up a sort of learning community.

# Thank for Visiting, See you next time!<img src="https://media.giphy.com/media/mGcNjsfWAjY5AEZNw6/giphy.gif" width="50">
